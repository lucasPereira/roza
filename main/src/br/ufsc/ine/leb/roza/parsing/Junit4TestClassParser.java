package br.ufsc.ine.leb.roza.parsing;

import org.junit.Before;
import org.junit.Test;

public class Junit4TestClassParser extends JunitTestClassParser implements TestClassParser {

	public Junit4TestClassParser() {
		super(Test.class, Before.class);
	}

}
