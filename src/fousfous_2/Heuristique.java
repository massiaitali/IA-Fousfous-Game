package fousfous_2;

/**
 * Projet IA - Polytech Paris-Sud 2017-2018
 * @author Massinissa Ait Ali et Mathilde PREVOST
 *
 */

public class Heuristique {
	
	public Heuristique() {}
	// Difference 
	public int calculDiff(PlateauFousFous plateau, String joueur) { 
		// Si joueur blanc 
		if(Color.BLANC.toString().equalsIgnoreCase(joueur.substring(0, 1))) {
			// On fait la difference des pions sur le plateau
			return (int) plateau.nbPionBlanc() - plateau.nbPionNoir() ;
		}
		// Si joueur noir
		if(Color.NOIR.toString().equalsIgnoreCase(joueur.substring(0, 1))) {
			return (int) plateau.nbPionNoir() - plateau.nbPionBlanc() ;
		}
		return 0;
	}
	
	//Pourcentage de diagonal occupé
	public int calculDiag(PlateauFousFous plateau, String joueur) {
		// Si joueur blanc 
		if(Color.BLANC.toString().equalsIgnoreCase(joueur.substring(0, 1))) {
			return (plateau.nbPionBlanc() * (1+plateau.pourcentageDiagonale(joueur))) - (plateau.nbPionNoir()) ;
		}
		// Si joueur noir
		if(Color.NOIR.toString().equalsIgnoreCase(joueur.substring(0, 1))) {
			return (plateau.nbPionNoir() * (1+plateau.pourcentageDiagonale(joueur))) - (plateau.nbPionBlanc()) ;
		}
		return 0;
	}
}
