package fousfous_2;

//Classe Case permet de faciliter les fonctions
public class Case {
	private int j; // Position en j 
	private int i; // Position en i 
	private String id; // Id de type B1 A1 ect...
	private Color color; // Couleur donc joueur de cette Case
	
	// Initialiser une Case
	public Case(int i, int j, Color color){ 
		this.i = i;
		this.j = j;
		this.color = color;
		this.id = ""+(char)(j+65)+""+(i+1);
	}
	
	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String toString() {
		String res = "tab["+i+"]["+j+"] = "+id +"("+color+")\n";
		return res;
	}
}

