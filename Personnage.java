public class Personnage {
	
    private int ID; //ID du personnage utilsé dans toutes les class
    private int ligne; //position du perso
    private int colonne;
    private int arme;// arme
    private boolean poids;//poids : true = lourd false = leger
    private Plateau plateau;
    private int classe; //1 = guerrier 2 = archer... a faire
    private String name; //nom de la classe voir du personnage si on se chauffe (création d'un objet classe ?)
	// Constructeur complet
	
        public Personnage (int n,int c, int l, int a, boolean po, Plateau p) {
			this.ID = n;
            this.colonne = c;
            this.ligne = l;
            this.arme = a;
            this.poids = po;
            
            this.plateau = p;
            plateau.AddPerso(this);
            
        }
        
    // Accesseurs
	public int GetLigne(){
        return ligne;
    }
    
    public int GetColonne(){
        return colonne;
    }
    
	public int GetArme(){
        return arme;
    }
    
    public boolean GetPoids(){
        return poids;
    }
    public int GetID(){
		return ID;
	}
	
	public void MovePerso(String d){
		
		plateau.RemovePerso(this);
		if ("s".equals(d))
		{
			ligne++;
		} else if ("q".equals(d))
		{
			colonne--;
		} else if ("z".equals(d))
		{
			ligne--;
		} else if ("d".equals(d))
		{
			colonne++;
		}
		plateau.AddPerso(this);
	}
	
	public String toString() {
        String description = "Le personnage "+ID+" est un <nom de classe>";
        return description;
    }
    
}

