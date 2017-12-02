package fousfous_2;

public class AlphaBeta implements AlgoJeu {
	/** La profondeur de recherche par défaut
     */
    private final static int PROFMAXDEFAUT = 2;

   
    // -------------------------------------------
    // Attributs
    // -------------------------------------------
 
    /**  La profondeur de recherche utilisée pour l'algorithme
     */
    private int profMax = PROFMAXDEFAUT;

     /**  L'heuristique utilisée par l'algorithme
      */
   private Heuristique h;

    /** Le joueur Min
     *  (l'adversaire) */
    private String joueurMin;

    /** Le joueur Max
     * (celui dont l'algorithme de recherche adopte le point de vue) */
    private String joueurMax;


  // -------------------------------------------
  // Constructeurs
  // -------------------------------------------
    public AlphaBeta(Heuristique h, String joueurMax, String joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public AlphaBeta(Heuristique h, String joueurMax, String joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
    }

   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------
    public String meilleurCoup(PlateauFousFous p) {		
		String meilleurCoup = "";
		int a = Integer.MIN_VALUE;
		int b = Integer.MAX_VALUE;

		for (String coup : p.mouvementsPossibles(this.joueurMax)) {
			PlateauFousFous temp_p = p.getCopyPlateau();
			temp_p.play(coup, this.joueurMax);
			int Max = maxMin(temp_p, this.profMax - 1, a, b);
			//System.out.println("Action:"+coup+", Val Heur :"+Max);
			if (a < Max) {
				b = Max;
				meilleurCoup = coup;
			}
		}
		//System.out.println("le meilleur coup est: " + meilleurCoup);
		return meilleurCoup;
	}

  // -------------------------------------------
  // Méthodes publiques
  // -------------------------------------------
    public String toString() {
        return "alBeta(ProfMax="+profMax+")";
    }
  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

    private int maxMin(PlateauFousFous p, int prof, int a, int b) {
		if (p.finDePartie() || prof == 0) {
			return h.calculDiag(p, this.joueurMax);
		} else {
			for (String c : p.mouvementsPossibles(this.joueurMax)) {
				PlateauFousFous temp_p = p.getCopyPlateau();
				temp_p.play(c, this.joueurMax);
				a = Math.max(a, minMax(temp_p, prof - 1, a, b));
				if (a >= b) {
					return b;
				}
			}
			return a;
		}
	}
	
	private int minMax(PlateauFousFous p, int prof, int a, int b) {
		if (p.finDePartie() || prof == 0) {
			return this.h.calculDiag(p, this.joueurMax);
		} else {
			for (String c : p.mouvementsPossibles(this.joueurMin)) {				
				PlateauFousFous temp_p = p.getCopyPlateau();

				temp_p.play(c, this.joueurMin);
				b = Math.min(b, maxMin(temp_p, prof - 1, a, b));
				if (a >= b) {
					return a;
				}
			}
			return b;
		}
	}


	
}
