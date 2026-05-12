# Roza Modern Knowledge

This document stores evolving knowledge discovered while designing and implementing modern Roza.

## Vocabulary

- Modern Roza: the modern architecture for Roza, developed under `br.ufsc.ine.leb.roza.core.modern`.
- Legacy Roza: the existing architecture, preserved under `br.ufsc.ine.leb.roza.core.legacy`.
- Pipeline: the high-level process that loads raw code files, identifies tests through parsing, and produces refactored tests.
- Stage: one step of the pipeline. Each stage is represented by an interface and may have multiple implementations.
- Stage implementation: a concrete strategy for executing one stage.
- Stage contract: the input/output model that allows one stage implementation to be combined with others without direct coupling.
- Loading stage: the first modern Roza pipeline stage; it loads raw code files into memory.
- `CodeFileLoader`: the loading stage interface, implemented by concrete raw code-file loaders.
- `FileSystemCodeFileLoader`: the first concrete `CodeFileLoader` implementation; it loads regular files from a provided folder, optionally recursing into child folders and filtering by file extension.
- `LoadedCodeFiles`: the concrete result class returned by `CodeFileLoader.load`; it exposes loaded `CodeFile` instances through `codeFiles()`.
- `CodeFile`: a concrete raw loaded code file class. Its minimum confirmed API exposes raw textual content through `content()`. Other attributes remain undefined until confirmed requirements make them necessary.
- Parsing stage: the second modern Roza pipeline stage; it reads loaded raw code files and creates ASTs for identified test classes.
- `TestClassParser`: the parsing stage interface.
- `JunitTestClassParser`: the first concrete modern parser implementation. It parses Java source code for a conservative JUnit subset, while JavaParser remains an internal implementation detail.
- `JunitAssertionMethods`: the internal supported assertion method list for JUnit 4, current JUnit Jupiter, and Hamcrest `assertThat`, shared by the parser and unsupported-feature validator.
- `ParsedTestClasses`: the result returned by `TestClassParser.parse`; it exposes `TestClass` instances through `testClasses()`.
- `UnsupportedFeaturePolicy`: the parser policy for unsupported Java test-code features. `SAFE` fails with a clear error; `UNSAFE` records diagnostics and skips unsupported input.
- `JavaUnsupportedFeatureValidator`: the separate validation step that runs before Java parser implementations extract modern Roza domain models.
- `TestClass`: the Java-first root domain abstraction for an identified Java test class. It hides JavaParser types from the public modern Roza contract.
- `Field`: a parsed Java field that decomposition can later transform into a local variable. Its type text must preserve complex Java forms such as generics, nested generics, wildcard generics, qualified types, generic arrays, arrays, and multidimensional arrays.
- `FixtureMethod`: a supported instance fixture method such as JUnit 4 `@Before`; JUnit 4 `@After` and JUnit 5 `@AfterEach` are unsupported in the first parser slice.
- `HelperMethod`: a supported non-test helper method in the Java test class.
- `CodeBlock`: a block of parsed top-level code statements.
- `CodeStatement`: a statement-level code unit with original text, normalized text, and assertion metadata for later decomposition and similarity measurement.
- Decomposition stage: the third modern Roza pipeline stage; it separates each test from its original class and carries the code required by that test.
- `TestCaseDecomposer`: the decomposition stage interface.
- `DecomposedTestCases`: the result returned by `TestCaseDecomposer.decompose`; it exposes decomposed `TestCase` instances through `testCases()`.
- `DefaultTestCaseDecomposer`: the first concrete decomposition implementation. It creates one `TestCase` per parsed test method and orders the decomposed body as field declarations, supported `@Before` statements, then original test body statements.
- `TestCase`: the model for a decomposed test. It is called a test case because after decomposition it is no longer exactly a method inside a test class. It preserves the original parsed test method name through `name()`, including duplicate names. Its `body()` includes statements derived from supported `@Before` fixtures and preserves assertions, but it does not need to preserve the `@Before` annotation itself. It does not expose `id()` or `testClass()` yet.
- Measurement stage: the fourth modern Roza pipeline stage; it applies a similarity metric to each pair of decomposed tests and produces a similarity matrix.
- `TestCaseSimilarityMeasurer`: the measurement stage interface.
- `TestCaseSimilarityMatrix`: the result returned by `TestCaseSimilarityMeasurer.measure`; the first implementation is a dense directed matrix indexed internally by source and target test case positions.
- `LccssTestCaseSimilarityMeasurer`: the first concrete measurement implementation. It projects each decomposed `TestCase` body up to the first assertion and applies the LCCSS common-prefix formula.
- `JplagTestCaseSimilarityMeasurer`: the modern JPlag metric implementation. It projects each decomposed `TestCase` body up to the first assertion, materializes those projections as Java files, runs the existing JPlag jar, and reads directional percentages from JPlag's HTML reports.
- `SimianTestCaseSimilarityMeasurer`: the modern Simian metric implementation. It projects each decomposed `TestCase` body up to the first assertion, materializes those projections as Java files, runs the existing Simian jar, and scores directed line coverage from Simian XML clone blocks.
- Clustering stage: the fifth modern Roza pipeline stage; it groups similar tests using only the similarity matrix.
- `TestCaseClusterer`: the clustering stage interface.
- `TestCaseClusters`: the result returned by `TestCaseClusterer.cluster`; it exposes `TestCaseCluster` instances through `clusters()`.
- `TestCaseCluster`: the model for a cluster of test cases. It has no minimum content API yet.
- Refactoring stage: the sixth modern Roza pipeline stage; it receives test groups and decides what refactoring to apply to them.
- `TestClassRefactorer`: the refactoring stage interface.
- `RefactoredTestClasses`: the result returned by `TestClassRefactorer.refactor`; it exposes refactored `TestClass` instances through `testClasses()`.
- Writing stage: the final modern Roza pipeline stage; it writes refactored test classes to an output destination such as the filesystem or cloud.
- `TestClassWriter`: the writing stage interface.
- `FileSystemTestClassWriter`: the first concrete writing implementation. It writes rendered Java test classes to a configured output folder.
- Legacy UI: the existing Swing UI now lives under `br.ufsc.ine.leb.roza.ui.legacy` and starts from `LegacyRozaUi`.
- Modern UI: the JavaFX manual-testing UI for modern Roza starts under `br.ufsc.ine.leb.roza.ui.modern` and starts from `ModernRozaUi`.

