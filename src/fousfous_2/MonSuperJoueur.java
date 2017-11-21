package fousfous_2;

import java.util.ArrayList;

public class MonSuperJoueur implements IJoueur {
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
		int taille = listeCoupsPossibles.size() - 1;
		int nombreAleatoire = 0 + (int)(Math.random() * ((taille - 0) + 1));
		System.out.println("taille="+taille);
		System.out.println("liste="+listeCoupsPossibles);
		System.out.println("NA="+nombreAleatoire);
		String coup = listeCoupsPossibles.get(nombreAleatoire);
		System.out.println(plateau);
		plateau.play(coup, MaCouleur);
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
		return "MonSuperJoueur";
	}

	@Override
	public void initJoueur(int mycolour) {
		plateau = new PlateauFousFous();
		this.ColorServeur = mycolour;
		if(mycolour == -1) {
			this.MaCouleur = plateau.JOUEUR_BLANC;
			this.MaCouleurEnnemie = plateau.JOUEUR_NOIR;
		}
		else if(mycolour == 1) {
			this.MaCouleur = plateau.JOUEUR_NOIR;
			this.MaCouleurEnnemie = plateau.JOUEUR_BLANC;		
		}	
	}

}
