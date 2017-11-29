package fousfous_2;

import java.util.ArrayList;

public class MonSuperJoueur implements IJoueur {
	private String NomBinome = "Massinissa - Mathilde";
	private int ColorServeur;
	private String MaCouleur;
	private String MaCouleurEnnemie;
	private PlateauFousFous plateau;
	private Minimax algoMiniMax;
	private int prof = 2;

	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}
	
	@Override
	public String choixMouvement() {
		//ArrayList<String> listeCoupsPossibles = plateau.mouvementsPossibles(MaCouleur);
		//int taille = listeCoupsPossibles.size() - 1;
		this.algoMiniMax = new Minimax(new heurest(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
		String coup = this.algoMiniMax.meilleurCoup(this.plateau);
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
