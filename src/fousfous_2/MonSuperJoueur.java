package fousfous_2;

import java.util.ArrayList;

public class MonSuperJoueur implements IJoueur {
	private String NomBinome = "Massinissa - Mathilde";
	int ColorServeur;
	String MaCouleur;
	String CouleurEnnemi;
	PlateauFousFous Plateau;
	
	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}

	@Override
	public String choixMouvement() {
		ArrayList<String> listeCoupsPossibles = Plateau.mouvementsPossibles(MaCouleur);
		int taille = listeCoupsPossibles.size();
		int nombreAleatoire = 0 + (int)(Math.random() * ((taille - 0) + 1));
		String coup = listeCoupsPossibles.get(nombreAleatoire);
		System.out.println("NA="+nombreAleatoire);
		return coup;
	}

	@Override
	public void declareLeVainqueur(int colour) {
		if(colour == this.ColorServeur) {
			System.out.println("Le binone " + NomBinome + " a gagné :)");
		}
		else {
			System.out.println("Le binone " + NomBinome + " a perdu... :(");			
		}
		
	}

	@Override
	public void mouvementEnnemi(String coup) {
		Plateau.play(coup, CouleurEnnemi);	
	}

	@Override
	public String binoName() {
//		return NomBinome;
		return "MonSuperJoueur";
	}

	@Override
	public void initJoueur(int mycolour) {
		ColorServeur = mycolour;
		if( mycolour == -1 ) { // blanc
			MaCouleur = "blanc";
			CouleurEnnemi = "noir";
		}
		else {
			MaCouleur = "noir";
			CouleurEnnemi = "blanc";
		}
	 Plateau = new PlateauFousFous();
	}

}
