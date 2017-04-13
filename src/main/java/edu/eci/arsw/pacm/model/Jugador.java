/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

/**
 *
 * @author 2106088
 */
public class Jugador {
    private int x ;
    private int y ;
    private int k ;
    private int mem;

    
    public Jugador (){}

    public Jugador(int x, int y, int k,int mem) {
        this.x = x;
        this.y = y;
        this.k = k;
        this.mem=mem;
    }

    /**
     * @return the x
     */
    public int getX() {
        return x;
    }

    /**
     * @param x the x to set
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     * @return the y
     */
    public int getY() {
        return y;
    }

    /**
     * @param y the y to set
     */
    public void setY(int y) {
        this.y = y;
    }

    /**
     * @return the key
     */
    public int getK() {
        return k;
    }

    /**
     * @param k the key to set
     */
    public void setK(int k) {
        this.k = k;
    }
    
    
    public int getMem() {
        return mem;
    }

    public void setMem(int mem) {
        this.mem = mem;
    }
    
    
}
