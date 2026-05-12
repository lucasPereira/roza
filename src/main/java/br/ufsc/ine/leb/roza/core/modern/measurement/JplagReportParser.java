package br.ufsc.ine.leb.roza.core.modern.measurement;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public final class JplagReportParser {

	private static final Pattern HEADER = Pattern.compile("^(.+) \\(([0-9]+(?:\\.[0-9]+)?)%\\)$");

	public JplagSimilarityReport parse(String reportContent) {
		Document document = Jsoup.parse(reportContent);
		for (Element row : document.select("tr")) {
			Elements headers = row.select("th");
			if (headers.size() >= 3) {
				Header source = parseHeader(headers.get(1).text());
				Header target = parseHeader(headers.get(2).text());
				return new JplagSimilarityReport(source.fileName, target.fileName, source.similarity, target.similarity);
			}
		}
		throw new IllegalArgumentException("Could not find JPlag similarity header.");
	}

	private Header parseHeader(String text) {
		Matcher matcher = HEADER.matcher(text);
		if (!matcher.matches()) {
			throw new IllegalArgumentException("Invalid JPlag similarity header: " + text);
		}
		return new Header(matcher.group(1), Double.parseDouble(matcher.group(2)) / 100.0);
	}

	private static final class Header {

		private final String fileName;
		private final double similarity;

		private Header(String fileName, double similarity) {
			this.fileName = fileName;
			this.similarity = similarity;
		}
	}
}
