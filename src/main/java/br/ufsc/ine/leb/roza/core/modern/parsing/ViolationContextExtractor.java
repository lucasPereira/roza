package br.ufsc.ine.leb.roza.core.modern.parsing;

import java.util.Optional;
import java.util.regex.Pattern;

import com.github.javaparser.JavaParser;
import com.github.javaparser.ast.CompilationUnit;
import com.github.javaparser.ast.body.ClassOrInterfaceDeclaration;
import com.github.javaparser.ast.body.MethodDeclaration;

import br.ufsc.ine.leb.roza.core.modern.loading.CodeFile;
import br.ufsc.ine.leb.roza.core.modern.loading.LoadedCodeFiles;

public final class ViolationContextExtractor {

	public Optional<String> extractContext(LoadedCodeFiles codeFiles, TestCodeViolation violation) {
		return findCodeFile(codeFiles, violation.testClassName())
				.flatMap(file -> extractContext(file, violation));
	}

	public Optional<String> extractClassCode(LoadedCodeFiles codeFiles, String qualifiedClassName) {
		return findCodeFile(codeFiles, qualifiedClassName)
				.flatMap(file -> extractClassCode(file, qualifiedClassName));
	}

	public Optional<String> extractMethodCode(LoadedCodeFiles codeFiles, String qualifiedClassName, String methodName) {
		return findCodeFile(codeFiles, qualifiedClassName)
				.flatMap(file -> extractMethodCode(file, qualifiedClassName, methodName));
	}

	private Optional<String> extractClassCode(CodeFile codeFile, String qualifiedClassName) {
		CompilationUnit unit = JavaParser.parse(codeFile.content());
		return findType(unit, qualifiedClassName).map(ClassOrInterfaceDeclaration::toString);
	}

	private Optional<String> extractMethodCode(CodeFile codeFile, String qualifiedClassName, String methodName) {
		CompilationUnit unit = JavaParser.parse(codeFile.content());
		return findType(unit, qualifiedClassName)
				.flatMap(type -> type.getMethodsByName(methodName).stream().findFirst().map(MethodDeclaration::toString));
	}

	private Optional<String> extractContext(CodeFile codeFile, TestCodeViolation violation) {
		if (violation.scope() == ViolationScope.TEST_METHOD) {
			return violation.testMethodName()
					.flatMap(methodName -> extractMethodCode(codeFile, violation.testClassName(), methodName));
		}
		return extractClassCode(codeFile, violation.testClassName());
	}

	private Optional<CodeFile> findCodeFile(LoadedCodeFiles codeFiles, String qualifiedClassName) {
		String simpleClassName = simpleClassName(qualifiedClassName);
		Pattern classDeclaration = Pattern.compile("\\bclass\\s+" + Pattern.quote(simpleClassName) + "\\b");
		for (CodeFile codeFile : codeFiles.codeFiles()) {
			if (!classDeclaration.matcher(codeFile.content()).find()) {
				continue;
			}
			CompilationUnit unit = JavaParser.parse(codeFile.content());
			if (findType(unit, qualifiedClassName).isPresent()) {
				return Optional.of(codeFile);
			}
		}
		return Optional.empty();
	}

	private Optional<ClassOrInterfaceDeclaration> findType(CompilationUnit unit, String qualifiedClassName) {
		String simpleClassName = simpleClassName(qualifiedClassName);
		String packageName = packageName(qualifiedClassName);
		String actualPackage = unit.getPackageDeclaration().map(declaration -> declaration.getNameAsString()).orElse("");
		if (!packageName.equals(actualPackage)) {
			return Optional.empty();
		}
		return unit.getTypes()
				.stream()
				.filter(type -> type.isClassOrInterfaceDeclaration())
				.map(type -> type.asClassOrInterfaceDeclaration())
				.filter(type -> type.getNameAsString().equals(simpleClassName))
				.findFirst();
	}

	private String packageName(String qualifiedClassName) {
		int separator = qualifiedClassName.lastIndexOf('.');
		if (separator == -1) {
			return "";
		}
		return qualifiedClassName.substring(0, separator);
	}

	private String simpleClassName(String qualifiedClassName) {
		int separator = qualifiedClassName.lastIndexOf('.');
		if (separator == -1) {
			return qualifiedClassName;
		}
		return qualifiedClassName.substring(separator + 1);
	}
}
