/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

/**
 *
 * @author User
 */
public class Sala {
    String[][] matriz ;
    int puntos;

    public String[][] getMatriz() {
        return matriz;
    }

    public void setMatriz(String[][] matriz) {
        this.matriz = matriz;
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
    }
    
    public Sala(){}
    
}
