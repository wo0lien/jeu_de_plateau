public class Grille {
	
    private int taille; // taille du monde
	private int[][] etat; // tableau 2D representant les cases de la grille avec le personnage dedans
	
    public Grille(int t) {
        this.taille = t;
        
        int[][] transi = new int[t][t];
        for(int i = 0; i < (t - 1); i++) {
            for(int j = 0; j < (t - 1); j++) {
                transi[i][j] = 0;
            }
        }
        this.etat = transi;
    }
	
    // Accesseurs
    public int GetTaille(){
        return taille;
    }
    public int[][] GetEtat(){
        return etat;
    }
    public void Reset(){ //remet toutes les valeurs de la grille a zero
		
		for(int i = 0; i < (taille - 1); i++) {
            for(int j = 0; j < (taille - 1); j++) {
                etat[i][j] = 0;
            }
        }
        
	}
	public void SetupDeplacement(Personnage pers, int nbCasesRest) {
		for (int i = 0; i < taille; i++)
		{
			if(Math.abs(i - pers.GetLigne()) < nbCasesRest && i != pers.GetLigne())
			{
				etat[i][pers.GetColonne()] = 10;
			}
		}
		for (int j = 0; j < taille; j++)
		{
			if(Math.abs(j - pers.GetColonne()) < nbCasesRest && j != pers.GetColonne())
			{
				etat[pers.GetLigne()][j] = 10;
			}
		}
		etat[pers.GetLigne()][pers.GetColonne()] = pers.GetID();
	}
}
