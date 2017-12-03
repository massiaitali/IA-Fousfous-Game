package fousfous_2;

/**
 * Projet IA - Polytech Paris-Sud 2017-2018
 * @author Massinissa Ait Ali et Mathilde PREVOST
 *
 */

public class Minimax implements AlgoJeu {
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
    public Minimax(Heuristique h, String joueurMax, String joueurMin) {
        this(h,joueurMax,joueurMin,PROFMAXDEFAUT);
    }

    public Minimax(Heuristique h, String joueurMax, String joueurMin, int profMaxi) {
        this.h = h;
        this.joueurMin = joueurMin;
        this.joueurMax = joueurMax;
        profMax = profMaxi;
    }

   // -------------------------------------------
  // Méthodes de l'interface AlgoJeu
  // -------------------------------------------
   public String meilleurCoup(PlateauFousFous p) {
	   int Max = Integer.MIN_VALUE;
		String meilleuraction = "";
		int nextval;
		for(String move : p.mouvementsPossibles(this.joueurMax)){
			if (move != null) {
				PlateauFousFous temp_p = p.getCopyPlateau();
				nextval = minMax(temp_p,this.profMax-1);
				if (nextval > 10000){
					return move;
				}
				if (nextval>Max){
					Max=nextval;
					meilleuraction=move;
				}
			}
		}
		return meilleuraction;		
	}
	

  // -------------------------------------------
  // Méthodes publiques
  // -------------------------------------------
    public String toString() {
        return "MiniMax(ProfMax="+profMax+")";
    }
  // -------------------------------------------
  // Méthodes internes
  // -------------------------------------------

   private int maxMin(PlateauFousFous p, int prof){
		if(p.finDePartie() || prof == 0){
			return this.h.calculDiag(p, this.joueurMax);
		}else{
			int Max = Integer.MIN_VALUE;
			for(String move : p.mouvementsPossibles(this.joueurMax)){
				if (move != null) {
					PlateauFousFous temp_p = p.getCopyPlateau();
					temp_p.play(move, this.joueurMax);
					Max = Math.max(Max, minMax(temp_p,prof-1));
				}
			}
			return Max;
		}
	}
	
	private int minMax(PlateauFousFous p, int prof){
		if(p.finDePartie() || prof == 0){
			return this.h.calculDiag(p, this.joueurMax);
		}else{
			int Min = Integer.MAX_VALUE;
			for(String move : p.mouvementsPossibles(this.joueurMin)){
				if (move != null) {
					PlateauFousFous temp_p = p.getCopyPlateau();
					temp_p.play(move, this.joueurMin);
					Min = Math.min(Min, maxMin(temp_p,prof-1));
				}
			}
			return Min;
		}
	}	
}
