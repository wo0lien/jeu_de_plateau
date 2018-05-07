/*
 * personnage.java
 * 
 * Copyright 2018 Ferreira - Rechauchere Pablo <pferreirar@pc107-043-07>
 * 
 * This program is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 2 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston,
 * MA 02110-1301, USA.
 * 
 * 
 */


public class Personnage {
	
    private int ID; //ID du personnage utilsé dans toutes les class
    private int ligne; //position du perso
    private int colonne;
    private int arme;// arme
    private boolean poids;//poids : true = lourd false = leger
    private Plateau plateau;
    private int classe; //1 = guerrier 2 = archer... a faire
    private String name; //nom de la classe voir du personnage si on se chauffe (création d'un objet classe ?)
	// Constructeur complet
	
        public Personnage (int n,int c, int l, int a, boolean po, Plateau p) {
			this.ID = n;
            this.colonne = c;
            this.ligne = l;
            this.arme = a;
            this.poids = po;
            this.plateau = p;
            
            plateau.AddPerso(this);
            
        }
        
    // Accesseurs
	public int GetLigne(){
        return ligne;
    }
    
    public int GetColonne(){
        return colonne;
    }
    
	public int GetArme(){
        return arme;
    }
    
    public boolean GetPoids(){
        return poids;
    }
    public int GetID(){
		return ID;
	}
	
	public String toString() {
        String description = "Le personnage "+ID+" est un <nom de classe>";
        return description;
    }
    
}

