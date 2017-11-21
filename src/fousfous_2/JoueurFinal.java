package fousfous_2;

import java.util.ArrayList;

public class JoueurFinal implements IJoueur {
	private String NomBinome = "Massinissa - Mathilde";
	private int ColorServeur;
	private String MaCouleur;
	private String MaCouleurEnnemie;
	private PlateauFousFous plateau;

	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}

	@Override
	public String choixMouvement() {
		ArrayList<String> listeCoupsPossibles = plateau.mouvementsPossibles(MaCouleur);
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
		plateau.play(coup, MaCouleurEnnemie);		
	}

	@Override
	public String binoName() {
//		return NomBinome;
		return "JoueurFinal";
	}

	@Override
	public void initJoueur(int mycolour) {
		this.ColorServeur = mycolour;
		if(mycolour == -1) {
			this.MaCouleur = plateau.JOUEUR_BLANC;
			this.MaCouleurEnnemie = plateau.JOUEUR_NOIR;
		}
		else if(mycolour == 1) {
			this.MaCouleur = plateau.JOUEUR_NOIR;
			this.MaCouleurEnnemie = plateau.JOUEUR_BLANC;		
		}	
		plateau = new PlateauFousFous();
	}
	
}
