package fousfous_2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;


public class PlateauFousFous implements Partie1 {
    // Initialisation du plateau avce un tableau de Case 
    public Case[][] plateau;
    public final int TAILLE = 8;
    public String joueurCourant = "";
    
    // Génerer le plateau avec les pions blanc et noir
	public PlateauFousFous() {

		this.plateau = new Case[this.TAILLE][this.TAILLE];

		for (int w = 0; w < this.TAILLE; w++) {
			for (int i = 0; i < this.TAILLE; i++) {
				if ((w % 2) == 0 && (i % 2) == 1) {
					this.plateau[w][i] = new Case(w, i, "b");
				} else if ((w % 2) == 1 && (i % 2) == 0) {
					this.plateau[w][i] = new Case(w, i, "n");
				} else {
					this.plateau[w][i] = new Case(w, i, "-");
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
				if ("b".equalsIgnoreCase(this.plateau[i][w].getColor())) {
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
				if ("n".equalsIgnoreCase(this.plateau[i][w].getColor())) {
					nb++;
				}
			}
		}
		return nb;
    }
	
	// Affichage du plateau
    public String toString() {
    	String res = "";
        res += "% Etat Initial du plateau de jeu:\n";
		res += "% ABCDEFGH\n";
		for (int i = 0; i < TAILLE; i++) {
			res += (i + 1) + " ";
			for (int j = 0; j < TAILLE; j++) {
				res += this.plateau[i][j].getColor();
			}
			res += " " + (i + 1)+"\n";
		}
		res += "% ABCDEFGH\n";
		return res;
    }
    
	// Permet de remplir le plateau à partir d'un fichier
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
							this.plateau[ligne-1][j] = new Case(ligne-1, j, line.substring(c, c+1));
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
		else {
			System.out.println("Le fichier n'existe pas");
		}
	}

	// Permet de sauvegarder le plateau dans un fichier
	public void saveToFile(String fileName) {
		final File file = new File(fileName); 
        try {
            final FileWriter writer = new FileWriter(file);
            try {            	
            	writer.write("% Etat Initial du plateau de jeu:\n");
            	writer.write("% ABCDEFGH\n");
        		for (int i = 0; i < TAILLE; i++) {
        			writer.write((i + 1) + " ");
        			for (int j = 0; j < TAILLE; j++) {
        				writer.write(this.plateau[i][j].getColor());
        			}
        			writer.write(" " + (i + 1)+"\n");
        		}
        		writer.write("% ABCDEFGH");
        		
            } finally {
                writer.close();
            }
        } catch (Exception e) {
            System.out.println("Impossible de créer le fichier");
        }		
	}

	@Override
	// Verifier la validité d'un coup
	public boolean estValide(String move, String player) {
		Case[] cas = mvcel(move);

		// Chemin vide et diag verif
		if(!verifDiagVide(cas, player)) {
			return false;
		}
		// vrai pion
		if("-".equalsIgnoreCase(cas[0].getColor())) {
			return false;
		}
		//Ne pas aller sur la même couleur 
		if(cas[1].getColor().equalsIgnoreCase(cas[0].getColor())) {
			return false;
		}
		// On attaque un pion 
 		if(("n".equalsIgnoreCase(cas[0].getColor()) && "b".equalsIgnoreCase(cas[1].getColor())) 
 				|| ("b".equalsIgnoreCase(cas[0].getColor()) && "n".equalsIgnoreCase(cas[1].getColor()))) {
			return true;
		}
		else {
			// Verif que nous sommes pas en position d'attaque
			if(men(cas[0], player)) {
				return false;
			}
			// On peut manger avec ce deplacement
			if(men(cas[1], player)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	@Override
	//Déplacement d'un pion d'une Case a une autre
	public void play(String move, String player) {
		if(estValide(move, player)) {
			Case[] dest = mvcel(move);
			if (player.substring(0, 1).equalsIgnoreCase(dest[0].getColor())) {
				dest[1].setColor(dest[0].getColor());
				dest[0].setColor("-");
				for (Case[] tab : this.plateau) {
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
				System.out.println("Impossible");
			}
		}
		else {
			System.out.println("Le mouvement est impossible");	
		}	
	}
	// Recuperer les pions en diagonal (on s'arrete une fois un pion trouvé)
	public Case[] Obtdiag(Case C) {
		Case[] res = new Case[15];

		int i = C.getI() + 1;
		int j = C.getJ() + 1;
		int iter = 0;
		//Diagonal sud est
		while (i < 8 && j < 8) {
			res[iter] = this.plateau[i][j];
			iter++;
			if ("b".equalsIgnoreCase(this.plateau[i][j].getColor())
					|| "n".equalsIgnoreCase(this.plateau[i][j].getColor())) {
				break;
			}

			i++;
			j++;
		}

		i = C.getI() - 1;
		j = C.getJ() - 1;
		//Diagonal nord ouest
		while (i >= 0 && j >= 0) {

			res[iter] = this.plateau[i][j];
			iter++;
			if ("b".equalsIgnoreCase(this.plateau[i][j].getColor())
					|| "n".equalsIgnoreCase(this.plateau[i][j].getColor())) {
				break;
			}

			i--;
			j--;
		}

		i = C.getI() - 1;
		j = C.getJ() + 1;
		//Diagonal nord est
		while (i >= 0 && j < 8) {

			res[iter] = this.plateau[i][j];
			iter++;
			if ("b".equalsIgnoreCase(this.plateau[i][j].getColor())
					|| "n".equalsIgnoreCase(this.plateau[i][j].getColor())) {
				break;
			}

			i--;
			j++;
		}

		i = C.getI() + 1;
		j = C.getJ() - 1;
		//Diagonal sud ouest
		while (i < 8 && j >= 0) {

			res[iter] = this.plateau[i][j];
			iter++;
			if ("b".equalsIgnoreCase(this.plateau[i][j].getColor())
					|| "n".equalsIgnoreCase(this.plateau[i][j].getColor())) {
				break;
			}

			i++;
			j--;
		}
		return res;

	}
	// Obtenir la liste d'enemie direct en diagonal
	public Case[] ObtEnemiDiag(Case C, String player) {
		Case[] res = new Case[4];
		int iter = 0;
		for (Case c : this.Obtdiag(C)) {
			if (c != null) {
				if ("b".equalsIgnoreCase(player.substring(0, 1))
						&& "n".equalsIgnoreCase(c.getColor())) {
					res[iter] = c;
					iter++;
				}
				if ("n".equalsIgnoreCase(player.substring(0, 1))
						&& "b".equalsIgnoreCase(c.getColor())) {
					res[iter] = c;
					iter++;
				}
			}
		}
		return res;
	}
	
	//Gestion de la menace d'un pion 
	public boolean men(Case cas, String player) {
		for (Case c : ObtEnemiDiag(cas, player)) {
			if (c != null) {
				return true;
			}
		}
		return false;
	}
	
	// Verifie que la case choisi a attaque appartient au enemie direct en diagonal
	public boolean verifDiagVide(Case[] cas, String player) {
		Case origin = cas[0];
		Case dest = cas[1];
		// Gere attaque a enemie direct
		for (Case c : Obtdiag(origin)) {
			if (c != null) {
				if(dest.getId().equalsIgnoreCase(c.getId())) {
					return true;
				}
			}
		}
		return false;
	}
	// Echange les positions sur le plateau
	private Case[] mvcel(String move) {
		Case[] mv = new Case[2];
		String[] data = move.split("-");
		String dest = data[1];
		String pion = data[0];
		mv[0] = this.plateau[Integer.parseInt(pion.substring(1, 2)) - 1][pion
				.toCharArray()[0] - 'A'];
		mv[1] = this.plateau[Integer.parseInt(dest.substring(1, 2)) - 1][dest
				                                             					.toCharArray()[0] - 'A'];
		return mv;

	}
	
	// retourne le nombre de coups possible pour le joueur courant
	public ArrayList<String> mouvementsPossibles(String player) {
        //System.out.println(this);
		ArrayList<String> coupsPossibles = new ArrayList<String>();

		// pour chaque pion du joueur courant
		for (Case[] tab : this.plateau) {
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
					for(Case[] tab2 : this.plateau) {
						for (Case c2 : tab2) {
							if(!"-".equalsIgnoreCase(c2.getColor()) && !pionCourant.equalsIgnoreCase(c2.getId())) {
								if(estValide(pionCourant+"-"+c2.getId(), this.joueurCourant)) {
									coupsPossibles.add(pionCourant+"-"+c2.getId());									
//									System.out.println("mouvement="+pionCourant+"-"+c2.getId());
								}
							}							
						}
					}
				}
			}
		}
		return coupsPossibles;
	}

    public static void main(String[] args) {
//    	PlateauFousFous PlateauTest = new PlateauFousFous();
//    	System.out.println(PlateauTest);
//    	System.out.println(PlateauTest.estValide("B1-C2", "blanc"));
//    	PlateauTest.play("B1-C2", "blanc");
//    	System.out.println(PlateauTest);
//    	System.out.println(PlateauTest.estValide("C2-F5", "noir"));
//    	PlateauTest.play("B1-C2", "blanc");
//    	System.out.println(PlateauTest);
//    	System.out.println(PlateauTest.estValide("C2-B1", "blanc"));
//    	PlateauTest.play("C2-B1", "blanc");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("E2-D3", "noir");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("F1-G2", "blanc");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("G2-F1", "blanc");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("D7-E8", "blanc");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("C6-B7", "noir");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("C6-B7", "noir");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("D1-E2", "blanc");
//    	System.out.println(PlateauTest);
//    	PlateauTest.play("H3-G4", "blanc");
//    	System.out.println(PlateauTest);
    	
    	Date today = new Date();
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	String file = "files/"+formater.format(today)+".txt";
    	Scanner sc = new Scanner(System.in);
    	PlateauFousFous PlateauTest = new PlateauFousFous();
    	
    	System.out.println("La partie va commencer...");
		while(!PlateauTest.finDePartie()) {
			// Affichage du plateau courant
	        System.out.println(PlateauTest);
	        
			System.out.println("Joueur courant : " + PlateauTest.joueurCourant);
			ArrayList<String> listeCoupsPossibles = PlateauTest.mouvementsPossibles(PlateauTest.joueurCourant);
			System.out.println("Il y a "+listeCoupsPossibles.size()+ " coups possibles sur le plateau.");
			System.out.println(listeCoupsPossibles);
			
			System.out.println("Veuillez saisir le coup à jouer (exemple : B1-C2) : ");
			String coup = sc.nextLine();
			PlateauTest.play(coup.toUpperCase(), PlateauTest.joueurCourant);

	        PlateauTest.saveToFile(file);
	        PlateauTest.setFromFile(file);
		}
        sc.close();
		if(PlateauTest.nbPionBlanc() == 0) {
			System.out.println("Le joueur noir a gagné !!");
		}
		if(PlateauTest.nbPionNoir() == 0) {
			System.out.println("Le joueur blanc a gagné !!");			
		}    	
	}
}