public class Personnage {
	
    private int ID; //ID du personnage utilsé dans toutes les class
    private int ligne; //position du perso
    private int colonne;
    private boolean poids;//poids : true = lourd false = leger
    private Plateau plateau;
    private int hp; //nombre d'HPs du personnage
    private int classe; //2 = guerrier 1 = archer... a faire
    private String name; //nom de la classe voir du personnage si on se chauffe (création d'un objet classe ?)
    private boolean equipe;
    private String equipeName; //nom de l'équipe pour éviter les true false + aligner l'affichage
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
            
            if (equipe)
			{
				this.equipeName = "Rouge";
			} else
			{
				this.equipeName = "Bleue";
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
	
	public void SetHp(int degats){
		if (hp >= degats)
		{
			hp -= degats;
		} else {
			hp = 0;
			plateau.RemovePerso(this);
		}
	}
		
	
	public boolean MovePerso(String d){
        
        boolean deplace = false;
		plateau.RemovePerso(this);
        
        int newLigne = ligne;
        int newColonne = colonne;
        
		if ("s".equals(d))
		{
			newLigne++;
		} else if ("q".equals(d))
		{
			newColonne--;
		} else if ("z".equals(d))
		{
            newLigne--;
		} else if ("d".equals(d))
		{
			newColonne++;
		}
        //test pour savoir si il y a deja un perso sur la case
        if (plateau.GetEtat()[newLigne][newColonne] != 0)
        {
            System.out.println("attention tu vas écraser un personnage");
        } else
        {
            ligne = newLigne;
            colonne = newColonne;
            deplace = true;
        }
		plateau.AddPerso(this);
        return deplace;
	}
	
	public String toString() {
        String description = "perso "+ID+" | equipe : "+equipeName+" | hp : "+hp+" | "+arme.GetName();
        return description;
    }
    
    /*public void testdeplacement (int x, int y){
        boolean test = true; 
        if (x==c && y==l ) {
            test == false;
            System.out.println("boloss, tu vas écraser un perso !");
        }
    }
    */        
}

