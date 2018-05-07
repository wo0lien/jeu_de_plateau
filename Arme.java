
public class Arme{
	private String name;// type de l'arme Hache Epee Arc Lance 
	private int id;  // id de l'arme, ref, ordre alphabetique 1234 arc epee hache lance
	private int dmg; // dommage de l'arme 20 de base
	private int poMax; // je sais pas le définir, par défaut je mets int
	private int poMin; // 0 si cac, arc 2
	
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
		return poMax;
	}
	public int GetPoMax(){
		return poMax;
	}
	
	//constructeur
	
	public Arme (int id){
		this.id=id;
		switch(this.id){
			case 1:
				this.name="Arc";
				this.dmg=10;
				this.poMax=5;
				this.poMin=2;
			break;
			case 2:
				this.name"Epee";
				this.dmg=20;
				this.poMax=1;
				this.poMin=1;
			case 3:
				this.name="Hache";
				this.dmg=20;
				this.poMax=1;
				this.poMin=1;
			break;
			case 4:
				this.name="Lance";
				this.dmg=20;
				this.poMax=1;
				this.poMin=1;
			break;
		}
				
