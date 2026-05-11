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
- AC-121: Unsupported parsing features are handled by `UnsupportedFeaturePolicy`.
- AC-122: Unsupported feature validation runs in a separate validator before extracting modern Roza domain models.
- AC-123: `UnsupportedFeaturePolicy.SAFE` fails immediately with a clear error when unsupported features are found.
- AC-124: `UnsupportedFeaturePolicy.UNSAFE` records diagnostics and skips unsupported input instead of silently accepting it.

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
- AC-132: `CodeStatement` exposes original text and normalized text.
- AC-133: The first parser treats control-flow blocks such as `for`, `while`, and `if` as top-level statements rather than flattening their internals.
- AC-134: Static members, nested classes, unsupported lifecycle features, parameterized tests, inheritance, static imports, and other listed unsupported features are rejected or skipped according to `UnsupportedFeaturePolicy`.
- AC-135: Modern Roza starts conservative: unsupported features should be removed from the unsupported list only when real support is implemented.

### REQ-010: Decomposition Stage

The third stage of the modern Roza pipeline must be the decomposition stage, responsible for separating each test from its original test class while carrying the code required by that test.

Acceptance criteria:

- AC-048: The decomposition stage consumes parsed test classes.
- AC-049: The decomposition stage extracts each test from its source `TestClass`.
- AC-050: The decomposition stage produces a new AST for each extracted test.
- AC-051: Each produced test AST contains the original test body plus the code required by that test.
- AC-052: If a source test class has an implicit setup and two tests, decomposition produces one AST per test, and each produced AST includes the implicit setup code plus that test's original body.
- AC-053: The decomposition stage separates tests from their class of origin.
- AC-080: The decomposition stage is represented by one interface named `TestMethodDecomposer`.
- AC-081: The decomposition interface has a method named `decompose`.
- AC-082: Each decomposed test is represented by `TestMethod`.
- AC-083: `TestMethod` has no minimum content API until confirmed requirements make one necessary.
- AC-084: `TestMethod` must not expose `id()` or `testClass()` unless future confirmed requirements make them necessary.
- AC-085: `TestMethodDecomposer.decompose` returns `DecomposedTestMethods`.
- AC-086: `DecomposedTestMethods` exposes decomposed test methods through `testMethods()`.

### REQ-011: Measurement Stage

The fourth stage of the modern Roza pipeline must be the measurement stage, responsible for applying a similarity metric to each pair of decomposed tests.

Acceptance criteria:

- AC-054: The measurement stage consumes decomposed tests.
- AC-055: The measurement stage applies a similarity metric to each pair of tests.
- AC-056: Similarity metrics may have different objectives.
- AC-057: Identifying duplicated tests or duplicated test code is the most common objective for a similarity metric.
- AC-058: The measurement stage produces a similarity matrix.
- AC-059: The similarity matrix indicates the similarity degree for each pair of tests according to the applied metric.
- AC-074: The similarity matrix is indexed by abstract test identities.
- AC-087: The measurement stage is represented by one interface named `TestMethodSimilarityMeasurer`.
- AC-088: The measurement interface has a method named `measure`.
- AC-089: `TestMethodSimilarityMeasurer.measure` receives `DecomposedTestMethods`.
- AC-090: `TestMethodSimilarityMeasurer.measure` returns `TestMethodSimilarityMatrix`.

### REQ-012: Clustering Stage

The fifth stage of the modern Roza pipeline must be the clustering stage, responsible for grouping the tests that are most similar to each other according to the similarity matrix.

Acceptance criteria:

- AC-060: The clustering stage consumes the similarity matrix produced by the measurement stage.
- AC-061: The clustering stage groups tests according to the similarities represented in the similarity matrix.
- AC-062: The clustering stage does not inspect or depend on the test ASTs directly.
- AC-063: The clustering stage knows only the information represented in the similarity matrix.
- AC-064: The clustering stage reverses the earlier decomposition direction by constructing groups from tests that were previously separated from their original classes.
- AC-075: The clustering stage consumes the indexed similarity matrix rather than test ASTs.
- AC-091: The clustering stage is represented by one interface named `TestMethodClusterer`.
- AC-092: The clustering interface has a method named `cluster`.
- AC-093: `TestMethodClusterer.cluster` receives `TestMethodSimilarityMatrix`.
- AC-094: `TestMethodClusterer.cluster` returns `TestMethodClusters`.
- AC-095: `TestMethodClusters` exposes test method clusters through `clusters()`.
- AC-096: Each test method cluster is represented by `TestMethodCluster`.
- AC-097: `TestMethodCluster` has no minimum content API until confirmed requirements make one necessary.

### REQ-013: Refactoring Stage

The sixth stage of the modern Roza pipeline must be the refactoring stage, responsible for receiving test groups and deciding what refactoring to apply to them.

Acceptance criteria:

