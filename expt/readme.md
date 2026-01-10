# Experiments

Each letter identify an experiment. The description of each experiment is defined below.

## `a`: similarity measurement experiment

Uses distinct similarity metrics to evaluate the capability of each metric to identify refactoring candidates by the implicit setup strategy. The metrics used are: JPlag, Siman, Deckard, LCS and LCCSS.

- Set of source test classes: `expt/resources/a`
- Code containing the definition of the control matrix: `expt/src/br/ufsc/ine/leb/roza/expt/a/GroundTruth.java`
- Code responsible to execute the experiment: `expt/src/br/ufsc/ine/leb/roza/expt/a/Experiment.java`
- Rankings of similarity for each configuration variant: `expt/results/a/matrix`
- Precision/recall curves for each configuration variant: `expt/results/a/precision-recall-curve`
- Average precision/recall curves for each configuration variant: `expt/results/a/average-precision-recall-curve`
- Average precision/recall curve for the best configuration variant of each metric: `expt/results/a/average-precision-recall-curve.csv`

## `b`: similarity measurement example

Uses distinct similarity metrics to exemplify the measurement of Róża.

- Set of source test classes: `expt/resources/b`
- Code responsible to execute the example: `expt/src/br/ufsc/ine/leb/roza/expt/b/Example.java`
- Similarity matrix: `expt/results/b/examples.csv`

## `c`: clustering experiment

Combine distinct linkage, referee and criterion to evaluate the capability of each combination refactor the code by the implicit setup strategy.

- Set of source test classes: `expt/resources/c`
- Code responsible to execute the example: `expt/src/br/ufsc/ine/leb/roza/expt/c/Experiment.java`
- Original test classes metrics: `expt/results/c/original.csv`
- Refactored test classes metrics: `expt/results/c/refactored.csv`

## `d`: refactoring experiment

Refactor the test code to measure the level of reuse. 16 programs from undergraduate students were selected to be refactored. Refactor using LCCSS, average linkage, any cluster referee and similarity based criterion (0.4).

- Set of source test classes: `expt/resources/d`
- Code responsible to execute the example: `expt/src/br/ufsc/ine/leb/roza/expt/d/Experiment.java`
- Code metrics: `expt/results/d/results.csv`

## `e`: banking system use case

Use case of a banking system to exemplify the capability of Róża to refactor candidates by the implicit setup strategy. Refactor using LCCSS, average linkage, any cluster referee and similarity based criterion (0.4).

- Source test classes: `expt/resources/e/BankAccountWithDuplicationTest.java`
- Code responsible to execute the example: `expt/src/br/ufsc/ine/leb/roza/expt/e/Experiment.java`
- Refactored classes: `expt/results/e/RefactoredTestClasse1.java` and `expt/results/e/RefactoredTestClasse2.java`
- Similarity matrix: `expt/results/e/similarity-matrix.csv`
