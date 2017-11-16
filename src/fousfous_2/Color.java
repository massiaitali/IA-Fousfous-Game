package fousfous_2;

public enum Color {
	//Objets directement construits	
	BLANC ("b"),
	NOIR ("n"),
	VIDE ("-");

	private String name = "";

	//Constructeur
	Color(String name){
		this.name = name;
	}

	public String toString() {
		return name;
	}
	
	// Convertion d'un string en Color
	public static Color fromString(String text) {
		for (Color c : Color.values()) {
			if (c.name.equalsIgnoreCase(text)) {
				return c;
			}
	    }
	    return null;
	}
}

