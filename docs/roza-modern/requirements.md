# Roza Modern Requirements

This document stores requirements and acceptance criteria for modern Roza in `br.ufsc.ine.leb.roza.core.modern`.

## Scope

Modern Roza is an extensible pipeline for automatic refactoring of test code. It loads raw code files that may contain tests, parses those files to identify test classes, analyzes/refactors the identified tests, and produces refactored tests as output.

## Functional Requirements

### REQ-001: Automated Test Refactoring Pipeline

Modern Roza must provide a pipeline for automatic test-code refactoring.

Acceptance criteria:

- AC-001: Given raw code files that may contain tests, modern Roza can execute a refactoring pipeline that identifies and refactors tests.
- AC-002: The pipeline output is refactored test code.
- AC-003: The pipeline model does not assume a single refactoring strategy.

### REQ-002: Pipeline Stages as Interfaces

Each pipeline stage must be represented by an interface for that stage.

Acceptance criteria:

- AC-004: Each stage has an explicit interface in `core.modern`.
- AC-005: Pipeline orchestration depends on stage interfaces, not concrete implementations.
- AC-006: A stage can have multiple implementations.

### REQ-003: Interchangeable Stage Implementations

Implementations of different stages must be combinable independently.

Acceptance criteria:

- AC-007: Implementation 1 of stage 1 can be used with implementation 1 of stage 2.
- AC-008: Implementation 1 of stage 1 can be used with implementation 2 of stage 2.
- AC-009: Adding a new implementation of one stage does not require changing implementations of other stages.

### REQ-004: Stage Independence

Pipeline stages must not communicate directly with each other.

Acceptance criteria:

- AC-010: A stage consumes only the input contract defined for that stage.
- AC-011: A stage produces only the output contract defined for that stage.
- AC-012: A stage implementation does not call another stage implementation directly.
- AC-013: Integration between stages happens through pipeline contracts and orchestration.

### REQ-005: Broad Extensibility

Modern Roza must be extensible enough to support different programming languages, test frameworks, refactoring types, and analysis strategies.

Acceptance criteria:

- AC-014: Core abstractions do not encode a specific programming language as a mandatory assumption.
- AC-015: Core abstractions do not encode a specific test framework as a mandatory assumption.
- AC-016: Core abstractions do not encode a fixed set of refactoring types.
- AC-017: New language/framework/refactoring support can be added through new implementations or extension points.

### REQ-006: Raw Code File Loading Input

Modern Roza must load raw code files as its initial input.

Acceptance criteria:

- AC-018: The input model can represent raw code files to be loaded.
- AC-019: The input model leaves room for metadata needed by language/framework-specific implementations.

### REQ-007: Loading Stage

The first stage of the modern Roza pipeline must be the loading stage, responsible for loading raw code files into memory.

Acceptance criteria:

- AC-020: The loading stage is represented by one interface named `CodeFileLoader`, shared by all code-loading implementations.
- AC-021: The loading interface has a single method named `load`.
- AC-022: Concrete loading implementations receive their required loading parameters through their constructors.
- AC-023: Modern Roza can support different loading implementations through the same loading interface.
- AC-024: Loading implementations may include recursive filesystem loading, Java-extension filesystem loading, parameterized-extension filesystem loading, Git-based loading, and other future loading strategies.
- AC-025: The `load` method loads raw code files rather than parsed modern Roza test models.
- AC-026: The loading stage does not guarantee that each loaded code file contains a test class.
- AC-032: The `load` method returns `LoadedCodeFiles`.
- AC-033: `LoadedCodeFiles` contains `CodeFile` instances.
- AC-034: `CodeFile` must not define attributes beyond those required by confirmed requirements.
- AC-076: `LoadedCodeFiles` exposes the loaded code files through `codeFiles()`.
- AC-077: `CodeFile` exposes the raw textual content of the loaded code file through `content()`.
- AC-174: `CodeFile` exposes a textual source identity for the loaded code file through `source()`, so manual UI flows can identify loaded files.
- AC-111: The loading contract belongs in the `br.ufsc.ine.leb.roza.core.modern.loading` package.
- AC-118: `LoadedCodeFiles` and `CodeFile` are concrete loading data classes, not extension interfaces.
- AC-112: A filesystem `CodeFileLoader` implementation includes files from the folder provided to it.
- AC-113: A non-recursive filesystem `CodeFileLoader` implementation does not include files from child folders of the provided folder.
- AC-114: A recursive filesystem `CodeFileLoader` implementation includes files from child folders of the provided folder.
- AC-115: A filesystem `CodeFileLoader` implementation with a non-empty extension list does not include files whose extension is not in the list.
- AC-116: A filesystem `CodeFileLoader` implementation with a non-empty extension list includes only files whose extension belongs to the list.
- AC-117: A filesystem `CodeFileLoader` implementation with an empty extension list includes files regardless of extension.

### REQ-008: Parsing Stage

The second stage of the modern Roza pipeline must be the parsing stage, responsible for reading loaded raw code files and building ASTs for identified test classes.

Acceptance criteria:

