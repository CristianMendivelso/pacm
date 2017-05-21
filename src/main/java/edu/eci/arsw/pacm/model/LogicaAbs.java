/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

/**
 *
 * @author 2107641
 */

public interface LogicaAbs {


    public void guardarTiempo(Actualizacion ac, Sala s);

    public Actualizacion mover(int idsala, Jugador j);
        
    public int[] morir(String data,String[][] matriz);
}

