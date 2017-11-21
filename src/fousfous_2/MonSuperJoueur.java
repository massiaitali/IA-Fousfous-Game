package fousfous_2;

public class MonSuperJoueur implements IJoueur {
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void declareLeVainqueur(int colour) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void mouvementEnnemi(String coup) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String binoName() {
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