- AC-065: The refactoring stage consumes groups produced by the clustering stage.
- AC-066: The refactoring stage decides what to do with each group.
- AC-067: The initial concrete refactoring purpose is to refactor test classes by regrouping tests into better classes so implicit setup can be used.
- AC-068: The pipeline design should leave room for other refactoring strategies, such as delegated setup, when supported by future refactoring implementations.
- AC-098: The refactoring stage is represented by one interface named `TestClassRefactorer`.
- AC-099: The refactoring interface has a method named `refactor`.
- AC-100: `TestClassRefactorer.refactor` receives `TestMethodClusters`.
- AC-101: `TestClassRefactorer.refactor` returns `RefactoredTestClasses`.
- AC-102: `RefactoredTestClasses` exposes refactored test classes through `testClasses()`.
- AC-103: `RefactoredTestClasses.testClasses()` returns `TestClass` instances.

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

The existing Roza implementation lives under `core.legacy`. Modern Roza architecture work should go under `core.modern` unless the user explicitly asks to change legacy code.

Acceptance criteria:

- AC-035: Modern Roza implementation must not use classes from legacy Roza.
- AC-036: Modern Roza implementation must not reuse classes from legacy Roza.
- AC-037: Modern Roza implementation must be written from scratch.
- AC-038: Code in `core.modern` must not import from `core.legacy`.

### NFR-004: Minimal Code Comments

Modern Roza code must not include comments unless they explain something that is not obvious from the code itself.

Acceptance criteria:

- AC-109: Do not add comments that merely restate type names, method names, or obvious behavior.
- AC-110: Add a code comment only when it explains non-obvious intent, constraints, or reasoning.

## Open Questions

### Q-001: What are the initial pipeline stages?

The high-level pipeline stages are `loading`, `parsing`, `decomposition`, `measurement`, `clustering`, `refactoring`, and `writing`. Confirm whether this is the complete high-level pipeline or whether optional/additional stages are expected.

### Q-002: What is the exact stage-to-stage data flow?

The user stated that stages do not communicate directly and that each stage generates output consumed later. Confirm whether each output is consumed by the next stage, by the pipeline orchestrator, or by another explicit routing mechanism.

### Q-003: What is the minimum raw code loading input model?

`CodeFileLoader.load` returns `LoadedCodeFiles`, which contains `CodeFile` instances and exposes them through `codeFiles()`. `CodeFile` exposes raw textual content through `content()`. Other attributes remain undecided until they are required.

### Q-004: What is the first target implementation slice?

Clarify whether the first implementation should be only interfaces/model contracts or also a small runnable pipeline skeleton.

### Q-005: Should TestClass use an existing AST abstraction?

Explore whether modern Roza should use or adapt an existing AST abstraction for `TestClass`, while respecting the requirement that modern Roza must not use or reuse legacy Roza classes.

### Q-006: What is the detailed design of TestClass?

`TestClass` is a crucial inflection point for the project. Its detailed design and minimum content API should be deferred until the whole pipeline is understood at a high level.

### Q-007: What are the decomposition interface and output model names?

The decomposition interface is named `TestMethodDecomposer`, and its method is named `decompose`. `TestMethodDecomposer.decompose` returns `DecomposedTestMethods`, which exposes decomposed test methods through `testMethods()`. Each decomposed test is represented by `TestMethod`, which has no minimum content API yet.

### Q-008: What are the measurement interface and output model names?

The measurement interface is named `TestMethodSimilarityMeasurer`, and its method is named `measure`. `TestMethodSimilarityMeasurer.measure` receives `DecomposedTestMethods` and returns `TestMethodSimilarityMatrix`. The minimum API of `TestMethodSimilarityMatrix`, the exact abstract test identity model, and any separate similarity metric or score abstractions are not defined yet.

### Q-009: What are the clustering interface and output model names?

The clustering interface is named `TestMethodClusterer`, and its method is named `cluster`. `TestMethodClusterer.cluster` receives `TestMethodSimilarityMatrix` and returns `TestMethodClusters`, which exposes test method clusters through `clusters()`. Each cluster is represented by `TestMethodCluster`, which has no minimum content API yet.

### Q-010: What are the refactoring interface and output model names?

The refactoring interface is named `TestClassRefactorer`, and its method is named `refactor`. `TestClassRefactorer.refactor` receives `TestMethodClusters` and returns `RefactoredTestClasses`, which exposes refactored `TestClass` instances through `testClasses()`.

### Q-011: What output destinations should writing support first?

The final output stage is named `writing`. Its interface is named `TestClassWriter`, and its method is named `write`. `TestClassWriter.write` receives `RefactoredTestClasses` and does not return a serialized model. The first concrete output destination remains undefined.

### Q-012: How generic can the pipeline be beyond the initial implicit-setup refactoring purpose?

The initial concrete purpose is to refactor test classes by regrouping tests into better classes so implicit setup can be used. The desired broader goal is an abstract and flexible pipeline for different refactoring purposes, but the feasibility and exact boundaries of that generality remain open.

### Q-013: What is the exact model for abstract test identities?

The similarity matrix must be indexed by abstract test identities, but the exact identity model and where those identities are created are not defined yet. `TestMethod` does not expose an `id()` method for now.

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
- 2026-05-10: Confirmed that the similarity matrix is indexed by abstract test identities and that clustering consumes this indexed matrix instead of ASTs.
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
