/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

/**
 *
 * @author tauro
 */
public class Util {
    
    public Propiedades leerPropiedades(){
        Propiedades registro=new Propiedades();
        Properties prop = new Properties();
        try (InputStream inputStream = new FileInputStream("C:\\Config\\consulta.properties")) {
            prop.load(inputStream);
            String cadena_conexion=prop.getProperty("cadena_conexion");
            String correo=prop.getProperty("correo");
            String aplicativo=prop.getProperty("aplicativo");
            System.out.println(cadena_conexion);
            registro=new Propiedades(cadena_conexion,correo,aplicativo);
        } catch (IOException io) {
            io.printStackTrace();
        }
        return registro;
    }
    
    public void escribirPropiedades(Propiedades registro){
        Properties prop = new Properties();
        try (OutputStream output = new FileOutputStream("C:\\Config\\consulta.properties")) {
            prop.setProperty("cadena_conexion", registro.getCadena_conexion());
            prop.setProperty("correo", registro.getCorreo());
            prop.setProperty("aplicativo", registro.getAplicativo());
            prop.store(output, null);
        } catch (IOException io) {
            io.printStackTrace();
        }
    }
}
