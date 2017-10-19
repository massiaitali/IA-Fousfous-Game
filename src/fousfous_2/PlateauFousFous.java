package fousfous_2;

public class PlateauFousFous implements Partie1 {
    // Initialisation du plateau avce un tableau de cellule 
    public Cellule[][] Plateau;
    
    // Génere le plateau avec les pions blanc et noir
	public PlateauFousFous() {

		this.Plateau = new Cellule[8][8];

		for (int w = 0; w < 8; w++) {
			for (int i = 0; i < 8; i++) {
				if ((w % 2) == 0 && (i % 2) == 1) {
					this.Plateau[w][i] = new Cellule(w, i, "b");
				} else if ((w % 2) == 1 && (i % 2) == 0) {
					this.Plateau[w][i] = new Cellule(w, i, "n");
				} else {
					this.Plateau[w][i] = new Cellule(w, i, "-");
				}
			}
		}
    }

    // Test la fin de partie
    public boolean finDePartie() {
		return nbPionBlanc() == 0 || nbPionNoir() == 0;
	}
    // Renvoie le nb de pions blanc sur le plateau
	public int nbPionBlanc() {
		int nb = 0;
		for (int i = 0; i < 8; i++) {
			for (int w = 0; w < 8; w++) {
				if (this.Plateau[i][w].getColor().equals("b")) {
					nb++;
				}
			}
		}
		return nb;
	}
    // Renvoie le nombre de pion noir sur le plateau
	public int nbPionNoir() {
		int nb = 0;
		for (int i = 0; i < 8; i++) {
			for (int w = 0; w < 8; w++) {
				if (this.Plateau[i][w].getColor().equals("n")) {
					nb++;
				}
			}
		}
		return nb;
    }
    public void VoirTableau() {
        System.out.println("% Etat Initial du plateau de jeu:");
		System.out.println("% ABCDEFGH");
		for (int i = 0; i < 8; i++) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < 8; j++) {
				System.out.print(this.Plateau[i][j].getColor());
			}
			System.out.println(" " + (i + 1));
		}
		System.out.println("% ABCDEFGH");
    }
    
    public static void main(String[] args) {
		// String fileimport = "test.txt";
		PlateauFousFous PlateauTest = new PlateauFousFous();
        //PlateauTest.saveToFile(fileimport);
        PlateauTest.VoirTableau();  
        System.out.print(PlateauTest.Plateau[0][0].getId());
		//PlateauTest.setFromFile(fileimport);
		//PlateauTest.VoirTableau();
		//PlateauTest.play("B1-C2", "blanc");
		//PlateauTest.VoirTableau();
		//PlateauTest.estValide("B2-C2", "blanc");
		//PlateauTest.MovePossible("blanc");
	}
}