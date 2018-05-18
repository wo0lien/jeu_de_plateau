
public class Arme{
	private String name;// type de l'arme Hache Epee Arc Lance 
	private int id;  // id de l'arme, ref, ordre alphabetique 1234 arc epee hache lance
	private int dmg; // dommage de l'arme 20 de base
	private int poMax; // je sais pas le définir, par défaut je mets int
	private int poMin; // 0 si cac, arc 2
	

	//constructeur
	
	public Arme (int idUsed){
		this.id=idUsed;
		switch(this.id){
			case 1:
				this.name="Arc";
				this.dmg=10;
				this.poMax=5;
				this.poMin=2;
			break;
			case 2:
				this.name="Epee";
				this.dmg=20;
				this.poMax=2;
				this.poMin=0;
			case 3:
				this.name="Hache";
				this.dmg=20;
				this.poMax=2;
				this.poMin=0;
			break;
			case 4:
				this.name="Lance";
				this.dmg=20;
				this.poMax=2;
				this.poMin=0;
			break;
		}
	}
	
	//Accesseurs
	public int GetId(){
		return id;
	}
	public int GetDmg(){
		return dmg;
	}
	public String GetName(){
		return name;
	}
	public int GetPoMin(){
		return poMin;
	}
	public int GetPoMax(){
		return poMax;
	}
	public int SetDmg(){
        
}
				
