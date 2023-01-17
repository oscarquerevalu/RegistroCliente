/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package data_accesss;

import com.google.gson.Gson;
import conexion.OperacionSqlServer;
import entity.Cliente;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import util.RespuestaJSON;

/**
 *
 * @author tauro
 */
public class DACliente {
    OperacionSqlServer operacion3;
    String jsonRespuesta="";
    Gson gson =  new Gson();
    int total;
    
    public DACliente(String cadena_conexion){
        operacion3=new OperacionSqlServer(cadena_conexion);	
    }
    
    public String Actualizar(String consultaObjetos,Object condiciones[]){
        try {
            operacion3.EjecutarConsulta(consultaObjetos, condiciones);
            jsonRespuesta=gson.toJson(new RespuestaJSON(true,null,null));
        } catch (SQLException ex) {
            jsonRespuesta=gson.toJson(new RespuestaJSON(false,null,ex.getMessage()));
        }
            operacion3.cerrarConexion();
        return jsonRespuesta;
    }
    
    public String Eliminar(String consultaObjetos,Object condiciones[]){
        try {
            operacion3.EjecutarConsulta(consultaObjetos, condiciones);
            jsonRespuesta=gson.toJson(new RespuestaJSON(true,null,null));
        } catch (SQLException ex) {
            jsonRespuesta=gson.toJson(new RespuestaJSON(false,null,ex.getMessage()));
        }
        operacion3.cerrarConexion();
        return jsonRespuesta;
    }
    
    public Cliente Insertar(String consultaObjetos,Object condiciones[]){
            Cliente cliente= new Cliente();
        try {
            ResultSet rsCombo=operacion3.EjecutarConsulta(consultaObjetos, condiciones);
            while (rsCombo!=null&&rsCombo.next()){
            cliente=
            new Cliente(
                                rsCombo.getString("nro_documento"),
                                rsCombo.getString("nombre"),
                                rsCombo.getString("direccion"),
                                rsCombo.getString("provincia"),
                                rsCombo.getString("pais"),
                                rsCombo.getString("codpais"),
                                rsCombo.getString("telefono"),
                                rsCombo.getString("email"),
                                rsCombo.getString("promociones"),
                                rsCombo.getString("estado"),
                                rsCombo.getString("habido"))                  
                        ;
            }
            if(rsCombo!=null){rsCombo.close();}
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            cliente=null;
        }
        operacion3.cerrarConexion();
        return cliente;
    }
    
    public ArrayList<Cliente> listar(String consultaObjetos,Object condiciones[]){
        ArrayList<Cliente> lista=new ArrayList<>();
        try {
            ResultSet rsCombo = operacion3.EjecutarConsulta(consultaObjetos,condiciones);
            while (rsCombo!=null&&rsCombo.next()){
                lista.add(
                        new Cliente(
                                rsCombo.getString("nro_documento"),
                                rsCombo.getString("nombre"),
                                rsCombo.getString("direccion"),
                                rsCombo.getString("provincia"),
                                rsCombo.getString("pais"),
                                rsCombo.getString("codpais"),
                                rsCombo.getString("telefono"),
                                rsCombo.getString("email"),
                                rsCombo.getString("promociones"),
                                rsCombo.getString("estado"),
                                rsCombo.getString("habido"))                    
                        );
            }
            if(rsCombo!=null){rsCombo.close();}
            operacion3.cerrarConexion();
            return lista;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            operacion3.cerrarConexion();
            return lista;
        }
    }
}
