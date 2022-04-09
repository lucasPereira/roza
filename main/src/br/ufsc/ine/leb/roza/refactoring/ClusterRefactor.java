package br.ufsc.ine.leb.roza.refactoring;

import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.clustering.Cluster;
import br.ufsc.ine.leb.roza.parsing.TestClass;

public interface ClusterRefactor {

	List<TestClass> refactor(Set<Cluster> clusters);

}
