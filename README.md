# Róża

## Running modern Róża UI

From the project root, run:

```sh
./gradlew runModernRozaUi
```

## Running legacy Róża UI

From the project root, run:

```sh
./gradlew runLegacyRozaUi
```

## Running Experiments

Experiment inputs are stored under `src/expt/resources`, and generated outputs are written under `experiment-results`.

Run an experiment with its Gradle task:

```sh
./gradlew runExperimentA
./gradlew runExperimentB
./gradlew runExperimentC
./gradlew runExperimentD
./gradlew runExperimentE
./gradlew runExperimentF
./gradlew runExperimentG
./gradlew runExperimentH
```

## Deckard

To use Deckard in Róża or run the Deckard-related tests, install it first:

```sh
cd external-tools/deckard
./install-using-docker.sh
```
