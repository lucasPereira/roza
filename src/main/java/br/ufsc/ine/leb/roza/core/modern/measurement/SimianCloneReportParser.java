package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.parser.Parser;
import org.jsoup.select.Elements;

public final class SimianCloneReportParser {

	public List<CloneFragment> parse(String reportContent) {
		Objects.requireNonNull(reportContent);
		Document document = Jsoup.parse(xml(reportContent), "", Parser.xmlParser());
		List<CloneFragment> fragments = new ArrayList<>();
		for (Element set : document.select("set")) {
			Elements blocks = set.select("block");
			for (Element source : blocks) {
				for (Element target : blocks) {
					if (!source.attr("sourceFile").equals(target.attr("sourceFile"))) {
						fragments.add(new CloneFragment(
								source.attr("sourceFile"),
								target.attr("sourceFile"),
								line(source, "startLineNumber"),
								line(source, "endLineNumber")));
					}
				}
			}
		}
		return fragments;
	}

	private String xml(String reportContent) {
		int xmlStart = reportContent.indexOf("<?xml");
		if (xmlStart < 0) {
			throw new IllegalArgumentException("Could not find Simian XML report.");
		}
		return reportContent.substring(xmlStart);
	}

	private int line(Element block, String attribute) {
		String value = block.attr(attribute);
		if (value.isBlank()) {
			throw new IllegalArgumentException("Missing Simian block attribute: " + attribute);
		}
		return Integer.parseInt(value);
	}
}
