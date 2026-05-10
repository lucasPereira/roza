package br.ufsc.ine.leb.roza.core.refactoring;

import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.core.Cluster;
import br.ufsc.ine.leb.roza.core.TestClass;

public interface ClusterRefactor {

	List<TestClass> refactor(Set<Cluster> clusters);

}
