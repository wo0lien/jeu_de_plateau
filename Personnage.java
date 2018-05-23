public class Personnage {
	
    private int ID; //ID du personnage utilsé dans toutes les class
    private int ligne; //position du perso
    private int colonne;
    private boolean poids;//poids : true = lourd false = leger
    private Plateau plateau;
    private int hp;
    private int classe; //2 = guerrier 1 = archer... a faire
    private String name; //nom de la classe voir du personnage si on se chauffe (création d'un objet classe ?)
    private boolean equipe;
    Arme arme;
	// Constructeur complet
	
        public Personnage (int n,int c, int l, int a, boolean pod, Plateau p, boolean e) {
			this.ID = n;
            this.colonne = c;
            this.ligne = l;
            this.arme = new Arme(a);
            this.equipe=e;
            
            
            if (a == 1){
                this.classe=1;
            }else{
                this.classe=2;
            }
            
            this.poids = pod;
            
            if (a == 2){
				
                if (pod== true){
                    this.hp = 70;
                } else {
                    this.hp=50;
                }
                
            } else {
				if (pod== true){
					this.hp = 50;
				} else {
					this.hp=30;
				}
            }
            
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

