/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

/**
 *
 * @author tauro
 */
public class Propiedades {
    String cadena_conexion;
    String correo;
    String aplicativo;

    public Propiedades() {
    }

    public Propiedades(String cadena_conexion, String correo, String aplicativo) {
        this.cadena_conexion = cadena_conexion;
        this.correo = correo;
        this.aplicativo = aplicativo;
    }

    public String getCadena_conexion() {
        return cadena_conexion;
    }

    public void setCadena_conexion(String cadena_conexion) {
        this.cadena_conexion = cadena_conexion;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getAplicativo() {
        return aplicativo;
    }

    public void setAplicativo(String aplicativo) {
        this.aplicativo = aplicativo;
    }
    
}
