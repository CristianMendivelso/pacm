/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.controllers;

import edu.eci.arsw.pacm.model.Elemento;
import edu.eci.arsw.pacm.model.Jugador;
import edu.eci.arsw.pacm.model.LeerFichero;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

@Controller
public class STOMPMessagesHandler {

    @Autowired
    SimpMessagingTemplate msgt;
    String[][] matriz ;
    int puntos;

    @MessageMapping("/JugarSala")
    public void prueba() {
        msgt.convertAndSend("/topic/JugarSala", "hola");
        matriz= LeerFichero.muestraContenido();
        puntos=LeerFichero.puntos;
    }

    @MessageMapping("/mover")
    public void mover(Jugador j) {
        synchronized (msgt) {
            ArrayList<Elemento> actualizaciones = new ArrayList();
            //Si es pacman
            if (matriz[j.getX()][j.getY()].equals("B") || matriz[j.getX()][j.getY()].equals("A") || matriz[j.getX()][j.getY()].equals("C") || matriz[j.getX()][j.getY()].equals("D") ) {
                if (j.getK() == 40) {
                    if (!(matriz[j.getX() + 1][j.getY()]).equals("3") && !(matriz[j.getX() + 1][j.getY()]).equals("A") && !(matriz[j.getX() + 1][j.getY()]).equals("B") && !(matriz[j.getX() + 1][j.getY()]).equals("C") && !(matriz[j.getX() + 1][j.getY()]).equals("D")) {
                        if ((matriz[j.getX() + 1][j.getY()]).equals("a") || (matriz[j.getX() + 1][j.getY()]).equals("b") || (matriz[j.getX() + 1][j.getY()]).equals("c") || (matriz[j.getX() + 1][j.getY()]).equals("d")){
                            //mimra si se puede comer al fantasma o no
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                            
                        }else{
                            matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX()+1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);

                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes", puntos);
                        }
                        
                    }
                } else if (j.getK() == 37) {
                    if (!(matriz[j.getX()][j.getY() - 1]).equals("3") && !(matriz[j.getX()][j.getY() - 1]).equals("A")&& !(matriz[j.getX()][j.getY() - 1]).equals("B") && !(matriz[j.getX()][j.getY() - 1]).equals("C") && !(matriz[j.getX()][j.getY() - 1]).equals("D")) {
                        if ((matriz[j.getX()][j.getY() - 1]).equals("a") || (matriz[j.getX()][j.getY() - 1]).equals("b")  || (matriz[j.getX()][j.getY() - 1]).equals("c")  || (matriz[j.getX()][j.getY() - 1]).equals("d")){
                            //
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                        }else{
                            matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY()-1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes", puntos);
                        }
                    }
                } else if (j.getK() == 38) {
                    if (!(matriz[j.getX() - 1][j.getY()]).equals("3") && !(matriz[j.getX() - 1][j.getY()]).equals("A") && !(matriz[j.getX() - 1][j.getY()]).equals("B") && !(matriz[j.getX() - 1][j.getY()]).equals("C") && !(matriz[j.getX() - 1][j.getY()]).equals("D")) {
                        if ((matriz[j.getX() - 1][j.getY()]).equals("a") || (matriz[j.getX() - 1][j.getY()]).equals("b") || (matriz[j.getX() - 1][j.getY()]).equals("c") || (matriz[j.getX() - 1][j.getY()]).equals("d")){
                            //
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                        }else{
                            matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX()-1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes", puntos);
                        }
                    }
                } else if (j.getK() == 39) {
                    if (!(matriz[j.getX()][j.getY() + 1]).equals("3") && !(matriz[j.getX()][j.getY() + 1]).equals("A") && !(matriz[j.getX()][j.getY() + 1]).equals("B") && !(matriz[j.getX()][j.getY() + 1]).equals("C") && !(matriz[j.getX()][j.getY() + 1]).equals("D")) {
                        if ((matriz[j.getX()][j.getY() + 1]).equals("a") || (matriz[j.getX()][j.getY() + 1]).equals("b") || (matriz[j.getX()][j.getY() + 1]).equals("c") || (matriz[j.getX()][j.getY() + 1]).equals("d")){
                            //
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                        }else{
                            matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY()+1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes", puntos);
                        }
                    }
                }
            //si es fantasma
            } else if(matriz[j.getX()][j.getY()].equals("b") || matriz[j.getX()][j.getY()].equals("a") || matriz[j.getX()][j.getY()].equals("c") || matriz[j.getX()][j.getY()].equals("d")  ) {
                //abajo
                if (j.getK() == 40) {
                    if (!(matriz[j.getX() + 1][j.getY()]).equals("3") && !(matriz[j.getX() + 1][j.getY()]).equals("a") && !(matriz[j.getX() + 1][j.getY()]).equals("b") && !(matriz[j.getX() + 1][j.getY()]).equals("c") && !(matriz[j.getX() + 1][j.getY()]).equals("d")) {
                        int temp;
                        try{
                            temp=Integer.parseInt(matriz[j.getX() + 1][j.getY()]);
                        }catch(Exception e){
                            temp=0;
                        }
                        System.out.println(temp+"TEEEEMP");
                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());
                        
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX()+1][j.getY()],temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()),0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                    }
                } else if (j.getK() == 37) {
                    if (!(matriz[j.getX()][j.getY() - 1]).equals("3") && !(matriz[j.getX()][j.getY() - 1]).equals("a") && !(matriz[j.getX()][j.getY() - 1]).equals("b") && !(matriz[j.getX()][j.getY() - 1]).equals("c") && !(matriz[j.getX()][j.getY() - 1]).equals("d")) {
                        int temp;
                        try{
                            temp=Integer.parseInt(matriz[j.getX()][j.getY() - 1]);
                        }catch(Exception e){
                            temp=0;
                        }
                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY()-1],temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()),0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                    }
                } else if (j.getK() == 38) {
                    if (!(matriz[j.getX() - 1][j.getY()]).equals("3") && !(matriz[j.getX() - 1][j.getY()]).equals("a") && !(matriz[j.getX() - 1][j.getY()]).equals("b") && !(matriz[j.getX() - 1][j.getY()]).equals("c") && !(matriz[j.getX() - 1][j.getY()]).equals("d")) {
                        int temp;
                        try{
                            temp=Integer.parseInt(matriz[j.getX() - 1][j.getY()]);
                        }catch(Exception e){
                            temp=0;
                        }
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX()-1][j.getY()],temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()),0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                    }
                } else if (j.getK() == 39) {
                    if (!(matriz[j.getX()][j.getY() + 1]).equals("3") && !(matriz[j.getX()][j.getY() + 1]).equals("a") && !(matriz[j.getX()][j.getY() + 1]).equals("b") && !(matriz[j.getX()][j.getY() + 1]).equals("c") && !(matriz[j.getX()][j.getY() + 1]).equals("d")) {
                        int temp;
                        try{
                            temp=Integer.parseInt(matriz[j.getX()][j.getY() + 1]);
                        }catch(Exception e){
                            temp=0;
                        }
                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY()+1],temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()),0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        msgt.convertAndSend("/topic/actualizarJuego", actualizaciones);
                    }
                }
            }
            if (puntos==0){
               msgt.convertAndSend("/topic/findejuego", "EQUIPO ATACANTE"); 
            }

        }

    }

}
