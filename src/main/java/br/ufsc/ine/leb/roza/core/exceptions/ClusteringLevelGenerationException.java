package br.ufsc.ine.leb.roza.core.exceptions;

import java.util.List;

import br.ufsc.ine.leb.roza.core.clustering.Level;

public class ClusteringLevelGenerationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private final List<Level> levels;
	private final TiebreakException exception;

	public ClusteringLevelGenerationException(List<Level> levels, TiebreakException exception) {
		this.levels = levels;
		this.exception = exception;
	}

	public TiebreakException getTiebreakException() {
		return exception;
	}

	public List<Level> getLevels() {
		return levels;
	}

}
