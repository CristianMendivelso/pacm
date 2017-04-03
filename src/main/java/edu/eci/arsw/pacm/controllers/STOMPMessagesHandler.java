/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.controllers;

import edu.eci.arsw.pacm.model.LeerFichero;
import java.awt.Point;
import java.util.concurrent.ConcurrentLinkedQueue;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    String[][] matriz ;

   
    @MessageMapping("/JugarSala")
    public void prueba() {
        msgt.convertAndSend("/topic/JugarSala", "hola");
        

    }

    

}