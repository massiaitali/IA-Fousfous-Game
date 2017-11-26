package fousfous_2;

public class heurest {
	public heurest() {
	}
	
	public int calcul(PlateauFousFous P, String Joueur) {
		// Si joueur blanc 
		if(Joueur.substring(0, 1).equals("b"))
		{
			// Si il y a aucun pion noir alors on donne une très bonne valeur de reussite à ce choix et ajoute le nb de pions ami
			if(P.NbPions("noir")==0){
				return (10000 + P.NbPions("blanc"));
			}
			// Sinon on fait le produit de pion blanc donc ami la stats de nombre de pions ami par rapport au nb de pion enemis (en prenant la position en facteur) moins le nombre de pions enemis ( 
			return (int) (P.NbPions("blanc") * (1+P.ValeurDiago(Joueur))) - (P.NbPions("noir")) ;
		}
		// Si joueur noir
		if(Joueur.substring(0, 1).equals("n"))
		{
			// Si il y a aucun pion blanc alors on donne une très bonne valeur de reussite à ce choix
			if(P.NbPions("blanc")==0){
				return (10000 + P.NbPions("noir"));
			}
			// Sinon on fait le produit de pion noir donc ami la stats de nombre de pions ami par rapport au nb de pion enemis (en prenant la position en facteur) moins le nombre de pions enemis donc blanc
			return (int) (P.NbPions("noir") * (1+P.ValeurDiago(Joueur))) - (P.NbPions("blanc")) ;
		}
		return 0;
	}
}
