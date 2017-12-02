package fousfous_2;

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

	@Override
	public int getNumJoueur() {
		return ColorServeur;
	}

	@Override
	public String choixMouvement() {
		//long debut = System.currentTimeMillis();
		int taille = plateau.mouvementsPossibles(MaCouleur).size() - 1;
		String coup;
		if (taille > 20){
			this.prof = 3;
			System.out.println("Alpha avec P=" + this.prof);
			this.AlpBeta = new AlphaBeta(new Heuristique(), this.MaCouleur, this.MaCouleurEnnemie,this.prof);
			coup = this.AlpBeta.meilleurCoup(this.plateau);
		} else {
			if ( taille > 10){
				this.prof = 3;
			} else {
				this.prof = 3;
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
