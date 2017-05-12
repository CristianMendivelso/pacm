/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.controllers;

import edu.eci.arsw.pacm.model.Elemento;
import edu.eci.arsw.pacm.model.Jugador;
import edu.eci.arsw.pacm.model.LeerFichero;
import edu.eci.arsw.pacm.model.Sala;
import edu.eci.arsw.pacm.model.Teams;
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

    private final ConcurrentHashMap<Integer, Sala> salasMatrices=new ConcurrentHashMap<>();
    
    //PENDIENTE DE ADECUAR A VARIAS SALAS

    @MessageMapping("/mover.{idsala}")
    public void mover(@DestinationVariable int idsala,Jugador j) {
        synchronized (salasMatrices) {
            if (!salasMatrices.containsKey(idsala)){
                Sala sala=new Sala(LeerFichero.muestraContenido(),LeerFichero.puntos);
                salasMatrices.put(idsala,sala);
            }
            ArrayList<Elemento> actualizaciones = new ArrayList();
            //Si es pacman
            String[][] matriz= salasMatrices.get(idsala).getMatriz();
           
                 
            int puntos=salasMatrices.get(idsala).getPuntos();
            if (matriz[j.getX()][j.getY()].equals("B") || matriz[j.getX()][j.getY()].equals("A") || matriz[j.getX()][j.getY()].equals("C") || matriz[j.getX()][j.getY()].equals("D") ) {
                if (j.getK() == 40) {
                    if (!(matriz[j.getX() + 1][j.getY()]).equals("3") && !(matriz[j.getX() + 1][j.getY()]).equals("A") && !(matriz[j.getX() + 1][j.getY()]).equals("B") && !(matriz[j.getX() + 1][j.getY()]).equals("C") && !(matriz[j.getX() + 1][j.getY()]).equals("D")) {
                        if ((matriz[j.getX() + 1][j.getY()]).equals("a") || (matriz[j.getX() + 1][j.getY()]).equals("b") || (matriz[j.getX() + 1][j.getY()]).equals("c") || (matriz[j.getX() + 1][j.getY()]).equals("d")){
                            //mimra si se puede comer al fantasma o no
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            
                            
                            //comer puntos
                        }else if ((matriz[j.getX() + 1][j.getY()]).equals("1")){
                            matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX()+1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);

                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                            
                            
                            //comer puntos grandes
                        }else if ((matriz[j.getX() + 1][j.getY()]).equals("2")){
                            matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX()+1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);

                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                            
                            
                            //no comer nada
                        }else{
                            matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX()+1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);

                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                        }
                        
                    }
                } else if (j.getK() == 37) {
                    if (!(matriz[j.getX()][j.getY() - 1]).equals("3") && !(matriz[j.getX()][j.getY() - 1]).equals("A")&& !(matriz[j.getX()][j.getY() - 1]).equals("B") && !(matriz[j.getX()][j.getY() - 1]).equals("C") && !(matriz[j.getX()][j.getY() - 1]).equals("D")) {
                        if ((matriz[j.getX()][j.getY() - 1]).equals("a") || (matriz[j.getX()][j.getY() - 1]).equals("b")  || (matriz[j.getX()][j.getY() - 1]).equals("c")  || (matriz[j.getX()][j.getY() - 1]).equals("d")){
                            //comer fantasmas
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            
                            
                            //comer puntos
                        }else if ((matriz[j.getX()][j.getY() - 1]).equals("1")){     
                            matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY()-1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                            
                            
                            //comer puntos grandes
                        }else if ((matriz[j.getX()][j.getY() - 1]).equals("2")){     
                            matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY()-1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                            
                            
                            //no comer nada
                        }else{
                            matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY()-1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                        }
                    }
                } else if (j.getK() == 38) {
                    if (!(matriz[j.getX() - 1][j.getY()]).equals("3") && !(matriz[j.getX() - 1][j.getY()]).equals("A") && !(matriz[j.getX() - 1][j.getY()]).equals("B") && !(matriz[j.getX() - 1][j.getY()]).equals("C") && !(matriz[j.getX() - 1][j.getY()]).equals("D")) {
                        if ((matriz[j.getX() - 1][j.getY()]).equals("a") || (matriz[j.getX() - 1][j.getY()]).equals("b") || (matriz[j.getX() - 1][j.getY()]).equals("c") || (matriz[j.getX() - 1][j.getY()]).equals("d")){
                            //comer fantasmas
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            
                            
                            //comer puntos
                        }else if ((matriz[j.getX() - 1][j.getY()]).equals("1")){
                            matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX()-1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                         
                            
                            //comper puntos grandes
                        }else if ((matriz[j.getX() - 1][j.getY()]).equals("1")){
                            matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX()-1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                         
                            
                            //no comer nada
                        }else{
                            matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX()-1][j.getY()],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);

                        }
                    }
                
                    
                    
                    //
                } else if (j.getK() == 39) {
                    if (!(matriz[j.getX()][j.getY() + 1]).equals("3") && !(matriz[j.getX()][j.getY() + 1]).equals("A") && !(matriz[j.getX()][j.getY() + 1]).equals("B") && !(matriz[j.getX()][j.getY() + 1]).equals("C") && !(matriz[j.getX()][j.getY() + 1]).equals("D")) {
                        if ((matriz[j.getX()][j.getY() + 1]).equals("a") || (matriz[j.getX()][j.getY() + 1]).equals("b") || (matriz[j.getX()][j.getY() + 1]).equals("c") || (matriz[j.getX()][j.getY() + 1]).equals("d")){
                            //comer fantasmas 
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            
                            
                            //comer puntos
                        }else if ((matriz[j.getX()][j.getY() + 1]).equals("1")){
                            matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY()+1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                            
                            
                            //comer puntos grandes
                        }else if ((matriz[j.getX()][j.getY() + 1]).equals("2")){
                            matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY()+1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            puntos-=1;
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
                            
                            
                            //no comer nada
                        }else{
                            matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY()+1],0);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0",0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                            msgt.convertAndSend("/topic/puntosRestantes."+String.valueOf(idsala), puntos);
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
                        msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
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
                        msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
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
                        msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
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
                        msgt.convertAndSend("/topic/actualizarJuego."+String.valueOf(idsala), actualizaciones);
                    }
                }
            }
            salasMatrices.get(idsala).setMatriz(matriz);
            salasMatrices.get(idsala).setPuntos(puntos);
            if (puntos==0){
               msgt.convertAndSend("/topic/findejuego."+String.valueOf(idsala), "EQUIPO ATACANTE"); 
            }
            
            
            }
 
    }

}
