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
- `ParsedTestClasses`: the result returned by `TestClassParser.parse`; it exposes `TestClass` instances through `testClasses()`.
- `UnsupportedFeaturePolicy`: the parser policy for unsupported Java test-code features. `SAFE` fails with a clear error; `UNSAFE` records diagnostics and skips unsupported input.
- `JavaUnsupportedFeatureValidator`: the separate validation step that runs before Java parser implementations extract modern Roza domain models.
- `TestClass`: the Java-first root domain abstraction for an identified Java test class. It hides JavaParser types from the public modern Roza contract.
- `Field`: a parsed Java field that decomposition can later transform into a local variable. Its type text must preserve complex Java forms such as generics, nested generics, wildcard generics, qualified types, generic arrays, arrays, and multidimensional arrays.
- `FixtureMethod`: a supported instance fixture method such as JUnit 4 `@Before`; JUnit 4 `@After` and JUnit 5 `@AfterEach` are unsupported in the first parser slice.
- `HelperMethod`: a supported non-test helper method in the Java test class.
- `CodeBlock`: a block of parsed top-level code statements.
- `CodeStatement`: a statement-level code unit with original and normalized text for later decomposition and similarity measurement.
- Decomposition stage: the third modern Roza pipeline stage; it separates each test from its original class and carries the code required by that test.
- `TestCaseDecomposer`: the decomposition stage interface.
- `DecomposedTestCases`: the result returned by `TestCaseDecomposer.decompose`; it exposes decomposed `TestCase` instances through `testCases()`.
- `DefaultTestCaseDecomposer`: the first concrete decomposition implementation. It creates one `TestCase` per parsed test method and orders the decomposed body as field declarations, supported `@Before` statements, then original test body statements.
- `TestCase`: the model for a decomposed test. It is called a test case because after decomposition it is no longer exactly a method inside a test class. It preserves the original parsed test method name through `name()`, including duplicate names. Its `body()` includes statements derived from supported `@Before` fixtures and preserves assertions, but it does not need to preserve the `@Before` annotation itself. It does not expose `id()` or `testClass()` yet.
- Measurement stage: the fourth modern Roza pipeline stage; it applies a similarity metric to each pair of decomposed tests and produces a similarity matrix.
- `TestCaseSimilarityMeasurer`: the measurement stage interface.
- `TestCaseSimilarityMatrix`: the result returned by `TestCaseSimilarityMeasurer.measure`; it is indexed by abstract test case identities and has no minimum content API beyond that yet.
- Indexed similarity matrix: the measurement output that records pairwise similarity degrees and is indexed by abstract test identities.
- Abstract test identity: an identity used to refer to a decomposed test across measurement, clustering, and refactoring without exposing the test AST to clustering.
- Clustering stage: the fifth modern Roza pipeline stage; it groups similar tests using only the similarity matrix.
- `TestCaseClusterer`: the clustering stage interface.
- `TestCaseClusters`: the result returned by `TestCaseClusterer.cluster`; it exposes `TestCaseCluster` instances through `clusters()`.
- `TestCaseCluster`: the model for a cluster of test cases. It has no minimum content API yet.
- Refactoring stage: the sixth modern Roza pipeline stage; it receives test groups and decides what refactoring to apply to them.
- `TestClassRefactorer`: the refactoring stage interface.
- `RefactoredTestClasses`: the result returned by `TestClassRefactorer.refactor`; it exposes refactored `TestClass` instances through `testClasses()`.
- Writing stage: the final modern Roza pipeline stage; it writes refactored test classes to an output destination such as the filesystem or cloud.
- `TestClassWriter`: the writing stage interface.

## Current Architectural Understanding

Modern Roza is intended to be a reusable and extensible framework for automated test-code refactoring. Its central abstraction is a pipeline composed of independent stages. Each stage should be customizable through interchangeable implementations.

The pipeline must allow combinations such as:

- stage 1 implementation 1 with stage 2 implementation 1;
- stage 1 implementation 1 with stage 2 implementation 2;
- future implementations added without changing unrelated stages.

The framework should be designed for broad extension across programming languages, test frameworks, refactoring types, and analysis techniques. The initial pipeline will impose some limits, but core abstractions should avoid unnecessary assumptions.

