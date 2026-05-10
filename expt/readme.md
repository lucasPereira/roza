# Experiments

Each letter identifies one experiment. The sections below explain what each experiment does, where its input data is stored, which Java class executes it, and which result files are produced.

## `a`: comparing similarity metrics for refactoring candidates

Experiment `a` compares similarity metrics for identifying test cases that are candidates for reuse-oriented refactoring through the implicit setup strategy. It uses the source test classes in `expt/resources/a`, defines the expected relevant pairs in `expt/src/br/ufsc/ine/leb/roza/expt/a/GroundTruth.java`, and is executed by `expt/src/br/ufsc/ine/leb/roza/expt/a/Experiment.java`. The evaluated metrics are JPlag, Simian, Deckard, LCS, and LCCSS.

The generated results in `expt/results/a/matrix` contain the similarity rankings for each metric configuration. The files in `expt/results/a/precision-recall-curve` contain the precision and recall curves for each configuration, while `expt/results/a/average-precision-recall-curve` contains the averaged curves. The consolidated file `expt/results/a/average-precision-recall-curve.csv` summarizes the best average precision/recall curve for each metric.

## `b`: example of similarity measurement

Experiment `b` is a compact example of how Róża computes similarity between test cases. It reads the source test classes from `expt/resources/b` and is executed by `expt/src/br/ufsc/ine/leb/roza/expt/b/Examples.java`.

The output file `expt/results/b/examples.csv` contains the generated similarity matrix, making this experiment useful for inspecting the measurement process on a small and easy-to-read dataset.

## `c`: configuration-sensitivity study on the SAAS project

Experiment `c` evaluates how different clustering configurations affect reuse-oriented refactoring results for the implicit setup strategy. It reads the source test classes from `expt/resources/c`, measures similarity with LCCSS, and combines linkage strategies, referee strategies, and stopping criteria in `expt/src/br/ufsc/ine/leb/roza/expt/c/Experiment.java`.

The file `expt/results/c/original.csv` contains the baseline metrics for the original test classes, including the number of classes, tests, statements, unique duplicated statements, and total duplicated statements. The file `expt/results/c/refactored.csv` contains the corresponding metrics after refactoring for each clustering configuration, allowing the configurations to be compared by their effect on code reuse.

## `d`: multi-project refactoring study on 16 student programs

Experiment `d` measures reuse after automatically refactoring 16 undergraduate student programs. The original projects are stored in `expt/resources/d`, and `expt/src/br/ufsc/ine/leb/roza/expt/d/Experiment.java` executes the refactoring using LCCSS, average linkage, the any-cluster referee, and a similarity-based threshold criterion of 0.4.

During execution, the experiment writes the generated refactored test classes into the corresponding `refactored` folders under `expt/resources/d`. The file `expt/results/d/results.csv` contains the before-and-after code metrics for each project, while `expt/results/d/analysis.ods` stores the spreadsheet used to analyze the experiment results.

## `e`: banking system refactoring use case

Experiment `e` is a banking-system use case that demonstrates the complete refactoring pipeline on a small test suite. It starts from `expt/resources/e/BankAccountWithDuplicationTest.java` and is executed by `expt/src/br/ufsc/ine/leb/roza/expt/e/Experiment.java` using LCCSS, average linkage, the any-cluster referee, and a similarity-based threshold criterion of 0.4.

The file `expt/results/e/similarity-matrix.csv` contains the pairwise similarity scores used by the clustering stage. The files `expt/results/e/RefactoredTestClass1.java` and `expt/results/e/RefactoredTestClass2.java` contain the refactored test classes produced by Róża.

## `f`: multi-project refactoring study on 47 student programs

Experiment `f` evaluates whether applying the selected Roza configuration reduces duplicated statements across 47 undergraduate student programs. The projects are stored in `expt/resources/f`, and `expt/src/br/ufsc/ine/leb/roza/expt/f/Experiment.java` executes the refactoring with LCCSS, complete linkage, a composed referee that first prioritizes the biggest cluster and then falls back to any cluster, and a similarity-based threshold criterion of 0.4.

The experiment writes the generated refactored classes into the corresponding `refactored` folders under `expt/resources/f`. The file `expt/results/f/results.csv` contains before-and-after metrics for each project, including classes, attributes, setup methods, test methods, statements, unique duplicated statements, and total duplicated statements.

## `g`: clustering scalability on Apache Commons Lang

Experiment `g` measures dendrogram construction scalability using JUnit 5 tests extracted from Apache Commons Lang. The benchmark corpus in `expt/resources/g` contains only the copied test source files needed to extract the benchmark subset used by the experiment, up to the largest configured case of 200 tests; the external project is not included as a dependency and does not need to be compiled or executed. Files from the `org.apache.commons.lang3` package are stored directly in `expt/resources/g`, while subpackages such as `concurrent` and `exception` are preserved as subfolders. The benchmark is implemented in `expt/src/br/ufsc/ine/leb/roza/expt/g/Experiment.java`, which runs the baseline strategy, the optimized strategy, and the comparison step in sequence.

The file `expt/results/g/baseline.csv` contains the measurements for the legacy candidate-generation strategy, including runtime, generated merge candidates, linkage evaluations, levels, final clusters, and memory usage. The file `expt/results/g/optimized.csv` contains the same metrics for the optimized incremental candidate-management strategy. The file `expt/results/g/comparison.csv` summarizes the comparable baseline and optimized runs, including runtime speedup and candidate-count reduction.

## `h`: comparative refactoring study on Apache Commons Lang

Experiment `h` compares three reuse-oriented refactoring conditions on Apache Commons Lang JUnit 5 tests: the original test classes, a local-only baseline that refactors each original test class independently, and Roza's global clustering strategy. The corpus in `expt/resources/h` contains the copied tests from `src/test/java/org/apache/commons/lang3` at Apache Commons Lang commit `19e2d568a4ee4910ff470eba40ec040673195d7d`. The experiment is implemented in `expt/src/br/ufsc/ine/leb/roza/expt/h/Experiment.java` and uses the selected Roza configuration: LCCSS, complete linkage, a composed referee that first prioritizes the biggest cluster and then falls back to any cluster, and a similarity-based threshold criterion of 0.4.

The file `expt/results/h/configuration.csv` records the subject, source commit, corpus location, and refactoring settings. The file `expt/results/h/skipped-files.csv` lists any Java files that could not be parsed by the current JavaParser configuration. The file `expt/results/h/summary.csv` compares the original, local-only, and global-clustering conditions using classes, attributes, setup methods, test methods, statements, unique duplicated statements, total duplicated statements, and duplicate reduction relative to the original test classes. By default, the experiment uses the full copied corpus; for smoke-test runs only, `ROZA_EXPERIMENT_H_MAX_FILES` can limit the number of Java files loaded.
