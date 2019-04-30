package br.ufsc.ine.leb.roza.uniformity;

import java.util.LinkedList;
import java.util.List;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.body.BodyDeclaration;
import com.github.javaparser.ast.body.FieldDeclaration;
import com.github.javaparser.ast.expr.Expression;
import com.github.javaparser.ast.expr.MethodCallExpr;
import com.github.javaparser.ast.nodeTypes.NodeWithArguments;

import br.ufsc.ine.leb.roza.Statement;

public class DataExtractor {

	public static List<String> from(Statement statement) {
		List<String> data = new LinkedList<String>();
		BodyDeclaration<?> parsedClass = JavaParser.parseBodyDeclaration(statement.getText());
		parsedClass.findAll(FieldDeclaration.class).forEach((parsedField) -> {
			parsedField.getVariables().forEach((parsedVariable) -> {
				if (parsedVariable.getInitializer().isPresent()) {
					Expression parseInitializer = parsedVariable.getInitializer().get();
					parseExpression(data, parseInitializer);
				}
			});
		});
		return data;
	}

	private static void parseExpression(List<String> data, Expression parseInitializer) {
		NodeWithArguments<?> object = null;
		if (parseInitializer.isObjectCreationExpr()) {
			object = parseInitializer.asObjectCreationExpr();
		} else if (parseInitializer.isMethodCallExpr()) {
			object = parseInitializer.asMethodCallExpr();
			((MethodCallExpr) object).findAll(MethodCallExpr.class).forEach((method) -> {
				if (method != parseInitializer.asMethodCallExpr()) {
					parseExpression(data, method);
				}
			});
		}
		object.getArguments().forEach((argument) -> {
			if (argument.isObjectCreationExpr()) {
				parseExpression(data, argument.asObjectCreationExpr());
			} else if (argument.isMethodCallExpr()) {
				parseExpression(data, argument.asMethodCallExpr());
			} else {
				data.add(argument.toString());
			}
		});

	}

}
