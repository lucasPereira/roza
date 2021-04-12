package br.ufsc.ine.leb.roza.refactoring;

import java.util.List;
import java.util.Set;

import br.ufsc.ine.leb.roza.Cluster;
import br.ufsc.ine.leb.roza.TestClass;

public interface ClusterRefactor {

	List<TestClass> refactor(Set<Cluster> clusters);

}
