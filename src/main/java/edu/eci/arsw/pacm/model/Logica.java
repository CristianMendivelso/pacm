/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import edu.eci.arsw.pacm.services.PacmServices;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author 2106088
 */
@Service
public class Logica implements LogicaAbs {

    @Autowired
    PacmServices services;
    private ConcurrentHashMap<Integer, Sala> salasMatrices ;

    public Logica() {
    }

    @Override

    public Actualizacion mover(int idsala, Jugador j) {
        salasMatrices=services.getSalasMatrices();
        Actualizacion ac = new Actualizacion();
        if (!salasMatrices.containsKey(idsala)) {
            Sala sala = new Sala(LeerFichero.muestraContenido(), LeerFichero.puntos);
            salasMatrices.put(idsala, sala);
        }
        ArrayList<Elemento> actualizaciones = new ArrayList();
        //Si es pacman
        String[][] matriz = salasMatrices.get(idsala).getMatriz();
        int[] vidas = salasMatrices.get(idsala).getVidas();
        int puntos = salasMatrices.get(idsala).getPuntos();
        ac.setPuntos(puntos);
        //ac.setComibles(comibles);
        if (matriz[j.getX()][j.getY()].equals("B") || matriz[j.getX()][j.getY()].equals("A") || matriz[j.getX()][j.getY()].equals("C") || matriz[j.getX()][j.getY()].equals("D")) {
            if (j.getK() == 40) {
                if (!(matriz[j.getX() + 1][j.getY()]).equals("3") && !(matriz[j.getX() + 1][j.getY()]).equals("A") && !(matriz[j.getX() + 1][j.getY()]).equals("B") && !(matriz[j.getX() + 1][j.getY()]).equals("C") && !(matriz[j.getX() + 1][j.getY()]).equals("D")) {
                    if ((matriz[j.getX() + 1][j.getY()]).equals("a") || (matriz[j.getX() + 1][j.getY()]).equals("b") || (matriz[j.getX() + 1][j.getY()]).equals("c") || (matriz[j.getX() + 1][j.getY()]).equals("d")) {
                        //mimra si se puede comer al fantasma o no
                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String data = matriz[j.getX() + 1][j.getY()];
                            matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            matriz[ans[0]][ans[1]] = data;
                            
                            Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], j.getMem());
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);

                        } else {
                            //matar pacman
                            String data = matriz[j.getX()][j.getY()];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            if (tvid>0){
                                
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                            actualizaciones.add(je);
                            
                            }
                            ac.setPosiciones(ans);
                            Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                            actualizaciones.add(e);
                           
                            
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);
                        }