## Current Architectural Understanding

Modern Roza is intended to be a reusable and extensible framework for automated test-code refactoring. Its central abstraction is a pipeline composed of independent stages. Each stage should be customizable through interchangeable implementations.

The pipeline must allow combinations such as:

- stage 1 implementation 1 with stage 2 implementation 1;
- stage 1 implementation 1 with stage 2 implementation 2;
- future implementations added without changing unrelated stages.

The framework should be designed for broad extension across programming languages, test frameworks, refactoring types, and analysis techniques. The initial pipeline will impose some limits, but core abstractions should avoid unnecessary assumptions.

Modern and legacy Roza are isolated architecture areas. The isolation is bidirectional: `core.modern` must not import `core.legacy`, and `core.legacy` must not import `core.modern`.

The first confirmed pipeline stage is `loading`. It has one shared interface named `CodeFileLoader` with a single method named `load`. The method returns `LoadedCodeFiles`, a concrete data class that exposes concrete `CodeFile` instances through `codeFiles()`. Each `CodeFile` exposes a textual source identity through `source()` and raw textual content through `content()`. The name avoids implying that every loaded file contains tests. Concrete loading implementations are configured through constructor parameters, allowing different strategies such as recursive filesystem loading, extension-based filesystem loading, and Git-based loading.

The second confirmed pipeline stage is `parsing`. Its interface is named `TestClassParser`. It receives the in-memory raw code files produced by `loading`, determines which loaded files contain test classes, identifies those test classes, and creates one Java-first domain model per identified test class. Its `parse` method returns `ParsedTestClasses`, which exposes `TestClass` instances through `testClasses()`. This keeps loading focused on loading files and leaves structural interpretation to parsing.

The initial `TestClass` model is Java-first, not language-universal. The architecture should still preserve pipeline extension points, but it should not sacrifice the quality of the Java model to prematurely support Python, Rust, or other languages. JavaParser may be used inside concrete Java parser implementations, but JavaParser types should not become the public modern Roza model.

The first concrete parser implementation is named `JunitTestClassParser` because its supported and unsupported features are defined around JUnit, not every Java testing framework. Other Java test frameworks can later have separate concrete parsers, such as TestNG or Spock parsers, while still implementing `TestClassParser`.

Assertion metadata is derived from an explicit supported assertion method list for JUnit 4, current JUnit Jupiter, and Hamcrest `assertThat`, not a method-name prefix. This avoids treating helper calls such as `assertBusinessRule()` as assertions.

The first supported parser scenario is the refactoring-safe subset used for experiments. It is deliberately conservative: a plain JUnit test class with one top-level concrete non-generic class, no inheritance, no explicit constructor, non-static fields without direct field initialization, supported `@Test` methods, and at most one simple `@Before` or `@BeforeEach`. Any annotation outside `@Test`, `@Before`, and `@BeforeEach` is unsupported. Helper methods and tear down/lifecycle methods other than the supported before fixture are unsupported in this subset. Unsupported-subset findings are represented as structured parsing violations with class or test-method scope, human-readable descriptions, and code snippets for the construct that caused the violation, so experiments can report how many items were excluded, which items were excluded, and why.

Assertion detection is an operational model, not a semantic proof. A detectable assertion is an assertion recognized in the current statement, including assertion calls with lambda or method-reference arguments such as `assertThrows`. Tests without detectable assertions are still supported; metrics that stop at assertions use the whole body when no detectable assertion exists.

The third confirmed pipeline stage is `decomposition`. Its interface is named `TestCaseDecomposer`, and its method is named `decompose`. It receives parsed test classes and separates each supported test from its class of origin. `TestCaseDecomposer.decompose` returns `DecomposedTestCases`, which exposes decomposed `TestCase` instances through `testCases()`. Each decomposed test is represented by `TestCase`. The name distinguishes decomposed tests from parsed `TestMethod` models, which still represent methods inside a `TestClass`. `TestCase` preserves the original parsed test method name for later refactoring use, even when names are duplicated. It also preserves the source `TestClass` reference and the original parsed test method annotations so refactoring can reuse imports, setup annotation decisions, and method-level JUnit style. Assertions remain in the full `TestCase` body; measurement-specific projections decide whether to stop before assertions. For now, `TestCase` does not expose `id()`. In the implicit setup example, a class with one implicit setup and two tests produces two decomposed test case models, each containing field declarations, the implicit setup statements, and the body of one test. When a supported `@Before` statement assigns to a parsed field, decomposition replaces that assignment with an initialized local declaration such as `TestCase alpha = new TestCase(...)`; this avoids producing a declaration-only prefix followed by a separate assignment. Decomposition skips classes and test methods listed in `ParsedTestClasses.violations()`, so measurement and refactoring operate only on the supported subset by construction.

