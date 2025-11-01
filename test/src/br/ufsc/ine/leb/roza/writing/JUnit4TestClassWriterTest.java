package br.ufsc.ine.leb.roza.writing;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import br.ufsc.ine.leb.roza.Field;
import br.ufsc.ine.leb.roza.SetupMethod;
import br.ufsc.ine.leb.roza.Statement;
import br.ufsc.ine.leb.roza.TestClass;
import br.ufsc.ine.leb.roza.TestMethod;
import br.ufsc.ine.leb.roza.utils.CodeStringBuilder;
import br.ufsc.ine.leb.roza.utils.FileUtils;
import br.ufsc.ine.leb.roza.utils.FolderUtils;

public class JUnit4TestClassWriterTest {

	private FolderUtils folderUtils;
	private TestClassWriter writer;
	private FileUtils fileUtils;

	@BeforeEach
	void setup() {
		fileUtils = new FileUtils();
		folderUtils = new FolderUtils("main/exec/writer");
		folderUtils.createEmptyFolder();
		writer = new Junit4TestClassWriter("main/exec/writer");
	}

	@Test
	void withoutClasses() {
		writer.write(List.of());
		assertEquals(0, folderUtils.listFilesRecursively().size());
	}

	@Test
	void emptyTestMethod() {
		TestMethod test = new TestMethod("alpha", List.of());
		TestClass alpha = new TestClass("Alpha", List.of(), List.of(), List.of(test));
		writer.write(List.of(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = folderUtils.listFilesRecursively();
		assertEquals(1, written.size());
		assertEquals("Alpha.java", written.get(0).getName());
		assertEquals(refactored.toString(), fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void testMethodWithStatements() {
		Statement fixture = new Statement("int a = 10;");
		Statement assertion = new Statement("assertEquals(10, a);");
		TestMethod test = new TestMethod("alpha", List.of(fixture, assertion));
		TestClass alpha = new TestClass("Alpha", List.of(), List.of(), List.of(test));
		writer.write(List.of(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().tab().add("int a = 10;");
		refactored.tab().tab().add("assertEquals(10, a);");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = folderUtils.listFilesRecursively();
		assertEquals(1, written.size());
		assertEquals("Alpha.java", written.get(0).getName());
		assertEquals(refactored.toString(), fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void emptySetupMethod() {
		SetupMethod setup = new SetupMethod("setup", List.of());
		TestMethod test = new TestMethod("alpha", List.of());
		TestClass alpha = new TestClass("Alpha", List.of(), List.of(setup), List.of(test));
		writer.write(List.of(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.Before;");
		refactored.add("import org.junit.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("@Before()");
		refactored.tab().add("public void setup() {");
		refactored.tab().add("}");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = folderUtils.listFilesRecursively();
		assertEquals(1, written.size());
		assertEquals("Alpha.java", written.get(0).getName());
		assertEquals(refactored.toString(), fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void setupMethodWithFixture() {
		Field field = new Field("int", "a");
		Statement fixture = new Statement("a = 10;");
		Statement assertion = new Statement("assertEquals(10, a);");
		SetupMethod setup = new SetupMethod("setup", List.of(fixture));
		TestMethod test = new TestMethod("alpha", List.of(assertion));
		TestClass alpha = new TestClass("Alpha", List.of(field), List.of(setup), List.of(test));
		writer.write(List.of(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.Before;");
		refactored.add("import org.junit.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("private int a;");
		refactored.add();
		refactored.tab().add("@Before()");
		refactored.tab().add("public void setup() {");
		refactored.tab().tab().add("a = 10;");
		refactored.tab().add("}");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().tab().add("assertEquals(10, a);");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = folderUtils.listFilesRecursively();
		assertEquals(1, written.size());
		assertEquals("Alpha.java", written.get(0).getName());
		assertEquals(refactored.toString(), fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void fieldInitialization() {
		Field field = new Field("int", "a", new Statement("10"));
		Statement assertion = new Statement("assertEquals(10, a);");
		TestMethod test = new TestMethod("alpha", List.of(assertion));
		TestClass alpha = new TestClass("Alpha", List.of(field), List.of(), List.of(test));
		writer.write(List.of(alpha));

		CodeStringBuilder refactored = new CodeStringBuilder();
		refactored.add("import org.junit.Test;");
		refactored.add();
		refactored.add("public class Alpha {");
		refactored.add();
		refactored.tab().add("private int a = 10;");
		refactored.add();
		refactored.tab().add("@Test()");
		refactored.tab().add("public void alpha() {");
		refactored.tab().tab().add("assertEquals(10, a);");
		refactored.tab().add("}");
		refactored.add("}");

		List<File> written = folderUtils.listFilesRecursively();
		assertEquals(1, written.size());
		assertEquals("Alpha.java", written.get(0).getName());
		assertEquals(refactored.toString(), fileUtils.readContetAsString(written.get(0)));
	}

	@Test
	void twoTestClassesOneWithTwoSetupMethodsAndTwoTestMethodsAndOtherWithOneEmptyTestMethod() {
		TestClass alpha = getTestClass();

		TestMethod testBeta = new TestMethod("testBeta", List.of());
		TestClass beta = new TestClass("Beta", List.of(), List.of(), List.of(testBeta));

		writer.write(List.of(alpha, beta));

		CodeStringBuilder refactoredAlpha = new CodeStringBuilder();
		refactoredAlpha.add("import org.junit.Before;");
		refactoredAlpha.add("import org.junit.Test;");
		refactoredAlpha.add();
		refactoredAlpha.add("public class Alpha {");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("private int a;");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("private int b;");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@Before()");
		refactoredAlpha.tab().add("public void setupAlphaOne() {");
		refactoredAlpha.tab().tab().add("a = 10;");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@Before()");
		refactoredAlpha.tab().add("public void setupAlphaTwo() {");
		refactoredAlpha.tab().tab().add("b = 20;");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@Test()");
		refactoredAlpha.tab().add("public void testAlphaOne() {");
		refactoredAlpha.tab().tab().add("assertEquals(10, a);");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add();
		refactoredAlpha.tab().add("@Test()");
		refactoredAlpha.tab().add("public void testAlphaTwo() {");
		refactoredAlpha.tab().tab().add("assertEquals(20, b);");
		refactoredAlpha.tab().add("}");
		refactoredAlpha.add("}");

		CodeStringBuilder refactoredBeta = new CodeStringBuilder();
		refactoredBeta.add("import org.junit.Test;");
		refactoredBeta.add();
		refactoredBeta.add("public class Beta {");
		refactoredBeta.add();
		refactoredBeta.tab().add("@Test()");
		refactoredBeta.tab().add("public void testBeta() {");
		refactoredBeta.tab().add("}");
		refactoredBeta.add("}");

		List<File> written = folderUtils.listFilesRecursively();
		assertEquals(2, written.size());
		assertEquals("Alpha.java", written.get(0).getName());
		assertEquals(refactoredAlpha.toString(), fileUtils.readContetAsString(written.get(0)));
		assertEquals("Beta.java", written.get(1).getName());
		assertEquals(refactoredBeta.toString(), fileUtils.readContetAsString(written.get(1)));
	}

	private static TestClass getTestClass() {
		Field filedAlphaOne = new Field("int", "a");
		Field filedAlphaTwo = new Field("int", "b");
		Statement fixtureAlphaOne = new Statement("a = 10;");
		Statement fixtureAlphaTwo = new Statement("b = 20;");
		Statement assertionAlphaOne = new Statement("assertEquals(10, a);");
		Statement assertionAlphaTwo = new Statement("assertEquals(20, b);");
		SetupMethod setupAlphaOne = new SetupMethod("setupAlphaOne", List.of(fixtureAlphaOne));
		SetupMethod setupAlphaTwo = new SetupMethod("setupAlphaTwo", List.of(fixtureAlphaTwo));
		TestMethod testAlphaOne = new TestMethod("testAlphaOne", List.of(assertionAlphaOne));
		TestMethod testAlphaTwo = new TestMethod("testAlphaTwo", List.of(assertionAlphaTwo));
		return new TestClass("Alpha", List.of(filedAlphaOne, filedAlphaTwo), List.of(setupAlphaOne, setupAlphaTwo), List.of(testAlphaOne, testAlphaTwo));
	}

}
