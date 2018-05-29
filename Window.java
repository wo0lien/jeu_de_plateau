import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;
import java.lang.reflect.Array;
import java.lang.reflect.Field;


/**
 * Gestionnaire d'affichage pour la fourmi de Langton (issue de l'affichage du "Jeu de la vie")
 * @author Jean-Francois TREGOUET
 */
public class Window extends JFrame implements MouseListener,MouseMotionListener {
    
    private PanneauGrille pg;
    private Color c;
    
    //public Grille portee = new Grille(10); //plateau utilisé uniquement pour la portée des attaques et des déplacements
        
    public Personnage[] persos = new Personnage[6];
    private int pSelected;
    private boolean wasPerso; //pour savoir si il y avait un personnage sur la dernière case survolée
    private boolean equipe; //equipe du joueur en train de jouer
    private int clicMode;
        
    boolean ended = false; //boolean pour l'arret en fin de partie

    public Window() {
        super("Jeu de ouf");
        this.setSize(670, 430);
        
		Init(); //renvoie à l'initialisation
		
        pg = new PanneauGrille(persos);
        setContentPane(pg);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        
        //Ajout des Mouse Listener pour les events
		this.addMouseListener(this);
		this.addMouseMotionListener(this);
		
		clicMode = 1;
		equipe = true; // joueur 1
		wasPerso = false;
        
    }
    