The fourth confirmed pipeline stage is `measurement`. Its interface is named `TestCaseSimilarityMeasurer`, and its method is named `measure`. It receives `DecomposedTestCases` and returns `TestCaseSimilarityMatrix`. It applies a similarity metric to each pair of decomposed test cases. Similarity metrics can have different objectives, with duplicated test/code identification as the most common one. The returned matrix records the similarity degree for each directed pair according to the applied metric. The first matrix implementation receives the ordered `TestCase` list, stores scores in a dense `double[]`, initializes all values as `0.0`, initializes the diagonal as `1.0`, exposes package-level `setSimilarity(int sourceIndex, int targetIndex, double similarity)` for measurers, and exposes public read methods for `size()`, `testCaseAt(int)`, and `similarity(int, int)`. Measurement may use a projection of the `TestCase` body instead of every statement. LCCSS, LCS, Deckard, JPlag, and Simian use the pre-assertion projection, stopping at the first assertion statement using `CodeStatement.isAssertion()` rather than inferring assertions from statement text. LCCSS counts the common contiguous prefix from the start of both projected statement lists, and its score is `(2 * commonPrefixSize) / (sourceProjectionSize + targetProjectionSize)`. LCS counts the longest common subsequence over the projected statements while preserving order, and its score is `(2 * commonSubsequenceSize) / (sourceProjectionSize + targetProjectionSize)`. JPlag uses the directional percentages reported in each `match*-top.html` file as directed scores. Deckard and Simian use asymmetric source line coverage over the projected test body, merging overlapping intervals and excluding wrapper lines introduced only for tool execution.

The fifth confirmed pipeline stage is `clustering`. Its interface is named `TestCaseClusterer`, and its method is named `cluster`. It receives `TestCaseSimilarityMatrix` and returns `TestCaseClusters`, which exposes `TestCaseCluster` instances through `clusters()`. It groups tests according to the similarity matrix produced by `measurement`, reading matrix size, test case identity by index, and source/target similarity values from the matrix. Architecturally, this starts rebuilding structure after the earlier decomposition split test classes into separated test-level ASTs. The first concrete implementation is agglomerative hierarchical clustering. Linkage is a strategy inside that algorithm; stop criteria use OR composition; merge tie breakers are ordered fallbacks.

The sixth confirmed pipeline stage is `refactoring`. Its interface is named `TestClassRefactorer`, and its method is named `refactor`. It receives `TestCaseClusters` and returns `RefactoredTestClasses`, which exposes refactored `TestClass` instances through `testClasses()`. It receives the groups produced by `clustering` and decides what to do with them. The first concrete implementation is `ImplicitSetupTestClassRefactorer`, which creates one generated test class per cluster, moves the common initial non-assertion prefix into setup, keeps assertions in test methods, and preserves each test method's original annotations. Setup annotation selection is decided before refactoring: `TestClass` exposes the setup annotation determined by parsing, using the original fixture annotation when present or inferring from `@Test` usage otherwise. The pipeline may later support other refactoring purposes, such as delegated setup, through different refactoring implementations.

The final confirmed pipeline stage is `writing`. Its interface is named `TestClassWriter`, and its method is named `write`. It receives `RefactoredTestClasses` and writes them to an output destination without returning a serialized model. The destination may be the filesystem or a cloud target, depending on the writer implementation. Concrete writer implementations receive required output destination parameters through constructors. The first concrete implementation is `FileSystemTestClassWriter`, which uses `JunitTestClassRenderer` to write one `.java` file per refactored `TestClass` into a configured output folder.

The first modern UI slice uses JavaFX 17.x while the project remains on Java 11. It is a code-only skeleton without FXML. Its layout mirrors the pipeline: a top stage bar, a left sidebar with selected-stage configuration and action button, and a center area with placeholder data produced by the previous stage. The layout should stay minimal, using mostly black, white, and gray; pipeline status colors are exceptions. The top stage bar and selected-stage action button use `#333333`, and blocked pipeline stage buttons use a lighter gray treatment without JavaFX disabled opacity so their text remains readable. Stage selection is visual state independent from pipeline status, so a previous completed stage can still be shown as selected. The loading stage is the first functional UI flow: it defaults the source folder to Roza's own `src/test/java`, opens a folder selector, lets the user choose recursive loading and accepted `.java`/`.txt` extensions, calls `FileSystemCodeFileLoader`, and shows the loaded files in the `Parsing` center. Selecting a loaded file shows its content. The parsing configuration only exposes parser implementation selection, not unsupported feature policy selection. The `Parsing` center shows parsing violations above the loaded-file viewer when violations exist, one violation at a time, with previous/next controls; showing a violation selects the loaded file that contains the referenced test class, uses `ClassName.methodName` format for method violations, and shows the violation code snippet on the normal background without a card-style white wrapper. The `Decomposition` center summarizes classes with class-level violations, tests with method-level violations, tests excluded by violations, and accepted tests. The `Measurement` center shows decomposed tests from the previous stage and the code for the selected test, with a metric dropdown that defaults to LCCSS and also includes LCS, Deckard, JPlag, and Simian. Triggering `Measure` produces the selected metric's similarity matrix. The `Clustering` center owns the source/target selectors, shows a narrow ranked source-target similarity score list with an order toggle, omits self-pairs, preserves ranking scroll when selecting an item, synchronizes both source and target controls from ranked-list selection, and shows side-by-side source and target decomposed-code blocks. Triggering `Refactor` runs the implicit-setup refactorer over the clustering output. The `Writing` center shows generated test classes in a list and renders the selected class's Java code on the right.

## Implementation Notes

