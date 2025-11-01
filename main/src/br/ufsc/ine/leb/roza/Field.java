package br.ufsc.ine.leb.roza;

public class Field {

	private final String type;
	private final String name;
	private final Statement initialization;

	public Field(String type, String name) {
		this(type, name, null);
	}

	public Field(String type, String name, Statement initialization) {
		this.type = type;
		this.name = name;
		this.initialization = initialization;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	public Statement getInitialization() {
		return initialization;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Field) {
			Field other = (Field) object;
			Boolean sameType = this.type.equals(other.type);
			Boolean sameName = this.name.equals(other.name);
			Boolean sameInitialization = (this.initialization == null && other.initialization == null) || (this.initialization != null && other.initialization != null && this.initialization.equals(other.initialization));
			return sameType && sameName && sameInitialization;
		}
		return false;
	}

	@Override
	public String toString() {
		return initialization != null ? String.format("%s %s = %s", type, name, initialization) : String.format("%s %s;", type, name);
	}

}
