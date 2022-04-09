package br.ufsc.ine.leb.roza.parsing;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;

import org.junit.jupiter.api.Test;

import com.github.javaparser.ast.NodeList;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.VariableDeclarator;
import com.github.javaparser.ast.expr.VariableDeclarationExpr;
import com.github.javaparser.ast.type.ClassOrInterfaceType;
import com.github.javaparser.ast.type.PrimitiveType;
import com.github.javaparser.ast.type.ReferenceType;
import com.github.javaparser.ast.type.Type;
import com.github.javaparser.ast.visitor.GenericVisitor;
import com.github.javaparser.ast.visitor.VoidVisitor;
import com.github.javaparser.resolution.declarations.ResolvedReferenceTypeDeclaration;
import com.github.javaparser.resolution.declarations.ResolvedTypeDeclaration;
import com.github.javaparser.resolution.types.ResolvedType;
import com.github.javaparser.symbolsolver.resolution.typesolvers.ReflectionTypeSolver;

public class VariableDeclarationStatementTest {

	@Test
	void create() throws Exception {
		new PrimitiveType(PrimitiveType.intType().getType().toBoxedType())
		Object fixture = 19;
		ResolvedReferenceTypeDeclaration type = new ReflectionTypeSolver().solveType("Integer");
		type.asReferenceType().
//		Type type = new ClassOrInterfaceType
		new ClassOrInterfaceDeclaration(new NodeList<>(), false, "Integer").asTypeDeclaration().;
		VariableDeclarator variableDeclarator = new VariableDeclarator(type , name, initialization);
		VariableDeclarationExpr parserExpression = new VariableDeclarationExpr(variableDeclarator);
		VariableDeclarationStatement statement = new VariableDeclarationStatement("Integer fixture;", "fixture");
		assertEquals("Integer fixture;", statement.getCode());
		assertEquals("fixture", statement.getVariableName());
	}

	@Test
	void equals() throws Exception {
		assertNotEquals(new VariableDeclarationStatement("Integer fixture;", "fixture"), new VariableDeclarationStatement("Integer fixture;", "fixture"));
	}

	@Test
	void string() throws Exception {
		assertEquals("Integer fixture;", new VariableDeclarationStatement("Integer fixture;", "fixture").toString());
	}

}
