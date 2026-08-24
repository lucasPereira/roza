# Modern Róża parsing violations

This document lists every **structured parsing violation** (`TestCodeViolation`) that modern Róża can emit when it parses a Java test source file with `JunitTestClassParser`. Most violations are produced by `JavaUnsupportedFeatureValidator` before the parser builds `TestClass` models. Files that JavaParser cannot parse produce a class-level parse-error violation instead of aborting the remaining files.

**Maintenance:** Whenever you add, remove, or change a violation in `JavaUnsupportedFeatureValidator` or in `JunitTestClassParser` parse-failure handling, update this file in the same change so the catalog stays accurate.

Violations use `ViolationScope.TEST_CLASS` (class-level) or `ViolationScope.TEST_METHOD` (scoped to a `@Test` / unsupported test method). Body checks on `@Before` / `@BeforeEach` and on lifecycle or helper methods attach snippets at **class** scope (no method name on the violation). Compilation units with no `@Test` method are helper classes and do not emit these subset violations. Unparseable files have no extracted `TestClass`; the violation `testClassName` is the loaded file source path.

---

## File and imports

| Description (prefix) | Example |
| --- | --- |
| `Parse error: (line 1, col 32) Parse error. Found "is", expected one of ...` | Source that JavaParser cannot parse even at language level JAVA_17. |
| `Multiple top-level classes in the same file` | Two public classes in one `.java` file. |
| `Wildcard import: import java.util.*;` | `import java.util.*;` |

```java
class Broken { @Test public void t() { this is not java } }
```

```java
class First { @Test public void t() { assertTrue(true); } }
class Second { }
```

```java
import java.util.*;
class Example { @Test public void t() { assertTrue(true); } }
```

---

## Class shape and declarations

| Description (prefix) | Example |
| --- | --- |
| `Nested class: Inner` | Non-static inner class inside the test class. |
| `Nested record: Pair` | Nested Java 17 `record` inside the test class. The nested type is still extracted onto `TestClass` so Ignore violations can emit it. |
| `Test class inheritance: Example` | `class Example extends Base { ... }` |
| `Abstract test class: Example` | `abstract class Example { ... }` |
| `Generic test class: Example` | `class Example<T> { ... }` |
| `Class annotation: @RunWith(...)` | Any annotation on the test class (only plain class allowed in subset). |
| `Class initializer in: Example` | Static or instance initializer block. |
| `Explicit constructor: Example` | User-declared constructor. |
| `Enum declaration: State` | `enum` inside or alongside the supported class pattern. |

```java
class Outer { class Inner { } @Test public void t() { assertTrue(true); } }
```

```java
class Example { private record Pair(int a, int b) {} @Test public void t() { assertTrue(true); } }
```

```java
class Example extends Object { @Test public void t() { assertTrue(true); } }
```

```java
@RunWith(org.junit.runners.JUnit4.class)
class Example { @Test public void t() { assertTrue(true); } }
```

---

## Fields

| Description (prefix) | Example |
| --- | --- |
| `Static field: x` (or `x, y`; **snippet** = full `FieldDeclaration` line) | `static int x;` |
| `Field annotation: @Inject` | Any field annotation. |
| `Field initialization: x` (**snippet** = full `FieldDeclaration` line) | Field declared with `= value`. |

```java
class Example { static int x; @Test public void t() { assertTrue(true); } }
```

```java
class Example { int x = 1; @Test public void t() { assertTrue(true); } }
```

---

## Unsupported or disallowed methods (classification)

| Description (prefix) | Example |
| --- | --- |
| `Lifecycle method: tearDown` | `@After`, `@AfterEach`, `@BeforeClass`, `@AfterClass`, `@BeforeAll`, `@AfterAll`, … |
| `Test method annotation: ParameterizedTest` | `@ParameterizedTest`, `@Theory`, `@TestFactory`, `@TestTemplate`, `@RepeatedTest` (reported in addition to other rules). |
| `Helper method: util` | Any method that is not `@Test`, not a supported fixture, and not one of the unsupported test annotations above. |