                        //comer puntos
                    } else if ((matriz[j.getX() + 1][j.getY()]).equals("1")) {
                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);

                        //comer puntos grandes
                    } else if ((matriz[j.getX() + 1][j.getY()]).equals("2")) {
                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        guardarTiempo(ac, salasMatrices.get(idsala));

                        //no comer nada
                    } else {
                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                    }

                }
            } else if (j.getK() == 37) {
                if (!(matriz[j.getX()][j.getY() - 1]).equals("3") && !(matriz[j.getX()][j.getY() - 1]).equals("A") && !(matriz[j.getX()][j.getY() - 1]).equals("B") && !(matriz[j.getX()][j.getY() - 1]).equals("C") && !(matriz[j.getX()][j.getY() - 1]).equals("D")) {
                    if ((matriz[j.getX()][j.getY() - 1]).equals("a") || (matriz[j.getX()][j.getY() - 1]).equals("b") || (matriz[j.getX()][j.getY() - 1]).equals("c") || (matriz[j.getX()][j.getY() - 1]).equals("d")) {
                        //comer fantasmas

                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String data = matriz[j.getX()][j.getY() - 1];
                            matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            matriz[ans[0]][ans[1]] = data;
                            Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], j.getMem());
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);
                        } else {

                           String data = matriz[j.getX()][j.getY()];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            //si tiene mas de una vida
                            if (tvid>0){
                                
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                             
                            }
                            ac.setPosiciones(ans);
                            Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                            actualizaciones.add(e);
                            

                            
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);
                        }

                        //comer puntos
                    } else if ((matriz[j.getX()][j.getY() - 1]).equals("1")) {
                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);

                        //comer puntos grandes
                    } else if ((matriz[j.getX()][j.getY() - 1]).equals("2")) {
                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        guardarTiempo(ac, salasMatrices.get(idsala));

                        //no comer nada
                    } else {
                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                    }
                }
            } else if (j.getK() == 38) {
                if (!(matriz[j.getX() - 1][j.getY()]).equals("3") && !(matriz[j.getX() - 1][j.getY()]).equals("A") && !(matriz[j.getX() - 1][j.getY()]).equals("B") && !(matriz[j.getX() - 1][j.getY()]).equals("C") && !(matriz[j.getX() - 1][j.getY()]).equals("D")) {
                    if ((matriz[j.getX() - 1][j.getY()]).equals("a") || (matriz[j.getX() - 1][j.getY()]).equals("b") || (matriz[j.getX() - 1][j.getY()]).equals("c") || (matriz[j.getX() - 1][j.getY()]).equals("d")) {
                        //comer fantasmas

                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String data = matriz[j.getX() - 1][j.getY()];
                            matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            matriz[ans[0]][ans[1]] = data;
                            Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], j.getMem());
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);
                        } else {
                           String data = matriz[j.getX()][j.getY()];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            if (tvid>0){
                                
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                             
                            }

                            ac.setPosiciones(ans);
                            Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                            actualizaciones.add(e);
                            
                            
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);
                        }

                        //comer puntos
                    } else if ((matriz[j.getX() - 1][j.getY()]).equals("1")) {
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);

                        //comper puntos grandes
                    } else if ((matriz[j.getX() - 1][j.getY()]).equals("2")) {
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        guardarTiempo(ac, salasMatrices.get(idsala));

                        //no comer nada
                    } else {
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);

                    }
                }

            } else if (j.getK() == 39) {
                if (!(matriz[j.getX()][j.getY() + 1]).equals("3") && !(matriz[j.getX()][j.getY() + 1]).equals("A") && !(matriz[j.getX()][j.getY() + 1]).equals("B") && !(matriz[j.getX()][j.getY() + 1]).equals("C") && !(matriz[j.getX()][j.getY() + 1]).equals("D")) {
                    if ((matriz[j.getX()][j.getY() + 1]).equals("a") || (matriz[j.getX()][j.getY() + 1]).equals("b") || (matriz[j.getX()][j.getY() + 1]).equals("c") || (matriz[j.getX()][j.getY() + 1]).equals("d")) {
                        //comer fantasmas 

                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String data = matriz[j.getX()][j.getY() + 1];
                            matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            matriz[ans[0]][ans[1]] = data;
                            Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], j.getMem());
                            Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            actualizaciones.add(e);
                            actualizaciones.add(e2);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            ac.setActualizaciones(actualizaciones);
                        } else {
                            //mi jugador
                            String data = matriz[j.getX()][j.getY()];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            matriz[j.getX()][j.getY()] = "0";
                            int[] ans = morir(data, matriz);
                            if (tvid>0){
                                
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                             
                            }
                            ac.setPosiciones(ans);
                            Elemento e = new Elemento(j.getX(), j.getY(), "0", j.getMem());
                            ac.setPlayer(data);
                            actualizaciones.add(e);
                            
                            ac.setActualizaciones(actualizaciones);
                        }

                        //comer puntos
                    } else if ((matriz[j.getX()][j.getY() + 1]).equals("1")) {
                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);

                        //comer puntos grandes
                    } else if ((matriz[j.getX()][j.getY() + 1]).equals("2")) {
                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        guardarTiempo(ac, salasMatrices.get(idsala));

                        //no comer nada
                    } else {
                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], j.getMem());
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                    }
                }
            }

            //si es fantasma
        } else if (matriz[j.getX()][j.getY()].equals("b") || matriz[j.getX()][j.getY()].equals("a") || matriz[j.getX()][j.getY()].equals("c") || matriz[j.getX()][j.getY()].equals("d")) {
            //abajo
            if (j.getK() == 40) {
                if (!(matriz[j.getX() + 1][j.getY()]).equals("3") && !(matriz[j.getX() + 1][j.getY()]).equals("a") && !(matriz[j.getX() + 1][j.getY()]).equals("b") && !(matriz[j.getX() + 1][j.getY()]).equals("c") && !(matriz[j.getX() + 1][j.getY()]).equals("d")) {

                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX() + 1][j.getY()]);

                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e1 = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                        actualizaciones.add(e1);
                        actualizaciones.add(e2);

                        ac.setActualizaciones(actualizaciones);

                    } catch (Exception e) {
                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String fantasma = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());
                            int[] ans = morir(fantasma, matriz);
                            matriz[ans[0]][ans[1]] = fantasma;
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            Elemento e3 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e3);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(fantasma);
                            ac.setActualizaciones(actualizaciones);

                        } else {

                            temp = 0;
                            String data = matriz[j.getX() + 1][j.getY()];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            
                            int[] ans = morir(data, matriz);
                            if (tvid>0){
                                
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                             

                            }
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            System.out.println("hoola"+matriz[j.getX()][j.getY()]);
                            matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                            Elemento e1 = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], temp);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e1);
                            actualizaciones.add(e2);

                            ac.setActualizaciones(actualizaciones);

                        }

                    }

                }
            } else if (j.getK() == 37) {
                if (!(matriz[j.getX()][j.getY() - 1]).equals("3") && !(matriz[j.getX()][j.getY() - 1]).equals("a") && !(matriz[j.getX()][j.getY() - 1]).equals("b") && !(matriz[j.getX()][j.getY() - 1]).equals("c") && !(matriz[j.getX()][j.getY() - 1]).equals("d")) {
                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX()][j.getY() - 1]);

                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e1 = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                        actualizaciones.add(e1);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                    } catch (Exception e) {
                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String fantasma = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());
                            int[] ans = morir(fantasma, matriz);
                            matriz[ans[0]][ans[1]] = fantasma;
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            Elemento e3 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e3);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(fantasma);
                            ac.setActualizaciones(actualizaciones);

                        } else {
                            temp = 0;
                            String data = matriz[j.getX()][j.getY() - 1];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            
                             int[] ans = morir(data, matriz);
                            if (tvid>0){
                               
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                             
                            }
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            System.out.println("hoola"+matriz[j.getX()][j.getY()]);

                          
                            matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                            Elemento e1 = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], temp);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e1);
                            actualizaciones.add(e2);
                            ac.setActualizaciones(actualizaciones);
                        }
                    }

                }
            } else if (j.getK() == 38) {
                if (!(matriz[j.getX() - 1][j.getY()]).equals("3") && !(matriz[j.getX() - 1][j.getY()]).equals("a") && !(matriz[j.getX() - 1][j.getY()]).equals("b") && !(matriz[j.getX() - 1][j.getY()]).equals("c") && !(matriz[j.getX() - 1][j.getY()]).equals("d")) {
                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX() - 1][j.getY()]);

                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e1 = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                        actualizaciones.add(e1);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);

                    } catch (Exception e) {
                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String fantasma = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());
                            int[] ans = morir(fantasma, matriz);
                            matriz[ans[0]][ans[1]] = fantasma;
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            Elemento e3 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e3);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(fantasma);
                            ac.setActualizaciones(actualizaciones);

                        } else {
                            temp = 0;
                            String data = matriz[j.getX() - 1][j.getY()];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            
                            int[] ans = morir(data, matriz);
                            if (tvid>0){
                                
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                            
                            }
                             ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            System.out.println("hoola"+matriz[j.getX()][j.getY()]);

                            matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                            Elemento e1 = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], temp);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e1);
                            actualizaciones.add(e2);
                            ac.setActualizaciones(actualizaciones);
                        }
                    }

                }
            } else if (j.getK() == 39) {
                if (!(matriz[j.getX()][j.getY() + 1]).equals("3") && !(matriz[j.getX()][j.getY() + 1]).equals("a") && !(matriz[j.getX()][j.getY() + 1]).equals("b") && !(matriz[j.getX()][j.getY() + 1]).equals("c") && !(matriz[j.getX()][j.getY() + 1]).equals("d")) {
                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX()][j.getY() + 1]);

                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                        Elemento e1 = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], temp);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                        actualizaciones.add(e1);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                    } catch (Exception e) {
                        long tiempo = System.currentTimeMillis();
                        // miro que la lista de tiempos no este vacia y miro si la diferencia del ultimo tiempo agregado es menor a 10 segundos
                        if ((!salasMatrices.get(idsala).getTiemposComibles().isEmpty()) && (tiempo - salasMatrices.get(idsala).getTiemposComibles().get(salasMatrices.get(idsala).getTiemposComibles().size() - 1) < 10000)) {
                            //comer fantasma
                            String fantasma = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());
                            int[] ans = morir(fantasma, matriz);
                            matriz[ans[0]][ans[1]] = fantasma;
                            Elemento ej = new Elemento(ans[0], ans[1], "0", 0);
                            Elemento e3 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e3);
                            actualizaciones.add(ej);
                            ac.setPosiciones(ans);
                            ac.setPlayer(fantasma);
                            ac.setActualizaciones(actualizaciones);

                        } else {
                            temp = 0;
                            String data = matriz[j.getX()][j.getY() + 1];
                            int tvid=sacarVidas(data,vidas,idsala);
                            
                            
                             int[] ans = morir(data, matriz);
                            if (tvid>0){
                               
                            matriz[ans[0]][ans[1]] = data;
                            //crear el pacman
                            Elemento je = new Elemento(ans[0], ans[1], data, j.getMem() - 1);
                             actualizaciones.add(je);
                             
                            }
                            ac.setPosiciones(ans);
                            ac.setPlayer(data);
                            System.out.println("hoola"+matriz[j.getX()][j.getY()]);                            
                            matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                            matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                            Elemento e1 = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], temp);
                            Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                            actualizaciones.add(e1);
                            actualizaciones.add(e2);
                            ac.setActualizaciones(actualizaciones);
                        }
                    }

                }
            }
        }
