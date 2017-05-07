/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.messaging.simp.SimpMessagingTemplate;

/**
 *
 * @author User
 */
public class HiloTiempos  extends Thread{
    
    SimpMessagingTemplate msgt;
    
    int numsala;
    String nombre;

    
    public HiloTiempos(int numsala,String nombre,SimpMessagingTemplate msgt) {
            this.numsala=numsala;
            this.nombre=nombre;
            this.msgt=msgt;
	}
    
    public HiloTiempos(){}
    
    @Override
	public void run() {
        try {
            HiloTiempos.sleep(4000);
            msgt.convertAndSend("/topic/Jugar."+String.valueOf(numsala),this.nombre);
            HiloTiempos.sleep(170000);
            msgt.convertAndSend("/topic/findejuego."+String.valueOf(numsala), "EQUIPO PROTECTOR"); 
        } catch (InterruptedException ex) {
            Logger.getLogger(HiloTiempos.class.getName()).log(Level.SEVERE, null, ex);
        }
        }
           
        
}
        
        

