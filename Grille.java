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
		
		for(int i = 0; i < taille; i++) {
            for(int j = 0; j < taille; j++) {
                etat[i][j] = 0;
            }
        }
        
	}
	public void SetupDeplacement(Personnage pers, int nbCasesRest, Plateau p) {
		
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				if (Math.abs(i - pers.GetLigne()) <= nbCasesRest && j == pers.GetColonne())
				{
					etat[i][j] = 10;
				} else if (Math.abs(j - pers.GetColonne()) <= nbCasesRest && i == pers.GetLigne())
				{
					etat[i][j] = 10;
				} 
				if (p.GetEtat()[i][j] != 0) {
					this.etat[i][j] = p.GetEtat()[i][j];
				}
				
			}
		}
		etat[pers.GetLigne()][pers.GetColonne()] = pers.GetID();	
	}
	
	public void SetupPortee(Personnage pers, Plateau p) {
		
		for (int i = 0; i < taille; i++) {
			for (int j = 0; j < taille; j++) {
				double distanceCase = Math.sqrt(Math.pow((double)(pers.GetColonne()) - j, 2) + Math.pow((double)(pers.GetLigne()) - i, 2));
				if (distanceCase > pers.arme.GetPoMin() && distanceCase < pers.arme.GetPoMax())
				{
					if (p.GetEtat()[i][j] == 0)
					{
						this.etat[i][j] = 10;
					} else
					{
						this.etat[i][j] = p.GetEtat()[i][j];
					}
					
				}
			}
		}
		
		
	}
}