The first confirmed pipeline stage is `loading`. It has one shared interface named `CodeFileLoader` with a single method named `load`. The method returns `LoadedCodeFiles`, a concrete data class that exposes concrete `CodeFile` instances through `codeFiles()`. Each `CodeFile` exposes raw textual content through `content()`. The name avoids implying that every loaded file contains tests. Concrete loading implementations are configured through constructor parameters, allowing different strategies such as recursive filesystem loading, extension-based filesystem loading, and Git-based loading.

The second confirmed pipeline stage is `parsing`. Its interface is named `TestClassParser`. It receives the in-memory raw code files produced by `loading`, determines which loaded files contain test classes, identifies those test classes, and creates one Java-first domain model per identified test class. Its `parse` method returns `ParsedTestClasses`, which exposes `TestClass` instances through `testClasses()`. This keeps loading focused on loading files and leaves structural interpretation to parsing.

The initial `TestClass` model is Java-first, not language-universal. The architecture should still preserve pipeline extension points, but it should not sacrifice the quality of the Java model to prematurely support Python, Rust, or other languages. JavaParser may be used inside concrete Java parser implementations, but JavaParser types should not become the public modern Roza model.

The first supported parser scenario should be deliberately conservative. Unsupported Java/JUnit features are validated by a separate validator before model extraction. `UnsupportedFeaturePolicy.SAFE` fails fast with a clear error; `UnsupportedFeaturePolicy.UNSAFE` records diagnostics and skips unsupported input rather than silently accepting it. The project should start with many unsupported features and remove them from the unsupported list only when real support is implemented.

The third confirmed pipeline stage is `decomposition`. Its interface is named `TestCaseDecomposer`, and its method is named `decompose`. It receives parsed test classes and separates each test from its class of origin. `TestCaseDecomposer.decompose` returns `DecomposedTestCases`, which exposes decomposed `TestCase` instances through `testCases()`. Each decomposed test is represented by `TestCase`. The name distinguishes decomposed tests from parsed `TestMethod` models, which still represent methods inside a `TestClass`. `TestCase` preserves the original parsed test method name for later refactoring use, even when names are duplicated. It does not need to preserve the source `@Before` annotation or fixture identity; it only needs the statements derived from supported `@Before` fixtures in its decomposed body. Assertions remain in the full `TestCase` body; measurement-specific projections decide whether to stop before assertions. For now, `TestCase` does not expose `id()` or `testClass()`. In the implicit setup example, a class with one implicit setup and two tests produces two decomposed test case models, each containing field declarations, the implicit setup statements, and the body of one test.

The fourth confirmed pipeline stage is `measurement`. Its interface is named `TestCaseSimilarityMeasurer`, and its method is named `measure`. It receives `DecomposedTestCases` and returns `TestCaseSimilarityMatrix`. It applies a similarity metric to each pair of decomposed test cases. Similarity metrics can have different objectives, with duplicated test/code identification as the most common one. The returned matrix records the similarity degree for each pair according to the applied metric and is indexed by abstract test case identities. Measurement may use a projection of the `TestCase` body instead of every statement. The LCCSS measurement must stop its measured projection at the first assertion statement, not merely filter assertion statements. `TestCaseSimilarityMatrix` has no minimum content API beyond that yet.

The fifth confirmed pipeline stage is `clustering`. Its interface is named `TestCaseClusterer`, and its method is named `cluster`. It receives `TestCaseSimilarityMatrix` and returns `TestCaseClusters`, which exposes `TestCaseCluster` instances through `clusters()`. It groups tests according to the indexed similarity matrix produced by `measurement`. This stage intentionally does not know anything about the tests themselves; it sees only the similarity matrix and the abstract identities used to index it. Architecturally, this starts rebuilding structure after the earlier decomposition split test classes into separated test-level ASTs. `TestCaseCluster` has no minimum content API yet.