- AC-027: The parsing stage consumes the raw code files loaded by the loading stage.
- AC-028: The parsing stage determines which loaded code files contain test classes.
- AC-029: The parsing stage identifies test classes from the loaded raw code files.
- AC-030: The parsing stage produces an AST for each identified test class.
- AC-031: The loading stage does not build ASTs or identify test classes.
- AC-039: The parsing stage is represented by one interface named `TestClassParser`.
- AC-040: The parsing interface has a method named `parse`.
- AC-041: `TestClassParser.parse` returns `ParsedTestClasses`.
- AC-042: `ParsedTestClasses` contains a list of `TestClass` instances.
- AC-079: `ParsedTestClasses` exposes the parsed test classes through `testClasses()`.
- AC-119: The first parsing implementation is Java-first rather than language-universal.
- AC-120: Concrete Java parsers may use JavaParser internally, but JavaParser types must not appear in the public modern Roza parsing contract.
- AC-150: The first concrete parser implementation is named `JunitTestClassParser` because it supports a conservative JUnit subset on Java source code, not every Java test framework.
- AC-121: Unsupported parsing features are handled by `UnsupportedFeaturePolicy`.
- AC-122: Unsupported feature validation runs in a separate validator before extracting modern Roza domain models.
- AC-123: The parser identifies unsupported-subset violations before downstream stages refactor tests.
- AC-124: Unsupported-subset violations are reported as structured parsing output instead of being silently accepted.
- AC-180: `ParsedTestClasses` exposes unsupported-subset violations through `violations()`.
- AC-181: Each unsupported-subset violation records whether it applies to a test class or a test method.
- AC-182: Each unsupported-subset violation records a human-readable description suitable for experiment reports.
- AC-199: Each unsupported-subset violation can expose a code snippet for the construct that caused it.
- AC-183: Supported classes in the first refactoring-safe subset are plain JUnit classes: one top-level concrete non-generic class, no inheritance, and no explicit constructor.
- AC-184: Supported fields in the first refactoring-safe subset are non-static instance fields without direct field initialization.
- AC-185: Multiple variables in one field declaration remain supported because parsing separates them into individual `Field` instances.
- AC-186: Supported methods in the first refactoring-safe subset are `@Test` methods and at most one simple `@Before` or `@BeforeEach` fixture.
- AC-268: A class that contains both `@Before` and `@BeforeEach`, or multiple setup fixtures of either kind, is outside the first refactoring-safe subset and is reported as a class-level violation.
- AC-187: Any annotation outside `@Test`, `@Before`, and `@BeforeEach` is unsupported in the first refactoring-safe subset.
- AC-188: Helper methods are unsupported in the first refactoring-safe subset.
- AC-189: Tear down and lifecycle methods other than the supported before fixture are unsupported in the first refactoring-safe subset.
- AC-190: Tests without a detectable assertion are supported; metrics that stop at assertions use the whole test body when no detectable assertion exists.
- AC-191: A detectable assertion is an assertion recognized in the current statement, including assertion calls with lambda or method-reference arguments.

### REQ-009: Java-First TestClass Domain Model

`TestClass` must be the root domain abstraction for each identified Java test class. It must be Java-first and must not expose parser-library types such as JavaParser `CompilationUnit`.

Acceptance criteria:

- AC-043: `TestClass` is the parent domain abstraction for the supported parts of an identified Java test class.
- AC-044: Consumers of `TestClass` can work with it without depending on JavaParser types.
- AC-045: Only concrete `TestClassParser` implementations know how to build `TestClass` instances from loaded raw code files.
- AC-046: The first `TestClass` model may expose Java test-code domain concepts instead of trying to support non-Java languages prematurely.
- AC-047: The `TestClass` abstraction must be powerful enough to support later Java decomposition, measurement, refactoring, and writing stages.
- AC-078: `TestClass` exposes only the minimum content required by confirmed requirements.
- AC-125: `TestClass` exposes its name.
- AC-126: `TestClass` exposes parsed fields because decomposition needs to transform class attributes into local variables.
- AC-127: `TestClass` exposes supported fixture methods.
- AC-128: `TestClass` exposes supported helper methods.
- AC-129: `TestClass` exposes supported test methods.
- AC-130: Parsed fields preserve type, name, supported modifiers, and optional initialization.
- AC-131: Parsed code blocks expose top-level `CodeStatement` instances.
- AC-132: `CodeStatement` exposes original text, normalized text, and whether the statement is an assertion.
- AC-133: The first parser treats control-flow blocks such as `for`, `while`, and `if` as top-level statements rather than flattening their internals.
- AC-134: Static members, nested classes, unsupported lifecycle features, parameterized tests, inheritance, wildcard imports, and other listed unsupported features are rejected or skipped according to `UnsupportedFeaturePolicy`.
- AC-135: Modern Roza starts conservative: unsupported features should be removed from the unsupported list only when real support is implemented.
- AC-136: JUnit 4 `@After` and JUnit 5 `@AfterEach` are unsupported in the first parser slice and are handled according to `UnsupportedFeaturePolicy`.
- AC-137: Parsed field types preserve complex Java type text needed by decomposition, including generics, nested generics, wildcard generics, qualified types, generic arrays, arrays, and multidimensional arrays.
- AC-151: `JunitTestClassParser` marks assertions using an explicit supported assertion method list covering JUnit 4, current JUnit Jupiter assertions, and Hamcrest `assertThat`, for unqualified calls and for calls on typical receivers (`Assert`, `Assertions`, `MatcherAssert`, and the usual fully qualified type names), not by checking whether a method name starts with `assert`.
- AC-269: `TestClass` exposes import declarations from the original source class.
- AC-270: `TestClass` exposes the setup annotation that should be used if later refactoring needs to generate implicit setup.
- AC-271: If a supported setup fixture exists, the `TestClass` setup annotation comes from that fixture.
- AC-272: If no supported setup fixture exists, the parser infers the `TestClass` setup annotation from the class's supported `@Test` annotation usage: JUnit 5 uses `@BeforeEach`; otherwise JUnit 4 uses `@Before`.

### REQ-010: Decomposition Stage

The third stage of the modern Roza pipeline must be the decomposition stage, responsible for separating each test from its original test class while carrying the code required by that test.

Acceptance criteria:

- AC-048: The decomposition stage consumes parsed test classes.
- AC-049: The decomposition stage extracts each test from its source `TestClass`.
- AC-050: The decomposition stage produces a new AST for each extracted test.
- AC-051: Each produced test AST contains the original test body plus the code required by that test.
- AC-052: If a source test class has an implicit setup and two tests, decomposition produces one AST per test, and each produced AST includes the implicit setup code plus that test's original body.
- AC-053: The decomposition stage separates tests from their class of origin.
- AC-080: The decomposition stage is represented by one interface named `TestCaseDecomposer`.
- AC-081: The decomposition interface has a method named `decompose`.
- AC-082: Each decomposed test is represented by `TestCase`, because after decomposition it is no longer exactly a method inside a test class.
- AC-083: `TestCase` exposes the minimum content needed by the current pipeline slice through `name()`, `body()`, `annotations()`, and `sourceTestClass()`.
- AC-084: `TestCase` must not expose `id()` unless future confirmed requirements make it necessary.
- AC-085: `TestCaseDecomposer.decompose` returns `DecomposedTestCases`.
- AC-086: `DecomposedTestCases` exposes decomposed test cases through `testCases()`.
- AC-140: `TestCase` preserves the original parsed test method name so refactoring can use it later, even when names are duplicated.
- AC-141: `TestCase` preserves a reference to its source `TestClass` so refactoring can reuse original imports and setup-annotation decisions.
- AC-273: `TestCase` preserves the original parsed test method annotations so generated test methods can keep their original JUnit style.
- AC-144: The default decomposition implementation preserves assertions in the decomposed `TestCase` body.
- AC-145: The default decomposition implementation orders the decomposed body as field declarations that are not initialized in supported `@Before` fixtures, supported `@Before` statements, then original test body statements.
- AC-215: When a supported `@Before` fixture assigns to a parsed field, the default decomposition implementation turns that assignment into a local variable declaration with initialization at the assignment position.
- AC-192: The default decomposition implementation does not decompose tests that belong to classes with class-level violations.
- AC-193: The default decomposition implementation does not decompose test methods with method-level violations.
- AC-194: Downstream measurement and refactoring consume only decomposed tests, so tests discarded because of parsing violations are excluded from those stages by construction.

