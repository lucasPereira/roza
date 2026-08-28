# Experiments

Each letter identifies one experiment. The sections below explain what each experiment does, where its input data is stored, which Java class executes it, and which result files are produced.

## `a`: comparing similarity metrics for refactoring candidates

Experiment `a` evaluates the measurement stage of the pipeline: which similarity metric best recovers test pairs that manual analysis classified as suitable for implicit-setup refactoring. The evaluation adopts an information-retrieval view. For each test in the suite, a metric ranks the others by similarity, and those rankings are compared against a manually built ground truth.

**Subject.** The corpus is 46 system-level acceptance tests (Java, JUnit, Selenium) from a CAPES book-evaluation fork of Open Monograph Press, spread across six classes. All 1035 unordered pairs were classified twice and disagreements were reconciled. Two hundred nineteen pairs (~21%) were judged suitable for placing both tests in the same class to reuse fixture setup through implicit setup; 816 were not. Only setup, exercise, and verify phases enter the comparison; teardown is excluded because these tests do not require explicit SUT cleanup.

**Procedure.** Five metrics are compared: JPlag, Simian, Deckard, LCS, and LCCSS. JPlag, Simian, and Deckard are swept across sensitivity parameters (51, 14, and 476 configurations); LCS and LCCSS have no parameters. For each configuration, Róża builds a similarity matrix and orders candidates for each reference test. At eleven standard recall levels (0%–100% in steps of 10%), precision measures how many highly ranked candidates are true positives. Curves are averaged across the 46 reference tests, and within each metric the configuration with the highest mean precision is selected as that metric’s representative.

**Findings.** LCCSS and Simian achieve precision 1.0 at every recall level, so their average-precision curves coincide. JPlag, Deckard, and LCS lose precision as recall increases, often because similarity outside the fixture prefix inflates scores when the contiguous start sequences differ. For the pipeline, LCCSS matches Simian on this dataset but requires no per-project calibration and aligns directly with the prefix constraint of implicit setup.

## `b`: example of similarity measurement

Experiment `b` is a minimal worked example of the measurement stage: two short JUnit 4 test methods from a small banking scenario are decomposed, materialized, and compared with all five metrics (LCS, LCCSS, JPlag, Simian, and Deckard). It produces a small similarity matrix that is easy to inspect by hand—for instance.

## `c`: configuration-sensitivity study on the SAAS project

Experiment `c` evaluates how clustering configuration affects reuse when Róża applies the implicit setup strategy end to end. It runs the full pipeline on a real acceptance-test corpus and compares configurations by duplicated setup statements before and after refactoring.

**Subject.** The corpus is a subset of the SAAS course-evaluation system developed for the Brazilian Government: 106 tests spread across 27 classes. The original suite contains 838 total duplicated statements in the setup projection used by the pipeline.

**Procedure.** Similarity is measured with LCCSS. The experiment sweeps linkage strategies, referee (tiebreak) strategies, and similarity thresholds from 0.1 to 1.0, then records code metrics for each configuration alongside the original baseline.

**Findings.** The similarity threshold dominates: values between 0.3 and 0.6 yield the lowest duplicate counts, while thresholds near 0.1–0.2 or 0.8–1.0 perform worse than the original suite. The best observed configuration is complete linkage with threshold 0.4 (770 total duplicated statements, 8.11% below the baseline 838). Referee strategy has little effect.

## `d`: multi-project refactoring study on 16 student programs

Experiment `d` measures whether automatic implicit-setup refactoring increases reuse across many independent student test suites. Each program is refactored with the same fixed clustering configuration, and before-and-after code metrics are compared per project and in aggregate.

**Subject.** The corpus is 16 undergraduate programs from a software testing course. Students implemented JUnit tests for a banking-system scenario (accounts, branches, monetary values) using Test-Driven Development, yielding heterogeneous class layouts and duplicated fixture setup across tests.

**Procedure.** Róża runs the full pipeline on each program with LCCSS measurement, average linkage, the any-cluster referee, and similarity threshold 0.4. The experiment records classes, attributes, setup methods, test methods, statements, and duplicate counts before and after refactoring, then applies Wilcoxon signed-rank tests on paired per-project differences.

