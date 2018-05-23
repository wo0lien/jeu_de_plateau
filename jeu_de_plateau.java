import java.util.Scanner;

public class jeu_de_plateau {
    
    public static void main(String Args[]) {
		
		Scanner sc = new Scanner(System.in);
		int entry = 0; //variable de l'entrée utilisateur
		char entryChar; //caractere entré par l'utilisateur
        
        // menu de base qui boucle tant que le joueur ne commenece pas la partie
       
        while(entry != 3) {
            System.out.println("Bonjour et bienvenue dans notre jeu de plateau");//accents font bugger le bordel
            System.out.println("               -----------------");
            System.out.println("1. Afficher les regles");
            System.out.println("2. Modifier les parametres ");
            System.out.println("3. Jouer");
            System.out.println("               -----------------");
            entry = sc.nextInt();
            if (entry == 1)
            {
                Regles();
            } else if (entry == 2)
            {
				
            } else if (entry == 3)
            {
                Jeu();
            }
        }
    }
    /**
     * Des que la partie commence on rentre dans cette méthode qui ne s'arrete que à la fin de la partie
     */
    public static void Jeu() {
        
        System.out.println("");
        System.out.println("Let's start !!");
        
		Scanner sc = new Scanner(System.in);
		int persoSelected = 0; //variable de l'entrée utilisateur
		char entryChar; //caractere entré par l'utilisateur
        
        //---- initialisation ici ----
        Plateau p = new Plateau(10);
        Grille portee = new Grille(10); //plateau utilisé uniquement pour la portée des attaques et des déplacements
        
        Personnage[] persos = new Personnage[16];
        
        persos = PlacementPersos(persos, p);
        
        AffichePlateau(p);
        
        boolean ended = false; //boolean de fin de partie
        
        while(!ended)
        {
			//debut du tour
			
			Deplacement( persos, portee);
			AffichePlateau(p);
			Attaque(p, persos, portee);
			           
        }
    }
    /**
     * Méthode de l'attaque d'un personnage sur un autre personnage
     */
    public static void Attaque(Plateau p, Personnage[] persos,  Grille portee) {
		
		Scanner sc = new Scanner(System.in);
		
		System.out.println("");
		System.out.println("Ok on passe à l'attaque mtn !");
		System.out.println("Choisis un personnage pour attaquer");
		
		//choix du personnage lignes a améliorer avec vérification de la validité de l'entrée ( ajout d'une variable de nombre de personnages sur le plateau + variable équipe dans le constructeur du personnage )
		//stockage du personnage choisis dans n
		//faudrait peut-etre en faire une méthode vu que on l'utilise 2 fois au moins dans le programme ca serait plu propre
		
		int c1 = sc.nextInt();
		portee.Reset();
		portee.SetupPortee(persos[c1], p);
		AfficheGrille(portee);
		
		System.out.println("Sur quel personnage veux-tu taper?");
		int c2 = sc.nextInt();
		
        
        //test de validité de la personnage qu'on va déboiter a faire + application des dégats (réduction des points de vie etc) FACILE LOL allez gl
		int d = (int)(Math.sqrt(Math.pow((persos[c2].GetLigne() - persos[c1].GetLigne()),2)+Math.pow((persos[c2].GetColonne()- persos[c1].GetColonne()),2)));
        boolean test=false;
        
        if (d <= persos[c1].GetArme().GetPoMax() && d >= persos[c1].GetArme().GetPoMin()){
            test = true;
        }
        
		//méthode à continuer
		
	}
    
    /*public static boolean SupArme (Personnage Att, Personnage Def){
        boolean supArme = false;
        Arme armeAtt = Att.GetArme();
        Arme armeDef = Def.GetArme();
        switch(armeAtt.GetId()){
			case 2:
                
                switch(armeDef.GetId()){
                    case 3:
                    armeAtt.setDmg(armeAtt.GetDmg()+10);
                    break;
                    case 4:
                    armeAtt.setDmg(armeAtt.GetDmg()-10);
                    break;
                    default:
                    armeAtt.setDmg(armeAtt.GetDmg());
                }
				
			case 3:
                switch(armeDef.GetId()){
                    case 4:
                    armeAtt.setDmg(armeAtt.GetDmg()+10);
                    break;
                    case 2:
                    armeAtt.setDmg(armeAtt.GetDmg()-10);
                    break;
                    default:
                    armeAtt.setDmg(armeAtt.GetDmg());
                }
				
			break;
			case 4:
                switch(armeDef.GetId()){
                    case 2:
                    armeAtt.setDmg(armeAtt.GetDmg()+10);
                    break;
                    case 3:
                    armeAtt.setDmg(armeAtt.GetDmg()-10);
                    break;
                    default:
                    armeAtt.setDmg(armeAtt.GetDmg());
                }
			break;
        }
    }*/
    