### REQ-011: Measurement Stage

The fourth stage of the modern Roza pipeline must be the measurement stage, responsible for applying a similarity metric to each pair of decomposed tests.

Acceptance criteria:

- AC-054: The measurement stage consumes decomposed tests.
- AC-055: The measurement stage applies a similarity metric to each pair of tests.
- AC-056: Similarity metrics may have different objectives.
- AC-057: Identifying duplicated tests or duplicated test code is the most common objective for a similarity metric.
- AC-058: The measurement stage produces a similarity matrix.
- AC-059: The similarity matrix indicates the similarity degree for each pair of tests according to the applied metric.
- AC-074: The first similarity matrix is indexed internally by source and target test case positions from the decomposed test case list.
- AC-087: The measurement stage is represented by one interface named `TestCaseSimilarityMeasurer`.
- AC-088: The measurement interface has a method named `measure`.
- AC-089: `TestCaseSimilarityMeasurer.measure` receives `DecomposedTestCases`.
- AC-090: `TestCaseSimilarityMeasurer.measure` returns `TestCaseSimilarityMatrix`.
- AC-142: Measurement can use a projection of each `TestCase` rather than measuring every statement in the decomposed body.
- AC-143: LCCSS measurement must stop its measured projection at the first assertion statement; it must not continue measuring statements that appear after the first assertion.
- AC-146: LCCSS compares the projected statement lists by counting the common contiguous prefix from the start of both lists.
- AC-147: LCCSS score is `(2 * commonPrefixSize) / (sourceProjectionSize + targetProjectionSize)`.
- AC-148: LCCSS score is `0.0` when both projected statement lists are empty for distinct test cases.
- AC-149: LCCSS measurement must use `CodeStatement.isAssertion()` to find the first assertion; it must not infer assertions from statement text during measurement.
- AC-218: LCS measurement uses the same pre-assertion projection as LCCSS, stopping at the first `CodeStatement.isAssertion()` statement.
- AC-219: LCS compares the projected statement lists by counting the longest common subsequence while preserving statement order.
- AC-220: LCS score is `(2 * commonSubsequenceSize) / (sourceProjectionSize + targetProjectionSize)`.
- AC-221: LCS score is `0.0` when both projected statement lists are empty for distinct test cases.
- AC-222: Deckard measurement exposes `MIN_TOKENS`, `STRIDE`, and `SIMILARITY` configuration values with defaults `1`, `0`, and `1.0`.
- AC-223: Deckard configuration rejects non-positive `MIN_TOKENS`, negative `STRIDE`, and `SIMILARITY` values outside `0.0` to `1.0`.
- AC-224: Deckard measurement uses the same pre-assertion projection as LCCSS and LCS, stopping at the first `CodeStatement.isAssertion()` statement.
- AC-225: Deckard measurement materializes each projected test case as an isolated Java source file and invokes the existing Deckard external-tool script.
- AC-226: Deckard clone-report parsing pairs entries in the same cluster only when `NODE_KIND`, `TBID`, and `TEID` match, avoiding the legacy Cartesian interpretation of every cluster entry.
- AC-227: Deckard score is asymmetric: merged covered source projected lines divided by the source projected line count.
- AC-228: Deckard score calculation ignores wrapper lines introduced only for Deckard materialization.
- AC-232: JPlag measurement exposes a `sensitivity` configuration value with default `1`.
- AC-233: JPlag configuration rejects non-positive `sensitivity` values.
- AC-234: JPlag measurement uses the same pre-assertion projection as LCCSS, LCS, and Deckard, stopping at the first `CodeStatement.isAssertion()` statement.
- AC-235: JPlag measurement materializes each projected test case as an isolated Java source file and invokes the existing JPlag external-tool jar.
- AC-236: JPlag report parsing reads `match[0-9]+-top.html` reports from the JPlag results directory.
- AC-237: JPlag report parsing extracts source and target materialized file names plus their directional percentages from the first report table header.
- AC-238: JPlag scores are the parsed directional percentages converted to `double` values between `0.0` and `1.0`.
- AC-242: Simian measurement exposes a `threshold` configuration value with default `6`.
- AC-243: Simian configuration rejects `threshold` values smaller than `2`.
- AC-244: Simian measurement uses the same pre-assertion projection as LCCSS, LCS, Deckard, and JPlag, stopping at the first `CodeStatement.isAssertion()` statement.
- AC-245: Simian measurement materializes each projected test case as an isolated Java source file and invokes the existing Simian external-tool jar.
- AC-246: Simian report parsing reads the XML report emitted by Simian, ignoring the textual preamble before the XML declaration.
- AC-247: Simian report parsing converts `<block>` entries inside each clone `<set>` into directed clone fragments between materialized files.
- AC-248: Simian score is asymmetric: merged covered source projected lines divided by the source projected line count.
- AC-152: The first `TestCaseSimilarityMatrix` constructor receives only the ordered list of `TestCase` instances.
- AC-153: The first `TestCaseSimilarityMatrix` stores similarities as a dense directed matrix and must not assume similarity metrics are symmetric.
- AC-154: The first `TestCaseSimilarityMatrix` initializes every similarity with `0.0` except the diagonal, which starts with `1.0`.
- AC-155: The first `TestCaseSimilarityMatrix` exposes the write operation needed by measurers, `setSimilarity(int sourceIndex, int targetIndex, double similarity)`, with package-level visibility.
- AC-204: `TestCaseSimilarityMatrix` exposes public read operations for matrix size, test case by index, and similarity by source/target indexes.

### REQ-012: Clustering Stage

The fifth stage of the modern Roza pipeline must be the clustering stage, responsible for grouping the tests that are most similar to each other according to the similarity matrix.

Acceptance criteria:

