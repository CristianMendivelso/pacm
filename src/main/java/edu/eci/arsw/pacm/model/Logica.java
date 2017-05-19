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

    static boolean comibles = false;

    @Autowired
    PacmServices services;
    private final ConcurrentHashMap<Integer, Sala> salasMatrices = new ConcurrentHashMap<>();
    ArrayList<Elemento> actualizaciones = new ArrayList();
    ArrayList<HiloComerFantasmas> hilosComibles = new ArrayList();

    public Logica() {
    }

    @Override
    public void contabilizarTiempo(Actualizacion ac) {
        if (comibles) {
            hilosComibles.get(0).terminate();
            hilosComibles.clear();
            HiloComerFantasmas hcf = new HiloComerFantasmas();
            hilosComibles.add(hcf);
            hcf.start();
        } else {
            comibles = true;
            ac.setComibles(comibles);
            HiloComerFantasmas hcf = new HiloComerFantasmas();
            hilosComibles.add(hcf);
            hcf.start();
        }

    }

    public Actualizacion mover(int idsala, Jugador j) {
        Actualizacion ac = new Actualizacion();

        if (!salasMatrices.containsKey(idsala)) {
            Sala sala = new Sala(LeerFichero.muestraContenido(), LeerFichero.puntos);
            salasMatrices.put(idsala, sala);
        }
        ArrayList<Elemento> actualizaciones = new ArrayList();
        //Si es pacman
        String[][] matriz = salasMatrices.get(idsala).getMatriz();

        int puntos = salasMatrices.get(idsala).getPuntos();
        ac.setPuntos(puntos);
        ac.setComibles(comibles);
        if (matriz[j.getX()][j.getY()].equals("B") || matriz[j.getX()][j.getY()].equals("A") || matriz[j.getX()][j.getY()].equals("C") || matriz[j.getX()][j.getY()].equals("D")) {
            if (j.getK() == 40) {
                if (!(matriz[j.getX() + 1][j.getY()]).equals("3") && !(matriz[j.getX() + 1][j.getY()]).equals("A") && !(matriz[j.getX() + 1][j.getY()]).equals("B") && !(matriz[j.getX() + 1][j.getY()]).equals("C") && !(matriz[j.getX() + 1][j.getY()]).equals("D")) {
                    if ((matriz[j.getX() + 1][j.getY()]).equals("a") || (matriz[j.getX() + 1][j.getY()]).equals("b") || (matriz[j.getX() + 1][j.getY()]).equals("c") || (matriz[j.getX() + 1][j.getY()]).equals("d")) {
                        //mimra si se puede comer al fantasma o no
                        
                        String data= matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        //crear el pacman
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(je);
                        ac.setPosiciones(ans);
                        ac.setPlayer(data);
                        ac.setActualizaciones(actualizaciones);

                        //comer puntos
                    } else if ((matriz[j.getX() + 1][j.getY()]).equals("1")) {
                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], 0);
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
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], 0);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        contabilizarTiempo(ac);

                        //no comer nada
                    } else {
                        matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], 0);
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
                        String data= matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        //crear el pacman
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(je);
                        
                        ac.setPosiciones(ans);
                        ac.setPlayer(data);
                        ac.setActualizaciones(actualizaciones);

                        //comer puntos
                    } else if ((matriz[j.getX()][j.getY() - 1]).equals("1")) {
                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], 0);
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
                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], 0);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        contabilizarTiempo(ac);
                        
                        
                        //no comer nada
                    } else {
                        matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], 0);
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
                        String data= matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        //crear el pacman
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        
                        Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(je);
                        ac.setPosiciones(ans);
                        ac.setPlayer(data);
                        ac.setActualizaciones(actualizaciones);

                        //comer puntos
                    } else if ((matriz[j.getX() - 1][j.getY()]).equals("1")) {
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], 0);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);

                        //comper puntos grandes
                    } else if ((matriz[j.getX() - 1][j.getY()]).equals("1")) {
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], 0);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        contabilizarTiempo(ac);
                        

                        //no comer nada
                    } else {
                        matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], 0);
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
                        
                        //mi jugador
                        
                        String data= matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        //crear el pacman
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        ac.setPosiciones(ans);
                        Elemento e = new Elemento(j.getX(), j.getY(), "0", 0);
                        ac.setPlayer(data);
                        actualizaciones.add(e);
                        actualizaciones.add(je);
                        ac.setActualizaciones(actualizaciones);

                        //comer puntos
                    } else if ((matriz[j.getX()][j.getY() + 1]).equals("1")) {
                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], 0);
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
                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], 0);
                        Elemento e2 = new Elemento(j.getX(), j.getY(), "0", 0);
                        actualizaciones.add(e);
                        actualizaciones.add(e2);
                        ac.setActualizaciones(actualizaciones);
                        puntos -= 1;
                        ac.setPuntos(puntos);
                        ac.setCambioDePuntos(true);
                        contabilizarTiempo(ac);
                        

                        //no comer nada
                    } else {
                        matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                        matriz[j.getX()][j.getY()] = "0";
                        Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], 0);
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
                    } catch (Exception e) {
                        temp = 0;
                        String data= matriz[j.getX() + 1][j.getY()];
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        actualizaciones.add(je);
                        ac.setPlayer(data);
                        ac.setPosiciones(ans);
                    }
                    matriz[j.getX() + 1][j.getY()] = matriz[j.getX()][j.getY()];
                    matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                    Elemento e = new Elemento(j.getX() + 1, j.getY(), matriz[j.getX() + 1][j.getY()], temp);
                    Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                    actualizaciones.add(e);
                    actualizaciones.add(e2);
                    
                    ac.setActualizaciones(actualizaciones);
                }
            } else if (j.getK() == 37) {
                if (!(matriz[j.getX()][j.getY() - 1]).equals("3") && !(matriz[j.getX()][j.getY() - 1]).equals("a") && !(matriz[j.getX()][j.getY() - 1]).equals("b") && !(matriz[j.getX()][j.getY() - 1]).equals("c") && !(matriz[j.getX()][j.getY() - 1]).equals("d")) {
                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX()][j.getY() - 1]);
                    } catch (Exception e) {
                        temp = 0;
                        String data= matriz[j.getX()][j.getY() - 1];
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        actualizaciones.add(je);
                        ac.setPosiciones(ans);
                        ac.setPlayer(data);
                    }
                    matriz[j.getX()][j.getY() - 1] = matriz[j.getX()][j.getY()];
                    matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                    Elemento e = new Elemento(j.getX(), j.getY() - 1, matriz[j.getX()][j.getY() - 1], temp);
                    Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                    actualizaciones.add(e);
                    actualizaciones.add(e2);
                    ac.setActualizaciones(actualizaciones);
                }
            } else if (j.getK() == 38) {
                if (!(matriz[j.getX() - 1][j.getY()]).equals("3") && !(matriz[j.getX() - 1][j.getY()]).equals("a") && !(matriz[j.getX() - 1][j.getY()]).equals("b") && !(matriz[j.getX() - 1][j.getY()]).equals("c") && !(matriz[j.getX() - 1][j.getY()]).equals("d")) {
                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX() - 1][j.getY()]);
                    } catch (Exception e) {
                        temp = 0;
                        String data= matriz[j.getX() - 1][j.getY()];
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        actualizaciones.add(je);
                        ac.setPosiciones(ans);
                        ac.setPlayer(data);
                    }
                    matriz[j.getX() - 1][j.getY()] = matriz[j.getX()][j.getY()];
                    matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                    Elemento e = new Elemento(j.getX() - 1, j.getY(), matriz[j.getX() - 1][j.getY()], temp);
                    Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                    actualizaciones.add(e);
                    actualizaciones.add(e2);
                    ac.setActualizaciones(actualizaciones);
                }
            } else if (j.getK() == 39) {
                if (!(matriz[j.getX()][j.getY() + 1]).equals("3") && !(matriz[j.getX()][j.getY() + 1]).equals("a") && !(matriz[j.getX()][j.getY() + 1]).equals("b") && !(matriz[j.getX()][j.getY() + 1]).equals("c") && !(matriz[j.getX()][j.getY() + 1]).equals("d")) {
                    int temp;
                    try {
                        temp = Integer.parseInt(matriz[j.getX()][j.getY() + 1]);
                    } catch (Exception e) {
                        temp = 0;
                        String data= matriz[j.getX()][j.getY() + 1];
                        int[] ans=morir(data, matriz);
                        matriz[ans[0]][ans[1]]=data;
                        Elemento je = new Elemento(ans[0],ans[1],data,0);
                        actualizaciones.add(je);
                        ac.setPlayer(data);
                        ac.setPosiciones(ans);
                    }
                    matriz[j.getX()][j.getY() + 1] = matriz[j.getX()][j.getY()];
                    matriz[j.getX()][j.getY()] = String.valueOf(j.getMem());

                    Elemento e = new Elemento(j.getX(), j.getY() + 1, matriz[j.getX()][j.getY() + 1], temp);
                    Elemento e2 = new Elemento(j.getX(), j.getY(), String.valueOf(j.getMem()), 0);
                    actualizaciones.add(e);
                    actualizaciones.add(e2);
                    ac.setActualizaciones(actualizaciones);
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
        
        
        salasMatrices.get(idsala).setMatriz(matriz);
        salasMatrices.get(idsala).setPuntos(puntos);
        
        
        return ac;
    }

    @Override
    public int[] morir(String data,String[][] matriz) {
        int myposx=1,myposy=1;
        int [] ans=new int[2];
        if (data.equals("A")) {
            myposx = 23;
            myposy = 1;
        } else if (data.equals('B')) {
            myposx = 1;
            myposy = 1;
        } else if (data.equals('D')) {
            myposx = 1;
            myposy = 34;
        } else if (data.equals('C')) {
            myposx = 23;
            myposy = 34;
        }

        if (!matriz[myposx][myposy].equals("0")) {
            for (int i = 0; i < 24; i++) {
                for (int col = 0; col < 35; col++) {
                    if (matriz[i][col].equals("0")) {
                        myposx=i;
                        myposy=col;
                        i = 25;
                        break;
                    }
                }
            }
        }
        ans[0]=myposx;
        ans[1]=myposy;
        return ans;
    }
}
