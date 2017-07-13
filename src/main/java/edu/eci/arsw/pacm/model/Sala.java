/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import java.util.ArrayList;

/**
 *
 * @author User
 */
public class Sala {
    String[][] matriz ;
    int puntos;
    int[] vidas=new int[4];
    private ArrayList<Long> tiemposComibles = new ArrayList();
    boolean[] memoriaFantasmas = new boolean[2];

    public ArrayList<Long> getTiemposComibles() {
        return tiemposComibles;
    }

    public boolean[] getMemoriaFantasmas() {
        return memoriaFantasmas;
    }

    public void setMemoriaFantasmas(boolean[] memoriaFantasmas) {
        this.memoriaFantasmas = memoriaFantasmas;
    }

    public void setTiemposComibles(ArrayList<Long> tiemposComibles) {
        this.tiemposComibles = tiemposComibles;
    }
    
    
    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
    }

    public int[] getVidas() {
        return vidas;
    }

    public void setVidas(int[] vidas) {
        this.vidas = vidas;
    }
    
    public int getPuntos() {
        return puntos;
    }

    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

    public Sala(String[][] matriz, int puntos) {
        this.matriz = matriz;
        this.puntos = puntos;
        for (int i=0;i<4;i++){
            vidas[i]=2;
        }
        
    }
    
    
    
    public Sala(){}
    
}
