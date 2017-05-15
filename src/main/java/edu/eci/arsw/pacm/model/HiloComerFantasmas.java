/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author 2106088
 */
public class HiloComerFantasmas extends Thread {

    int numsala;
    String nombre;

    public HiloComerFantasmas(int numsala, String nombre) {
        this.numsala = numsala;
        this.nombre = nombre;
    }

    public HiloComerFantasmas() {
    }

    @Override
    public void run() {
        try {
            HiloTiempos.sleep(10000);
            
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloTiempos.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
