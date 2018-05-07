public class Plateau {
	
    private int taille; // taille du monde
	private int[][] etat; // tableau 2D representant les cases du plateau avec l'ID des personnages
	
    public Plateau(int t) {
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
    public void AddPerso(Personnage pers){
		etat[pers.GetLigne()][pers.GetColonne()] = pers.GetID();
	}
	public void RemovePerso(Personnage pers){
		etat[pers.GetLigne()][pers.GetColonne()] = 0;
	}
	public void MovePerso(Personnage pers, int c, int l) { //Deplacement du point pas de gestion des out of index
		etat[pers.GetLigne()][pers.GetColonne()] = pers.GetID();
		etat[l][c] = pers.GetID();
	}
}