//        for(int fil=0;fil<25;fil++){
//                for(int cc=0;cc<36;cc++){
//                    System.out.print(matriz[fil][cc]);
//                    
//           }
//            System.out.println("");
//        
//        }
//         System.out.println("");
        
        salasMatrices.get(idsala).setMatriz(matriz);
        salasMatrices.get(idsala).setPuntos(puntos);
        services.setSalaMatrices(salasMatrices);
        return ac;
    }

    @Override
    public int[] morir(String data, String[][] matriz) {
        boolean flag = true;
        int myposx = 1, myposy = 1;
        int[] ans = new int[2];
        if (data.equals("A")) {
            myposx = 23;
            myposy = 1;
        } else if (data.equals("B")) {
            myposx = 1;
            myposy = 1;
        } else if (data.equals("D")) {
            myposx = 1;
            myposy = 34;
        } else if (data.equals("C")) {
            myposx = 23;
            myposy = 34;

        } else if (data.equals("a")) {
            myposx = 13;
            myposy = 15;
            flag = false;
        } else if (data.equals("b")) {
            myposx = 18;
            myposy = 16;
            flag = false;
        } else if (data.equals("c")) {
            myposx = 17;
            myposy = 18;
            flag = false;
        } else if (data.equals("d")) {
            myposx = 12;
            myposy = 17;
            flag = false;
        }
        if (flag) {
            if (!matriz[myposx][myposy].equals("0")) {
                for (int i = 0; i < 24; i++) {
                    for (int col = 0; col < 35; col++) {
                        if (matriz[i][col].equals("0")) {
                            myposx = i;
                            myposy = col;
                            i = 25;
                            break;
                        }
                    }
                }
            }
        } else if (!matriz[myposx][myposy].equals("0")) {
            for (int i = 14; i < 18; i++) {
                for (int col = 16; col < 20; col++) {
                    if (matriz[i][col].equals("0")) {
                        myposx = i;
                        myposy = col;
                        i = 21;
                        break;
                    }
                }
            }
        }
        ans[0] = myposx;
        ans[1] = myposy;
        return ans;
    }

    @Override
    public void guardarTiempo(Actualizacion ac, Sala s) {
        ArrayList<Long> tiemposComibles = s.getTiemposComibles();
        long inicio = System.currentTimeMillis();
        tiemposComibles.add(inicio);
        ac.setComibles(true);
        s.setTiemposComibles(tiemposComibles);

    }
    
    public int sacarVidas(String data,int[] vidas,int sala){
        int tvid=0;
        if (data.equals("A")){
            vidas[0] = vidas[0] - 1;
            tvid = vidas[0];
        } else if (data.equals("B")) {
            vidas[1] = vidas[1] - 1;
            tvid = vidas[1];
        } else if (data.equals("C")) {
            vidas[2] = vidas[2] - 1;
            tvid = vidas[2];
        } else if (data.equals("D")) {
            vidas[3] = vidas[3] - 1;
            tvid = vidas[3];
        }
        salasMatrices.get(sala).setVidas(vidas);
        return tvid;
    }
}
