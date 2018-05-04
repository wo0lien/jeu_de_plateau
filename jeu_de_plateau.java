import java.util.Scanner;
public class jeu_de_plateau {
    
    public static void main(String Args[]) {
        
        Scanner sc = new Scanner(System.in);
        int entry = 0; //variable de l'entrée utilisateur
        
        // menu de base qui boucle tant que le joueur ne commenece pas la partie
       
        while(entry != 3) {
            System.out.println("Bonjour et bienvenue dans notre jeu de plateau");
            System.out.println("               -----------------");
            System.out.println("1. Afficher les règles");
            System.out.println("2. Modifier les paramètres ");
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
        int[][] plateau = new int[30][30]; //création du plateau -- directement modifié
        PlacementPersos();
        
        
        boolean ended = false; //boolean de fin de partie
        
        while(!ended) //tant que un joueur n'a pas gagné on continue
        {
            
        }
    }
    
    /**
     * Méthode qui initilise les 2 équipes autours d'une case
     */
    public static void PlacementPersos() {
        
        //décrit toutes les cases pour placer les unités autours d'une case définie. (2 tableau unités pour 2 équipes)
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 3; j++) {
                if (i != 1 && j != 1) //exclu la case centrale
                {
                    
                }
                
            }
        }
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
    
}
