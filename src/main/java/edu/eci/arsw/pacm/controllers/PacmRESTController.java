/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.pacm.controllers;

import edu.eci.arsw.pacm.model.Player;
import edu.eci.arsw.pacm.services.PacmServices;
import edu.eci.arsw.pacm.services.ServicesException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 *
 * @author User
 */
@RestController
@RequestMapping(value = "/salas")
public class PacmRESTController {
    
    @Autowired
    PacmServices services;
    
    @Autowired
    SimpMessagingTemplate msgt;
    
    @RequestMapping(path = "/{salanum}/atacantes",method = RequestMethod.PUT)
    public ResponseEntity<?> agregarAtacante(@PathVariable(name = "salanum") String salanum,@RequestBody Player p) throws ServicesException {
        synchronized(services){
        try {
            if (services.getAtacantes(Integer.parseInt(salanum)).size()<4){
                services.registrarJugadorAtacante(Integer.parseInt(salanum), p);
                ArrayList<List<Player>> temp=new ArrayList<>();
                List <Player >atacantes=services.getAtacantes(Integer.parseInt(salanum));
                 List <Player > protectores=services.getProtectores(Integer.parseInt(salanum));
                temp.add(atacantes);
                temp.add(protectores);
                msgt.convertAndSend("/topic/mostrarJugadores",temp);
                if (protectores.size()==4 && atacantes.size()==4){
                    msgt.convertAndSend("/topic/Jugar",p.getNombre());
                }
            }
            else{
                throw new ServicesException("No se puede elegir el equipo atacante porque está lleno");
            }
            
        } catch (ServicesException ex) {
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    
    @RequestMapping(path = "/{salanum}/protectores",method = RequestMethod.PUT)
    public ResponseEntity<?> agregarProtector(@PathVariable(name = "salanum") String salanum,@RequestBody Player p) throws ServicesException {
        synchronized(this){
        try {
            if (services.getProtectores(Integer.parseInt(salanum)).size()<4){
                services.registrarJugadorProtector(Integer.parseInt(salanum), p);
                ArrayList<List<Player>> temp=new ArrayList<>();
                List <Player >atacantes=services.getAtacantes(Integer.parseInt(salanum));
                 List <Player > protectores=services.getProtectores(Integer.parseInt(salanum));
                temp.add(atacantes);
                temp.add(protectores);
                msgt.convertAndSend("/topic/mostrarJugadores",temp);
                if (protectores.size()==4 && atacantes.size()==4){
                    msgt.convertAndSend("/topic/Jugar",p.getNombre());
                }
            }
             else{
                throw new ServicesException("No se puede elegir el equipo protector porque está lleno");
            }
        } catch (ServicesException ex) {
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.BAD_REQUEST);
        }
            return new ResponseEntity<>(HttpStatus.CREATED);
        }
    }
    
    
    @RequestMapping(path = "/{salanum}/atacantes",method = RequestMethod.GET)
    public ResponseEntity<?> getAtacantes(@PathVariable(name = "salanum") String salanum) {
        
        try {
            return new ResponseEntity<>(services.getAtacantes(Integer.parseInt(salanum)),HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
        } catch (NumberFormatException ex){
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("/{salanum}/ must be an integer value.",HttpStatus.BAD_REQUEST);
        }
    }
    
    
    @RequestMapping(path = "/{salanum}/protectores",method = RequestMethod.GET)
    public ResponseEntity<?> getProtectores(@PathVariable(name = "salanum") String salanum) {
        
        try {
            return new ResponseEntity<>(services.getProtectores(Integer.parseInt(salanum)),HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
        } catch (NumberFormatException ex){
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("/{salanum}/ must be an integer value.",HttpStatus.BAD_REQUEST);
        }
    }
    
    @RequestMapping(path = "/tablero",method = RequestMethod.GET)
    public ResponseEntity<?> getTablero() {
        
        try {
            ArrayList<Object> informacion = new ArrayList();
            informacion.add(services.getTablero());
            informacion.add(services.getIdentificadores());
            return new ResponseEntity<>(informacion,HttpStatus.ACCEPTED);
        } catch (ServicesException ex) {
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>(ex.getLocalizedMessage(),HttpStatus.NOT_FOUND);
        } catch (NumberFormatException ex){
            Logger.getLogger(PacmRESTController.class.getName()).log(Level.SEVERE, null, ex);
            return new ResponseEntity<>("/{salanum}/ must be an integer value.",HttpStatus.BAD_REQUEST);
        }
    }
    
    
}
