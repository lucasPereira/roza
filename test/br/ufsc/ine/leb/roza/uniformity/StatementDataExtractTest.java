package br.ufsc.ine.leb.roza.uniformity;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Statement;

public class StatementDataExtractTest {

	@Test
	public void ofFieldWithoutConstructor() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut;");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(0, data.size());
	}

	@Test
	public void ofFieldWithConstructorEmpty() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut();");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(0, data.size());
	}

	@Test
	public void ofFieldWithInstatnceAnonymousInConstructorEmpty() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut(new Sut());");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(0, data.size());
	}

	@Test
	public void ofFieldWithInstatnceMethod() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = method();");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(0, data.size());
	}
	

	@Test
	public void ofFieldWithLittleTrain() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = method(\"data\").method(\"data1\").method2(3);");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(2, data.size());
		assertEquals("\"data\"", data.get(0));
		assertEquals("\"data1\"", data.get(0));
	}

	@Test
	public void ofFieldWithInstatnceMethodWithParameter() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = method(new Sut(\"data\"));");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(1, data.size());
		assertEquals("\"data\"", data.get(0));
	}

	@Test
	public void ofFieldWithInstatnceAnonymousWithDataInConstructorEmpty() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut(new Sut(\"data\"));");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(1, data.size());
		assertEquals("\"data\"", data.get(0));
	}

	@Test
	public void ofFieldWithStringInConstructor() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut(\"data\");");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(1, data.size());
		assertEquals("\"data\"", data.get(0));
	}
	@Test
	public void ofFieldWithStringInConstructorCallMethod() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut(method(\"data\"));");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(1, data.size());
		assertEquals("\"data\"", data.get(0));
	}
	@Test
	public void ofFieldWithObjectReferenceInInstanceInConstructor() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut(another);");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(1, data.size());
		assertEquals("another", data.get(0));
	}

	@Test
	public void ofFieldWithIntegerAndStringInConstructor() throws Exception {
		Statement fieldDeclaration = new Statement("private Sut sut = new Sut(1,\"data\");");
		List<String> data = DataExtractor.from(fieldDeclaration);
		assertEquals(2, data.size());
		assertEquals("1", data.get(0));
		assertEquals("\"data\"", data.get(1));
	}

}
