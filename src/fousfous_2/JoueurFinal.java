package fousfous_2;

/**
 * Projet IA - Polytech Paris-Sud 2017-2018
 * @author Massinissa Ait Ali et Mathilde PREVOST
 *
 */

public class JoueurFinal implements IJoueur {
	private String NomBinome = "Massinissa - Mathilde";
	private int ColorServeur;
	private String MaCouleur;
	private String MaCouleurEnnemie;
	private PlateauFousFous plateau;
	private AlphaBeta AlpBeta;
	private Minimax algoMiniMax;
	private int prof = 2;
	private long timer = 0;
	static int PROFMIN = 3;
	static int PROFMAX = 7;
	static int SEUILMIN = 7;
	static int SEUILMAX = 30;

	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}

	@Override
	public String choixMouvement() {
		//long debut = System.currentTimeMillis();
		int taille = plateau.mouvementsPossibles(MaCouleur).size() - 1;
		String coup = "";
		if (taille > 20){
			this.prof = 3;
			System.out.println("Alpha avec P=" + this.prof);
			this.AlpBeta = new AlphaBeta(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
			coup = this.AlpBeta.meilleurCoup(this.plateau);
		} else {
			if ( taille > 10){
				this.prof = 4;
			} else {
				this.prof = 5;
			}
			System.out.println("Mini avec P= " + this.prof);
			this.algoMiniMax = new Minimax(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
			coup = this.algoMiniMax.meilleurCoup(this.plateau);
		}
		plateau.play(coup, MaCouleur);
		//timer += System.currentTimeMillis() - debut;
		//System.out.println("TIMER : " + (timer / 1000) + " secondes");
		return coup;
		
//		long debut = System.currentTimeMillis();
//		int taille = plateau.mouvementsPossibles(MaCouleur).size() - 1;
//		String coup = "";
//		if (taille > 22){
//			this.prof = choisiProf(taille);
//			System.out.println("Alpha avec P=" + this.prof);
//			this.prof = getProfWithtimer();
//			this.AlpBeta = new AlphaBeta(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
//			coup = this.AlpBeta.meilleurCoup(this.plateau);
//		} else {
//			if ( taille > 10){
//				this.prof = choisiProf(taille) - 1;
//			} else {
//				this.prof = choisiProf(taille);
//			}
//			System.out.println("Mini avec P= " + this.prof);
//			this.prof = getProfWithtimer();
//			this.algoMiniMax = new Minimax(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
//			coup = this.algoMiniMax.meilleurCoup(this.plateau);
//		}
//		plateau.play(coup, MaCouleur);
//		timer += System.currentTimeMillis() - debut;
//		System.out.println("TIMER : " + (timer / 1000) + " secondes");
//		return coup;
	}

	@Override
	public void declareLeVainqueur(int colour) {
		if(colour == this.ColorServeur) {
			System.out.println("Le binone " + binoName() + " a gagné :)");
		}
		else {
			System.out.println("Le binone " + binoName() + " a perdu... :(");			
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
	
	private int choisiProf(int taille){
		if (taille >= SEUILMAX)
			return PROFMIN;
		else if (taille <= SEUILMIN)
			return PROFMAX;
		else{
			double xtrmRange = (PROFMAX - PROFMIN);
			double seuilRange = (SEUILMAX - SEUILMIN);
			double step = xtrmRange / seuilRange;
			return (int) (PROFMAX - (step*taille));
		}		
	}
	private int getProfWithtimer() {
		int score = 0;
		// timer
		if(this.timer < 60000 * 2) {
			score += 1;
		} else if(this.timer < 60000 * 4) {
			score += 2;
		} else if(this.timer < 60000 * 6) {
			score += 3;
		} else if(this.timer < 60000 * 8) {
			score += 4;
		} else if(this.timer < 60000 * 10) {
			score += 5;
		}
		
		// pions
		int nbPions = 0;
		if(this.plateau.JOUEUR_BLANC.equalsIgnoreCase(this.MaCouleur)) {
			nbPions = this.plateau.nbPionBlanc();			
		}
		if(this.plateau.JOUEUR_NOIR.equalsIgnoreCase(this.MaCouleur)) {
			nbPions = this.plateau.nbPionNoir();			
		}
		if(nbPions > 13) {
			score += 5;
		} else if(nbPions > 10) {
			score += 4;
		} else if(nbPions > 7) {
			score += 3;
		} else if(nbPions > 4) {
			score += 2;
		} else if(nbPions > 1) {
			score += 1;
		} 

		System.out.println("score="+score);
		
		if(score > 7) {
			return diminueProf(3);
		} else if(score > 5) {
			return diminueProf(2);
		}
		
		
		return this.prof;
	}
	
	private int diminueProf(int diminution) {
		if(this.prof > diminution) {
			return this.prof - diminution;
		} else if(this.prof < diminution) {
			return PROFMIN;
		} else if(this.prof == diminution) {
			return this.prof - 1;
		}
		return this.prof;
	}
}
