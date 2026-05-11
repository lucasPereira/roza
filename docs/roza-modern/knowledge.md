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
- `LoadedCodeFiles`: the result returned by `CodeFileLoader.load`; it exposes loaded `CodeFile` instances through `codeFiles()`.
- `CodeFile`: a raw loaded code file. Its minimum confirmed API exposes raw textual content through `content()`. Other attributes remain undefined until confirmed requirements make them necessary.
- Parsing stage: the second modern Roza pipeline stage; it reads loaded raw code files and creates ASTs for identified test classes.
- `TestClassParser`: the parsing stage interface.
- `ParsedTestClasses`: the result returned by `TestClassParser.parse`; it exposes `TestClass` instances through `testClasses()`.
- `TestClass`: the generalized root AST abstraction for an identified test class and the parent of all its sub-ASTs. It has no minimum content API yet.
- Decomposition stage: the third modern Roza pipeline stage; it separates each test from its original class and carries the code required by that test.
- `TestMethodDecomposer`: the decomposition stage interface.
- `DecomposedTestMethods`: the result returned by `TestMethodDecomposer.decompose`; it exposes decomposed `TestMethod` instances through `testMethods()`.
- `TestMethod`: the model for a decomposed test. It has no minimum content API yet and does not expose `id()` or `testClass()`.
- Measurement stage: the fourth modern Roza pipeline stage; it applies a similarity metric to each pair of decomposed tests and produces a similarity matrix.
- `TestMethodSimilarityMeasurer`: the measurement stage interface.
- `TestMethodSimilarityMatrix`: the result returned by `TestMethodSimilarityMeasurer.measure`; it is indexed by abstract test method identities and has no minimum content API beyond that yet.
- Indexed similarity matrix: the measurement output that records pairwise similarity degrees and is indexed by abstract test identities.
- Abstract test identity: an identity used to refer to a decomposed test across measurement, clustering, and refactoring without exposing the test AST to clustering.
- Clustering stage: the fifth modern Roza pipeline stage; it groups similar tests using only the similarity matrix.
- `TestMethodClusterer`: the clustering stage interface.
- `TestMethodClusters`: the result returned by `TestMethodClusterer.cluster`; it exposes `TestMethodCluster` instances through `clusters()`.
- `TestMethodCluster`: the model for a cluster of test methods. It has no minimum content API yet.
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

The first confirmed pipeline stage is `loading`. It has one shared interface named `CodeFileLoader` with a single method named `load`. The method returns `LoadedCodeFiles`, which exposes `CodeFile` instances through `codeFiles()`. Each `CodeFile` exposes raw textual content through `content()`. The name avoids implying that every loaded file contains tests. Concrete loading implementations are configured through constructor parameters, allowing different strategies such as recursive filesystem loading, extension-based filesystem loading, and Git-based loading.

The second confirmed pipeline stage is `parsing`. Its interface is named `TestClassParser`. It receives the in-memory raw code files produced by `loading`, determines which loaded files contain test classes, identifies those test classes, and creates one AST per identified test class. Its `parse` method returns `ParsedTestClasses`, which exposes `TestClass` instances through `testClasses()`. This keeps loading focused on loading files and leaves structural interpretation to parsing.

`TestClass` is currently the most delicate abstraction in modern Roza. It must be powerful enough to represent the root AST for a test class while staying independent of concrete origins such as `.java`, `.py`, JUnit 4, JUnit 5, or future languages/frameworks. Concrete `TestClassParser` implementations know how to build `TestClass`; downstream consumers should work against `TestClass` without knowing the origin. Its minimum content API is intentionally undefined for now.

The detailed design of `TestClass` is the current inflection point for modern Roza. It should be deliberately deferred until the whole pipeline is understood at a high level, then revisited with the pipeline's downstream needs in mind.

The third confirmed pipeline stage is `decomposition`. Its interface is named `TestMethodDecomposer`, and its method is named `decompose`. It receives parsed test classes and separates each test from its class of origin. `TestMethodDecomposer.decompose` returns `DecomposedTestMethods`, which exposes decomposed `TestMethod` instances through `testMethods()`. Each decomposed test is represented by `TestMethod`. For now, `TestMethod` has no minimum content API and does not expose `id()` or `testClass()`. In the implicit setup example, a class with one implicit setup and two tests produces two decomposed test models, each conceptually containing the implicit setup code plus the body of one test, without yet exposing that structure through the public API.

The fourth confirmed pipeline stage is `measurement`. Its interface is named `TestMethodSimilarityMeasurer`, and its method is named `measure`. It receives `DecomposedTestMethods` and returns `TestMethodSimilarityMatrix`. It applies a similarity metric to each pair of decomposed test methods. Similarity metrics can have different objectives, with duplicated test/code identification as the most common one. The returned matrix records the similarity degree for each pair according to the applied metric and is indexed by abstract test method identities. `TestMethodSimilarityMatrix` has no minimum content API beyond that yet.

