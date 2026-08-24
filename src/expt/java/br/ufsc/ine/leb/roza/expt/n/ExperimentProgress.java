package br.ufsc.ine.leb.roza.expt.n;

import java.io.Flushable;
import java.io.IOException;
import java.util.List;
import java.util.Locale;

import br.ufsc.ine.leb.roza.core.modern.StageProgress;

final class ExperimentProgress {

	private static final List<String> STAGES = List.of("measure", "cluster", "rank");
	private static final long LIVE_INTERVAL_NANOS = 500_000_000L;

	private final int variantCount;
	private final int totalVariants;
	private final Appendable out;
	private int completedVariants;
	private int subjectStart;
	private String project = "";
	private String variant = "";
	private String stage = "";
	private double stageFraction;
	private boolean inVariant;
	private long lastLiveAt;
	private String liveLine = "";

	ExperimentProgress(int subjectCount, int variantCount) {
		this(subjectCount, variantCount, System.out);
	}

	ExperimentProgress(int subjectCount, int variantCount, Appendable out) {
		this.variantCount = variantCount;
		this.totalVariants = subjectCount * variantCount;
		this.out = out;
	}

	void beginSubject(String name, int tests) {
		project = name;
		subjectStart = completedVariants;
		emit(String.format(Locale.ROOT, "[%s] %d tests", name, tests), true);
	}

	void beginVariant(String variantName) {
		variant = variantName;
		stage = "";
		stageFraction = 0;
		inVariant = true;
		emit(line(), true);
	}

	StageProgress stage(String stageName) {
		stage = stageName;
		stageFraction = 0;
		emit(line(), true);
		return (completed, total) -> {
			stageFraction = total <= 0 ? 1.0 : Math.min(1.0, completed / (double) total);
			emit(line(), false);
		};
	}

	void finishVariant() {
		stage = "done";
		stageFraction = 1.0;
		emit(line(), true);
		completedVariants++;
		inVariant = false;
		stage = "";
		stageFraction = 0;
	}

	void abandonSubject() {
		inVariant = false;
		completedVariants = subjectStart + variantCount;
		stage = "";
		stageFraction = 0;
	}

	double totalPercent() {
		if (totalVariants == 0) {
			return 100.0;
		}
		return 100.0 * (completedVariants + variantFraction()) / totalVariants;
	}

	double variantPercent() {
		return 100.0 * variantFraction();
	}

	private double variantFraction() {
		if (!inVariant) {
			return 0.0;
		}
		if ("done".equals(stage)) {
			return 1.0;
		}
		int index = STAGES.indexOf(stage);
		if (index < 0) {
			return 0.0;
		}
		return (index + stageFraction) / STAGES.size();
	}

	private String line() {
		if (stage.isEmpty()) {
			return String.format(Locale.ROOT, "[%s] %s | total %.1f%%", project, variant, totalPercent());
		}
		return String.format(
				Locale.ROOT,
				"[%s] %s %s %.0f%% | variant %.0f%% | total %.1f%%",
				project,
				variant,
				stage,
				stageFraction * 100.0,
				variantPercent(),
				totalPercent());
	}

	private void emit(String text, boolean newline) {
		if (out == null) {
			return;
		}
		long now = System.nanoTime();
		if (!newline && now - lastLiveAt < LIVE_INTERVAL_NANOS) {
			return;
		}
		lastLiveAt = now;
		try {
			if (!newline) {
				out.append('\r');
			} else if (!liveLine.isEmpty()) {
				out.append('\r');
				out.append(text);
				pad(text);
				out.append('\n');
				liveLine = "";
				flush();
				return;
			}
			out.append(text);
			pad(text);
			if (newline) {
				out.append('\n');
				liveLine = "";
			} else {
				liveLine = text;
			}
			flush();
		} catch (Exception exception) {
			throw new IllegalStateException(exception);
		}
	}

	private void pad(String text) throws IOException {
		int extra = liveLine.length() - text.length();
		for (int index = 0; index < extra; index++) {
			out.append(' ');
		}
	}

	private void flush() {
		if (out instanceof Flushable) {
			try {
				((Flushable) out).flush();
			} catch (IOException exception) {
				throw new IllegalStateException(exception);
			}
		}
	}
}
