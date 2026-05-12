package br.ufsc.ine.leb.roza.core.modern.refactoring;

import br.ufsc.ine.leb.roza.core.modern.clustering.TestCaseClusters;

public interface TestClassRefactorer {

	RefactoredTestClasses refactor(TestCaseClusters clusters);
}