```java
class Example { @After public void tearDown() {} @Test public void t() { assertTrue(true); } }
```

```java
class Example { @ParameterizedTest void t() { assertTrue(true); } }
```

```java
class Example { void util() {} @Test public void t() { assertTrue(true); } }
```

---

## Fixture rules

| Description (prefix) | Example |
| --- | --- |
| `Static fixture method: setup` | `@Before` / `@BeforeEach` declared `static`. |
| `Fixture method with parameters: setup` | Fixture with parameters. |
| `Fixture method return type: setup` | Fixture not `void`. |
| `Multiple @Before fixtures` | More than one `@Before` and/or `@BeforeEach` combined. |

```java
class Example { @BeforeEach static void setup() {} @Test void t() { assertTrue(true); } }
```

```java
class Example { @Before public void a() {} @BeforeEach public void b() {} @Test public void t() { assertTrue(true); } }
```

---

## Annotations on supported methods

| Description (prefix) | Example |
| --- | --- |
| `Repeated annotation: @Tag("a")` | Same annotation name twice on one method. |
| `Method annotation: @Disabled` | On `@Test` (or unsupported test method): annotation other than `Test` / supported extras path. |
| `Method annotation: @Rule` | On fixture: disallowed annotation. |
| `@Test attributes: @Test(timeout = 1000)` | `@Test` with parameters. |
| `Fixture annotation attributes: @BeforeEach(Timeout.class)` | `@Before` / `@BeforeEach` with attributes. |

```java
class Example { @Tag("a") @Tag("b") @Test public void t() { assertTrue(true); } }
```

```java
class Example { @Disabled @Test public void t() { assertTrue(true); } }
```

```java
class Example { @Test(timeout = 1000) public void t() { assertTrue(true); } }
```

---

## `@Test` method signature

| Description (prefix) | Example |
| --- | --- |
| `Test method with parameters: t` | `@Test void t(int x)` |
| `Test method return type: t` | Non-`void` return type. |
| `Private test method: t` | `private @Test` |
| `Static test method: t` | `static @Test` |
| `Test method without body: t` | Missing body / abstract. |

```java
class Example { @Test public void t(int x) { assertTrue(true); } }
```

---

## Statements and expressions inside method bodies

Checked inside bodies of `@Test`, unsupported test methods, fixtures, helpers, and lifecycle methods. Lambdas and method references **inside** a supported assertion call (e.g. `assertThrows`) are allowed.

| Description | Example |
| --- | --- |
| `Lambda expression` | `Runnable r = () -> {};` outside an assertion. |
| `Method reference` | `Runnable r = this::run;` outside an assertion. |
| `Anonymous class` | `new Runnable() { public void run() {} }` |
| `Local class: Local` | Class declared inside a method. |
| `Local record: LocalRecord` | Java 17 `record` declared inside a method. The statement stays in the extracted test body. |
| `For loop` | `for (int i = 0; i < n; i++) { ... }` |
| `For-each loop` | `for (String s : list) { ... }` |
| `While loop` | `while (cond) { ... }` |
| `Do-while loop` | `do { ... } while (cond);` |
| `Try statement` | `try` / `try-with-resources` |
| `Switch statement` | `switch (x) { ... }` |
| `Synchronized block` | `synchronized (this) { ... }` |
| `Labeled statement` | `label: stmt;` |
| `Break statement` | `break;` |
| `Continue statement` | `continue;` |
| `Explicit throw statement` | `throw new RuntimeException();` |
| `Explicit this expression` | `this.toString()` |
| `Explicit super expression` | `super.toString()` |

```java
class Example { @Test public void t() { Runnable r = () -> {}; assertTrue(true); } }
```

```java
class Example { @Test public void t() { for (int i = 0; i < 1; i++) { assertTrue(true); } } }
```

---

## Summary

- **Single source of truth in code:** `br.ufsc.ine.leb.roza.core.modern.parsing.JavaUnsupportedFeatureValidator`
- **Tests that pin many of these cases:** `br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParserTest#unsupportedFeatures`
