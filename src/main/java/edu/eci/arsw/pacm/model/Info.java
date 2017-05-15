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
public class Info {
    String nombre;
    String letra; 
    
    public Info(){}
    
    public Info(String nombre, String letra){
        this.nombre=nombre;
        this.letra=letra;
    }
    public String getAlias() {
        return letra;
    }

    public void setAlias(String letra) {
        this.letra = letra;
    }   
    
    public void setNombre(String nombre){
        this.nombre=nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
