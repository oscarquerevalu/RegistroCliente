/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package business_logic;

import com.google.gson.Gson;
import data_accesss.DACliente;
import entity.Cliente;
import entity.decodificapersona;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import util.Propiedades;

/**
 *
 * @author tauro
 */
public class BLCliente {
    
    Gson gson =  new Gson();

    public BLCliente() {
    }
    
    public Cliente registrar(Propiedades propiedades,Cliente cliente){                   
                        Object condiciones[]={
                            cliente.getNro_documento(),
                            cliente.getNombre(),
                            cliente.getDireccion(),
                            cliente.getProvincia(),
                            cliente.getPais(),
                            cliente.getCodpais(),
                            cliente.getEmail(),
                            cliente.getTelefono(),
                            cliente.getPromociones(),
                            cliente.getEstado(),
                            cliente.getHabido()};
        Cliente registro=new DACliente(propiedades.getCadena_conexion()).Insertar("{call sp_consulta_documento_existente(?,?,?,?,?,?,?,?,?,?,?)}", condiciones);
        return registro;
    }
    
    public boolean crearSp(Propiedades propiedades){
        boolean seguir=false;
        String sp= "CREATE PROCEDURE [dbo].[sp_consulta_documento_existente]	\n" +
        "@p_nro_documento varchar(15),\n" +
        "@p_nombre_razon_social varchar(250),\n" +
        "@p_direccion varchar(250),\n" +
        "@p_provincia varchar(250),\n" +
        "@p_pais varchar(250),\n" +
        "@p_codigopais varchar(250),\n" +
        "@p_email varchar(250),\n" +
        "@p_phone varchar(250),\n" +
        "@p_promociones varchar(250),\n" +
        "@p_estado varchar(250),\n" +
        "@p_condicion varchar(250)\n" +
        "AS\n" +
        "BEGIN\n" +
        "	declare @text varchar(300);\n" +
        "	declare @text_3 varchar(300);\n" +
        "	declare @existe int;\n" +
        "	declare @text_14 varchar(300);\n" +
        "	declare @text_15 varchar(300);\n" +
        "	declare @text_16 int;\n" +
        "	declare @text_17 int;\n" +
        "       declare @cant int;\n" +
        "	declare @text_18 varchar(300);\n" +
        "\n" +
        "	if len(@p_nro_documento)=0\n" +
        "	begin\n" +
        "		RAISERROR ('Ingresar Nro Documento',16,1);  \n" +
        "	end\n" +
        "       SELECT @existe=COUNT(1) FROM CLIENTES WHERE CIF = @p_nro_documento;\n" +
        "	set @text=replace(@p_nombre_razon_social,'''','');\n" +
        "	set @text_3=@text;\n" +
        "	set @p_direccion=replace(@p_direccion,'''','');\n" +
        "	set @text_14=case when len(ltrim(rtrim(@p_nro_documento)))>8 then 'sunat' else 'reniec' end;\n" +
        "	set @text_15='192';\n" +
        "       Set @Cant=(Select isnull(max(codcliente), 0) From CLIENTES);\n" +
        "	Set @text_18=  Cast(Cast(@Cant As Int)+1 As VarChar(8));\n" +
        "	if @existe=0\n" +
        "	begin\n" +
        "           INSERT CLIENTES (\n" +
        "		CODCLIENTE, CODCONTABLE, NOMBRECOMERCIAL, DIRECCION1, PROVINCIA, PAIS, TELEFONO1, E_MAIL, \n" +
        "		CANTPORTESPAG, NUMDIASENTREGA, RIESGOCONCEDIDO, TIPO, CODVENDEDOR, DIAPAGO1, \n" +
        "		DIAPAGO2, FECHAMODIFICADO, CIF, \n" +
        "		NOMBRECLIENTE, REGIMFACT, ESCLIENTEDELGRUPO, Codpais, NIF20, RECIBIRINFORMACION)\n" +
        "		VALUES (\n" +
        "		@text_18, @text_15+@text_18 ,@text ,@p_direccion ,@p_provincia ,@p_pais ,@p_phone ,@p_email, \n" +
        "		0,0, 0, 0, 0, 0,\n" +
        "		0, CURRENT_TIMESTAMP, @p_nro_documento, \n" +
        "		@text_3,case when @text_14='sunat' then '6' else '1' end, 0, @p_codigopais, @p_nro_documento, 0); \n" +
        "       end" +
        "	else\n" +
        "	begin\n" +
        "           UPDATE CLIENTES SET e_mail = @p_email, \n" +  
        "                               NOMBRECOMERCIAL = @p_nombre_razon_social, \n" +
        "                               NOMBRECLIENTE = @p_nombre_razon_social, \n" +
        "                               RECIBIRINFORMACION = 0, \n" +
        "                               FECHAMODIFICADO = GETDATE() \n" +
        "           WHERE CIF = @p_nro_documento;\n" +
        "	end\n" +
        "\n" +
        "END";
        Object Condiciones[]={};
        
        try{
            String respuesta1=new DACliente(propiedades.getCadena_conexion()).Actualizar(
                    "if exists (select * from sysobjects where id=OBJECT_ID(N'[dbo].[sp_consulta_documento_existente]') and OBJECTPROPERTY(id,N'IsProcedure')=1)\n" +
                    "begin\n" +
                    "	drop procedure sp_consulta_documento_existente;\n" +
                    "end ",Condiciones);
            String respuesta2=new DACliente(propiedades.getCadena_conexion()).Actualizar(sp,Condiciones);
            seguir=true;
        }catch(Exception e){
            
        }
        return seguir;
    }
    
