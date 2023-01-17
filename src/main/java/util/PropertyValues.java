/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Date;
import java.util.Properties;

/**
 *
 * @author tauro
 */
public class PropertyValues {
	String result = "";
	InputStream inputStream;
 
	public Propiedades getPropValues() throws IOException {
            Propiedades propiedades=new Propiedades();
            try {
                Properties prop = new Properties();
		String propFileName = "consulta.properties";
 
		inputStream = getClass().getClassLoader().getResourceAsStream(propFileName);
 
                System.out.println(getClass().getClassLoader().toString());
		if (inputStream != null) {
                    prop.load(inputStream);
		} else {
                    throw new FileNotFoundException("property file '" + propFileName + "' not found in the classpath");
		}
 
		Date time = new Date(System.currentTimeMillis());
                // get the property value and print it out
		String cadena_conexion = prop.getProperty("cadena_conexion");
                
		String correo = prop.getProperty("correo");
		String aplicativo = prop.getProperty("aplicativo");
                
                propiedades=new Propiedades(cadena_conexion,correo,aplicativo);
                
                FileOutputStream out = new FileOutputStream("consulta.properties");
                prop.setProperty("cadena_conexion", "america");
                prop.store(out, null);
                out.close();
            } catch (Exception e) {
                System.out.println("Exception: " + e);
            } finally {
                inputStream.close();
            }
            return propiedades;
	}
}