    /**
     * Méthode du déplacement sur le plateau
     */
    public static void Deplacement( Personnage[] p, Grille g) {
		
		Scanner sc = new Scanner(System.in);
		Scanner scString = new Scanner(System.in); //deuxieme pour les types string sinon ca bug
		int nbCasesRest;
		String entryChar = "";
		
		System.out.println("Choisi un personnage a deplacer");
		int n = sc.nextInt();
		
		if(p[n].GetPoids()) //en fonction du poids lourd ou leger
		{
			nbCasesRest = 3;
		} else
		{
			nbCasesRest = 4;
		}
		
		while (nbCasesRest >= 1 && !"a".equals(entryChar))
		{
			System.out.println("Ok ! Mtn choisis ou tu veux le deplacer avec z,q,s,d ou sinon c'est a pour arreter");

			g.Reset();
			g.SetupDeplacement(p[n], nbCasesRest);
			AfficheGrille(g);
			entryChar = scString.nextLine();
			p[n].MovePerso(entryChar);
			nbCasesRest--;
		}
		g.Reset();
		g.SetupDeplacement(p[n], nbCasesRest);
		AfficheGrille(g);
		System.out.println("On passe a la suite !");
    }
    
    /**
     * Méthode qui initilise les 2 équipes autours d'une case
     */
    public static Personnage[] PlacementPersos(Personnage[] persos, Plateau p) {
		
		int n = 1; //compteur
        
        //décrit toutes les cases pour placer les unités autours d'une case définie. version pour 3 personnages par joueurs
        /*for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (i != 1 || j != 1) //exclu la case centrale
                {
                    persos[n] = new Personnage(n, j, i, 1, true, p);
                    n++;
                }
                
            }
        }*/
        
        for (int i = 0; i < 3; i++)
		{
			persos[n] = new Personnage(n, 2, (int)(i+p.GetTaille()/2-1), 2, true, p);
            n++;
            persos[n] = new Personnage(n, p.GetTaille() - 3, (int)(i+p.GetTaille()/2-1), 1, true, p);
            n++;
		}
        return persos;
    }
    
    /**
     * Méthode qui affiche les règles du jeu quand le joueur le demande
     */
    public static void Regles() {
        System.out.println("");
        System.out.println("Voici les regles : blablabla");
        System.out.println("Retour au menu...");
        System.out.println("");
        
    }
    
    /**
     * Méthode qui affiche le plateau dans l'état actuel
     */
    public static void AffichePlateau(Plateau p) { //il faudrait faire en sorte quelle soit en haut de la fenetre (=saute le bon nombre de lignes)
        System.out.println("");
        for(int i = 0; i < p.GetTaille(); i++) {
			System.out.print("|");
			for(int j = 0; j < p.GetTaille(); j++) {
				switch (p.GetEtat()[i][j])
				{
					case 0:
						System.out.print(" ");
						break;
					default :
						System.out.print(p.GetEtat()[i][j]);
						break;
				}
				
				System.out.print("|");
			}
			System.out.println("");
		}
    }
    
    /**
     * Méthode qui affiche la grille dans l'état actuel
     */
    public static void AfficheGrille(Grille g) { //il faudrait faire en sorte quelle soit en haut de la fenetre (=saute le bon nombre de lignes)
        System.out.println("");
        for(int i = 0; i < g.GetTaille(); i++) {
			System.out.print("|");
			for(int j = 0; j < g.GetTaille(); j++) {
				if (g.GetEtat()[i][j] == 0)
				{
					System.out.print(" ");
				} else if (g.GetEtat()[i][j] == 10)
				{
					System.out.print("x");
				} else if (g.GetEtat()[i][j] == 11)
				{
					System.out.print("o");
				} else
				{
					System.out.print(g.GetEtat()[i][j]);
				}
				
				System.out.print("|");
			}
			System.out.println("");
		}
    }
    
}