- The minimum loading API is implemented in `src/main/java/br/ufsc/ine/leb/roza/core/modern/loading` as `CodeFileLoader`, `LoadedCodeFiles`, and `CodeFile`.
- `FileSystemCodeFileLoader` is implemented in `src/main/java/br/ufsc/ine/leb/roza/core/modern/loading` and covered by tests in `src/test/java/br/ufsc/ine/leb/roza/core/modern/loading`.
- The first parsing implementation is being designed as Java-first and belongs in `src/main/java/br/ufsc/ine/leb/roza/core/modern/parsing`.
- The first decomposition implementation is `DefaultTestCaseDecomposer` in `src/main/java/br/ufsc/ine/leb/roza/core/modern/decomposition`.
- Code comments should be avoided unless they explain something non-obvious.
- Do not add code for hypothetical future needs. Implement only what the current task requires, and remove unused code as soon as it appears.
- **Modern UI sidebar spacing (`ModernRozaUi`)**: Keep vertical rhythm consistent without large accumulated gaps. The configuration column uses an inner `VBox` with **10px** spacing between adjacent children. After each **configuration group** (the block that ends before the next bold section title), apply **`VBox.setMargin` with bottom 12px** on the last control of that group. On each **bold section title** that follows another group, add **`VBox.setMargin` with top 4px**. The stage **action** button uses **`VBox.setMargin` with top 12px** after the configuration `VBox` only when the stage has visible configuration controls. Stages without visible configuration controls add the action button directly to the sidebar so their first visible control aligns with loading and refactoring. Do not add a group-ending margin when there is no following group. **`ComboBox` height**: force a single-line control with explicit **`-fx-min-height` / `-fx-pref-height` / `-fx-max-height`** (30px in the first implementation) and modest horizontal padding.
- **Modern UI writing output (`ModernRozaUi`)**: The `Writing` sidebar follows the loading folder-selector pattern for the output destination and defaults to `output/writer` when the app runs from the Roza project.
- **Modern writing implementation**: `FileSystemTestClassWriter` owns filesystem writes for the writing stage. UI code should call the writer instead of creating directories and writing rendered files directly.

## Decisions

