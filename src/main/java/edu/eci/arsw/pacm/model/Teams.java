/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author User
 */
public class Teams {
    
    
    private CopyOnWriteArrayList <Player> protectores = new CopyOnWriteArrayList ();
    private CopyOnWriteArrayList <Player> atacantes = new CopyOnWriteArrayList ();

    public Teams() {
        
    }

    public CopyOnWriteArrayList<Player> getProtectores() {
        return protectores;
    }

    public void setProtectores(CopyOnWriteArrayList<Player> protectores) {
        this.protectores = protectores;
    }

    public CopyOnWriteArrayList<Player> getAtacantes() {
        return atacantes;
    }

    public void setAtacantes(CopyOnWriteArrayList<Player> atacantes) {
        this.atacantes = atacantes;
    }

   
    

}
