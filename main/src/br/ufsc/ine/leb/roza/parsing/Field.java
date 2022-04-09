package br.ufsc.ine.leb.roza.parsing;

public class Field {

	private String type;
	private String name;

	public Field(String type, String name) {
		this.type = type;
		this.name = name;
	}

	public String getType() {
		return type;
	}

	public String getName() {
		return name;
	}

	@Override
	public boolean equals(Object object) {
		if (object instanceof Field) {
			Field other = (Field) object;
			Boolean sameType = this.type.equals(other.type);
			Boolean sameName = this.name.equals(other.name);
			return sameType && sameName;
		}
		return false;
	}

	@Override
	public String toString() {
		return String.format("private %s %s;", type, name);
	}

}
