/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.services;

import edu.eci.arsw.pacm.model.LeerFichero;
import edu.eci.arsw.pacm.model.Player;
import edu.eci.arsw.pacm.model.Teams;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import org.springframework.stereotype.Service;

/**
 *
 * @author User
 */
@Service
public class PacmServicesStub implements PacmServices{

    private ConcurrentHashMap<Integer, Teams> salasData=new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, String> identificadores=new ConcurrentHashMap<>();
    private String[][] mat;
    private int salas=0;
    

    public PacmServicesStub(){
        salasData.put(0,new Teams());
    }
    
    @Override
    public void registrarJugadorAtacante(int salanum, Player p) throws ServicesException{
        if (!salasData.containsKey(salanum)){
            salasData.put(salanum,new Teams());
            
        }
            CopyOnWriteArrayList tmp = salasData.get(salanum).getAtacantes();
            int a = 64+tmp.size();
            identificadores.put(p.getNombre(),Character.toString ((char) a));
            tmp.add(p);
            salasData.get(salanum).setAtacantes(tmp);
        
    }

    @Override
    public void registrarJugadorProtector(int salanum, Player p) throws ServicesException{
        if (!salasData.containsKey(salanum)){
            throw new ServicesException("Sala "+salanum+" no ha sido registrada en el servidor.");
        }
        else{
            CopyOnWriteArrayList tmp = salasData.get(salanum).getProtectores();
            int a = 96+tmp.size();
            identificadores.put(p.getNombre(),Character.toString ((char) a));
            tmp.add(p);
            salasData.get(salanum).setProtectores(tmp);
        }
    }

    @Override
    public List<Player> getAtacantes(int salanum) throws ServicesException {
        return salasData.get(salanum).getAtacantes();
    }

    @Override
    public List<Player> getProtectores(int salanum) throws ServicesException {
        return salasData.get(salanum).getProtectores();
    }

    @Override
    public String[][] getTablero() throws ServicesException {
        if (mat==null){
            mat=LeerFichero.muestraContenido();
        }
        return mat;

    }
    @Override
    public ConcurrentHashMap<String, String> getIdentificadores() throws ServicesException {
        return identificadores;
    }

    @Override
    public int getSalaDisponible() throws ServicesException {
        return salas;
    }

    @Override
    public void setSalaDisponible(int sala) throws ServicesException {
        salasData.put(sala,new Teams());
        this.salas=sala;
    }
    
    
}
