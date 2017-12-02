package fousfous_2;

public class MonSuperJoueur implements IJoueur {
	private String NomBinome = "Massinissa - Mathilde";
	private int ColorServeur;
	private String MaCouleur;
	private String MaCouleurEnnemie;
	private PlateauFousFous plateau;
	private Minimax algoMiniMax;
	private AlphaBeta AlpBeta;
	private int prof = 2;
	private long timer = 0;
	static int PROFMIN = 3;
	static int PROFMAX = 8;
	static int SEUILMIN = 7;
	static int SEUILMAX = 31;

	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}
	
	@Override
	public String choixMouvement() {
		//long debut = System.currentTimeMillis();
		int taille = plateau.mouvementsPossibles(MaCouleur).size() - 1;
		String coup;
		for(String test : plateau.mouvementsPossibles(MaCouleur)) {
			System.out.print(test+" ,");
		}
		System.out.println();
		//System.out.println(taille);
		if (taille > 22){
			this.prof = choisisProf(taille) - 1;
			System.out.println("Alpha avec P=" + this.prof);
			this.AlpBeta = new AlphaBeta(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
			coup = this.AlpBeta.meilleurCoup(this.plateau);
		} else {
			if ( taille > 12){
				this.prof = choisisProf(taille) - 1;
			} else {
				this.prof = choisisProf(taille) - 1;
			}
			System.out.println("Mini avec P= " + this.prof);
			this.algoMiniMax = new Minimax(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
			coup = this.algoMiniMax.meilleurCoup(this.plateau);
		}
		plateau.play(coup, MaCouleur);
		//timer += System.currentTimeMillis() - debut;
		//System.out.println("TIMER : " + (timer / 1000) + " secondes");
		return coup;
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
	private int choisisProf(int taille){
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

}
