package edu.eci.arsw.pacm.model;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class LeerFichero {
    
    public static String[][] muestraContenido(){
        String[][] mat= new String[25][36];
        FileReader f= null;
        try {
            
            String cadena;
            f = new FileReader("file.txt");
            BufferedReader b = new BufferedReader(f);
            int cont=0;
            while((cadena = b.readLine())!=null) {
                String[] temp = cadena.split("\t");
                for (int i=0;i<temp.length;i++){
                    mat[cont][i]=temp[i];
                }
                cont++;
            }   b.close();
            
        } catch (FileNotFoundException ex) {
            Logger.getLogger(LeerFichero.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(LeerFichero.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                f.close();
            } catch (IOException ex) {
                Logger.getLogger(LeerFichero.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        System.out.println(Arrays.toString(mat));
        return mat;
    }

   
}