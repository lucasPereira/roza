# Modern Róża parsing violations

This document lists every **structured parsing violation** (`TestCodeViolation`) that modern Róża can emit when it parses a Java test source file with `JunitTestClassParser`. Violations are produced by `JavaUnsupportedFeatureValidator` before the parser builds `TestClass` models.

**Maintenance:** Whenever you add, remove, or change a violation in `JavaUnsupportedFeatureValidator`, update this file in the same change so the catalog stays accurate.

Violations use `ViolationScope.TEST_CLASS` (class-level) or `ViolationScope.TEST_METHOD` (scoped to a `@Test` / unsupported test method). Body checks on `@Before` / `@BeforeEach` and on lifecycle or helper methods attach snippets at **class** scope (no method name on the violation).

---

## File and imports

| Description (prefix) | Example |
| --- | --- |
| `Unsupported multiple top-level classes in the same file` | Two public classes in one `.java` file. |
| `Unsupported wildcard import: import java.util.*;` | `import java.util.*;` |

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
| `Unsupported nested class: Inner` | Non-static inner class inside the test class. |
| `Unsupported test class inheritance: Example` | `class Example extends Base { ... }` |
| `Unsupported abstract test class: Example` | `abstract class Example { ... }` |
| `Unsupported generic test class: Example` | `class Example<T> { ... }` |
| `Unsupported class annotation: @RunWith(...)` | Any annotation on the test class (only plain class allowed in subset). |
| `Unsupported class initializer in: Example` | Static or instance initializer block. |
| `Unsupported explicit constructor: Example` | User-declared constructor. |
| `Unsupported enum declaration: State` | `enum` inside or alongside the supported class pattern. |

```java
class Outer { class Inner { } @Test public void t() { assertTrue(true); } }
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
| `Unsupported static field: x` (or `x, y`; **snippet** = full `FieldDeclaration` line) | `static int x;` |
| `Unsupported field annotation: @Inject` | Any field annotation. |
| `Unsupported field initialization: x` (**snippet** = full `FieldDeclaration` line) | Field declared with `= value`. |

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
| `Unsupported lifecycle method: tearDown` | `@After`, `@AfterEach`, `@BeforeClass`, `@AfterClass`, `@BeforeAll`, `@AfterAll`, … |
| `Unsupported test method annotation: ParameterizedTest` | `@ParameterizedTest`, `@Theory`, `@TestFactory`, `@TestTemplate`, `@RepeatedTest` (reported in addition to other rules). |
| `Unsupported helper method: util` | Any method that is not `@Test`, not a supported fixture, and not one of the unsupported test annotations above. |

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
| `Unsupported static fixture method: setup` | `@Before` / `@BeforeEach` declared `static`. |
| `Unsupported fixture method with parameters: setup` | Fixture with parameters. |
| `Unsupported fixture method return type: setup` | Fixture not `void`. |
| `Unsupported multiple @Before fixtures` | More than one `@Before` and/or `@BeforeEach` combined. |

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
| `Unsupported repeated annotation: @Tag("a")` | Same annotation name twice on one method. |
| `Unsupported method annotation: @Disabled` | On `@Test` (or unsupported test method): annotation other than `Test` / supported extras path. |
| `Unsupported method annotation: @Rule` | On fixture: disallowed annotation. |
| `Unsupported @Test attributes: @Test(timeout = 1000)` | `@Test` with parameters. |
| `Unsupported fixture annotation attributes: @BeforeEach(Timeout.class)` | `@Before` / `@BeforeEach` with attributes. |

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
| `Unsupported test method with parameters: t` | `@Test void t(int x)` |
| `Unsupported test method return type: t` | Non-`void` return type. |
| `Unsupported private test method: t` | `private @Test` |
| `Unsupported static test method: t` | `static @Test` |
| `Unsupported test method without body: t` | Missing body / abstract. |

```java
class Example { @Test public void t(int x) { assertTrue(true); } }
```

---

## Statements and expressions inside method bodies

Checked inside bodies of `@Test`, unsupported test methods, fixtures, helpers, and lifecycle methods. Lambdas and method references **inside** a supported assertion call (e.g. `assertThrows`) are allowed.

| Description | Example |
| --- | --- |
| `Unsupported lambda expression` | `Runnable r = () -> {};` outside an assertion. |
| `Unsupported method reference` | `Runnable r = this::run;` outside an assertion. |
| `Unsupported anonymous class` | `new Runnable() { public void run() {} }` |
| `Unsupported thread creation` | `new Thread(...)` |
| `Unsupported network object creation: Socket` / `URL` | `new Socket(...)`, `new URL(...)` |
| `Unsupported local class: Local` | Class declared inside a method. |
| `Unsupported for loop` | `for (int i = 0; i < n; i++) { ... }` |
| `Unsupported for-each loop` | `for (String s : list) { ... }` |
| `Unsupported while loop` | `while (cond) { ... }` |
| `Unsupported do-while loop` | `do { ... } while (cond);` |
| `Unsupported try statement` | `try` / `try-with-resources` |
| `Unsupported switch statement` | `switch (x) { ... }` |
| `Unsupported synchronized block` | `synchronized (this) { ... }` |
| `Unsupported labeled statement` | `label: stmt;` |
| `Unsupported break statement` | `break;` |
| `Unsupported continue statement` | `continue;` |
| `Unsupported explicit throw statement` | `throw new RuntimeException();` |
| `Unsupported explicit this expression` | `this.toString()` |
| `Unsupported explicit super expression` | `super.toString()` |

```java
class Example { @Test public void t() { Runnable r = () -> {}; assertTrue(true); } }
```

```java
class Example { @Test public void t() { for (int i = 0; i < 1; i++) { assertTrue(true); } } }
```

---

## Disallowed method calls (by name / receiver)

Messages are prefixed as below; the full description includes the call expression.

| Description (prefix) | Example |
| --- | --- |
| `Unsupported reflection call:` | `Class.forName("x")`, `type.getMethod("m")`, `getDeclaredField`, … |
| `Unsupported side-effect or time-related call:` | Calls with receiver `Files`, `Paths`, `DriverManager`, `URL`, `Socket`, `Clock`, `Instant`, `LocalDate`, `LocalDateTime`, … |
| `Unsupported system call:` | `System.currentTimeMillis()`, `nanoTime`, `exit`, `getProperty` |
| `Unsupported async call:` | `CompletableFuture...`, `Executors...` |

```java
class Example { @Test public void t() { Class.forName("java.lang.String"); } }
```

```java
class Example { @Test public void t() { java.nio.file.Files.readAllBytes(null); } }
```

```java
class Example { @Test public void t() { System.currentTimeMillis(); } }
```

```java
class Example { @Test public void t() { CompletableFuture.runAsync(() -> {}); } }
```

---

## Summary

- **Single source of truth in code:** `br.ufsc.ine.leb.roza.core.modern.parsing.JavaUnsupportedFeatureValidator`
- **Tests that pin many of these cases:** `br.ufsc.ine.leb.roza.core.modern.parsing.JunitTestClassParserTest#unsupportedFeatures`
