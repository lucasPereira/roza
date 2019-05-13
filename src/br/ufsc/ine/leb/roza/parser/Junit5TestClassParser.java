package br.ufsc.ine.leb.roza.parser;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class Junit5TestClassParser extends JunitTestClassParser implements TestClassParser {

	public Junit5TestClassParser() {
		super(Test.class, BeforeEach.class);
	}

}
