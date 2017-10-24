package fousfous_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Scanner;


public class PlateauFousFous implements Partie1 {
    // Initialisation du plateau avce un tableau de Case 
    public Case[][] Plateau;
    public final int TAILLE = 8;
    public String joueurCourant = "";
    
    // Génerer le plateau avec les pions blanc et noir
	public PlateauFousFous() {

		this.Plateau = new Case[this.TAILLE][this.TAILLE];

		for (int w = 0; w < this.TAILLE; w++) {
			for (int i = 0; i < this.TAILLE; i++) {
				if ((w % 2) == 0 && (i % 2) == 1) {
					this.Plateau[w][i] = new Case(w, i, "b");
				} else if ((w % 2) == 1 && (i % 2) == 0) {
					this.Plateau[w][i] = new Case(w, i, "n");
				} else {
					this.Plateau[w][i] = new Case(w, i, "-");
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

    		PlateauFousFous PlateauTest = new PlateauFousFous();
    		PlateauTest.VoirTableau();
    		System.out.println(PlateauTest.estValide("B1-C2", "blanc"));
    		PlateauTest.play("B1-C2", "blanc");
    		PlateauTest.VoirTableau();
    		System.out.println(PlateauTest.estValide("C2-E4", "blanc"));
    		PlateauTest.play("C2-E4", "blanc");
    		PlateauTest.VoirTableau();
    		/*Date today = new Date();
    		SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
		String file = "files/"+formater.format(today)+".txt";
    		Scanner sc = new Scanner(System.in);
		PlateauFousFous PlateauTest = new PlateauFousFous();
		System.out.println("La partie va commencer...");
        PlateauTest.VoirTableau();  
		while(!PlateauTest.finDePartie()) {
			System.out.println("Joueur courant : " + PlateauTest.joueurCourant);
			System.out.println("Il y a "+PlateauTest.getNbCoupsPossibles()+ " coups possibles sur le plateau.");
			
			System.out.println("Veuillez saisir le coup à jouer (exemple : B1-C2) : ");
			String coup = sc.nextLine();
			// utiliser la méthode estValide dans play
			PlateauTest.play(coup, PlateauTest.joueurCourant);

	        PlateauTest.saveToFile(file);
	        PlateauTest.setFromFile(file);
	        PlateauTest.VoirTableau();  
		}
		// score
		// Le joueur a gagné
        sc.close();*/

    	
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
							this.Plateau[ligne-1][j] = new Case(ligne, j, line.substring(c, c+1));
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
	// Verifier la validé d'un coup
	public boolean estValide(String move, String player) {
		Case[] cas = mvcel(move);
		if (cas[0].getColor().equals(cas[1].getColor())) {return false;}
		if (cas[1].getColor().equals("b") && player.substring(0, 1).equals("n")) {return true;}
		if (cas[1].getColor().equals("n") && player.substring(0, 1).equals("b")) {return true;}
		if (this.men(cas[0], player)) {return false;}
		if (this.men(cas[1], player)) {return true;}
		return false;
	}

	@Override
	//Déplacement d'un pion d'une Case a une autre
	public void play(String move, String player) {
		Case[] dest = mvcel(move);
		if (player.substring(0, 1).equals(dest[0].getColor()) && estValide(move, player)) {
			dest[1].setColor(dest[0].getColor());
			dest[0].setColor("-");
			for (Case[] tab : this.Plateau) {
				for (Case c : tab) {
					if (c.getI() == dest[0].getI() && c.getJ() == dest[0].getJ()) {
						c = dest[0];
					}
					if (c.getI() == dest[1].getI() && c.getJ() == dest[1].getJ()) {
						c = dest[1];
					}
				}
				if("blanc".equalsIgnoreCase(player)) {
					this.joueurCourant = "noir";
				} else {
					this.joueurCourant = "blanc";
				}

			}
		} else {
			System.out.print("Impossible");
		}
		
	}
	//Gestion de la menace d'un pion 
	public boolean men(Case cas, String player) {
		//obtenir les pions en diagonal 
		Case[] rest = new Case[15]; 
		// Je boucle sur la diagonal qui vas vers le sud est
		int i = cas.getI() + 1; int j = cas.getJ() + 1; int iter = 0;
		while (i < 8 && j < 8) {
			rest[iter] = this.Plateau[i][j];iter++;
			if (this.Plateau[i][j].getColor().equals("n")
					||this.Plateau[i][j].getColor().equals("b")
					) {
				break;
			}
			i++;j++;
		}
		// Je boucle sur la diagonal qui vas vers le nord ouest
		i = cas.getI() - 1; j = cas.getJ() - 1;
		while (i >= 0 && j >= 0) {
			rest[iter] = this.Plateau[i][j]; iter++;
			if (this.Plateau[i][j].getColor().equals("b")
					|| this.Plateau[i][j].getColor().equals("n")) {
				break;
			}
			i--;j--;
		}
		// Je boucle sur la diagonal qui vas vers le nord est
		i = cas.getI() - 1; j = cas.getJ() + 1;
		while (i >= 0 && j < 8) {
			rest[iter] = this.Plateau[i][j]; iter++;
			if (this.Plateau[i][j].getColor().equals("b")
					|| this.Plateau[i][j].getColor().equals("n")) {
				break;
			}
			i--;j++;
		}
		// Je boucle sur la diagonal qui vas vers le sud ouest
		i = cas.getI() + 1; j = cas.getJ() - 1;
		while (i < 8 && j >= 0) {
			rest[iter] = this.Plateau[i][j]; iter++;
			if (this.Plateau[i][j].getColor().equals("b")
					|| this.Plateau[i][j].getColor().equals("n")) {
				break;
			}
			i++; j--;
		}
		//Obtenir les enemies diagonal
		for (Case c : rest) {
			if (c != null) {
				//Verification de la menace cas joueur blanc et couleur de pion noir
				if (player.substring(0, 1).equals("b")
						&& c.getColor().equals("n")) {
					return true;
				}
				//Verification de la menace cas joueur noir et couleur de pion blanc
				if (player.substring(0, 1).equals("n")
						&& c.getColor().equals("b")) {
					return true;
				}
			}
		}
		return false;
	}
	// Echange les position sur le plateau
	private Case[] mvcel(String move) {
		Case[] mv = new Case[2];
			String[] data = move.split("-");
			String dest = data[1];
			String pion = data[0];
			mv[0] = this.Plateau[Integer.parseInt(pion.substring(1, 2)) - 1][pion
					.toCharArray()[0] - 'A'];
			mv[1] = this.Plateau[Integer.parseInt(dest.substring(1, 2)) - 1][dest
				                                             					.toCharArray()[0] - 'A'];
		return mv;

	}
	// retourne le nombre de coups possible pour le joueur courant
			public int getNbCoupsPossibles() {
				int nb = 0;
				// pour chaque pion du joueur courant
				for (Case[] tab : this.Plateau) {
					for (Case c : tab) {
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
							for(Case[] tab2 : this.Plateau) {
								for (Case c2 : tab2) {
									if(estValide(pionCourant+"-"+c2.getId(), this.joueurCourant)) {
										nb++;
//										System.out.println(nb + " - coup :"+pionCourant+"-"+c2.getId());
									}
								}
							}
						}
					}
				}
				return nb;
			}

}