    /**
     * Gestion de toutes les interractions avec la souris
     */ 
	public void mouseEntered(MouseEvent e) {
		
	}
	public void mouseExited(MouseEvent e) {
		
	}
	public void mouseReleased(MouseEvent e) {
		
	}
	public void mousePressed(MouseEvent e) {
		
	}
	public void mouseDragged(MouseEvent e) {
		
	}
	public void mouseClicked(MouseEvent e) { //a chaque clic de la souris
		
		//récupération de la case cliquée
		int ligne = pg.WhichCase(e.getX(), e.getY())[0];
		int colonne = pg.WhichCase(e.getX(), e.getY())[1];
		
		//on récupère un possible personnage sur la case, pas encore de gestion de l'equipe
		int p = getPersoInCase(ligne, colonne);
		
		//au clic il y a plusieurs modes / a faire plus tard
		if (clicMode == 1) //mode 1
		{
			if (p != 10) //test si il y a un perso ou pas
			{
				if (persos[p].GetEquipe() == equipe)
				{
					clicMode = 2; //on passe en mode 2 pour le prochain clic
					System.out.println("check");
					pSelected = p; //on garde en mémoire le personnage qui a été choisi
					
					//setup du nombre de case de déplacement possible
					double nbCaseDeplacement;
					if(persos[pSelected].GetPoids()) //en fonction du poids lourd ou leger
					{
						nbCaseDeplacement = 2.0;
					} else
					{
						nbCaseDeplacement = 3.0;
					}
					pg.resetDamier();
					pg.SetupPortee(ligne, colonne, 0, nbCaseDeplacement, 3);
					this.repaint(); //affichage
				} else
				{
					System.out.println("Ce personnage n'est pas dans ton équipe !!");
				}
				
			} else
			{
				System.out.println("Choisis mieux !");
			}
			
		} else if (clicMode == 2)//mode 2
		{
			//on regarde si on peut se placer sur la case
			if (pg.lastColor == 3)
			{
				if (p == 10 || p == pSelected) //test si il y a un perso ou pas
				{
					persos[pSelected].MovePerso(ligne, colonne);
					clicMode = 3;
					pg.resetDamier();
					this.repaint();
					System.out.println("On passe à la phase d'attaque  !");
					System.out.println("Choisis maintenant avec quel perso tu vas attaquer !");
					
				} else
				{
					System.out.println("Tu vas écraser quelqu'un !! Fais attention !");
				}
			} else
			{
				System.out.println("Trop loin...");
			}
			
		} else if (clicMode == 3) //passage en mode 3
		{
			if (p != 10) //test si il y a un perso ou pas
			{
				if (persos[p].GetEquipe() == equipe)
				{
					System.out.println("Ok c'est parti ! Et maintenant qui veux tu attaquer ?");
					SetupAttaque(p);
					clicMode = 4; //passage en mode 4
				} else 
				{
					System.out.println("Tu ne peux attaquer qu'avec les personnages de ton éuipe!");
				}
			} else
			{
				System.out.println("Nope pas ici, il n'y a personne!");
			}
		} else if (clicMode == 4)
		{
			if (p != 10)
			{
				if (persos[p].GetEquipe() != equipe)
				{
					if (pg.lastColor == 4 || pg.lastColor == 5) //on test la portée de l'attaque
					{
						System.out.println("Atttaaaaaaaaque");
						
						AppliqueDmgs(persos[pSelected], persos[p]); // méthode qui applique les dégats au deuxieme champion
						
						//liste des choses a faire pour repasser en mode 1 proprement
						pg.resetDamier();
						this.repaint();
						equipe = !equipe;
						clicMode = 1;
						System.out.println("Changement de joueur !!");
						
					} else {
						System.out.println("Ce personnage n'est pas a portée...");
					}
					
				} else {
					System.out.println("Tu ne peux pas attaquer ton équipe ! On va changer de perso du coup");
					SetupAttaque(p);
				}
			} else
			{
				System.out.println("Il n'y a persoone ici tant pis pour toi. On passe a l'autre joueur !");
				pg.resetDamier();
				this.repaint();
				equipe = !equipe;
				clicMode = 1;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) { //a chaque déplacement de la souris
		
		//récupération du x et du y
		int x = e.getX();
		int y = e.getY();
		
		//set offsets
		int offsetX = 9 + pg.offsetInX;
		int offsetY = 30 + pg.offsetInY + 15; //+15 pour etre a l'angle le plus a gauche
		
		//application des offsets au losange utilisé
		x -= offsetX;
		y -= offsetY;
		
		
		if (x > 0 && x < 500)
		{
			//recupere la ligne et la colonne de la case survolée
			int ligne = pg.WhichCase(e.getX(), e.getY())[0];
			int colonne = pg.WhichCase(e.getX(), e.getY())[1];
			
			//on récupère un possible personnage sur la case, pas encore de gestion de l'equipe
			int p = getPersoInCase(ligne, colonne);
			
			if (ligne != pg.lastLigne  || colonne != pg.lastColonne)//test si il s'agit d'une nouvelle case
			{
				//changements de couleurs pour la case survolée
				pg.setCase(pg.lastLigne, pg.lastColonne, pg.lastColor);
				pg.lastColor = pg.damier[ligne -1][colonne - 1];
				pg.setCase(ligne, colonne, 2);
				pg.lastLigne = ligne;
				pg.lastColonne = colonne;
				
				if (wasPerso && (clicMode == 1 || clicMode == 3))//test chui pas sur que ca marche
				{
					pg.resetDamier();
					wasPerso = false;
				}
				if (p != 10) //on teste si il y a un personnage sur la case 
				{
					DescriptionPerso(p); //description du perso dans la console
					if (clicMode == 1 || clicMode == 3)
					{
						SetupAttaque(p); //on affiche a la volée la portée d'attaque du perso
						wasPerso = true;
					}
				}
				this.repaint();
			}
			
			
		}
	}
	/**
	 * Méthode qui permet de setup la portée d'une attaque a partir d'un personnage
	 */
	public void DescriptionPerso(int p) {
		 for (int i = 0; i < 20; i++)
		 {
			 System.out.println("");
		 }
		 System.out.println("--------------------------------");
		 System.out.println("----Description du personnage---");
		 System.out.println("");
		 System.out.println("   type : "+persos[p].GetArme().GetName());
		 System.out.println("");
		 System.out.println("   HP : "+persos[p].GetHP());
		 System.out.println("");
		 System.out.println("--------------------------------");
	}
	/**
	 * Méthode qui permet de setup la portée d'une attaque a partir d'un personnage
	 */
	public void SetupAttaque(int p) {
		pg.resetDamier(); //on commence par reset le damier
		pSelected = p;
		if ((pg.lastLigne + pg.lastColonne) % 2 == 0) //on peut l'écrire bien plus proprement aussi 
		{
			pg.lastColor = 1;
		} else
		{
			pg.lastColor = 0;
		}
		int color;
		
		if (persos[p].GetEquipe())
		{
			color = 4;//attaque de l'quipe bleue
		} else {
			color = 5;//attaque de l'équipe rouge
		}
		
		pg.SetupPortee(persos[p].GetLigne(), persos[p].GetColonne(), persos[p].GetArme().GetPoMin(), persos[p].GetArme().GetPoMax(), color); //setup le damier pour la grille
		this.repaint();
	}
	/**
	 * Méthode qui permet de récupérer l'ID du personnage dans la case visée
	 */
	public int getPersoInCase(int l, int c) {
		
		boolean occupe = false;
		int n = 0;
		
		for (int i = 0; i < persos.length; i++)
		{
			if (persos[i].GetLigne() == l && persos[i].GetColonne() == c && persos[i].GetHP() != 0)
			{
				occupe = true;
				n = i;
			}
			
		}
		if (occupe)
		{
			return n;
		} else {
			return 10; //10 signifie qu'il n'y a personne dans la case
		}
		
	}
	
	/**
	 * Méthode d'initialisation du jeu
	 */
	public void Init() {
		
		System.out.println("");
        System.out.println("Let's start !!");
        
        persos = PlacementPersos(persos); //initialisation des personnages sur la carte et dans le tableau
        
        System.out.println("On commence par le joueur 1 !");
        System.out.println("Maintenant clique sur un de tes personnages");
	}
	
	/**
     * Méthode qui initilise les 2 équipes autours d'une case
     */
    public static Personnage[] PlacementPersos(Personnage[] persos) {
		
		int n = 0; //compteur
        
        for (int i = 0; i < 3; i++)
		{
			boolean pod= false;
			int c=(int)(2*Math.random());
				if(c==1){
					pod=true;
				}else{
					pod=false;
				}
				c =(int)(4*Math.random());
				int a = c+1;
				
				
			persos[n] = new Personnage(n + 1, 2, i + 3, a, pod, true);
            n++;
            persos[n] = new Personnage(n + 1, 8, i + 3, a, pod, false);
            n++;
		}
        return persos;
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
}

