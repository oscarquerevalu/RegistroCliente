/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entity;

/**
 *
 * @author tauro
 */
public class Cliente {
    String nro_documento="";
    String nombre="";
    String direccion="";
    String provincia="";
    String pais="PERÚ";
    String codpais="PE";
    String telefono="";
    String email="";
    String promociones="NO";
    String estado="";
    String habido="";

    public Cliente() {
    }

    public Cliente(String nro_documento, String nombre, String direccion, String provincia, String pais, String codpais, String telefono, String email, String promociones, String estado, String habido) {
        this.nro_documento = nro_documento;
        this.nombre = nombre;
        this.direccion = direccion;
        this.provincia = provincia;
        this.pais = pais;
        this.codpais = codpais;
        this.telefono = telefono;
        this.email = email;
        this.promociones = promociones;
        this.estado = estado;
        this.habido = habido;
    }

    public String getNro_documento() {
        return nro_documento;
    }

    public void setNro_documento(String nro_documento) {
        this.nro_documento = nro_documento;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getProvincia() {
        return provincia;
    }

    public void setProvincia(String provincia) {
        this.provincia = provincia;
    }

    public String getPais() {
        return pais;
    }

    public void setPais(String pais) {
        this.pais = pais;
    }

    public String getCodpais() {
        return codpais;
    }

    public void setCodpais(String codpais) {
        this.codpais = codpais;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPromociones() {
        return promociones;
    }

    public void setPromociones(String promociones) {
        this.promociones = promociones;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getHabido() {
        return habido;
    }

    public void setHabido(String habido) {
        this.habido = habido;
    }

}