The fifth confirmed pipeline stage is `clustering`. Its interface is named `TestMethodClusterer`, and its method is named `cluster`. It receives `TestMethodSimilarityMatrix` and returns `TestMethodClusters`, which exposes `TestMethodCluster` instances through `clusters()`. It groups tests according to the indexed similarity matrix produced by `measurement`. This stage intentionally does not know anything about the tests themselves; it sees only the similarity matrix and the abstract identities used to index it. Architecturally, this starts rebuilding structure after the earlier decomposition split test classes into separated test-level ASTs. `TestMethodCluster` has no minimum content API yet.

The sixth confirmed pipeline stage is `refactoring`. Its interface is named `TestClassRefactorer`, and its method is named `refactor`. It receives `TestMethodClusters` and returns `RefactoredTestClasses`, which exposes refactored `TestClass` instances through `testClasses()`. It receives the groups produced by `clustering` and decides what to do with them. The first concrete purpose is to refactor test classes by regrouping tests into better classes so implicit setup can be used, but the pipeline may be able to support other refactoring purposes, such as delegated setup, through different refactoring implementations.

The final confirmed pipeline stage is `writing`. Its interface is named `TestClassWriter`, and its method is named `write`. It receives `RefactoredTestClasses` and writes them to an output destination without returning a serialized model. The destination may be the filesystem or a cloud target, depending on the writer implementation. Concrete writer implementations receive required output destination parameters through constructors.

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
- DEC-027: The decomposition interface is named `TestMethodDecomposer`, and its method is named `decompose`.
- DEC-028: A decomposed test is represented by `TestMethod`, which has no minimum content API yet and does not expose `id()` or `testClass()`.
- DEC-029: `TestMethodDecomposer.decompose` returns `DecomposedTestMethods`, which exposes decomposed test methods through `testMethods()`.
- DEC-030: The measurement interface is named `TestMethodSimilarityMeasurer`, and its `measure` method receives `DecomposedTestMethods` and returns `TestMethodSimilarityMatrix`.
- DEC-031: The clustering interface is named `TestMethodClusterer`, and its `cluster` method receives `TestMethodSimilarityMatrix` and returns `TestMethodClusters`.
- DEC-032: `TestMethodClusters` exposes `TestMethodCluster` instances through `clusters()`, while `TestMethodCluster` has no minimum content API yet.
- DEC-033: The refactoring interface is named `TestClassRefactorer`, and its `refactor` method receives `TestMethodClusters` and returns `RefactoredTestClasses`.
- DEC-034: `RefactoredTestClasses` exposes refactored `TestClass` instances through `testClasses()`.
- DEC-035: The writing interface is named `TestClassWriter`; its `write` method receives `RefactoredTestClasses` and does not return a serialized model.

## Hypotheses

- HYP-001: The pipeline can likely remain abstract and flexible if the generic core stops at stage contracts and each concrete purpose is isolated in implementations, especially in measurement, clustering, and refactoring. The first vertical slice should stay anchored in implicit-setup regrouping to prevent premature abstractions.
- HYP-002: A useful boundary may be to keep `loading`, `parsing`, `decomposition`, `measurement`, and `clustering` relatively purpose-neutral, while allowing `refactoring` implementations to encode concrete refactoring intentions such as implicit setup or delegated setup.
- HYP-003: Refactoring may use the abstract identities returned by clustering to recover grouped decomposed tests, but the exact lookup/ownership model remains undecided. Those identities are not part of the `TestMethod` API for now.

## Interview Backlog

- Confirm whether the high-level pipeline stages are complete: `loading`, `parsing`, `decomposition`, `measurement`, `clustering`, `refactoring`, and `writing`.
- Define the input and output contract for the whole pipeline.
- Decide which additional attributes `CodeFile` needs beyond `content()` when a confirmed requirement makes them necessary.
- Decide which minimum content API `TestClass` needs when a confirmed requirement makes it necessary.
- Decide how parsing should represent ASTs and identified test classes in the core model.
- Explore whether an existing non-legacy AST abstraction can support `TestClass`.
- Decide whether later core contracts should model files, ASTs, tests, changes, or all of them.
- Define how pipeline composition is configured.
- Define how failures, partial results, and unsupported inputs are represented.
- Define what the first runnable vertical slice should prove.
- Return to detailed `TestClass` design after the high-level pipeline is mapped.
- Define the minimum `TestMethodSimilarityMatrix` API and decide whether separate similarity metric or score abstractions are necessary.
- Decide the first concrete output destination for `TestClassWriter`.
- Investigate how far the pipeline can generalize beyond the implicit-setup regrouping purpose without weakening the concrete first use case.
- Define the exact abstract test identity model and where those identities are created.
- Define how abstract test identities relate to `TestMethod` without adding premature attributes to `TestMethod`.

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
