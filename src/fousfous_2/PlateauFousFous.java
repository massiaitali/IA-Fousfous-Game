package fousfous_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class PlateauFousFous implements Partie1 {
    // Initialisation du plateau avce un tableau de cellule 
    public Cellule[][] Plateau;
    public final int TAILLE = 8;
    public String joueurCourant = "";
    
    // Génere le plateau avec les pions blanc et noir
	public PlateauFousFous() {

		this.Plateau = new Cellule[this.TAILLE][this.TAILLE];

		for (int w = 0; w < this.TAILLE; w++) {
			for (int i = 0; i < this.TAILLE; i++) {
				if ((w % 2) == 0 && (i % 2) == 1) {
					this.Plateau[w][i] = new Cellule(w, i, "b");
				} else if ((w % 2) == 1 && (i % 2) == 0) {
					this.Plateau[w][i] = new Cellule(w, i, "n");
				} else {
					this.Plateau[w][i] = new Cellule(w, i, "-");
				}
			}
		}
		this.joueurCourant = "blanc";
    }

    // Test la fin de partie
    public boolean finDePartie() {
		return nbPionBlanc() == 0 || nbPionNoir() == 0;
	}
    // Renvoie le nb de pions blanc sur le plateau
	public int nbPionBlanc() {
		int nb = 0;
		for (int i = 0; i < TAILLE; i++) {
			for (int w = 0; w < TAILLE; w++) {
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
		for (int i = 0; i < TAILLE; i++) {
			for (int w = 0; w < TAILLE; w++) {
				if (this.Plateau[i][w].getColor().equals("n")) {
					nb++;
				}
			}
		}
		return nb;
    }
	// Afficher le tableau pour effetuer les tests
    public void VoirTableau() {
        System.out.println("% Etat Initial du plateau de jeu:");
		System.out.println("% ABCDEFGH");
		for (int i = 0; i < TAILLE; i++) {
			System.out.print((i + 1) + " ");
			for (int j = 0; j < TAILLE; j++) {
				System.out.print(this.Plateau[i][j].getColor());
			}
			System.out.println(" " + (i + 1));
		}
		System.out.println("% ABCDEFGH");
    }
    
    public static void main(String[] args) {
		String fileimport = "files/test.txt";
		String fileexport = "files/test2.txt";
		PlateauFousFous PlateauTest = new PlateauFousFous();
		PlateauTest.VoirTableau();
		System.out.println(PlateauTest.joueurCourant);
		System.out.println(PlateauTest.getNbCoupsPossibles());
		PlateauTest.play("B1-C2", "blanc");
		System.out.println(PlateauTest.joueurCourant);
		System.out.println(PlateauTest.getNbCoupsPossibles());
		PlateauTest.VoirTableau();
        PlateauTest.saveToFile(fileexport);
        //PlateauTest.VoirTableau();  
        fileexport = fileimport;
		PlateauTest.setFromFile(fileimport);
		PlateauTest.VoirTableau();
		System.out.println(PlateauTest.estValide("B1-C2", "blanc"));
		//PlateauTest.MovePossible("blanc");
	}

	@Override
	public void setFromFile(String fileName) {
		File file = new File(fileName);
		if(file.isFile()) {
			FileReader fr;
			try {
				fr = new FileReader(file);
				BufferedReader br = new BufferedReader(fr);
				String line;
				int c = 2;
				while ((line = br.readLine()) != null) {
					c = 2;
					if(!"%".equalsIgnoreCase(line.substring(0, 1))) {
						int ligne = Integer.parseInt(line.substring(0, 1));
						for (int j = 0; j < TAILLE; j++) {							
							this.Plateau[ligne-1][j] = new Cellule(ligne, j, line.substring(c, c+1));
							c++;
						}
					}
				}
				br.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}			
		}		
	}

	@Override
	public void saveToFile(String fileName) {
		final File file = new File(fileName); 
        try {
        	file.createNewFile();
            final FileWriter writer = new FileWriter(file);
            try {
            	
            	writer.write("% Etat Initial du plateau de jeu:\n");
            	writer.write("% ABCDEFGH\n");
        		for (int i = 0; i < TAILLE; i++) {
        			writer.write((i + 1) + " ");
        			for (int j = 0; j < TAILLE; j++) {
        				writer.write(this.Plateau[i][j].getColor());
        			}
        			writer.write(" " + (i + 1)+"\n");
        		}
        		writer.write("% ABCDEFGH");
        		
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de creer le fichier");
        }		
	}

	@Override
	public boolean estValide(String move, String player) {
		Cellule[] cel = mvcel(move);
		if (player.substring(0, 1).equals("n") 
				&& cel[1].getColor().equals("b")) {
				return true;
			}
		if (player.substring(0, 1).equals("b") 
			&& cel[1].getColor().equals("n")) {
			return true;
		}
		return false;
	}

	@Override
	//Deplacement d'un pion d'une cellule a une autre
	public void play(String move, String player) {
		Cellule[] dest = mvcel(move);
		if (player.substring(0, 1).equals(dest[0].getColor())) {
			dest[1].setColor(dest[0].getColor());
			dest[0].setColor("-");
			for (Cellule[] tab : this.Plateau) {
				for (Cellule c : tab) {
					if (c.getI() == dest[0].getI() && c.getJ() == dest[0].getJ()) {
						c = dest[0];
					}
					if (c.getI() == dest[1].getI() && c.getJ() == dest[1].getJ()) {
						c = dest[1];
					}
				}
			}
			if("blanc".equalsIgnoreCase(player)) {
				this.joueurCourant = "noir";
			} else {
				this.joueurCourant = "blanc";
			}
		} else {
			System.out.print("Impossible");
		}
		
	}
	// Echange les position sur le plateau
	private Cellule[] mvcel(String move) {
		Cellule[] mv = new Cellule[2];
			String[] data = move.split("-");
			String dest = data[1];
			mv[1] = this.Plateau[Integer.parseInt(dest.substring(1, 2)) - 1][dest
				                                             					.toCharArray()[0] - 'A'];
			String pion = data[0];
			mv[0] = this.Plateau[Integer.parseInt(pion.substring(1, 2)) - 1][pion
					.toCharArray()[0] - 'A'];
		return mv;

	}
	
	// retourne le nombre de coups possible pour le joueur courant
	public int getNbCoupsPossibles() {
		int nb = 0;
		// pour chaque pion du joueur courant
		for (Cellule[] tab : this.Plateau) {
			for (Cellule c : tab) {
				String pionCourant = "";
				if("b".equalsIgnoreCase(c.getColor()) && "blanc".equalsIgnoreCase(this.joueurCourant)) {
					pionCourant = c.getId();
				}
				else if("n".equalsIgnoreCase(c.getColor()) && "noir".equalsIgnoreCase(this.joueurCourant)) {
					pionCourant = c.getId();
				}
				// Si la cellule n'est pas vide
				if(pionCourant != "") {
					// on parcourt le plateau pour calculer le nombre de déplacement que le pion courant peut faire
					for(Cellule[] tab2 : this.Plateau) {
						for (Cellule c2 : tab2) {
							if(estValide(pionCourant+"-"+c2.getId(), this.joueurCourant)) {
								nb++;
//								System.out.println(nb + " - coup :"+pionCourant+"-"+c2.getId());
							}
						}
					}
				}
			}
		}
		return nb;
	}
	
}