- AC-060: The clustering stage consumes the similarity matrix produced by the measurement stage.
- AC-061: The clustering stage groups tests according to the similarities represented in the similarity matrix.
- AC-062: The clustering stage does not inspect or depend on the test ASTs directly.
- AC-063: The clustering stage knows only the information represented in the similarity matrix.
- AC-064: The clustering stage reverses the earlier decomposition direction by constructing groups from tests that were previously separated from their original classes.
- AC-075: The clustering stage consumes the indexed similarity matrix rather than test ASTs.
- AC-091: The clustering stage is represented by one interface named `TestCaseClusterer`.
- AC-092: The clustering interface has a method named `cluster`.
- AC-093: `TestCaseClusterer.cluster` receives `TestCaseSimilarityMatrix`.
- AC-094: `TestCaseClusterer.cluster` returns `TestCaseClusters`.
- AC-095: `TestCaseClusters` exposes test case clusters through `clusters()`.
- AC-096: Each test case cluster is represented by `TestCaseCluster`.
- AC-097: `TestCaseCluster` exposes grouped `TestCase` instances and their original similarity-matrix indexes.
- AC-252: The first clustering implementation is agglomerative hierarchical clustering.
- AC-253: Agglomerative hierarchical clustering receives linkage as a strategy/configuration, not as separate clusterer implementations.
- AC-254: The supported linkage strategies are single linkage, complete linkage, and average linkage.
- AC-255: Clustering stop rules use the name `StopCriterion` rather than `ThresholdCriterion`.
- AC-256: `CompositeStopCriterion` combines configured stop criteria with OR semantics.
- AC-257: An empty `CompositeStopCriterion` never stops clustering by criterion; clustering then stops only when there are no more merges.
- AC-258: The initial stop criteria are minimum similarity, maximum tests per cluster, maximum level, target cluster count, and minimum shared prefix.
- AC-259: Merge-tie resolution uses the name `MergeTieBreaker` rather than `Referee`.
- AC-260: `CompositeMergeTieBreaker` tries configured tie breakers in order and reports an unresolved tie when none resolves it.
- AC-261: An empty `CompositeMergeTieBreaker` is allowed; the agglomerative clusterer throws when an actual tie remains unresolved.
- AC-262: The initial merge tie breakers are largest merged cluster, smallest merged cluster, and stable test-case order.
- AC-263: Agglomerative clustering exposes generated clustering levels for inspection.

### REQ-013: Refactoring Stage

The sixth stage of the modern Roza pipeline must be the refactoring stage, responsible for receiving test groups and deciding what refactoring to apply to them.

Acceptance criteria:

- AC-065: The refactoring stage consumes groups produced by the clustering stage.
- AC-066: The refactoring stage decides what to do with each group.
- AC-067: The initial concrete refactoring purpose is to refactor test classes by regrouping tests into better classes so implicit setup can be used.
- AC-068: The pipeline design should leave room for other refactoring strategies, such as delegated setup, when supported by future refactoring implementations.
- AC-098: The refactoring stage is represented by one interface named `TestClassRefactorer`.
- AC-099: The refactoring interface has a method named `refactor`.
- AC-100: `TestClassRefactorer.refactor` receives `TestCaseClusters`.
- AC-101: `TestClassRefactorer.refactor` returns `RefactoredTestClasses`.
- AC-102: `RefactoredTestClasses` exposes refactored test classes through `testClasses()`.
- AC-103: `RefactoredTestClasses.testClasses()` returns `TestClass` instances.
- AC-274: The first concrete refactoring implementation is named `ImplicitSetupTestClassRefactorer`.
- AC-275: `ImplicitSetupTestClassRefactorer` creates one generated `TestClass` per cluster.
- AC-276: `ImplicitSetupTestClassRefactorer` moves the common initial non-assertion statement prefix of a cluster into a generated setup method.
- AC-277: `ImplicitSetupTestClassRefactorer` converts local variable declarations moved into setup into fields plus setup assignments.
- AC-278: `ImplicitSetupTestClassRefactorer` preserves each generated test method's original parsed test annotations.
- AC-279: `ImplicitSetupTestClassRefactorer` leaves assertion calls unchanged and never moves assertion statements into setup.
- AC-280: If clustered tests disagree on setup annotation, `ImplicitSetupTestClassRefactorer` prefers the JUnit 5 setup annotation already determined by parsing/decomposition.
- AC-281: Refactoring output remains structured `TestClass` models; source-code rendering is a separate concern.

### REQ-014: Writing Stage

The final stage of the modern Roza pipeline must be the writing stage, responsible for writing refactored test classes to an output destination.

Acceptance criteria:

- AC-069: The writing stage consumes refactored test classes.
- AC-070: The writing stage writes refactored test classes to an output destination.
- AC-071: The output destination may be the filesystem.
- AC-072: The output destination may be a cloud destination, depending on the writer implementation.
- AC-073: Different writer implementations may target different output destinations.
- AC-104: The writing stage is represented by one interface named `TestClassWriter`.
- AC-105: The writing interface has a method named `write`.
- AC-106: `TestClassWriter.write` receives `RefactoredTestClasses`.
- AC-107: `TestClassWriter.write` does not return a serialized model.
- AC-108: Concrete writer implementations receive their required output destination parameters through their constructors.

## Architectural Constraints

### NFR-001: Extensibility First

When there is a design choice, prefer extensibility over assumptions tied to one language, framework, test style, or refactoring technique.

### NFR-002: Loose Coupling Between Stages

Pipeline stages should remain loosely coupled through explicit input/output contracts.

### NFR-003: Legacy Isolation

The existing Roza implementation lives under `core.legacy`. Modern Roza architecture work should go under `core.modern` unless the user explicitly asks to change legacy code. Imports between `core.modern` and `core.legacy` must not cross in either direction.

Acceptance criteria:

- AC-035: Modern Roza implementation must not use classes from legacy Roza.
- AC-036: Modern Roza implementation must not reuse classes from legacy Roza.
- AC-037: Modern Roza implementation must be written from scratch.
- AC-038: Code in `core.modern` must not import from `core.legacy`.
- AC-156: Code in `core.legacy` must not import from `core.modern`.

### NFR-006: UI Isolation

The existing UI must be separated from the new modern Roza UI so manual testing can evolve independently for legacy and modern implementations.

Acceptance criteria:

- AC-157: Existing legacy UI code lives under `br.ufsc.ine.leb.roza.ui.legacy`.
- AC-158: The modern UI package starts under `br.ufsc.ine.leb.roza.ui.modern`.
- AC-159: The existing Gradle UI entry point continues to run the legacy UI until a modern UI entry point is implemented.
- AC-160: The modern UI uses JavaFX 17.x for the first slice, without upgrading the project Java version unless a concrete JavaFX/JDK limitation requires it.
- AC-161: The modern UI skeleton has a top pipeline bar that represents the modern Roza pipeline stages.
- AC-162: The modern UI skeleton allows navigation only to the current stage and stages already completed.
- AC-163: The modern UI skeleton uses distinct visual states for completed stages, the current pending stage, and blocked future stages.
- AC-164: The modern UI skeleton shows the selected stage configuration and action button in a left sidebar.
- AC-165: The modern UI skeleton shows placeholder data from the previous stage in the center content area.
- AC-166: The existing Swing UI entry point is named `LegacyRozaUi`, and the JavaFX modern UI entry point is named `ModernRozaUi`.
- AC-167: The modern UI skeleton should stay visually minimal, avoiding redundant section titles and unnecessary nested cards.
- AC-168: The final writing stage can be marked as completed in the modern UI skeleton after its action is triggered.
- AC-169: The modern UI skeleton visually marks the selected pipeline stage independently from whether the stage is completed or current.
- AC-170: The modern UI skeleton uses a mostly black, white, and gray visual scheme; pipeline stage status colors are exceptions.
- AC-171: The modern UI toolbar and stage action buttons use `#333333` as their black background.
- AC-172: The modern UI keeps stage action buttons active for completed stages; triggering a completed stage resets only later completed stages.
- AC-173: Blocked pipeline stage buttons use a lighter gray treatment than available stages, without relying on disabled opacity that harms text contrast.
- AC-175: The modern UI loading configuration has a `Source folder` button that opens a folder selector.
- AC-176: The modern UI loading configuration has a recursive loading checkbox.
- AC-177: The modern UI loading configuration has accepted extension checkboxes for `.java` and `.txt`.
- AC-178: Triggering `Load` in the modern UI uses the filesystem code file loader and advances to `Parsing` when loading succeeds.
- AC-179: The modern UI `Parsing` center shows the loaded file list after `Load`; selecting a loaded file shows that file's content.
- AC-216: The modern UI loading source folder defaults to Roza's own test source folder, `roza/src/test/java`.
- AC-195: The modern UI `Parsing` center shows parsing violations at the top when violations exist.
- AC-196: The modern UI `Parsing` violation viewer shows one violation at a time with previous and next controls.
- AC-197: The modern UI selects the loaded file for the class referenced by the displayed parsing violation, including the first displayed violation.
- AC-198: The modern UI parsing configuration does not expose unsupported feature policy selection.
- AC-200: The modern UI parsing violation viewer shows the violation target using `ClassName.methodName` format when a method is present.
- AC-201: The modern UI parsing violation viewer shows the code snippet for the displayed violation without an extra card-style white background.
- AC-202: The modern UI `Decomposition` center shows a summary with the number of classes with class-level violations, tests with method-level violations, tests excluded by violations, and accepted tests.
- AC-203: After decomposition, the modern UI `Measurement` center shows all decomposed tests and the code for the selected test.
- AC-205: The modern UI `Measurement` configuration exposes a metric dropdown with `LCCSS` selected by default and `LCS` as an alternative.
- AC-206: Triggering `Measure` in the modern UI uses the selected similarity measurer and advances to `Clustering` when measurement succeeds.
- AC-207: The modern UI `Clustering` center shows source and target test selectors.
- AC-208: The modern UI `Clustering` center shows a ranked source-target similarity pair list.
- AC-209: The modern UI `Clustering` ranked list has an order toggle button above it.
- AC-210: Selecting an item from the modern UI `Clustering` ranked list synchronizes the selected source and target tests.
- AC-211: The modern UI `Clustering` center shows source and target decomposed-code blocks next to the ranked list.
- AC-212: The modern UI `Clustering` ranked list omits self-pairs.
- AC-213: The modern UI `Clustering` ranked list items show only the similarity score.
- AC-214: Selecting an item from the modern UI `Clustering` ranked list preserves the current list scroll position.
- AC-217: The modern UI `Clustering` ranked score list uses a narrow width so the source and target code blocks receive most of the horizontal space.
- AC-229: The modern UI `Measurement` metric dropdown exposes `Deckard` as an option.
- AC-230: When `Deckard` is selected in the modern UI, the `Measurement` configuration shows `MIN_TOKENS`, `STRIDE`, and `SIMILARITY` inputs with Deckard defaults.
- AC-231: Triggering `Measure` with `Deckard` selected uses the configured Deckard measurer and then reuses the existing `Clustering` ranked-pair view.
- AC-239: The modern UI `Measurement` metric dropdown exposes `JPlag` as an option.
- AC-240: When `JPlag` is selected in the modern UI, the `Measurement` configuration shows a `Sensitivity` input with JPlag's default.
- AC-241: Triggering `Measure` with `JPlag` selected uses the configured JPlag measurer and then reuses the existing `Clustering` ranked-pair view.
- AC-249: The modern UI `Measurement` metric dropdown exposes `Simian` as an option.
- AC-250: When `Simian` is selected in the modern UI, the `Measurement` configuration shows a `Threshold` input with Simian's default.
- AC-251: Triggering `Measure` with `Simian` selected uses the configured Simian measurer and then reuses the existing `Clustering` ranked-pair view.
- AC-264: The modern UI `Clustering` configuration exposes linkage selection.
- AC-265: The modern UI `Clustering` configuration allows enabling zero or more stop criteria.
- AC-266: The modern UI `Clustering` configuration allows configuring ordered merge tie breaker fallbacks.
- AC-267: Triggering `Cluster` runs the configured agglomerative clusterer and shows the resulting clusters while keeping the ranked similarity inspection available.
- AC-282: Triggering `Refactor` in the modern UI runs `ImplicitSetupTestClassRefactorer` over the final clustering output, i.e., the last generated clustering level.
- AC-283: The modern UI stores the generated `RefactoredTestClasses` as the output consumed by the `Writing` tab.
- AC-284: The modern UI `Writing` center shows a list of generated test classes.
- AC-285: Selecting a generated test class in the modern UI `Writing` center shows its rendered Java code on the right.
- AC-286: The modern UI `Refactoring` configuration exposes a `Refactor Current level` action that runs `ImplicitSetupTestClassRefactorer` over the currently selected clustering level.
- AC-268: In the modern UI sidebar, pipeline stages without visible configuration controls show their primary action button at the same top height as the loading stage's first control and the refactoring action button, without empty placeholder spacing above the action.
- AC-269: The modern UI `Writing` configuration shows an output folder chooser button and a selected-path label, following the loading source folder pattern.
- AC-270: The modern UI `Writing` output folder defaults to Roza's `output/writer` folder.
- AC-271: Triggering `Write` in the modern UI writes the rendered refactored test classes to the selected output folder.