    public Cliente listardocumento(Propiedades propiedades,String tipo_documento, String nro_documento){
        //Object condiciones[]={nro_documento};
        //DACliente da= new DACliente(propiedades.getCadena_conexion());
        //System.out.println(propiedades.getCadena_conexion());
        //ArrayList<Cliente> lista=new ArrayList<Cliente>();
        boolean encontrado=false;
        decodificapersona abonadodec=new decodificapersona ();
        Cliente cliente=new Cliente();
        /*lista = da.listar(
                "select a.CIF nro_documento,a.NOMBRECLIENTE nombre,a.DIRECCION1 direccion,a.PROVINCIA provincia,a.PAIS pais,a.CODPAIS codpais,\n" +
                "a.TELEFONO1 telefono,a.E_MAIL email,b.CLI_MAILPROMO promociones,b.CL_ESTADOSUNAT estado,b.HABIDO habido\n" +
                "from CLIENTES a inner join\n" +
                "CLIENTESCAMPOSLIBRES b on a.CODCLIENTE=b.CODCLIENTE\n" +
                "where a.CIF=?", condiciones);*/
        /*if(lista.isEmpty()){*/
            System.out.println("vacio");
            System.out.println(tipo_documento);
            try {
                URL url;
                if(tipo_documento.equals("DNI")){
                    url = new URL("https://dniruc.apisperu.com/api/v1/dni/"+nro_documento+"?token=XXXX");

                }else{
                    url = new URL("https://dniruc.apisperu.com/api/v1/ruc/"+nro_documento+"?token=XXXX");
                }
                HttpURLConnection connection = (HttpURLConnection)url.openConnection();
                connection.setRequestMethod("GET");
                connection.connect();

                int responseCode = connection.getResponseCode();
                InputStream inputStream;
                if (200 <= responseCode && responseCode <= 299) {
                    inputStream = connection.getInputStream();
                } else {
                    inputStream = connection.getErrorStream();
                }

                BufferedReader in = new BufferedReader(
                    new InputStreamReader(
                        inputStream));

                StringBuilder response = new StringBuilder();
                String currentLine;

                while ((currentLine = in.readLine()) != null) 
                    response.append(currentLine);

                in.close();

                abonadodec= gson.fromJson(response.toString(),abonadodec.getClass()); 

                System.out.println(response.toString());
                if((!abonadodec.getDni().equals(""))||(!abonadodec.getRuc().equals(""))){
                    encontrado=true;
                }else{
                    encontrado=false;
                }
                encontrado=true;
                cliente.setPais("PERÚ");
                cliente.setCodpais("PE");
                
                cliente.setDireccion(abonadodec.getDireccion());
                cliente.setEmail("");
                cliente.setEstado(abonadodec.getEstado());
                cliente.setHabido(abonadodec.getCondicion());
                cliente.setNombre((abonadodec.getNombres()+" "+abonadodec.getApellidoPaterno()+" "+abonadodec.getApellidoMaterno()+" "+abonadodec.getRazonSocial()).trim());
                cliente.setNro_documento(abonadodec.getDni()+abonadodec.getRuc());
                cliente.setPromociones("NO");
                cliente.setProvincia(abonadodec.getProvincia());
                cliente.setTelefono("");
            } catch (Exception ex) {
                encontrado=false;
            }
        /*}else{
            cliente=lista.get(0);
        }*/
        return cliente;
    }
}
