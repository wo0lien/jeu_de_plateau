import java.awt.*;
import java.io.IOException;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.io.File;
/**
 * mise en formet et affichage du damier
 */
class PanneauGrille extends JPanel {
	
	private Color[] c = new Color[6];
	public int[][] damier = new int[10][10];
	private boolean init = false; //initialisé ou non
	private BufferedImage map;
	private BufferedImage trees;
	
	//personnages
	private BufferedImage epee;
	private BufferedImage epeeBandit;
	private BufferedImage hache;
	private BufferedImage lastBandit;
	private BufferedImage lance;
	private BufferedImage lanceBandit;
	private BufferedImage arc;
	private BufferedImage arcBandit;
	
	
	private int transparency;
	public int offsetInX;
	public int offsetInY;
	private Personnage[] persos;
	
	public int lastLigne; //garde en mémoire la case sur laquelle est la souris pour faire moins de calculs
    public int lastColonne;
    public int lastColor;
	
	public PanneauGrille(Personnage[] p) {
		
		persos = p;
		
		try {
			map = ImageIO.read(new File("asset/map.jpg"));
			trees = ImageIO.read(new File("asset/map_trees.png"));
			
			//personnages
			
			epee = ImageIO.read(new File("asset/epee.png"));
			epeeBandit = ImageIO.read(new File("asset/epee_bandit.png"));
			hache = ImageIO.read(new File("asset/hache.png"));
			lastBandit = ImageIO.read(new File("asset/last_bandit.png"));
			lance = ImageIO.read(new File("asset/lance.png"));
			lanceBandit = ImageIO.read(new File("asset/lance_bandit.png"));
			arc = ImageIO.read(new File("asset/arc.png"));
			arcBandit = ImageIO.read(new File("asset/arc_bandit.png"));
		} catch (IOException e) {}
		
		transparency = 100;
		
		c[0] = new Color(200, 200, 200, transparency); //couleur de base gris clair
		c[1] = new Color(175, 175, 175, transparency); //couleur de base gris foncé
		c[2] = new Color(254, 233, 42, transparency); //couleur jaune case survolée
		c[3] = new Color(83, 255, 31, transparency); //couleur jaune case survolée
		c[4] = new Color(48, 148, 255, transparency); //couleur bleu case accessible attaque equipe 1 
		c[5] = new Color(254, 42, 49, transparency); //couleur rouge case accessible attaque equipe 2
		
		lastLigne = 5;
        lastColonne = 6;
        lastColor = 0;
		
		resetDamier();
		
		offsetInX = 120; //positionnement de la grille par rapport à l'image
		offsetInY = 200;
	}
	/**
	 * Méthode qui remet le damier sur sa configuration d'origine
	 */
	public void resetDamier() {
		for(int i = 0; i < 10; i++)
		{
			for (int j = 0; j < 10; j++)//inversé car on part de 0 !!! attention
			{
				if ((i + j) % 2 == 0)
				{
					damier[i][j] = 1;
				} else
				{
					damier[i][j] = 0;
				}	
			}
			if ((lastLigne + lastColonne) % 2 == 0) //on peut l'écrire bien plus proprement aussi !! a refaire si j'ai le temps
			{
				lastColor = 1;
			} else
			{
				lastColor = 0;
			}
		}
	}
	
	public void paint(Graphics g) { //je dois ajouter la couleur en arrière plan + creer des offsets fonctionnels
		g.drawImage(map, 0, 0, null);
		Damier(g);
		
		//Placement des personnages sur la grille
		
		for (int i = 0; i < persos.length; i++)
		{
			if (persos[i].GetHP() != 0)
			{
				PlaceImageInCase(g, persos[i]);
			}
		}
		g.drawImage(trees, 0, 0, null);
	}

	/**
	 * Méthode qui place l'image du personnage au centre de la case
	 * @param personnage pour la position
	 */
	public void PlaceImageInCase(Graphics g, Personnage p) {
		
		BufferedImage img;
		
		if (p.GetEquipe())
		{
			switch (p.GetArme().GetId())
			{
				case 1:
					img = arc;
					break;
				case 2:
					img = epee;
					break;
				case 3:
					img = hache;
					break;
				case 4:
					img = lance;
					break;
				default:
				img = arc;
			}
			
		} else {
			switch (p.GetArme().GetId())
			{
				case 1:
					img = arcBandit;
					break;
				case 2:
					img = epeeBandit;
					break;
				case 3:
					img = lastBandit;
					break;
				case 4:
					img = lanceBandit;
					break;
				default:
				img = arc;
			}
		}
		
		int x = offsetInX + 25; //prise en compte des offsets de base + mise en position au milieu de la premiere case
		int y = offsetInY + 15 + 10; //décalage vers le bas par rapport au centre
		
		for (int i = 1; i < p.GetColonne(); i++)
		{
			x += 25;
			y -= 15;
		}
		for (int i = 1; i < p.GetLigne(); i++)
		{
			x += 25;
			y += 15;
		}
		
		//on positionne le bas de l'image au milieu de la case
		
		x -= (img.getWidth() / 2);
		y -= img.getHeight();
		g.drawImage(img, x, y, null);
	}
		 
