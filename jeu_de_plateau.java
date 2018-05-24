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
        
        //---- initialisation ici ----
        
        boolean joueur = false; //true pour le joueur 1 et false pour le joueur 2
        
        Plateau p = new Plateau(10);
        Grille portee = new Grille(10); //plateau utilisé uniquement pour la portée des attaques et des déplacements
        
        Personnage[] persos = new Personnage[16];
        
        persos = PlacementPersos(persos, p);
        
        boolean ended = false; //boolean de fin de partie
        
        while(!ended)
        {
			//debut du tour
			joueur = !joueur;
			AffichePlateau(p, persos);
			Deplacement(persos, portee, p );
			AffichePlateau(p, persos);
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
		
		//test si il y a au moins un personnage à portée
		
		boolean persoAPortee = false;
		
		for(int i = 0; i < portee.GetTaille(); i++) {
			for(int j = 0; j < portee.GetTaille(); j++) {
				
				if (portee.GetEtat()[i][j] > 0 && portee.GetEtat()[i][j] < 6) //equivaut a dire qu'il y a un perso affiché sur cette case (cad dans la portée)
				{
					persoAPortee = true;
				}
			}
		}
		
		//en fonction du résultat du test =>
		
		if (persoAPortee)
		{
		
			boolean testDistance = false;
			
			while (!testDistance)
			{
			
				System.out.println("Sur quel personnage veux-tu taper?");
				int c2 = sc.nextInt();
			
			
				//test de validité de la personnage qu'on va déboiter a faire + application des dégats (réduction des points de vie etc) FACILE LOL allez gl
			
			
				double distance = Math.sqrt(Math.pow((double)(persos[c1].GetColonne()) - (double)(persos[c2].GetColonne()), 2) + Math.pow((double)(persos[c1].GetLigne()) - (double)(persos[c2].GetLigne()), 2));
				if (distance <= persos[c1].GetArme().GetPoMax() && distance >= persos[c1].GetArme().GetPoMin())
				{
					testDistance = true;
					AppliqueDmgs(persos[c1], persos[c2]); 
				} else
				{
					System.out.println("Vous ne pouvez pas attaquer ce personnage, il n'est pas a portée");
				}
				
			}
		} else
		{
			System.out.println("Aucun personnage n'est à portée... dommage ! on passe à la suite");
		}
		
		
	}
	
	/**
     * Méthode qui applique les dégats d'un personnage sur un autre
     */
    public static void AppliqueDmgs(Personnage att, Personnage def) {
		
		int degats = att.GetArme().GetDmg();
		
		if ((att.GetArme().GetId() + 1) % 4 == def.GetArme().GetId()) //si l'arme du defenseur est celle d'apres l'arme de l'attaquant alors - 10
		{
			degats = degats - 10;
		} else if ((def.GetArme().GetId() + 1) % 4 == att.GetArme().GetId())//si l'arme du defenseur est celle d'avant l'arme de l'attaquant alors + 10
		{
			degats = degats + 10;
		}
		
		def.SetHp(degats);
		System.out.println("Les hps du perso sont maintenant de "+def.GetHP());
		
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
    public static void Deplacement( Personnage[] p, Grille g, Plateau plat) {
		
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
			g.SetupDeplacement(p[n], nbCasesRest, plat);
			AfficheGrille(g);
			entryChar = scString.nextLine();
			p[n].MovePerso(entryChar);
			nbCasesRest--;
		}
		g.Reset();
		g.SetupDeplacement(p[n], nbCasesRest, plat);
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
			persos[n] = new Personnage(n, 2, (int)(i+p.GetTaille()/2-1), 2, true, p, true);
            n++;
            persos[n] = new Personnage(n, p.GetTaille() - 3, (int)(i+p.GetTaille()/2-1), 1, true, p, false);
            n++;
		}
        return persos;
    }
    /**
     * Méthode qui permet de choisir un perso dans une des 2 équipes
     */
    public static int ChoixPersoEquipe(boolean equipe, Personnage[] persos, boolean joueur) {
		
		Scanner sc = new Scanner(System.in);
		
		int c=10;
		boolean testequipe =false;
		while(testequipe==false){
			while(c<1||c>6){
					
				c = sc.nextInt();//ID du perso
			}
			if (persos[c].GetEquipe()==joueur)
			{
				testequipe=true;
				//System.out.println("ce personnage est dans la bonne équipe!");
			}else{
				System.out.println("Ce personnage n'est pas dans la bonne équipe!");
			}
		}
		return c;
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
    public static void AffichePlateau(Plateau p, Personnage[] persos) { //il faudrait faire en sorte quelle soit en haut de la fenetre (=saute le bon nombre de lignes)
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
			if (i > 0 && i < 7)//affichage des persos verif hps peut etre ?
			{
				System.out.print("    "+persos[i]);
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
