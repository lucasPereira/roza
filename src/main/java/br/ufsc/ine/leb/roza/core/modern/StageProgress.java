package br.ufsc.ine.leb.roza.core.modern;

@FunctionalInterface
public interface StageProgress {

	void report(int completed, int total);

	static StageProgress ignore() {
		return (completed, total) -> {
		};
	}
}
