package fousfous_2;

// Classe cellule permet de faciliter les fonctions
public class Cellule {
    private int j; // Position en j 
    private int i; // Position en i 
    private String id; // Id de type B1 A1 ect...
	private String Color; // Couleur donc joueur de cette cellule
	
	
	public Cellule(int i, int j, String Color){ // Initialiser une cellule
		this.i = i;
		this.j = j;
		this.Color = Color;
		this.id=""+(char)(j+65)+""+(i+1);
	}
	
	public String getId(){ // Retourner l'id 
		return this.id;
	}
	public int getI(){ // retourner I
		return this.i;
	}
	
	public int getJ(){ // retourner J
		return this.j;
	}
	public String getColor(){ // Retourner la couleur 
		return this.Color;
	}
	
	public void setColor(String Color){ // Definir la couleur
		this.Color = Color;
	}
}
