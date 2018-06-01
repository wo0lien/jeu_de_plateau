public class Personnage {
	
    private int ID; //ID du personnage utilsé dans toutes les class
    private int ligne; //position du perso
    private int colonne;
    private boolean poids;//poids : true = lourd false = leger
    private int hp; //nombre d'HPs du personnage
    private boolean equipe; //true = équipe bleue false = équipe rouge
    private String equipeName; //nom de l'équipe pour éviter les true false + aligner l'affichage
    Arme arme;
	// Constructeur complet
	
        public Personnage (int n,int c, int l, int a, boolean pod, boolean e) {
			this.ID = n;
            this.colonne = c;
            this.ligne = l;
            this.arme = new Arme(a);
            this.equipe=e;
            
            this.poids = pod; //poids lourd ou léger (léger = + de déplacment  / lourd  = + d'HPs
            
            if (a == 2){
				
                if (pod== true){
                    this.hp = 70;
                } else {
                    this.hp=50;
                }
                
            } else if(a != 1){
				if (pod== true){
					this.hp = 60;
				} else {
					this.hp= 40;
				}
            } else
			{
				this.hp = 30;
			}
			
            
            if (equipe) //pour l'affichage des équipes
			{
				this.equipeName = "Bleue";
			} else
			{
				this.equipeName = "Rouge";
			}
			
			
            
        }
        
    // Accesseurs
	public int GetLigne(){
        return ligne;
    }
    
    public int GetColonne(){
        return colonne;
    }
    
    public boolean GetPoids(){
        return poids;
    }
    public int GetID(){
		return ID;
	}
	
    public int GetHP(){
		return hp;
	}
    public Arme GetArme(){
        return arme;
    }
    public boolean GetEquipe(){
		return this.equipe;
	}
	
	/**
	 * Méthode qui modifie le nombre d'hp mais pas en dessous de 0
	 * @param dégats appliqués
	 */
	public void SetHp(int degats){ 
		if (hp >= degats)
		{
			hp -= degats;
		} else {
			hp = 0;
		}
	}
    
	/**
	 * Méthode de déplacements du perso
	 * @param ligne et colonne de la nouvelle case
	 */
	public void MovePerso(int l, int c){
        ligne = l;
        colonne = c;
    }
	/**
	 * Affichage des caractéristiques des personnges juxtaposé au plateau
	 */
	public String toString() {
        String description = "perso "+ID+" | equipe : "+equipeName+" | hp : "+hp+" | "+arme.GetName();
        return description;
    }
    
     
}

