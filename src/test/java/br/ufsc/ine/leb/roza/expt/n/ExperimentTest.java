package br.ufsc.ine.leb.roza.expt.n;

import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.core.modern.parsing.ParsedTestClasses;
import br.ufsc.ine.leb.roza.core.modern.refactoring.DelegatedSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ImplicitSetupTestClassRefactorer;
import br.ufsc.ine.leb.roza.core.modern.refactoring.RankingSetupContributor;
import br.ufsc.ine.leb.roza.core.modern.refactoring.ResidualImplicitSetupTestClassRefactorer;

class ExperimentTest {

	@Test
	void helperWrapperKeepsIncrementalRankingForEverySetupStrategy() {
		ParsedTestClasses parsed = new ParsedTestClasses(List.of());
		assertTrue(Experiment.withExistingHelpers(new ImplicitSetupTestClassRefactorer(), parsed) instanceof RankingSetupContributor);
		assertTrue(Experiment.withExistingHelpers(new ResidualImplicitSetupTestClassRefactorer(), parsed) instanceof RankingSetupContributor);
		assertTrue(Experiment.withExistingHelpers(new DelegatedSetupTestClassRefactorer(), parsed) instanceof RankingSetupContributor);
	}
}