- DEC-001: Existing Roza code was moved to `core.legacy`; new architecture work belongs in `core.modern`.
- DEC-002: Durable requirements for modern Roza are stored in `docs/roza-modern/requirements.md`.
- DEC-003: Evolving model knowledge and implementation discoveries are stored in this file.
- DEC-004: Agents working on modern Roza should use the `roza-modern-development` skill.
- DEC-005: Automated test-code refactoring pipelines are not unique to modern Roza; modern Roza work must be distinguished by explicit references to modern Roza, `core.modern`, or the modern architecture documents.
- DEC-006: `requirements.md` is user-driven and normative; `knowledge.md` is agent-maintained and non-normative. Agent interpretations stay in `knowledge.md` unless the user confirms them as requirements.
- DEC-007: Modern Roza is currently understood as loading raw code files as initial input, not as being defined by receiving a whole code project as input.
- DEC-008: The first confirmed pipeline stage is `loading`; its interface is named `CodeFileLoader`.
- DEC-009: The loading interface's `load` method loads raw code files, not parsed modern Roza test models.
- DEC-010: The second confirmed pipeline stage is `parsing`; it consumes loaded raw code files and creates ASTs for identified test classes.
- DEC-011: `CodeFileLoader.load` returns `LoadedCodeFiles`, which contains `CodeFile` instances.
- DEC-012: `CodeFile` should not receive attributes until those attributes become necessary through confirmed requirements.
- DEC-013: Modern Roza must be implemented from scratch and must not use or reuse classes from legacy Roza.
- DEC-014: The parsing interface is named `TestClassParser`; its `parse` method returns `ParsedTestClasses`, which contains `TestClass` instances.
- DEC-015: `TestClass` is the generalized root AST abstraction for an identified test class and must be origin-independent.
- DEC-016: Detailed `TestClass` design is deferred until the whole modern Roza pipeline is understood at a high level.
- DEC-017: The third confirmed pipeline stage is `decomposition`; it separates each test from its original class while carrying code required by that test.
- DEC-018: The fourth confirmed pipeline stage is `measurement`; it applies a similarity metric to test pairs and produces a similarity matrix.
- DEC-019: The fifth confirmed pipeline stage is `clustering`; it groups similar tests using only the similarity matrix.
- DEC-020: The sixth confirmed pipeline stage is `refactoring`; it receives clustered groups and decides what to do with them.
- DEC-021: The final confirmed pipeline stage is `writing`; it writes refactored test classes to a destination.
- DEC-022: The first concrete purpose of modern Roza is regrouping tests into better classes to enable implicit setup, while broader refactoring generality remains a desired but still uncertain design goal.
- DEC-023: Clustering consumes the similarity matrix instead of test ASTs; the first matrix implementation is indexed internally by test case positions.
- DEC-024: The minimum loading API is `CodeFileLoader.load()`, `LoadedCodeFiles.codeFiles()`, and `CodeFile.content()`.
- DEC-025: `TestClass` has no minimum content API yet; its detailed shape remains deferred.
- DEC-026: The minimum parsing API includes `TestClassParser.parse(LoadedCodeFiles)` and `ParsedTestClasses.testClasses()`, while `TestClass` remains without a minimum content API.
- DEC-027: The decomposition interface is named `TestCaseDecomposer`, and its method is named `decompose`.
- DEC-028: A decomposed test is represented by `TestCase`, which exposes its name, body, source test class, and original test method annotations, but does not expose `id()`.
- DEC-029: `TestCaseDecomposer.decompose` returns `DecomposedTestCases`, which exposes decomposed test cases through `testCases()`.
- DEC-030: The measurement interface is named `TestCaseSimilarityMeasurer`, and its `measure` method receives `DecomposedTestCases` and returns `TestCaseSimilarityMatrix`.
- DEC-031: The clustering interface is named `TestCaseClusterer`, and its `cluster` method receives `TestCaseSimilarityMatrix` and returns `TestCaseClusters`.
- DEC-032: `TestCaseClusters` exposes `TestCaseCluster` instances through `clusters()`, while `TestCaseCluster` has no minimum content API yet.
- DEC-033: The refactoring interface is named `TestClassRefactorer`, and its `refactor` method receives `TestCaseClusters` and returns `RefactoredTestClasses`.
- DEC-034: `RefactoredTestClasses` exposes refactored `TestClass` instances through `testClasses()`.
- DEC-035: The writing interface is named `TestClassWriter`; its `write` method receives `RefactoredTestClasses` and does not return a serialized model.
- DEC-036: Modern Roza code should not include comments unless they explain non-obvious information.
- DEC-037: The loading contract belongs in the `br.ufsc.ine.leb.roza.core.modern.loading` package.
- DEC-038: The first concrete loading implementation is `FileSystemCodeFileLoader`, configured through its constructor with a folder, a recursive flag, and an extension list.
- DEC-039: `LoadedCodeFiles` and `CodeFile` are concrete loading data classes rather than extension interfaces; `CodeFileLoader` remains the extension interface for loading strategies.
- DEC-040: The first modern parsing model is Java-first rather than a language-universal AST abstraction.
- DEC-041: JavaParser is allowed inside concrete Java parser implementations, but JavaParser types must not appear in the public modern Roza parsing contract.
- DEC-042: Unsupported parser features are handled by `UnsupportedFeaturePolicy`, with `SAFE` as the conservative fail-fast mode and `UNSAFE` as the diagnostic-and-skip mode.
- DEC-043: Unsupported feature detection must run in a separate validator before extracting `TestClass`, `Field`, `TestMethod`, or other parsing models.
- DEC-044: `docs/roza-modern/requirements.md` must maintain a Current API Contracts section for pipeline interfaces and immediate stage boundary containers, updated whenever those contracts change.
- DEC-045: Parsed tests inside a class are `TestMethod`; decomposed tests are `TestCase` because they no longer belong to a source `TestClass` as methods.
- DEC-046: `TestCase` preserves the original parsed test method name so refactoring can use it later, including as a prefix when duplicate names exist.
- DEC-047: `TestCase` bodies include statements from supported `@Before`/`@BeforeEach` fixtures, while setup annotation choice for later refactoring is preserved through the source `TestClass`.
- DEC-048: LCCSS measurement should stop at the first assertion statement because code after assertions should not be measured as reusable setup/fixture code.
- DEC-049: `DefaultTestCaseDecomposer` keeps assertions in the full decomposed `TestCase` body; assertion handling belongs to measurement projections.
- DEC-050: LCCSS uses common contiguous prefix scoring: `(2 * commonPrefixSize) / (sourceProjectionSize + targetProjectionSize)`, not `commonPrefixSize / min(sourceProjectionSize, targetProjectionSize)`.
- DEC-051: Assertion detection belongs before measurement and is stored as `CodeStatement.isAssertion()` metadata; LCCSS must consume that metadata.
- DEC-052: The first modern parser implementation is named `JunitTestClassParser`, not `JavaTestClassParser`, because it is Java-based but JUnit-specific.
- DEC-053: Assertion detection uses an explicit supported method list for JUnit 4, current JUnit Jupiter, and Hamcrest `assertThat`, shared by parser and validator, not `startsWith("assert")`.
- DEC-054: The first `TestCaseSimilarityMatrix` is dense and directed; it must not assume metrics are symmetric.
- DEC-055: The first matrix write API is package-level `setSimilarity(int sourceIndex, int targetIndex, double similarity)` because LCCSS only needs to fill scores by index.
- DEC-056: The first measurement implementation is `LccssTestCaseSimilarityMeasurer`.
- DEC-057: Isolation between `core.modern` and `core.legacy` is bidirectional; imports must not cross between them in either direction.
- DEC-058: UI code is split into `ui.legacy` and `ui.modern`; the existing UI remains the legacy UI, and the modern UI will be developed separately.
- DEC-059: The modern UI uses JavaFX 17.x for the first slice and defers any Java version upgrade until a concrete limitation requires it.
- DEC-060: UI entrypoint names are explicit: `LegacyRozaUi` for the existing Swing UI and `ModernRozaUi` for the new JavaFX UI.
- DEC-061: The modern UI skeleton should favor a minimal visual layout over descriptive headings and nested cards.
- DEC-062: The modern UI skeleton can advance past the last pipeline stage so `Writing` can be visually marked as completed.
- DEC-063: Modern UI pipeline selection is tracked separately from completed/current/blocked pipeline status.
- DEC-064: Modern UI stage actions remain active for completed stages and reset only subsequent completed stages when triggered.
- DEC-065: `CodeFile.source()` is now required to support manual UI inspection of loaded files without adding parser responsibilities to loading.
- DEC-066: The first functional modern UI pipeline behavior is loading from the filesystem and presenting the loaded files as the previous-stage output in `Parsing`.
- DEC-067: The modern JavaFX configuration sidebar uses moderate margin-based vertical gaps between configuration groups (12px below the last control of a group, 4px above the following section title), a 12px top margin on the stage action button only after visible configuration controls, direct action-button placement for stages without visible configuration controls, and explicit single-line `ComboBox` height.
- DEC-068: The first experiment-oriented parser output carries structured unsupported-subset violations with class or test-method scope instead of silently discarding unsupported input.
- DEC-069: The first refactoring-safe supported subset excludes unsupported classes/tests before decomposition so experiment metrics can count only refactored items while reporting exclusions.
- DEC-070: Assertion detection is a syntactic operational model: Roza stops metrics at detectable assertions, not at every possible semantic assertion hidden behind arbitrary calls.
- DEC-071: The modern UI `Parsing` tab presents parsing violations as navigable context and selects the corresponding loaded class file for each displayed violation.
- DEC-072: The modern UI does not expose unsupported feature policy selection in the parsing configuration.
- DEC-073: Parsing violations carry a code snippet, and the modern UI shows it on the normal parsing background using `ClassName.methodName` for method targets.
- DEC-074: The modern UI `Decomposition` tab summarizes class-level violations, method-level test violations, tests excluded by violations, and accepted tests before showing decomposed test details.
- DEC-075: The modern UI `Measurement` tab uses the decomposed-test output as its input view, showing the accepted test list and selected test code before measurement is implemented.
- DEC-076: `TestCaseSimilarityMatrix` now exposes public read operations needed by the modern UI and future clustering implementation: `size()`, `testCaseAt(int)`, and `similarity(int, int)`.
- DEC-077: The modern UI `Measurement` tab runs LCCSS through a metric dropdown, and the `Clustering` tab inspects the resulting similarity matrix by selected source and target tests.
- DEC-078: The modern UI `Clustering` tab places source/target selectors in the center and uses a ranked similarity score list plus side-by-side source/target code blocks instead of rendering the full matrix as JavaFX nodes.
- DEC-079: The modern UI `Clustering` ranked list omits self-pairs and updates target code directly on list selection to avoid resetting the list scroll position.
- DEC-081: Ranked-list selection in the modern UI `Clustering` tab synchronizes both source and target selectors while suppressing full content rerendering.
- DEC-082: The modern UI `Clustering` ranked list is global over source-target pairs rather than filtered to the currently selected source.
- DEC-083: The modern UI loading stage defaults its source folder to Roza's own test source directory.
- DEC-084: The modern UI `Clustering` ranked score list is intentionally narrow to prioritize code comparison space.
- DEC-085: Modern LCS uses the same pre-assertion projection as LCCSS and computes Dice-style similarity from the longest common subsequence size.
- DEC-086: The modern UI `Measurement` metric dropdown offers LCCSS by default and LCS as an alternative.
- DEC-080: Default decomposition turns supported `@Before` assignments to parsed fields into initialized local declarations at the assignment position.
- DEC-087: Modern Deckard integration is gated by actual Deckard execution. On this machine, the local script fails because `python` is not available, while the existing Docker script executes successfully.
- DEC-088: Modern Deckard clone-report parsing uses a conservative same-cluster and same-`NODE_KIND`/`TBID`/`TEID` interpretation instead of the legacy Cartesian product across all cluster entries.
- DEC-089: Modern Deckard scoring is asymmetric line coverage over the source projected test body, merging overlapping intervals and excluding wrapper lines introduced for tool execution.
- DEC-090: The modern UI exposes Deckard's three user-facing configurations only when Deckard is selected: `MIN_TOKENS`, `STRIDE`, and `SIMILARITY`, with defaults `1`, `0`, and `1.0`.
- DEC-091: Modern JPlag integration is gated by actual JPlag execution. On this machine, the existing `jplag-2.11.9.jar` executes successfully through the script-style arguments used by legacy Roza.
- DEC-092: Modern JPlag exposes only `sensitivity` to users, with default `1` and positive-integer validation; language, match reporting, verbosity, log, results, and recursive source flags stay fixed to the legacy defaults.
- DEC-093: Modern JPlag report parsing reads each `match*-top.html` file and treats the two percentages in the first report table header as directional scores for the two materialized files.
- DEC-094: Modern Simian integration is gated by actual Simian execution. On this machine, the existing `simian-2.5.10.jar` executes successfully through the script-style arguments used by legacy Roza.
- DEC-095: Modern Simian exposes only `threshold` to users, with default `6` and validation that the value is at least `2`; all other Simian options stay fixed to the legacy defaults.
- DEC-096: Modern Simian report parsing ignores the textual license preamble before the XML declaration, reads each clone `<set>`, and converts the contained `<block>` entries into directed clone fragments scored by projected source line coverage.
- DEC-097: The first modern clustering implementation is `AgglomerativeHierarchicalTestCaseClusterer`.
- DEC-098: Linkage remains a clustering configuration strategy, with single, complete, and average linkage as the initial options.
- DEC-099: The stop-rule abstraction is named `StopCriterion`, not `ThresholdCriterion`, because some rules are not thresholds.
- DEC-100: `CompositeStopCriterion` uses OR semantics and accepts an empty list, which means no criterion stops clustering.
- DEC-101: Merge tie resolution is named `MergeTieBreaker`, not `Referee`.
- DEC-102: `CompositeMergeTieBreaker` uses ordered fallback semantics and accepts an empty list; unresolved actual ties throw an exception.
- DEC-103: The first deterministic merge-tie fallback is `StableTestCaseOrderMergeTieBreaker`, based on original similarity-matrix indexes rather than random choice.
- DEC-104: Agglomerative clustering exposes generated `ClusteringLevel` objects so tests and the UI can inspect the clustering sequence.
- DEC-105: Divisive hierarchical clustering is registered as future work, not part of the first clustering slice.
- DEC-106: `TestClass` exposes original import declarations because refactoring and rendering need to carry source-class context into generated classes.
- DEC-107: Parsing defines each `TestClass` setup annotation for generated implicit setup. It reuses an existing supported fixture annotation when present; otherwise it infers `@BeforeEach` from JUnit 5 `@Test` usage and `@Before` otherwise.
- DEC-108: A source class with both `@Before` and `@BeforeEach`, or with multiple setup fixtures, is unsupported for the first refactoring-safe subset.
- DEC-109: `TestCase` keeps a `sourceTestClass()` reference and the original test method annotations so refactoring does not need to infer imports or test-framework style from statements.
- DEC-110: The first refactoring implementation is `ImplicitSetupTestClassRefactorer`.
- DEC-111: `ImplicitSetupTestClassRefactorer` consumes setup annotation decisions from decomposed tests and only handles the implicit-setup transformation: common non-assertion prefix extraction, field/setup generation, and test-method generation.
- DEC-112: Rendering generated test classes to Java source is handled by `JunitTestClassRenderer`, separate from the refactoring stage output model.
- DEC-113: The modern UI `Writing` tab displays generated test classes as a list and shows rendered code for the selected class.
- DEC-114: The modern UI `Refactoring` tab has two actions: `Refactor` uses the final clustering level, while `Refactor Current level` uses the level selected in the level inspector.
- DEC-115: The first writing implementation is `FileSystemTestClassWriter`; it renders refactored `TestClass` models as Java files in a configured output folder.