**Findings.** Average total duplicated statements fell from 14.69 to 10.25 (~30% reduction in the paper’s headline figure). Wilcoxon tests reject equal duplication (p = 0.0077 for unique duplicates, p = 0.0281 for total duplicates). Average class count rose from 4.56 to 7.00 because clustering splits broad classes into more cohesive groups; lower duplication does not imply fewer generated artifacts.

## `e`: banking system refactoring use case

Experiment `e` is an illustrative end-to-end run of the pipeline on a small banking test suite with deliberate fixture duplication. Róża measures similarity with LCCSS, clusters with average linkage and the any-cluster referee at threshold 0.4, and refactors into implicit setup. The run produces a pairwise similarity matrix for inspection and two refactored test classes that show how duplicated arrange code is consolidated.

## `f`: multi-project refactoring study on 47 student programs

Experiment `f` asks whether a fixed clustering configuration reduces duplicated test statements across many independent projects without per-project tuning. It uses LCCSS measurement, complete linkage, a composed referee (biggest cluster, then any cluster), and similarity threshold 0.4.

**Subject.** The corpus is 47 undergraduate programs from a software testing course. Students implemented JUnit tests for an ad-hoc system using Test-Driven Development, yielding heterogeneous layouts and substantial duplicated fixture setup across tests.

**Procedure.** Róża runs the full pipeline on each program and records classes, attributes, setup methods, test methods, statements, and duplicate counts before and after refactoring. Shapiro–Wilk normality checks and Wilcoxon signed-rank tests are applied to paired per-project differences.

**Findings.** Aggregate total duplicated statements fell from 1741 to 1131 (−35.04%). Per project: 27 improved, 10 worsened, 10 unchanged. Wilcoxon on total duplicates: p = 0.0053 (median reduction 2 statements per project, IQR 0–8; rank-biserial effect size 0.53). Excluding project 24, which accounted for the largest gain (757→281 duplicates), the aggregate reduction is still 13.62% (984→850), with the same 27/10/10 split. Duplicate reduction coexists with more generated classes and setup methods in many projects, so lower duplication does not imply fewer artifacts overall.

## `g`: clustering scalability on Apache Commons Lang

Experiment `g` measures how scalable dendrogram construction is when the clustering stage grows the merge-candidate set during agglomerative clustering. It compares a legacy candidate-generation strategy against an optimized incremental candidate-management strategy on the same extracted test subsets.

**Subject.** The benchmark uses JUnit 5 tests from Apache Commons Lang. Growing subsets of 50, 100, 150, and 200 tests are drawn from the project's main test tree, including tests from several nested packages. The benchmark relies on stored test sources only; the full Commons Lang build is not required.

**Procedure.** For each subset size and linkage strategy (single, complete, and average), both strategies build a dendrogram while recording runtime, generated merge candidates, linkage evaluations, hierarchy levels, final clusters, and peak memory. Comparable baseline and optimized runs are paired to compute runtime speedup and candidate-count reduction.

**Findings.** The optimized strategy cuts generated candidates by roughly 8.7× at 50 tests, 17× at 100, and 25.3× at 150, with paired runtime speedups between about 4× and 16× depending on linkage and subset size. Average linkage remains the slowest variant, but optimization keeps it tractable through 200 tests (for example, ~9 s at 200 tests with average linkage). The baseline strategy skips cases that exceed a practical runtime limit (150 tests with average linkage, and all linkages at 200 tests), while the optimized strategy completes the full sweep.

## `h`: comparative refactoring study on Apache Commons Lang

Experiment `h` compares how much setup duplication implicit-setup refactoring removes when tests can be redistributed globally versus refactored only within their original class. Three conditions are measured on the same corpus: the original test classes, a local-only baseline that refactors each class independently, and global clustering under several similarity thresholds.

**Subject.** The corpus is the JUnit 5 test suite from Apache Commons Lang: 328 Java files (2 skipped by the parser), 276 classes, and 4683 test methods. The original suite contains 4792 total duplicated setup statements (1619 unique).

**Procedure.** Global clustering uses LCCSS measurement, complete linkage, and a composed referee (biggest cluster, then any cluster) at thresholds 0.2, 0.3, 0.4, and 0.5. The local-only baseline runs the refactorer on each original class without redistributing tests. The experiment records classes, attributes, setup methods, test methods, statements, duplicate counts, and runtime for each condition.

