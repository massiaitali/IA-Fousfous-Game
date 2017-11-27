package fousfous_2;

public class heurest {
	public heurest() {
	}
	// Difference 
	public int calculDiff(PlateauFousFous P, String Joueur) {
		// Si joueur blanc 
		if(Joueur.substring(0, 1).equals("b"))
		{
			// On fait la difference des pions sur le plateau
			return (int) P.nbPionBlanc() - P.nbPionNoir() ;
		}
		// Si joueur noir
		if(Joueur.substring(0, 1).equals("n"))
		{
			return (int) P.nbPionNoir() - P.nbPionBlanc() ;
		}
		return 0;
	}
	//Pourcentage de diagonal occupé
	public int calculDiag(PlateauFousFous P, String Joueur) {
		// Si joueur blanc 
		if(Joueur.substring(0, 1).equals("b"))
		{
			// On fait la difference des pions sur le plateau
			return (int) P.PourcentageDiag(Joueur) ;
		}
		// Si joueur noir
		if(Joueur.substring(0, 1).equals("n"))
		{
			return (int) P.PourcentageDiag(Joueur) ;
		}
		return 0;
	}
}