	/**
	 * Méthode qui fabrique un damier sur la carte v2
	 */
	public void Damier(Graphics g) {
		
		int[] pointsY = {15, 0, 15, 30};
		int[] pointsX = {0, 25, 50, 25};
		int[] pointsXInit = {0, 25, 50, 25};
		int[] pointsYInit = {15, 0, 15, 30};
		
		//application des offsets In
		
		for (int i = 0; i < 4; i++)
		{
			pointsX[i] += offsetInX;
			pointsXInit[i] += offsetInX;
			
			pointsYInit[i] += offsetInY;
			pointsY[i] += offsetInY;
		}
		
		for (int i = 0; i < 10; i++)
		{
			for (int l = 0; l < 4; l++)
			{
				pointsX[l] = pointsXInit[l] + 25 * i;
				pointsY[l] = pointsYInit[l] + 15 * i;
			}
			
			for (int j = 0; j < 10; j++)
			{
				g.setColor(c[damier[i][j]]);
				g.fillPolygon(pointsX, pointsY, 4);
				
				for (int k = 0; k < 4; k++)
				{
					pointsX[k] += 25;
					pointsY[k] -= 15;
				}
				
			}
			
		}
		
	}
	
	/**
	 * Méthode qui setup le damier pour afficher la portée d'une attaque ou d'un déplacement
	 * @param l'ID du perso dans le tableau / le nombre de cases min et max
	 */
	
	public void SetupPortee(int ligne, int colonne, int min, double max, int color) {
		
		
		for (int i = 0; i < 10; i++) {
			for (int j = 0; j < 10; j++) {
				
				double distanceCase = Math.sqrt(Math.pow((double)(ligne - 1) - (double)(i), 2) + Math.pow((double)(colonne - 1) - (double)(j), 2)); //attention aux -1 qui sont primordiaux pour ne pas confondre les lignes et le tableau
				if (distanceCase >= (double)(min) && distanceCase <= (double)(max))
				{
					damier[i][j] = color;
				}
			}
		}
		
		lastColor = color; //ne pas oublier la case qui est survolé sinon elle redevient grise
		
	}
	
	public static void afftab(int[][] tab){
		System.out.println("");
		for (int i = 0; i < tab.length; i++)
		{
			for (int j = 0; j < tab[i].length; j++)
			{
				System.out.print(tab[i][j]+" ");
			}
			System.out.println("");
		}
	}
	/**
	 * Méthode qui renvoie la ligne et la colonne du losange se trouvant sous un point V2
	 * @param x et y du point
	 */
	public int[] WhichCase(int x, int y) {
		
		//offsets des losanges (offset In + offset Out fixes cad par rapport a la fenetre)
		int offsetX = 9 + offsetInX + 15; //+15 pour etre a l'angle le plus a gauche
		int offsetY = 30 + offsetInY;
		
		//application des offsets au losange utilisé
		x -= offsetX;
		y -= offsetY;
		
		y = -y; //passsage dans un repère classique
		
		int colonne = 1;
		int ligne = 1;
		
		//détermination de la colonne
		int i = 0;
		while((double)(y) > ((double)(i) * 30.0) - ((double)(x) * 3.0/5.0 )) {
			i ++;
		}
		colonne = Math.min(i + 1, 10);
		
		//détermination de la ligne
		i = 1;
		while((double)(y) < (-30.0 * (double)(i)) + ((double)(x) * 3.0/5.0 )) {
			i++;
		}
		ligne = Math.min(i, 10);
		
		int coord[] = {ligne, colonne};
		
		return coord;
		
	}
	
	//juste pour afficher des tableaux pour debug tout ca
	public static void affichet(int[] tab) {
		
		for (int i = 0; i < tab.length; i++)
		{
			System.out.print(tab[i]+"   ");
		}
		
	}
		
	//changement de la couleur utilisée par la méthode
	public void setColor(Color col, int i) {
		c[i] = col;
	}
	//configure la couleur de la case (par l'intermédiaire du damier)
	public void setCase(int ligne, int colonne, int color) {
		damier[ligne - 1][colonne - 1] = color;
	}
}
