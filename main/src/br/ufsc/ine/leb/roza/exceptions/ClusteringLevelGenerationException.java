package br.ufsc.ine.leb.roza.exceptions;

import java.util.List;

import br.ufsc.ine.leb.roza.clustering.Level;

public class ClusteringLevelGenerationException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	private List<Level> levels;
	private TiebreakException exception;

	public ClusteringLevelGenerationException(List<Level> levels, TiebreakException exception) {
		this.levels = levels;
		this.exception = exception;
	}

	public TiebreakException getTibreakException() {
		return exception;
	}

	public List<Level> getLevels() {
		return levels;
	}

}