**Findings.** Local-only refactoring barely changes duplication (4792→4760 total duplicates, 0.67% reduction). Global clustering removes far more, with lower thresholds yielding stronger gains: 26.36% at 0.2 (3529 duplicates), 23.69% at 0.3, 15.53% at 0.4, and 6.74% at 0.5. That trade-off comes with many more generated classes (for example, 2999 classes at threshold 0.2 versus 276 originally) and lower tests-per-class density, so duplicate reduction is not free in structural complexity.

## `i`: eligible and per-level refactoring metrics on Róża tests

Experiment `i` measures setup duplication on Róża's own JUnit tests before and after implicit-setup refactoring at every level of the agglomerative hierarchy. It tracks how decomposition, recomposition under the original class partition, and global clustering change duplicated fixture code on an internal corpus that evolves with the framework.

**Subject.** The corpus is Róża's current test base, loaded at run time so each execution reflects the suite as it stands. After filtering to classes with at least one accepted test, the eligible baseline has 81 test classes, 365 test methods, 1776 setup statements, and 1171 duplicated setup statements (65.9% duplication rate).

**Procedure.** Duplication counts only setup code: initialized fields, fixture-method statements, and each test's arrange phase up to the first recognized assertion; teardown, helpers, and post-assertion code are excluded. Duplicates use textual surplus counting over that projection. The experiment records control conditions (original filtered baseline, decomposed-but-unrecomposed, and recomposition under the original source-class partition) and then runs LCCSS measurement with the textual implicit-setup refactorer, single linkage, a stable test-order merge tie breaker, and the full hierarchy without an extra stop criterion, reporting metrics at every clustering level.

**Findings.** Decomposition alone inflates counted duplication (3094 duplicated statements, 84.0% rate) before any clustering benefit appears. Recomposition under the original partition barely moves the baseline (1163 duplicates). Across global clustering levels, duplication falls from the decomposed starting point but never below the original filtered baseline; the best level (239) still has 1235 duplicates (+64 versus the original 1171). On this corpus, global redistribution trades class count (127 classes at the best level) without winning on the duplication metric.

## `j`: eligible and per-level refactoring metrics on SAAS tests

Experiment `j` measures setup duplication on the SAAS course-evaluation acceptance-test corpus before and after implicit-setup refactoring at every agglomerative level. It tracks how clustering depth changes duplicated fixture code on a large real-world suite with heavy setup reuse opportunity.

**Subject.** The corpus is the SAAS project test tree: 870 eligible test classes and 3560 test methods after filtering. The original baseline has 25193 setup statements and 20148 duplicated setup statements (80.0% duplication rate), indicating substantial fixture overlap across acceptance tests.

**Procedure.** Setup duplication counts initialized fields, fixture-method statements, and arrange code before the first recognized assertion, with textual surplus counting over that projection. LCCSS measurement drives single-linkage clustering with a stable test-order merge tie breaker and the full hierarchy without an extra stop criterion; the textual implicit-setup refactorer runs at every level. The large corpus requires an 8 GB heap for the Gradle task.

**Findings.** Decomposition inflates counted duplication before clustering (82414 duplicated statements at the first level, 93.8% rate). Across later levels, duplication falls from that peak but never below the original filtered baseline; the best level (2437) still has 21075 duplicates (+927 versus the original 20148). Global redistribution increases class count (1124 classes at the best level versus 870 originally) without improving the duplication metric on this corpus.

## `k`: eligible and per-level refactoring metrics on Apache Commons Math tests

Experiment `k` measures setup duplication on the Apache Commons Math test suite before and after implicit-setup refactoring at every agglomerative level. It applies the same per-level LCCSS pipeline to the multi-module project added as an external submodule, probing fixture overlap on a widely used open-source numerical library.

**Subject.** The corpus is the Commons Math submodule: tests are loaded from every module's test source tree (404 Java files, 323 parsed test classes). The modern decomposition stage accepts 138 test methods across 51 eligible classes; most remaining methods are rejected as violations (for example abstract fixtures, parameterized tests, and other patterns outside the current parser). The filtered baseline has 550 setup statements and 283 duplicated setup statements (51.5% duplication rate).

