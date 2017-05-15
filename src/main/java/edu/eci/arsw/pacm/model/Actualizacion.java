/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import java.util.ArrayList;

/**
 *
 * @author 2106088
 */
public class Actualizacion {

    private ArrayList<Elemento> actualizaciones;
    private Boolean cambioDePuntos = false;
    private int puntos;

    /**
     * @return the actualizaciones
     */
    public ArrayList<Elemento> getActualizaciones() {
        return actualizaciones;
    }

    /**
     * @param actualizaciones the actualizaciones to set
     */
    public void setActualizaciones(ArrayList<Elemento> actualizaciones) {
        this.actualizaciones = actualizaciones;
    }

    /**
     * @return the cambioDePuntos
     */
    public Boolean getCambioDePuntos() {
        return cambioDePuntos;
    }

    /**
     * @param cambioDePuntos the cambioDePuntos to set
     */
    public void setCambioDePuntos(Boolean cambioDePuntos) {
        this.cambioDePuntos = cambioDePuntos;
    }

    /**
     * @return the puntos
     */
    public int getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(int puntos) {
        this.puntos = puntos;
    }

}
