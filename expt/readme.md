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

- Set of source test classes: `expt/resources/a`
- Code responsible to execute the example: `expt/src/br/ufsc/ine/leb/roza/expt/b/Example.java`
- Similarity matrix: `expt/results/b/examples.csv`

## `c`: clustering experiment

In development.
