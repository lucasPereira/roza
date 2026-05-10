package br.ufsc.ine.leb.roza.core.legacy.retrieval;

import br.ufsc.ine.leb.roza.core.legacy.exceptions.NegativeRelevantSetSizeException;
import br.ufsc.ine.leb.roza.core.legacy.exceptions.RecallLevelOutOfBoundsException;

public class RecallLevel {

	private final Integer level;

	public RecallLevel(Integer level) {
		if (level < 0 || level > 10) {
			throw new RecallLevelOutOfBoundsException();
		}
		this.level = level;
	}

	public Integer getAmountOfElementsInRelevantSet(Integer relevantSetSize) {
		if (relevantSetSize < 0) {
			throw new NegativeRelevantSetSizeException();
		}
		Double percent = level / 10.0;
		return (int) (percent * relevantSetSize);
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof RecallLevel) {
			RecallLevel other = (RecallLevel) object;
			return level.equals(other.level);
		}
		return super.equals(object);
	}

	public Integer getLevel() {
		return level;
	}

}
