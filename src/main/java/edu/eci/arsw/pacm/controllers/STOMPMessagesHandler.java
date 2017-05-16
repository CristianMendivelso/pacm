/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.controllers;

import edu.eci.arsw.pacm.model.Actualizacion;
import edu.eci.arsw.pacm.model.Elemento;
import edu.eci.arsw.pacm.model.HiloComerFantasmas;
import edu.eci.arsw.pacm.model.Jugador;
import edu.eci.arsw.pacm.model.LeerFichero;
import edu.eci.arsw.pacm.model.Sala;
import edu.eci.arsw.pacm.model.Teams;
import edu.eci.arsw.pacm.model.Logica;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;

    Logica l = new Logica();
    Boolean comibles = false;

    @MessageMapping("/mover.{idsala}")
    public void mover(@DestinationVariable int idsala, Jugador j) {
        synchronized (l) {
            Actualizacion ac = l.mover(idsala, j);
            msgt.convertAndSend("/topic/actualizarJuego." + String.valueOf(idsala), ac.getActualizaciones());
            if (ac.getCambioDePuntos()) {
                msgt.convertAndSend("/topic/puntosRestantes." + String.valueOf(idsala), ac.getPuntos());
            }
            if (ac.getPuntos()==0){
               msgt.convertAndSend("/topic/findejuego."+String.valueOf(idsala), "EQUIPO ATACANTE"); 
            }
            if (!ac.getComibles().equals(comibles)){
                comibles=ac.getComibles();
                msgt.convertAndSend("/topic/fantasmasComibles."+String.valueOf(idsala), ac.getComibles()); 
            }
        }

    }

}
