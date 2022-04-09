package br.ufsc.ine.leb.roza.refactoring;

import java.util.List;

import br.ufsc.ine.leb.roza.parsing.Field;
import br.ufsc.ine.leb.roza.parsing.SetupMethod;
import br.ufsc.ine.leb.roza.parsing.TestMethod;

public interface TestClassNamingStrategy {

	String nominate(List<Field> fields, List<SetupMethod> setupMethods, List<TestMethod> testMethods);

}