The sixth confirmed pipeline stage is `refactoring`. Its interface is named `TestClassRefactorer`, and its method is named `refactor`. It receives `TestCaseClusters` and returns `RefactoredTestClasses`, which exposes refactored `TestClass` instances through `testClasses()`. It receives the groups produced by `clustering` and decides what to do with them. The first concrete purpose is to refactor test classes by regrouping tests into better classes so implicit setup can be used, but the pipeline may be able to support other refactoring purposes, such as delegated setup, through different refactoring implementations.

The final confirmed pipeline stage is `writing`. Its interface is named `TestClassWriter`, and its method is named `write`. It receives `RefactoredTestClasses` and writes them to an output destination without returning a serialized model. The destination may be the filesystem or a cloud target, depending on the writer implementation. Concrete writer implementations receive required output destination parameters through constructors.

## Implementation Notes

- The minimum loading API is implemented in `src/main/java/br/ufsc/ine/leb/roza/core/modern/loading` as `CodeFileLoader`, `LoadedCodeFiles`, and `CodeFile`.
- `FileSystemCodeFileLoader` is implemented in `src/main/java/br/ufsc/ine/leb/roza/core/modern/loading` and covered by tests in `src/test/java/br/ufsc/ine/leb/roza/core/modern/loading`.
- The first parsing implementation is being designed as Java-first and belongs in `src/main/java/br/ufsc/ine/leb/roza/core/modern/parsing`.
- The first decomposition implementation is `DefaultTestCaseDecomposer` in `src/main/java/br/ufsc/ine/leb/roza/core/modern/decomposition`.
- Code comments should be avoided unless they explain something non-obvious.

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
- DEC-023: The similarity matrix is indexed by abstract test identities, and clustering consumes that indexed matrix instead of test ASTs.
- DEC-024: The minimum loading API is `CodeFileLoader.load()`, `LoadedCodeFiles.codeFiles()`, and `CodeFile.content()`.
- DEC-025: `TestClass` has no minimum content API yet; its detailed shape remains deferred.
- DEC-026: The minimum parsing API includes `TestClassParser.parse(LoadedCodeFiles)` and `ParsedTestClasses.testClasses()`, while `TestClass` remains without a minimum content API.
- DEC-027: The decomposition interface is named `TestCaseDecomposer`, and its method is named `decompose`.
- DEC-028: A decomposed test is represented by `TestCase`, which has no minimum content API yet and does not expose `id()` or `testClass()`.
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
- DEC-047: `TestCase` bodies include statements from supported `@Before` fixtures, but `TestCase` does not need to preserve the `@Before` annotation itself.
- DEC-048: LCCSS measurement should stop at the first assertion statement because code after assertions should not be measured as reusable setup/fixture code.
- DEC-049: `DefaultTestCaseDecomposer` keeps assertions in the full decomposed `TestCase` body; assertion handling belongs to measurement projections.

## Hypotheses

- HYP-001: The pipeline can likely remain abstract and flexible if the generic core stops at stage contracts and each concrete purpose is isolated in implementations, especially in measurement, clustering, and refactoring. The first vertical slice should stay anchored in implicit-setup regrouping to prevent premature abstractions.
- HYP-002: A useful boundary may be to keep `loading`, `parsing`, `decomposition`, `measurement`, and `clustering` relatively purpose-neutral, while allowing `refactoring` implementations to encode concrete refactoring intentions such as implicit setup or delegated setup.
- HYP-003: Refactoring may use the abstract identities returned by clustering to recover grouped decomposed tests, but the exact lookup/ownership model remains undecided. Those identities are not part of the `TestCase` API for now.

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
- Decide the first concrete output destination for `TestClassWriter`.
- Investigate how far the pipeline can generalize beyond the implicit-setup regrouping purpose without weakening the concrete first use case.
- Define the exact abstract test identity model and where those identities are created.
- Define how abstract test identities relate to `TestCase` without adding premature attributes to `TestCase`.

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
- 2026-05-10: Added hypotheses about keeping the early/middle stages purpose-neutral and using abstract test identities between measurement, clustering, and refactoring.
- 2026-05-10: Confirmed that the similarity matrix is indexed by abstract test identities and that clustering consumes this indexed matrix instead of ASTs.
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
- 2026-05-11: Recorded that JUnit 5 `@AfterEach` is unsupported in the first parser slice, like JUnit 4 `@After`.
