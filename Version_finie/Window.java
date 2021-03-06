import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseEvent;



/**
 * Gestionnaire d'affichage pour la fourmi de Langton (issue de l'affichage du "Jeu de la vie")
 * @author Jean-Francois TREGOUET
 */
public class Window extends JFrame implements MouseListener,MouseMotionListener {
    
    private PanneauGrille pg;
    
    //public Grille portee = new Grille(10); //plateau utilisé uniquement pour la portée des attaques et des déplacements
        
    public Personnage[] persos = new Personnage[6];
    private int pSelected; //ID perso sélectionné et gardé en mémoire temporairement
    private boolean wasPerso; //pour savoir si il y avait un personnage sur la dernière case survolée
    private int lastPerso; //perso sur la case survolée
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
		
		clicMode = 1; //mode pour que le clic souris correponde a differentes actions (attaque / déplacement)
		equipe = true; // joueur 1
		wasPerso = false; //perso dans la dernière case survolée
		lastPerso = 10;
        
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
		
		if (clicMode == 1) //mode 1
		{
			if (p != 10) //test si il y a un perso ou pas
			{
				if (persos[p].GetEquipe() == equipe)
				{
					clicMode = 2; //on passe en mode 2 pour le prochain clic
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
				System.out.println("Choisis mieux il n'y a personne ici !");
			}
			
		} else if (clicMode == 2)//mode 2
		{
			//on regarde si on peut se placer sur la case
			if (pg.lastColor == 3)
			{
				if (p == 10 || p == pSelected) //test si il y a un perso ou pas
				{
					//déplacement du personnage sur le damier et visuellement
					persos[pSelected].MovePerso(ligne, colonne);
					clicMode = 3;
					pg.resetDamier();
					this.repaint();
					System.out.println("On passe a la phase d'attaque  !");
					System.out.println("Choisis maintenant avec quel perso tu vas attaquer !");
					
				} else
				{
					System.out.println("Tu vas ecraser quelqu'un !! Fais attention !");
				}
			} else
			{
				System.out.println("Trop loin...");
			}
			
		} else if (clicMode == 3) //en mode 3
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
		} else if (clicMode == 4) //mode 4
		{
			if (p != 10) //si il y a quelqu'un sur la case
			{
				if (persos[p].GetEquipe() != equipe) // si il n'est pas dans l'équipe du joueur
				{
					if (pg.lastColor == 4 || pg.lastColor == 5) //on test la portée de l'attaque
					{
						System.out.println("Atttaaaaaaaaque");
						
						AppliqueDmgs(persos[pSelected], persos[p]); // méthode qui applique les dégats au deuxieme champion
						if (Gagne(equipe)) //test si la personne a gagné
						{
							setVisible(false); //on ne peut plusvoir la fenetre
							System.out.println("La partie est finie :)");
							dispose(); //Destruction de l'objet JFrame
						}
						//liste des choses a faire pour repasser en mode 1 proprement
						pg.resetDamier();
						this.repaint();
						equipe = !equipe;
						clicMode = 1;
						System.out.println("Changement de joueur !!");
						
					} else {
						System.out.println("Ce personnage n'est pas a portee...");
					}
					
				} else {
					System.out.println("Tu ne peux pas attaquer ton equipe ! On va changer de perso du coup");
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
				
				if (wasPerso && (clicMode == 1 || clicMode == 3)) //si il y avait un perso et qu'on est pas en mode 2 ou 4 (portée affichée de base et permannente)
				{
					pg.resetDamier();
					wasPerso = false;
				}
				if (p != 10) //on teste si il y a un personnage sur la case 
				{
					if (lastPerso != p)
					{
						System.out.println("");
						System.out.println(persos[p]); //description du perso dans la console
						System.out.println("");
						lastPerso = p;
					}
					
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
	 * Méthode qui permet de récupérer l'ID du personnage dans la case visée renvoie 10 si il n'y a personne 
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
		boolean equipe = false; //choix de l'équipe
        
        for (int i = 0; i < 6; i++)
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
				equipe = !equipe;
			
			if (equipe)
			{
				persos[n] = new Personnage(n + 1, 2, i + 3, a, pod, true);
			} else
			{
				persos[n] = new Personnage(n + 1, 8, i + 3, a, pod, false);
			}
			n++;
		}
        return persos;
    }
    
    /**
     * Méthode qui applique les dégats d'un personnage sur un autre
     */
    public static void AppliqueDmgs(Personnage att, Personnage def) {
		
		int degats = att.GetArme().GetDmg();
		
		//ici on décale de 2 les Id car ils commencent à 2 et on veut faire un modulo autours de 0
		if (att.GetArme().GetId() != 1)
		{
			if ((att.GetArme().GetId() - 1) % 4 == def.GetArme().GetId() - 2) //si l'arme du defenseur est celle d'apres l'arme de l'attaquant alors - 10
			{
				degats = degats - 10;
				System.out.println("Ce n'est pas tres efficace !");
			} else if ((def.GetArme().GetId() - 1) % 4 == att.GetArme().GetId() - 2)//si l'arme du defenseur est celle d'avant l'arme de l'attaquant alors + 10
			{
				degats = degats + 10;
				System.out.println("C'est super efficace !");
			} else
			{
				System.out.println("C'est la meme arme degats classiques !");
			}
			
		} else
		{
			System.out.println("L'arc applique toujours les memes degats !");
		}
		
		System.out.print(degats+" de degats ");
		def.SetHp(degats);
		System.out.println("Les hps du perso sont maintenant de : "+def.GetHP());
		
	}
	/**
	 * Méthode qui déteremine si le joueur a gagner
	 * @param equipe du joueur
	 */
	 public boolean Gagne(boolean equipe) {
		 
		 boolean gagne = true;
		 
		 for (int i = 0; i < 6; i++)
		 {
			 if (!persos[i].GetEquipe() && persos[i].GetHP() != 0) //si un perso de l'équipe adverse a encore de la vie
			 {
				gagne = false;
			 }
			 
		 }
		 return gagne;
	}
		 
}

