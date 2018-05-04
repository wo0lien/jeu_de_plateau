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


public class personnage {
	
    private int l; // ligne
    private int c; // colonne
    private int a;// arme
    private int p;//poids
	// Constructeur complet
	
        public personnage (int ligne, int colonne, int arme, int poids) {
            this.l = ligne ;
            this.c = colonne ;
            this.a = arme;
            this.p = poids;
        }
    // Accesseurs
	public int getLigne(){
        return l;
    }
    
    public int getColonne(){
        return c;
    }
    
	public int getarme(){
        return a;
    }
    
    public int getpoids(){
        return p;
    }
    
}