## Hypotheses

- HYP-001: The pipeline can likely remain abstract and flexible if the generic core stops at stage contracts and each concrete purpose is isolated in implementations, especially in measurement, clustering, and refactoring. The first vertical slice should stay anchored in implicit-setup regrouping to prevent premature abstractions.
- HYP-002: A useful boundary may be to keep `loading`, `parsing`, `decomposition`, `measurement`, and `clustering` relatively purpose-neutral, while allowing `refactoring` implementations to encode concrete refactoring intentions such as implicit setup or delegated setup.
- HYP-003: Later refactoring implementations may need additional source context beyond `TestCase.sourceTestClass()`, but no extra context should be added until a concrete implementation requires it.

## Interview Backlog

- Confirm whether the high-level pipeline stages are complete: `loading`, `parsing`, `decomposition`, `measurement`, `clustering`, `refactoring`, and `writing`.
- Define the input and output contract for the whole pipeline.
- Decide which additional attributes `CodeFile` needs beyond `content()` when a confirmed requirement makes them necessary.
- Refine the Java-first `TestClass` model as decomposition, measurement, refactoring, and writing expose real needs.
- Decide how parsing should represent ASTs and identified test classes in the core model.
- Explore whether an existing non-legacy AST abstraction can support `TestClass`.
- Decide whether later core contracts should model files, ASTs, tests, changes, or all of them.
- Define how pipeline composition is configured.
- Define how failures, partial results, and unsupported inputs are represented.
- Define what the first runnable vertical slice should prove.
- Return to detailed `TestClass` design after the high-level pipeline is mapped.
- Define the minimum `TestCaseSimilarityMatrix` API and decide whether separate similarity metric or score abstractions are necessary.
- Investigate how far the pipeline can generalize beyond the implicit-setup regrouping purpose without weakening the concrete first use case.
- Define the public read API that clustering needs from `TestCaseSimilarityMatrix`.
- Define how clusters should refer back to grouped `TestCase` instances without adding premature attributes to `TestCase`.