### NFR-004: Minimal Code Comments

Modern Roza code must not include comments unless they explain something that is not obvious from the code itself.

Acceptance criteria:

- AC-109: Do not add comments that merely restate type names, method names, or obvious behavior.
- AC-110: Add a code comment only when it explains non-obvious intent, constraints, or reasoning.

### NFR-005: Requirements API Snapshot

This requirements document must keep an up-to-date snapshot of the current modern Roza public API contracts.

Acceptance criteria:

- AC-138: When a modern Roza pipeline interface or stage boundary container is added, removed, or changed, the Current API Contracts section must be updated in the same task.
- AC-139: The API snapshot must distinguish implemented contracts from contracts that are only confirmed but not yet implemented.

## Current API Contracts

This section is the current requirements-level pipeline contract snapshot. It includes each pipeline interface and its immediate stage boundary containers. It must be updated whenever a modern Roza pipeline interface or stage boundary container changes.

### Implemented: Loading

```java
package br.ufsc.ine.leb.roza.core.modern.loading;

public interface CodeFileLoader {
	LoadedCodeFiles load();
}

public final class LoadedCodeFiles {
	public List<CodeFile> codeFiles();
}

public final class CodeFile {
	public String source();
	public String content();
}
```

### Implemented: Parsing

```java
package br.ufsc.ine.leb.roza.core.modern.parsing;

public interface TestClassParser {
	ParsedTestClasses parse(LoadedCodeFiles codeFiles);
}

public final class ParsedTestClasses {
	public List<TestClass> testClasses();
	public List<TestCodeViolation> violations();
}

public final class TestClass {
	public String name();
	public List<String> imports();
	public Optional<SetupAnnotation> setupAnnotation();
	public List<Field> fields();
	public List<FixtureMethod> fixtures();
	public List<HelperMethod> helperMethods();
	public List<TestMethod> testMethods();
}

public final class SetupAnnotation {
	public CodeAnnotation annotation();
	public Optional<String> importDeclaration();
}

public enum ViolationScope {
	TEST_CLASS,
	TEST_METHOD
}

public final class TestCodeViolation {
	public ViolationScope scope();
	public String testClassName();
	public Optional<String> testMethodName();
	public String description();
	public String codeSnippet();
}
```

### Implemented: Decomposition

```java
package br.ufsc.ine.leb.roza.core.modern.decomposition;

public interface TestCaseDecomposer {
	DecomposedTestCases decompose(ParsedTestClasses parsedTestClasses);
}

public final class DecomposedTestCases {
	public List<TestCase> testCases();
}

public final class TestCase {
	public String name();
	public CodeBlock body();
	public Optional<TestClass> sourceTestClass();
	public List<CodeAnnotation> annotations();
}
```

### Implemented: Measurement

```java
package br.ufsc.ine.leb.roza.core.modern.measurement;

public interface TestCaseSimilarityMeasurer {
	TestCaseSimilarityMatrix measure(DecomposedTestCases testCases);
}

public final class TestCaseSimilarityMatrix {
	public TestCaseSimilarityMatrix(List<TestCase> testCases);
	public int size();
	public TestCase testCaseAt(int index);
	public double similarity(int sourceIndex, int targetIndex);
}

public final class LcsTestCaseSimilarityMeasurer implements TestCaseSimilarityMeasurer {
	public TestCaseSimilarityMatrix measure(DecomposedTestCases testCases);
}
```

### Implemented: Clustering

```java
package br.ufsc.ine.leb.roza.core.modern.clustering;

public interface TestCaseClusterer {
	TestCaseClusters cluster(TestCaseSimilarityMatrix similarityMatrix);
}

public final class TestCaseClusters {
	public List<TestCaseCluster> clusters();
}

public final class TestCaseCluster {
	public List<TestCase> testCases();
	public List<Integer> testCaseIndexes();
	public int size();
}

public final class AgglomerativeHierarchicalTestCaseClusterer implements TestCaseClusterer {
	public TestCaseClusters cluster(TestCaseSimilarityMatrix matrix);
	public List<ClusteringLevel> generateLevels(TestCaseSimilarityMatrix matrix);
}
```

### Implemented: Refactoring

```java
package br.ufsc.ine.leb.roza.core.modern.refactoring;

public interface TestClassRefactorer {
	RefactoredTestClasses refactor(TestCaseClusters clusters);
}

public final class RefactoredTestClasses {
	public List<TestClass> testClasses();
}

public final class ImplicitSetupTestClassRefactorer implements TestClassRefactorer {
	public RefactoredTestClasses refactor(TestCaseClusters clusters);
}
```

### Confirmed, Not Yet Implemented: Writing

```java
package br.ufsc.ine.leb.roza.core.modern.writing;

public interface TestClassWriter {
	void write(RefactoredTestClasses testClasses);
}
```

## Open Questions

### Q-001: What are the initial pipeline stages?

The high-level pipeline stages are `loading`, `parsing`, `decomposition`, `measurement`, `clustering`, `refactoring`, and `writing`. Confirm whether this is the complete high-level pipeline or whether optional/additional stages are expected.

### Q-002: What is the exact stage-to-stage data flow?

The user stated that stages do not communicate directly and that each stage generates output consumed later. Confirm whether each output is consumed by the next stage, by the pipeline orchestrator, or by another explicit routing mechanism.

### Q-003: What is the minimum raw code loading input model?

`CodeFileLoader.load` returns `LoadedCodeFiles`, which contains `CodeFile` instances and exposes them through `codeFiles()`. `CodeFile` exposes a textual source identity through `source()` and raw textual content through `content()`. Other attributes remain undecided until they are required.

### Q-004: What is the first target implementation slice?

Clarify whether the first implementation should be only interfaces/model contracts or also a small runnable pipeline skeleton.

### Q-005: Should TestClass use an existing AST abstraction?

Explore whether modern Roza should use or adapt an existing AST abstraction for `TestClass`, while respecting the requirement that modern Roza must not use or reuse legacy Roza classes.

### Q-006: What is the detailed design of TestClass?

`TestClass` is a crucial inflection point for the project. Its detailed design and minimum content API should be deferred until the whole pipeline is understood at a high level.

### Q-007: What are the decomposition interface and output model names?

