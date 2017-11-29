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
    // Initialise le plateau avec un tableau de Case 
    public Case[][] plateau;
    public String joueurCourant = "";
    public final int TAILLE = 8;
    public final String JOUEUR_BLANC = "blanc";
    public final String JOUEUR_NOIR = "noir";
    
    // Génère le plateau avec les pions blanc et noir
	public PlateauFousFous() {
		this.plateau = new Case[this.TAILLE][this.TAILLE];
		for (int w = 0; w < this.TAILLE; w++) {
			for (int i = 0; i < this.TAILLE; i++) {
				if ((w % 2) == 0 && (i % 2) == 1) {
					this.plateau[w][i] = new Case(w, i, Color.BLANC);
				} else if ((w % 2) == 1 && (i % 2) == 0) {
					this.plateau[w][i] = new Case(w, i, Color.NOIR);
				} else {
					this.plateau[w][i] = new Case(w, i, Color.VIDE);
				}
			}
		}
		this.joueurCourant = JOUEUR_BLANC;
    }

    // Test la fin de partie
    public boolean finDePartie() {
		return nbPionBlanc() == 0 || nbPionNoir() == 0;
	}
    // Renvoie le nombre de pions blancs sur le plateau
	public int nbPionBlanc() {
		int nb = 0;
		for (int i = 0; i < TAILLE; i++) {
			for (int w = 0; w < TAILLE; w++) {
				if (Color.BLANC == this.plateau[i][w].getColor()) {
					nb++;
				}
			}
		}
		return nb;
	}
    // Renvoie le nombre de pions noirs sur le plateau
	public int nbPionNoir() {
		int nb = 0;
		for (int i = 0; i < TAILLE; i++) {
			for (int w = 0; w < TAILLE; w++) {
				if (Color.NOIR == (this.plateau[i][w].getColor())) {
					nb++;
				}
			}
		}
		return nb;
    }
	
	// Affiche le plateau
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
							this.plateau[ligne-1][j] = new Case(ligne-1, j, Color.fromString(line.substring(c, c+1)));
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
        				writer.write(this.plateau[i][j].getColor().toString());
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

	// Vérifie la validité d'un coup
	public boolean estValide(String move, String player) {
		Case[] caseCourante = mvcel(move);

		// Chemin vide et diagonale vérifiée
		if(!verificationDiagonaleVide(caseCourante, player)) {
			return false;
		}
		// Vrai pion -> pas vide
		if(Color.VIDE == (caseCourante[0].getColor())) {
			return false;
		}
		// Ne pas aller sur la même couleur 
		if(caseCourante[1].getColor() == (caseCourante[0].getColor())) {
			return false;
		}
		// On attaque un pion 
 		if((Color.NOIR == (caseCourante[0].getColor()) && Color.BLANC == (caseCourante[1].getColor())) 
 				|| (Color.BLANC == (caseCourante[0].getColor()) && Color.NOIR == (caseCourante[1].getColor()))) {
			return true;
		}
		else {
			// Vérifie que nous ne sommes pas en position d'attaque
			if(menace(caseCourante[0], player)) {
				return false;
			}
			// On peut prendre avec ce déplacement
			if(menace(caseCourante[1], player)) {
				return true;
			}
			else {
				return false;
			}
		}
	}

	// Déplace pn pion d'une Case à une autre
	public void play(String move, String player) {
		if(estValide(move, player)) {
			Case[] dest = mvcel(move);
			if (player.substring(0, 1).equalsIgnoreCase(dest[0].getColor().toString())) {
				dest[1].setColor(dest[0].getColor());
				dest[0].setColor(Color.VIDE);
				for (Case[] tab : this.plateau) {
					for (Case c : tab) {
						if (c.getI() == dest[0].getI() && c.getJ() == dest[0].getJ()) {
							c = dest[0];
						}
						if (c.getI() == dest[1].getI() && c.getJ() == dest[1].getJ()) {
							c = dest[1];
						}
					}
					// On passe au joueur suivant
					if(JOUEUR_BLANC.equalsIgnoreCase(player)) {
						this.joueurCourant = JOUEUR_NOIR;
					} else {
						this.joueurCourant = JOUEUR_BLANC;
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

	// Récupère les pions en diagonale (on s'arrête une fois un pion trouvé)
	public ArrayList<Case> getDiagonal(int iDeb, int jDeb, int iIter, int jIter) {
		ArrayList<Case> res = new ArrayList<Case>();
		while (iDeb < 8 && jDeb < 8 && iDeb >= 0 && jDeb >= 0) {
			res.add(this.plateau[iDeb][jDeb]);
			if (Color.BLANC == (this.plateau[iDeb][jDeb].getColor())
					|| Color.NOIR == (this.plateau[iDeb][jDeb].getColor())) {
				break;
			}

			iDeb += iIter;
			jDeb += jIter;
		}
		return res;
	}

	// On fusionne les points des 4 diagonales
	public ArrayList<Case> obtdiag(Case caseCourante) {
		ArrayList<Case> resTotal = new ArrayList<Case>();
		// Diagonale Sud-Est
		resTotal.addAll(getDiagonal(caseCourante.getI() + 1, caseCourante.getJ() + 1, 1, 1));
		// Diagonale Nord-Ouest
		resTotal.addAll(getDiagonal(caseCourante.getI() - 1, caseCourante.getJ() - 1, -1, -1));
		// Diagonale Sud-Ouest
		resTotal.addAll(getDiagonal(caseCourante.getI() - 1, caseCourante.getJ() + 1, -1, 1));
		// Diagonale Nord-Est
		resTotal.addAll(getDiagonal(caseCourante.getI() + 1, caseCourante.getJ() - 1, 1, -1));
		return resTotal;
	}
	
	// Obtient la liste des ennemies directs en diagonale
	public ArrayList<Case> obtenirDiagonaleEnnemie(Case caseCourante, String player) {
		ArrayList<Case> res = new ArrayList<Case>();
		
		for (Case c : this.obtdiag(caseCourante)) {
			if (Color.BLANC.toString().equalsIgnoreCase(player.substring(0, 1)) 
					&& Color.NOIR == (c.getColor())) {
				res.add(c);
			}
			if (Color.NOIR.toString().equalsIgnoreCase(player.substring(0, 1))
					&& Color.BLANC == (c.getColor())) {
				res.add(c);
			}
		}
		return res;
	}
	
	// Renvoie vrai si un ami est sur la diagonale
	public boolean estAmiDiagonale(Case caseCourante, String player) {
		boolean res = false;
		for (Case c : this.obtdiag(caseCourante)) {
			if (Color.BLANC.toString().equalsIgnoreCase(player.substring(0, 1)) 
					&& Color.BLANC == (c.getColor())) {
				res = true;
			}
			if (Color.NOIR.toString().equalsIgnoreCase(player.substring(0, 1))
					&& Color.NOIR == (c.getColor())) {
				res = true;
			}
		}
		return res;
	}
	
	// Gestion de la menace d'un pion 
	public boolean menace(Case caseCourante, String player) {
		if(obtenirDiagonaleEnnemie(caseCourante, player).size() > 0) {
			return true;
		}
		return false;
	}
	
	// Vérifie que la case choisie à attaquer appartient à l'ennemie direct en diagonale
	public boolean verificationDiagonaleVide(Case[] caseCourante, String player) {
		Case origine = caseCourante[0];
		Case destination = caseCourante[1];
		// Gère l'attaque d'un ennemie direct
		for (Case c : obtdiag(origine)) {
			if(destination.getId().equalsIgnoreCase(c.getId())) {
				return true;
			}
		}
		return false;
	}
	
	// Echange les positions sur le plateau
	private Case[] mvcel(String move) {
		Case[] mv = new Case[2];
		String[] data = move.split(Color.VIDE.toString());
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
		ArrayList<String> coupsPossibles = new ArrayList<String>();

		// parcours de toutes les cases du plateau
		for (Case[] tab : this.plateau) {
			for (Case c : tab) {
				// pour chaque pion du joueur courant
				String pionCourant = "";
				if(Color.BLANC == (c.getColor()) && JOUEUR_BLANC.equalsIgnoreCase(this.joueurCourant)) {
					pionCourant = c.getId();
				}
				else if(Color.NOIR == (c.getColor()) && JOUEUR_NOIR.equalsIgnoreCase(this.joueurCourant)) {
					pionCourant = c.getId();
				}
				// Si la cellule n'est pas vide
				if(pionCourant != "") {
					// on parcourt le plateau pour calculer le nombre de déplacement que le pion courant peut faire
					for(Case[] tab2 : this.plateau) {
						for (Case c2 : tab2) {
							if(!pionCourant.equalsIgnoreCase(c2.getId())) {
								ArrayList<Case> res = obtdiag(c2);								
								for(Case case1 : res) {
									if(estValide(pionCourant+Color.VIDE+case1.getId(), this.joueurCourant)) {
										coupsPossibles.add(pionCourant+Color.VIDE+case1.getId());				
									}									
								}	
							}							
						}
					}
				}
			}
		}
		return coupsPossibles;
	}
	
	public Case[][] getCopyPlateau(PlateauFousFous plateau) {
		Case[][] tmp = new Case[this.TAILLE][this.TAILLE];
		for (int w = 0; w < this.TAILLE; w++) {
			for (int i = 0; i < this.TAILLE; i++) {				
				tmp[w][i] = plateau.plateau[w][i];				
			}
		}
		return tmp;
	}
	
	public Case[][] getCopyPlateau(Case[][] plateau) {
		Case[][] tmp = new Case[this.TAILLE][this.TAILLE];
		for (int w = 0; w < this.TAILLE; w++) {
			for (int i = 0; i < this.TAILLE; i++) {				
				tmp[w][i] = plateau[w][i];				
			}
		}
		return tmp;
	}
	
	public int pourcentageDiagonale(String player){
		float totalPions = 0;
		float nbPionsAmi = 0;
		for (int i = 0 ; i < TAILLE ; i++) {
			for (int j = 0 ; j < TAILLE ; j++) {
				if (this.plateau[i][j].getColor().toString().equals(player.substring(0, 1))) {
					if(estAmiDiagonale(this.plateau[i][j],player)){
						totalPions++;
					}
					nbPionsAmi++;
				}
			}
		}
		System.out.println("totalPions = "+totalPions+" Nb pion ami = "+nbPionsAmi);
		return (int)(100*(totalPions/nbPionsAmi));
	}
	
    public static void main(String[] args) {        	
    	// Création du fichier de sauvegarde et de lecture
    	Date today = new Date();
    	SimpleDateFormat formater = new SimpleDateFormat("yyyyMMddHHmmss");
    	String file = "files/"+formater.format(today)+".txt";
    	
    	Scanner sc = new Scanner(System.in);
    	PlateauFousFous PlateauTest = new PlateauFousFous();    	
    	    	    	
    	System.out.println("La partie va commencer...");
		while(!PlateauTest.finDePartie()) {
			// Afficher le plateau courant
	        System.out.println(PlateauTest);
	        
	        // Calculer et afficher les coups possibles
			System.out.println("Joueur courant : " + PlateauTest.joueurCourant);
			ArrayList<String> listeCoupsPossibles = PlateauTest.mouvementsPossibles(PlateauTest.joueurCourant);
			System.out.println("Il y a "+listeCoupsPossibles.size()+ " coups possibles sur le plateau.");
			System.out.println(listeCoupsPossibles);
			
			// Récupérer déplacement à faire + jouer 
			System.out.println("Veuillez saisir le coup à jouer (exemple : B1-C2) : ");
			String coup = sc.nextLine();
						
			PlateauTest.play(coup, PlateauTest.joueurCourant);

			// Import et export dans un fichier
	        PlateauTest.saveToFile(file);
	        PlateauTest.setFromFile(file);
		}
        sc.close();
        System.out.println(PlateauTest);
        // Affiche le joueur gagnant
		if(PlateauTest.nbPionBlanc() == 0) {
			System.out.println("Le joueur noir a gagné !!");
		}
		if(PlateauTest.nbPionNoir() == 0) {
			System.out.println("Le joueur blanc a gagné !!");			
		}   
	}
}