## Change Log

- 2026-05-10: Created initial knowledge base for modern Roza.
- 2026-05-10: Clarified that generic Roza pipeline/refactoring work can also describe legacy Roza and should not automatically trigger modern Roza requirements.
- 2026-05-10: Clarified the separation between user-confirmed requirements and agent-maintained understanding.
- 2026-05-10: Updated understanding to focus modern Roza input on loaded tests.
- 2026-05-10: Removed an incorrect implementation note that treated `core.modern` as an empty architecture area.
- 2026-05-10: Recorded `loading` as the first confirmed pipeline stage and captured candidate naming work for its interface.
- 2026-05-10: Confirmed `TestCodeLoader` as the loading interface and clarified that it loads raw test code.
- 2026-05-10: Recorded `parsing` as the second confirmed pipeline stage and clarified its responsibility boundary with `loading`.
- 2026-05-10: Reopened the loading interface name and refined loading to raw code files that may not contain tests.
- 2026-05-10: Confirmed `CodeFileLoader` as the loading interface, replacing the earlier `TestCodeLoader` candidate.
- 2026-05-10: Confirmed `LoadedCodeFiles` and `CodeFile` as loading output model names, with `CodeFile` attributes intentionally deferred.
- 2026-05-10: Recorded that modern Roza must not use or reuse legacy Roza classes.
- 2026-05-10: Confirmed `TestClassParser`, `ParsedTestClasses`, and `TestClass` as parsing-stage model names.
- 2026-05-10: Recorded `TestClass` as the generalized root AST abstraction and the most delicate model point currently known.
- 2026-05-10: Deferred detailed `TestClass` design until the high-level pipeline is better understood.
- 2026-05-10: Recorded `decomposition` as the third confirmed pipeline stage.
- 2026-05-10: Recorded `measurement` as the fourth confirmed pipeline stage.
- 2026-05-10: Recorded `clustering` as the fifth confirmed pipeline stage.
- 2026-05-10: Recorded `refactoring` as the sixth confirmed pipeline stage.
- 2026-05-10: Recorded `writing` as the final confirmed pipeline stage.
- 2026-05-10: Recorded the tension between the concrete implicit-setup regrouping purpose and the desired broader refactoring generality.
- 2026-05-10: Added hypotheses about keeping the early/middle stages purpose-neutral between measurement, clustering, and refactoring.
- 2026-05-10: Confirmed that clustering consumes a similarity matrix instead of ASTs.
- 2026-05-10: Confirmed the minimum loading API as `CodeFileLoader.load()`, `LoadedCodeFiles.codeFiles()`, and `CodeFile.content()`.
- 2026-05-10: Confirmed that `TestClass` has no minimum content API yet.
- 2026-05-10: Confirmed that `ParsedTestClasses` exposes parsed test classes through `testClasses()`.
- 2026-05-10: Confirmed the decomposition interface as `TestMethodDecomposer`, its method as `decompose`, the return type as `DecomposedTestMethods`, and the decomposed test model as `TestMethod` with no minimum content API.
- 2026-05-10: Confirmed the measurement interface as `TestMethodSimilarityMeasurer`, its method as `measure`, and its contract from `DecomposedTestMethods` to `TestMethodSimilarityMatrix`.
- 2026-05-10: Confirmed the clustering interface as `TestMethodClusterer`, its method as `cluster`, and its contract from `TestMethodSimilarityMatrix` to `TestMethodClusters`.
- 2026-05-10: Confirmed the refactoring interface as `TestClassRefactorer`, its method as `refactor`, and its contract from `TestMethodClusters` to `RefactoredTestClasses`.
- 2026-05-10: Confirmed the final stage as `writing`, represented by `TestClassWriter.write(RefactoredTestClasses)` without returning a serialized model.
- 2026-05-10: Implemented the minimum loading API in `core.modern`.
- 2026-05-10: Recorded the code comment policy and removed obvious comments from the minimum loading API.
- 2026-05-10: Moved the minimum loading API to the `core.modern.loading` package.
- 2026-05-10: Implemented and tested the first concrete filesystem loader for folder inclusion, recursion, and extension filtering.
- 2026-05-11: Changed `LoadedCodeFiles` and `CodeFile` from interfaces to concrete data classes.
- 2026-05-11: Pivoted parsing from a language-universal AST goal to a Java-first domain model that keeps JavaParser behind concrete implementations.
- 2026-05-11: Recorded `UnsupportedFeaturePolicy` and the separate unsupported-feature validator as the conservative parsing strategy.
- 2026-05-11: Recorded that the requirements file is the durable home for the current pipeline interface and stage boundary container snapshot.
- 2026-05-11: Renamed decomposed-test terminology from `TestMethod` to `TestCase` and propagated the change through decomposition, measurement, clustering, and refactoring contracts.
- 2026-05-11: Confirmed that `TestCase` preserves the original parsed test method name for later refactoring use.
- 2026-05-11: Confirmed that `TestCase` does not need to preserve the `@Before` annotation, only the statements derived from supported `@Before` fixtures.
- 2026-05-11: Confirmed that LCCSS measurement should stop at the first assertion statement rather than merely filtering assertions.
- 2026-05-11: Implemented the first `DefaultTestCaseDecomposer` and recorded that decomposition preserves assertions while measurement projections decide what to measure.
- 2026-05-11: Recorded the LCCSS formula from legacy behavior without reusing legacy classes.
- 2026-05-11: Recorded that JUnit 5 `@AfterEach` is unsupported in the first parser slice, like JUnit 4 `@After`.
- 2026-05-11: Added assertion metadata to `CodeStatement` and recorded that measurement must use it instead of detecting assertions from text.
- 2026-05-11: Renamed the modern concrete parser implementation to `JunitTestClassParser`.
- 2026-05-11: Replaced assertion prefix matching with an explicit supported assertion method list for JUnit 4, current JUnit Jupiter, and Hamcrest `assertThat`.
- 2026-05-11: Implemented the first measurement contracts, dense directed similarity matrix, and LCCSS measurer.
- 2026-05-11: Recorded bidirectional import isolation between `core.modern` and `core.legacy`.
- 2026-05-11: Split UI packages into `ui.legacy` and `ui.modern` to prepare for a new modern Roza UI.
- 2026-05-11: Recorded JavaFX 17.x as the first modern UI technology and defined the initial pipeline navigation skeleton.
- 2026-05-11: Recorded the minimal layout direction for the modern UI skeleton.
- 2026-05-11: Recorded that the modern UI skeleton can visually complete the final writing stage.
- 2026-05-11: Recorded that modern UI pipeline stage selection is independent from stage completion status.
- 2026-05-11: Recorded the black toolbar, neutral color direction, and completed-stage reset behavior for the modern UI skeleton.
- 2026-05-11: Recorded that blocked pipeline stage buttons should use a lighter gray treatment.
- 2026-05-11: Recorded the first functional loading behavior in the modern UI and the resulting `CodeFile.source()` requirement.
- 2026-05-11: Recorded the first refactoring-safe supported subset and structured parsing violation model for experiment exclusions.
- 2026-05-11: Recorded the modern UI parsing violation navigation behavior.
- 2026-05-11: Recorded that unsupported feature policy selection is not part of the modern UI parsing configuration.
- 2026-05-11: Recorded parsing violation code snippets and the simplified UI rendering for violation details.
- 2026-05-11: Recorded the modern UI `Decomposition` summary for violation and accepted-test counts.
- 2026-05-11: Removed parsing violation records from the modern UI `Decomposition` summary.
- 2026-05-11: Recorded the modern UI `Measurement` input view for decomposed tests.
- 2026-05-11: Recorded LCCSS measurement execution and clustering matrix inspection in the modern UI.
- 2026-05-11: Recorded the ranked-list clustering inspection behavior that replaces full matrix rendering in the modern UI.
- 2026-05-11: Recorded ranked-list refinements for self-pair omission, score-only labels, and scroll preservation.
- 2026-05-11: Recorded source/target synchronization for ranked-list selection in the modern UI `Clustering` tab.
- 2026-05-11: Recorded that the modern UI `Clustering` ranked list ranks global source-target pairs.
- 2026-05-11: Recorded the default modern UI loading source folder as Roza's own test source directory.
- 2026-05-11: Recorded the narrower ranked score list in the modern UI `Clustering` tab.
- 2026-05-11: Recorded modern LCS measurement and UI selection.
- 2026-05-11: Recorded the decomposition correction for field assignments in supported `@Before` fixtures.
- 2026-05-11: Documented modern UI sidebar spacing conventions (margin-based group gaps, action-button top margin, single-line ComboBox) and recorded DEC-067.
- 2026-05-11: Recorded validated Deckard execution, conservative Deckard report parsing, asymmetric Deckard scoring, and UI configuration exposure.
- 2026-05-11: Recorded validated JPlag execution, sensitivity-only configuration, HTML report parsing, and directional score handling.
- 2026-05-11: Recorded validated Simian execution, threshold-only configuration, XML report parsing, and asymmetric line-coverage scoring.
- 2026-05-12: Recorded the first modern clustering implementation decisions for agglomerative clustering, linkage, stop criteria, merge tie breakers, level inspection, deterministic fallback, and divisive clustering as future work.
- 2026-05-12: Recorded that modern UI stages without visible sidebar configuration controls place their action button directly in the sidebar to avoid empty configuration spacing.
- 2026-05-12: Recorded the modern UI writing output folder selector and default `output/writer` destination.
- 2026-05-12: Recorded the first modern refactoring implementation, setup annotation inference boundary, source-class context preservation, generated-class rendering, and Writing-tab class/code inspection.
- 2026-05-12: Recorded separate modern UI refactoring actions for final clustering output and the currently selected clustering level.
- 2026-05-12: Recorded and implemented `FileSystemTestClassWriter` as the first concrete writing implementation.
