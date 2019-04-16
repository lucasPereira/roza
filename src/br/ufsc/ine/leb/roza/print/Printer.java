package br.ufsc.ine.leb.roza.print;

import java.io.IOException;
import java.io.PrintStream;

import br.ufsc.ine.leb.roza.SimilarityAssessment;
import br.ufsc.ine.leb.roza.SimilarityReport;
import br.ufsc.ine.leb.roza.TestField;

public class Printer {

	private SimilarityReport<TestField> report;

	public Printer(SimilarityReport<TestField> report) throws IOException {
		this.report = report;
	}

	public void print(PrintStream out) throws IOException {
		out.write(String.format("score:%s", report.getScore()).getBytes());
		for (SimilarityAssessment<TestField> assessment : report.getAssessments()) {
			out.write(String.format("\n%s	%s	%s", assessment.getSource().getTestClass().getName(),
					assessment.getTarget().getTestClass().getName(), assessment.getScore()).getBytes());
		}
		out.flush();		
	}

}
