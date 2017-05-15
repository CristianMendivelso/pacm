/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.model;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author User
 */
public class Teams {

    private CopyOnWriteArrayList<Player> protectores = new CopyOnWriteArrayList();
    private CopyOnWriteArrayList<Player> atacantes = new CopyOnWriteArrayList();
    private HashMap<String, String> identificadores = new HashMap<>();
    private CopyOnWriteArrayList<Info> informacion = new CopyOnWriteArrayList();
    
    public CopyOnWriteArrayList<Info> getInformacion() {
        return informacion;
    }

    public void setInformacion(Info informacion) {
        this.informacion.add(informacion);
    }
    
    public void setIdentificadores(String a, String b) {
        this.identificadores.put(a, b);
        Iterator it = identificadores.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry e = (Map.Entry) it.next();
            System.out.println(e.getKey() + " " + e.getValue());
        }
    }

    public Teams() {

    }
    
    
    public HashMap<String, String> getIdentificadores() {
        return identificadores;
    }

    public CopyOnWriteArrayList<Player> getProtectores() {
        return protectores;
    }

    public void setProtectores(CopyOnWriteArrayList<Player> protectores) {
        this.protectores = protectores;
    }

    public CopyOnWriteArrayList<Player> getAtacantes() {
        return atacantes;
    }

    public void setAtacantes(CopyOnWriteArrayList<Player> atacantes) {
        this.atacantes = atacantes;
    }

}
