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
        
        //Personnage pers = new Personnage(1, 3, 4, 1, true, p);
        //AffichePlateau(p);
        
        boolean ended = false; //boolean de fin de partie
        
        while(!ended)
        {
			//debut du tour
			System.out.println("Choisi un personnage a déplacer");
			persoSelected = sc.nextInt();
			System.out.println("Ok ! Mtn choisis ou tu veux le déplacer z,q,s,d a pour arreter");
			Deplacement(persoSelected, persos, portee);
			           
        }
    }
    
    
    /**
     * Méthode du déplacement sur le plateau
     */
    public static void Deplacement(int n, Personnage[] p, Grille g) {
		
		Scanner sc = new Scanner(System.in);
		int nbCasesRest;
		char entryChar;
		
		if(p[n].GetPoids()) //en fonction du poids lourd ou leger
		
		{
			nbCasesRest = 2;
		} else
		{
			nbCasesRest = 4;
		}
		
		g.SetupDeplacement(p[n], nbCasesRest);
		AfficheGrille(g);
		//nbCasesRest = sc.nextInt(); scanner char a trouver
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
			persos[n] = new Personnage(n, 5, (int)(i+p.GetTaille()/2-1), 1, true, p);
            n++;
            //persos[n] = new Personnage(n, p.GetTaille() - 1, (int)(i+p.GetTaille()/2-1), 1, true, p);
            //n++;
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
				switch (g.GetEtat()[i][j])
				{
					case 0:
						System.out.print(" ");
						break;
					case 10:
						System.out.print("x");
					default :
						System.out.print(g.GetEtat()[i][j]);
						break;
				}
				
				System.out.print("|");
			}
			System.out.println("");
		}
    }
    
}