The decomposition interface is named `TestCaseDecomposer`, and its method is named `decompose`. `TestCaseDecomposer.decompose` returns `DecomposedTestCases`, which exposes decomposed test cases through `testCases()`. Each decomposed test is represented by `TestCase`, because after decomposition it is no longer exactly a method inside a test class. `TestCase` exposes its name, body, source test class, and original test annotations.

### Q-008: What are the measurement interface and output model names?

The measurement interface is named `TestCaseSimilarityMeasurer`, and its method is named `measure`. `TestCaseSimilarityMeasurer.measure` receives `DecomposedTestCases` and returns `TestCaseSimilarityMatrix`. The first matrix API is intentionally minimal for LCCSS: construction from the ordered `TestCase` list and package-level writing by source and target indexes. Public read methods and any separate similarity metric or score abstractions are deferred until clustering needs them.

### Q-009: What are the clustering interface and output model names?

The clustering interface is named `TestCaseClusterer`, and its method is named `cluster`. `TestCaseClusterer.cluster` receives `TestCaseSimilarityMatrix` and returns `TestCaseClusters`, which exposes test case clusters through `clusters()`. Each cluster is represented by `TestCaseCluster`, which has no minimum content API yet.

### Q-010: What are the refactoring interface and output model names?

The refactoring interface is named `TestClassRefactorer`, and its method is named `refactor`. `TestClassRefactorer.refactor` receives `TestCaseClusters` and returns `RefactoredTestClasses`, which exposes refactored `TestClass` instances through `testClasses()`.

### Q-011: What output destinations should writing support first?

The final output stage is named `writing`. Its interface is named `TestClassWriter`, and its method is named `write`. `TestClassWriter.write` receives `RefactoredTestClasses` and does not return a serialized model. The first concrete output destination remains undefined.

### Q-012: How generic can the pipeline be beyond the initial implicit-setup refactoring purpose?

The initial concrete purpose is to refactor test classes by regrouping tests into better classes so implicit setup can be used. The desired broader goal is an abstract and flexible pipeline for different refactoring purposes, but the feasibility and exact boundaries of that generality remain open.

### Q-013: What public read API should clustering require from the similarity matrix?

The current clustering implementation requires the matrix size, test case by index, and directed source-target similarity values. These are exposed through `TestCaseSimilarityMatrix.size()`, `testCaseAt(int)`, and `similarity(int, int)`.

## Change Log

