package fousfous_2;

import java.util.ArrayList;

public class JoueurFinal implements IJoueur {
	private String NomBinome = "Massinissa - Mathilde";
	private int ColorServeur;
	private String MaCouleur;
	private String MaCouleurEnnemie;
	private PlateauFousFous plateau;
	private alBeta AlpBeta;
	private Minimax algoMiniMax;
	private int prof = 3;

	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}

	@Override
	public String choixMouvement() {
		/*ArrayList<String> listeCoupsPossibles = plateau.mouvementsPossibles(MaCouleur);
		int taille = listeCoupsPossibles.size() - 1;
		int nombreAleatoire = 0 + (int)(Math.random() * ((taille - 0) + 1));
		System.out.println("NA="+nombreAleatoire);
		System.out.println("taille="+taille);
		System.out.println("liste="+listeCoupsPossibles);
		String coup = listeCoupsPossibles.get(nombreAleatoire);
		System.out.println(plateau);
		heurest test = new heurest();
		System.out.println("Avant coup avec heurestique DIff : "+ test.calculDiff(plateau, MaCouleur));
		System.out.println("Avant coup avec heurestique Pourcentage : "+ test.calculDiag(plateau, MaCouleur));
		plateau.play(coup, MaCouleur);
		System.out.println("Après coup avec heurestique DIff : "+ test.calculDiff(plateau, MaCouleur));
		System.out.println("Après coup avec heurestique Pourcentage : "+ test.calculDiag(plateau, MaCouleur));
		return coup;*/
		this.AlpBeta = new alBeta(new heurest(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
		String coup = this.AlpBeta.meilleurCoup(this.plateau);
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
		return "JoueurFinal";
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
