/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.controllers;

import edu.eci.arsw.pacm.model.Actualizacion;
import edu.eci.arsw.pacm.model.Jugador;
import edu.eci.arsw.pacm.model.Logica;
import edu.eci.arsw.pacm.services.PacmServices;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    @Autowired
    PacmServices services;
    
    @Autowired
    Logica l;
    Object Lock = new Object();
	// comentario
	// para re-subir el proyecto
    @MessageMapping("/mover.{idsala}")
    public void mover(@DestinationVariable int idsala, Jugador j) {
        synchronized (Lock) {
            Actualizacion ac = l.mover(idsala, j);
            if (ac.getActualizaciones()!=null){
                msgt.convertAndSend("/topic/actualizarJuego." + String.valueOf(idsala), ac.getActualizaciones());
                if (ac.getCambioDePuntos()) {
                    msgt.convertAndSend("/topic/puntosRestantes." + String.valueOf(idsala), ac.getPuntos());
                }
                if (ac.getPuntos()==0){
                   msgt.convertAndSend("/topic/findejuego."+String.valueOf(idsala), "images/WAtacante.png"); 
                }
                if (ac.getComibles()){
                    ac.setComibles(false);
                    msgt.convertAndSend("/topic/fantasmasComibles."+String.valueOf(idsala), ac.getComibles()); 
                }
                if(ac.getPosiciones()[0]!=0){
                   int[] vidas=services.getSalasMatrices().get(idsala).getVidas();
                    System.out.println(Arrays.toString(vidas));
                   if (vidas[0]+vidas[1]==0){
                       msgt.convertAndSend("/topic/findejuego."+String.valueOf(idsala), "images/WProtector.png"); 
                   }
                   else{
                       msgt.convertAndSend("/topic/cambioVidas."+String.valueOf(idsala), vidas);
                       msgt.convertAndSend("/topic/"+String.valueOf(idsala)+'/'+ac.getPlayer(), ac.getPosiciones());                 
                   }
                }   
            }
        }

    }

}
