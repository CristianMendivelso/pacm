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
public class Player {
    String nombre;
    String alias;

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }
    
    public Player(String nombre){
        this.nombre=nombre;
    }
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }
    
    public Player(){}
    
    
}