**Procedure.** Setup duplication counts initialized fields, fixture-method statements, and arrange code before the first recognized assertion, with textual surplus counting over that projection. LCCSS measurement drives single-linkage clustering with a stable test-order merge tie breaker and the full hierarchy without an extra stop criterion; the textual implicit-setup refactorer runs at every level.

**Findings.** On the accepted subset, global clustering can reduce duplication below the original baseline. The best level (50) reaches 188 duplicated setup statements (−95 versus the original 283, 41.6% rate) with 89 generated classes. Coverage remains limited: only 138 of roughly 2900 annotated tests enter the pipeline, so corpus-wide duplication in Commons Math is still largely untapped by the current modern stack.

## `l`: eligible and per-level refactoring metrics on JFreeChart tests

Experiment `l` measures setup duplication on the JFreeChart test suite before and after implicit-setup refactoring at every agglomerative level. It applies the per-level LCCSS pipeline to the charting library added as an external submodule, probing fixture overlap on a large open-source visualization project.

**Subject.** The corpus is the JFreeChart submodule: 361 test source files and 350 parsed test classes. The modern decomposition stage accepts 66 test methods across 26 eligible classes; most remaining methods are rejected as violations (abstract bases, parameterized tests, and other patterns outside the current parser). The filtered baseline has 132 setup statements and 29 duplicated setup statements (22.0% duplication rate).

**Procedure.** Setup duplication counts initialized fields, fixture-method statements, and arrange code before the first recognized assertion, with textual surplus counting over that projection. LCCSS measurement drives single-linkage clustering with a stable test-order merge tie breaker and the full hierarchy without an extra stop criterion; the textual implicit-setup refactorer runs at every level.

**Findings.** On the accepted subset, global clustering reduces duplication well below the original baseline. The best level (21) reaches 2 duplicated setup statements (−27 versus the original 29, 1.9% rate) with 46 generated classes. Coverage remains narrow: only 66 of roughly 2300 annotated tests enter the pipeline, so the experiment characterizes a small eligible slice rather than the full JFreeChart test base.

## `m`: bookstore refactoring use case for implicit and delegated setup

Experiment `m` is a small end-to-end use case on a five-test bookstore suite written so the same tests can be refactored by both implicit setup and delegated setup. The tests currently sit in two mixed classes, `StorefrontTest` and `CheckoutTest`, but they form two fixture families: three tests share a cart prefix that creates a cart and adds *Dune* and *Foundation*, and two tests share a catalog prefix that creates a catalog and adds the same titles. Those prefixes start each test and leave a single live-out, so LCCSS can lift them into `@BeforeEach` after regrouping tests globally, while CCS can extract them into helper methods without moving the tests from their original classes. For each strategy, and for the two sequential compositions (implicit then delegated, delegated then implicit), the experiment builds the full average-linkage hierarchy, ranks every level by duplicated setup statements, and writes the suite from the best level. The comparison covers original, implicit, delegated, implicit + delegated, and delegated + implicit, with bar charts for total statements, duplicated statements, and duplication rate.

## `n`: multi-project comparison of implicit, residual, and delegated setup

Experiment `n` compares implicit setup, residual implicit setup, delegated setup, and the sequential compositions of those strategies on every subject under `external-projects/` plus Róża's own tests. Each library is one subject even when it has several test modules; only SAAS is split (`saas+teste`, `saas+teste+moodle`, `saas+teste+selenium`, `saas+teste+service`). Decomposition ignores parser violations so every parseable test enters the pipeline. Clustering uses LCCSS or CCS (minimum run length 2) with single linkage, a stable test-order tie break, and the full hierarchy; each variant keeps the level with the lowest duplicated setup statements.

**Input.** `src/expt/resources/n/experimento-n-design.canvas.tsx` holds the locked design. Subject roots live under `external-projects/` and `src/test/java`.

**Procedure.** `./gradlew runExperimentN` (heap 32g) wipes `experiment-results/n/` and runs every subject from scratch. `./gradlew runExperimentNMissing` passes `--missing-only`: it keeps `comparison.csv` and reruns only subjects that still lack all eight variants. Each run writes `comparison.csv`, `skipped.csv` (project, variant, reason), three grouped-bar SVGs, and the thesis tables as CSV in the same folder. Statistics (Wilcoxon, Friedman, Holm, medians, IQR, improved/worsened/tied) are computed in the same run.