- 2026-05-10: Created initial requirements from the first modern Roza architecture description.
- 2026-05-10: Refined the input requirement from project-level input to loading tests.
- 2026-05-10: Added the first pipeline stage, `loading`, and its shared loading interface constraints.
- 2026-05-10: Confirmed the loading interface name as `TestCodeLoader` and clarified that `load` loads raw test code.
- 2026-05-10: Added `parsing` as the second pipeline stage, consuming loaded raw test files and producing ASTs for identified test classes.
- 2026-05-10: Refined loading to load raw code files that may or may not contain tests, reopened the loading interface naming question, and clarified that parsing determines test classes.
- 2026-05-10: Confirmed the loading interface name as `CodeFileLoader`, replacing the earlier `TestCodeLoader` candidate.
- 2026-05-10: Confirmed that `CodeFileLoader.load` returns `LoadedCodeFiles`, composed of `CodeFile` instances, and that `CodeFile` starts without predefined attributes.
- 2026-05-10: Added the constraint that modern Roza must not use or reuse legacy Roza classes and must be implemented from scratch.
- 2026-05-10: Confirmed the parsing interface as `TestClassParser`, with `parse` returning `ParsedTestClasses`, which contains `TestClass` instances.
- 2026-05-10: Added `TestClass` as the generalized root AST abstraction for identified test classes.
- 2026-05-10: Deferred the detailed `TestClass` design until the whole pipeline is understood at a high level.
- 2026-05-10: Added `decomposition` as the third pipeline stage, separating each test from its original class while carrying required code.
- 2026-05-10: Added `measurement` as the fourth pipeline stage, applying similarity metrics to test pairs and producing a similarity matrix.
- 2026-05-10: Added `clustering` as the fifth pipeline stage, grouping similar tests based only on the similarity matrix.
- 2026-05-10: Added `refactoring` as the sixth pipeline stage, receiving groups and deciding how to refactor them.
- 2026-05-10: Added `writing` as the final pipeline stage, outputting refactored test classes to a destination such as the filesystem or cloud.
- 2026-05-10: Recorded the initial concrete purpose as regrouping tests into better classes for implicit setup, while keeping broader refactoring generality open.
- 2026-05-10: Confirmed that clustering consumes a similarity matrix instead of ASTs.
- 2026-05-10: Confirmed the minimum loading contract: `LoadedCodeFiles.codeFiles()` and `CodeFile.content()`.
- 2026-05-10: Confirmed that `TestClass` has no minimum content API yet.
- 2026-05-10: Confirmed that `ParsedTestClasses` exposes parsed test classes through `testClasses()`.
- 2026-05-10: Confirmed the decomposition interface as `TestMethodDecomposer`, its method as `decompose`, the return type as `DecomposedTestMethods`, and the decomposed test model as `TestMethod` with no minimum content API.
- 2026-05-10: Confirmed the measurement interface as `TestMethodSimilarityMeasurer`, its method as `measure`, and its contract from `DecomposedTestMethods` to `TestMethodSimilarityMatrix`.
- 2026-05-10: Confirmed the clustering interface as `TestMethodClusterer`, its method as `cluster`, and its contract from `TestMethodSimilarityMatrix` to `TestMethodClusters`.
- 2026-05-10: Confirmed the refactoring interface as `TestClassRefactorer`, its method as `refactor`, and its contract from `TestMethodClusters` to `RefactoredTestClasses`.
- 2026-05-10: Confirmed the final stage as `writing`, represented by `TestClassWriter.write(RefactoredTestClasses)` without returning a serialized model.
- 2026-05-10: Added the constraint that modern Roza code should not include comments unless they explain non-obvious information.
- 2026-05-10: Moved the loading contract to the `core.modern.loading` package.
- 2026-05-10: Added acceptance criteria for the first filesystem `CodeFileLoader` implementation, covering folder inclusion, recursion, and extension filtering.
- 2026-05-11: Confirmed `LoadedCodeFiles` and `CodeFile` as concrete loading data classes rather than extension interfaces.
- 2026-05-11: Refined parsing and `TestClass` from a language-universal AST goal to a Java-first domain model that hides JavaParser behind concrete parser implementations.
- 2026-05-11: Added `UnsupportedFeaturePolicy` and fail-fast/diagnostic handling for unsupported parser features.
- 2026-05-11: Marked JUnit 4 `@After` and JUnit 5 `@AfterEach` as unsupported in the first parser slice.
- 2026-05-11: Confirmed that parsed fields must preserve complex Java type text such as generics and multidimensional arrays.
- 2026-05-11: Added the Current API Contracts snapshot and the requirement to keep it updated whenever public modern Roza contracts change.
- 2026-05-11: Narrowed the Current API Contracts snapshot to pipeline interfaces only.
- 2026-05-11: Refined the Current API Contracts snapshot to include pipeline interfaces and immediate stage boundary containers.
- 2026-05-11: Renamed decomposed test terminology from test method to test case: `TestCaseDecomposer`, `DecomposedTestCases`, `TestCase`, `TestCaseSimilarityMeasurer`, `TestCaseSimilarityMatrix`, `TestCaseClusterer`, `TestCaseClusters`, and `TestCaseCluster`.
- 2026-05-11: Confirmed that `TestCase` preserves the original parsed test method name for later refactoring use.
- 2026-05-11: Confirmed that decomposed `TestCase` bodies include statements from `@Before` fixtures but do not need to preserve the `@Before` annotation itself.
- 2026-05-11: Confirmed that LCCSS measurement must stop at the first assertion statement rather than merely filtering assertions.
- 2026-05-11: Implemented the first `DefaultTestCaseDecomposer`, with `TestCase.name()` and `TestCase.body()`, preserving assertions in the full decomposed body.
- 2026-05-11: Added assertion metadata to `CodeStatement`; LCCSS must use this metadata to stop at the first assertion instead of inferring assertions from text.
- 2026-05-11: Recorded the LCCSS scoring formula as common contiguous prefix based: `(2 * commonPrefixSize) / (sourceProjectionSize + targetProjectionSize)`.
- 2026-05-11: Renamed the first modern parser implementation from `JavaTestClassParser` to `JunitTestClassParser` to reflect that the implementation targets JUnit, not all Java test frameworks.
- 2026-05-11: Replaced assertion-name prefix matching with an explicit supported assertion method list for JUnit 4, current JUnit Jupiter, and Hamcrest `assertThat`, shared by parsing and unsupported-feature validation.
- 2026-05-11: Implemented the first measurement slice with `TestCaseSimilarityMeasurer`, dense directed `TestCaseSimilarityMatrix`, and `LccssTestCaseSimilarityMeasurer`.
- 2026-05-11: Chose JavaFX 17.x for the first modern UI skeleton and confirmed explicit entrypoint names: `LegacyRozaUi` and `ModernRozaUi`.
- 2026-05-11: Refined the modern UI skeleton toward a minimal layout without redundant headings, bordered pipeline buttons, or an inner center content card.
- 2026-05-11: Refined the modern UI skeleton so writing can be completed visually and configuration controls do not sit inside a white card.
- 2026-05-11: Refined the modern UI skeleton so previous stages can be visually selected without losing their completed status.
- 2026-05-11: Refined the modern UI skeleton with a black toolbar, mostly neutral colors, and active actions for completed stages that reset later stages.
- 2026-05-11: Refined blocked pipeline stage buttons to use a lighter gray treatment.
- 2026-05-11: Confirmed the first functional modern UI loading flow: source folder selector, recursive checkbox, `.java`/`.txt` extension checkboxes, loaded file list in `Parsing`, and file-content inspection.
- 2026-05-11: Strengthened legacy isolation so imports between `core.modern` and `core.legacy` must not cross in either direction.
- 2026-05-11: Defined the first refactoring-safe supported subset and structured parsing violations for experiment exclusions.
- 2026-05-11: Added the modern UI parsing violation viewer behavior: show one violation at a time and select the violated class file.
- 2026-05-11: Removed unsupported feature policy selection from the modern UI parsing configuration.
- 2026-05-11: Added code snippets to parsing violations and displayed them in the modern UI parsing violation viewer.
- 2026-05-11: Confirmed that method references inside detectable assertion calls are not unsupported-subset violations.
- 2026-05-11: Added a modern UI `Decomposition` summary for violation and accepted-test counts.
- 2026-05-11: Removed parsing violation records from the modern UI `Decomposition` summary.
- 2026-05-11: Added the decomposed-test list and selected-test code view to the modern UI `Measurement` center.
- 2026-05-11: Added the LCCSS metric selector, measurement action, and clustering matrix inspection to the modern UI.
- 2026-05-11: Replaced the full modern UI `Clustering` matrix rendering with a ranked similarity list and side-by-side source/target code blocks.
- 2026-05-11: Refined the modern UI `Clustering` ranked list to omit self-pairs, show scores only, and preserve scroll on selection.
- 2026-05-11: Fixed ranked-list selection so it synchronizes both source and target controls without resetting scroll.
- 2026-05-11: Changed the modern UI `Clustering` ranked list from selected-source targets to global source-target pairs.
- 2026-05-11: Corrected default decomposition so supported `@Before` assignments to fields become initialized local declarations.
- 2026-05-11: Set the modern UI default loading source folder to Roza's own test sources.
- 2026-05-11: Narrowed the modern UI `Clustering` ranked score list.
- 2026-05-11: Shortened the modern UI `Clustering` ranked-order button text.
- 2026-05-11: Added modern LCS measurement and exposed it in the modern UI metric dropdown.
- 2026-05-11: Validated Deckard execution through the existing Docker script and added modern Deckard configuration, report parsing, scoring, and UI selection requirements.
- 2026-05-11: Validated JPlag execution through the existing jar and added modern JPlag sensitivity, HTML report parsing, directional scoring, and UI selection requirements.
- 2026-05-11: Validated Simian execution through the existing jar and added modern Simian threshold, XML report parsing, asymmetric line-coverage scoring, and UI selection requirements.
- 2026-05-12: Implemented the first modern clustering slice with agglomerative hierarchical clustering, linkage strategies, composable stop criteria, ordered merge tie breakers, level inspection, and JavaFX clustering configuration/output.
- 2026-05-12: Implemented the first modern refactoring slice with `ImplicitSetupTestClassRefactorer`, source-class context preservation, setup annotation inference in parsing/decomposition, generated test-class rendering, and Writing-tab class/code inspection.
- 2026-05-12: Confirmed that modern UI sidebar stages without visible configuration controls should align their primary action button with the first visible loading control and the refactoring action.
- 2026-05-12: Confirmed the modern UI writing sidebar output folder chooser, default `output/writer` folder, and selected-folder write behavior.
