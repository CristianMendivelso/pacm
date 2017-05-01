package edu.eci.arsw.pacm.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeerFichero {
    
    public static int puntos;
    
    public static String[][] muestraContenido(){
        String[][] mat=null;
        FileReader f= null;
        
        try {
            
            String cadena;
            f = new FileReader("file.txt");
            BufferedReader b = new BufferedReader(f);
            int cont=0;
            cadena = b.readLine();
                String[] temp = cadena.split(" ");
                
                int filas=Integer.valueOf(temp[0]);
                int col=Integer.valueOf(temp[1]);
                puntos=Integer.valueOf(temp[2]);
                mat=new String[filas][col];
                
                for (int i=0;i<filas;i++){
                    cadena = b.readLine();
                    temp = cadena.split("\t");
                    for (int j=0;j<temp.length;j++){
                        mat[i][j]=temp[j];
                    }
                }
                b.close();

            } catch (IOException ex) {   
                Logger.getLogger(LeerFichero.class.getName()).log(Level.SEVERE, null, ex);
            }   
            
        return mat;
    }

   
}