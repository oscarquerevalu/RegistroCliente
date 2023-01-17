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
                            propiedades.getAplicativo().equals("FRONT REST")?"0":"1",cliente.getNro_documento(),cliente.getNombre(),cliente.getDireccion(),cliente.getProvincia(),
                            cliente.getPais(),cliente.getCodpais(),cliente.getEmail(),cliente.getTelefono(),cliente.getPromociones(),cliente.getEstado(),cliente.getHabido()};
        Cliente registro=new DACliente(propiedades.getCadena_conexion()).Insertar("{call sp_consulta_documento_existente(?,?,?,?,?,?,?,?,?,?,?,?)}", condiciones);
        return registro;
    }
    
    public boolean crearSp(Propiedades propiedades){
        boolean seguir=false;
        String sp="CREATE   PROCEDURE [dbo].[sp_consulta_documento_existente]	\n" +
        "@p_aplicacion int,--front rest = 0 front retail = 1\n" +
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
        "	SET NOCOUNT ON;\n" +
        "\n" +
        "	declare @existe int;\n" +
        "	\n" +
        "	declare @arg varchar(300);\n" +
        "	declare @arg2 varchar(300);\n" +
        "	\n" +
        "	declare @cod_cliente varchar(300);\n" +
        "	declare @text varchar(300);\n" +
        "	declare @text_2 varchar(2);\n" +
        "	declare @text_3 varchar(300);\n" +
        "\n" +
        "	declare @text_14 varchar(300);\n" +
        "	declare @text_15 varchar(300);\n" +
        "	declare @text_16 int;\n" +
        "	declare @text_17 int;\n" +
        "	declare @text_18 varchar(300);\n" +
        "	declare @text_19 varchar(300);\n" +
        "	declare @text_20 varchar(300);\n" +
        "\n" +
        "	\n" +
        "	declare @text_24 varchar(300);\n" +
        "	declare @text_25 varchar(300);\n" +
        "	\n" +
        "	declare @num3 int;\n" +
        "	declare @num4 int;\n" +
        "\n" +
        "	declare @num7 int;\n" +
        "\n" +
        "	if len(@p_nro_documento)=0\n" +
        "	begin\n" +
        "		RAISERROR ('Ingresar Nro Documento',16,1);  \n" +
        "	end\n" +
        "\n" +
        "	if len(@p_nombre_razon_social)=0\n" +
        "	begin\n" +
        "		RAISERROR ('No hay datos para busqueda realizada',16,1);  \n" +
        "	end\n" +
        "	\n" +
        "\n" +
        "	if @p_aplicacion=0\n" +
        "	begin\n" +
        "		if len(rtrim(ltrim(@p_nombre_razon_social)))>80\n" +
        "		begin\n" +
        "			RAISERROR ('El nombre del cliente debe contener máximo 80 caracteres',16,1);  \n" +
        "		end\n" +
        "	else\n" +
        "	begin\n" +
        "		if len(@p_nombre_razon_social)>255\n" +
        "		begin\n" +
        "			RAISERROR ('El nombre del cliente debe contener máximo 255 caractere',16,1); \n" +
        "		end\n" +
        "	end\n" +
        "	end\n" +
        "\n" +
        "	set @text_2='SI'\n" +
        "\n" +
        "	if len(@p_nombre_razon_social)<=80\n" +
        "	begin\n" +
        "		set @text_2='NO'\n" +
        "	end\n" +
        "	\n" +
        "	set @text=replace(@p_nombre_razon_social,'''','');\n" +
        "	set @text_3=@text;\n" +
        "	set @p_direccion=replace(@p_direccion,'''','');\n" +
        "	set @text_14=case when len(ltrim(rtrim(@p_nro_documento)))>8 then 'sunat' else 'reniec' end;\n" +
        "	set @text_15='000';\n" +
        "\n" +
        "	/*VALIDAR CORREO Y NUMERO TELEFONICO*/\n" +
        "\n" +
        "\n" +
        "	if @p_aplicacion=0--fron rest\n" +
        "	begin\n" +
        "		SELECT @text_15=cast(VALOR as int) FROM PARAMETROS WHERE TERMINAL = '$RAIZCONTA' AND CLAVE != 'PROV';\n" +
        "		Select @text_15=right(replicate('0',3) + @text_15,3);\n" +
        "	end\n" +
        "	else\n" +
        "	begin\n" +
        "		set @text_15='192';\n" +
        "	end\n" +
        "	\n" +
        "	SELECT @existe=COUNT(1) FROM CLIENTES WHERE DESCATALOGADO = 'F' AND CIF = @p_nro_documento;\n" +
        "\n" +
        "	if @existe=0\n" +
        "	begin\n" +
        "		if @p_aplicacion=0--fron rest\n" +
        "		begin\n" +
        "			SELECT @text_17=Maximo, @text_16=Minimo FROM REM_RANGOS WHERE Tipo = 2;\n" +
        "\n" +
        "			SELECT TOP 1 @text_18=CODCLIENTE FROM CLIENTES  WHERE DESCATALOGADO = 'F' AND CODCLIENTE BETWEEN @text_16 AND @text_17 ORDER BY CODCLIENTE DESC;\n" +
        "\n" +
        "			set @text_18=case when @text_18='' then '0' else @text_18 end;\n" +
        "			set @text_19=@text_18;\n" +
        "			set @text_20=GETDATE();\n" +
        "			set @num3=cast(@text_18 as int);\n" +
        "			set @num4=cast(@text_19 as int);\n" +
        "\n" +
        "			if @text_18=@text_17 and @text_19!='0'\n" +
        "			begin\n" +
        "				RAISERROR ('No se puede crear el cliente. Limite alcanzado',16,1); \n" +
        "			end\n" +
        "\n" +
        "			if @num4=0\n" +
        "			begin\n" +
        "				set @num4=cast(isnull(@text_16,'1') as int);\n" +
        "				set @text_18=isnull(@text_16,'1');\n" +
        "			end\n" +
        "			else\n" +
        "			begin\n" +
        "				set @text_18=cast((@num3+1) as varchar);\n" +
        "			end\n" +
        "\n" +
        "			set @text_18=right(replicate('0',7) + @text_18,7);\n" +
        "			set @num4=@num4+1;\n" +
        "\n" +
        "			UPDATE REM_RANGOS SET SIGUIENTECOD = @num4 WHERE Tipo = 2\n" +
        "\n" +
        "			INSERT CLIENTES (\n" +
        "			CODCLIENTE, CODCONTABLE, NOMBRECOMERCIAL, DIRECCION1, PROVINCIA, PAIS, TELEFONO1, E_MAIL, \n" +
        "			CANTPORTESPAG, NUMDIASENTREGA, RIESGOCONCEDIDO, TIPO, RECARGO, CODVENDEDOR, DIAPAGO1, \n" +
        "			DIAPAGO2, CODFORMAPAGO, DTO, IDTARIFAV, FECHAMODIFICADO, ESTADO, CIF, FACTSINIMPUESTOS, \n" +
        "			NOMBRECLIENTE, DESCATALOGADO, REGIMFACT, JURIDICA, ESCLIENTEDELGRUPO, Codpais)\n" +
        "			VALUES (\n" +
        "			@text_18,@text_15+@text_18,@text,@p_direccion,@p_provincia,@p_pais,@p_phone,@p_email, \n" +
        "			0,0, 0, 0, 'F', 0, 0,\n" +
        "			0, 0, 0, 0, CURRENT_TIMESTAMP, '0000000000', @p_nro_documento, 'F', \n" +
        "			@text_3, 'F',case when @text_14='sunat' then '6' else '1' end, 'F', 0, @p_codigopais );\n" +
        "\n" +
        "\n" +
        "			UPDATE CLIENTESCAMPOSLIBRES   \n" +
        "			SET \n" +
        "			TIPODOCCLIENTE=case when @text_14='sunat' then '6' else '1' end,  \n" +
        "			CL_ESTADOSUNAT = @p_estado, \n" +
        "			HABIDO = @p_condicion, \n" +
        "			CLI_MAILPROMO = @p_promociones, \n" +
        "			CL_NOM_EDITADO = @text_2\n" +
        "			WHERE CODCLIENTE  =@text_18;\n" +
        "\n" +
        "			/*insercion final*/\n" +
        "			if @p_aplicacion=0\n" +
        "			begin\n" +
        "				SELECT TOP 1 @arg= ISNULL(CAJA,'0') ,@arg2= (ISNULL(NUMERO,'0') +1) \n" +
        "				FROM ARQUEOS WHERE CAJA = (SELECT CAJA FROM TERMINALES WHERE TERMINAL = HOST_NAME()) AND ARQUEO = 'Z' ORDER BY NUMERO DESC ;\n" +
        "			end\n" +
        "			else\n" +
        "			begin\n" +
        "				SELECT TOP 1 @arg= '999', @arg2=(ISNULL(NUMERO,'0') +1)                     \n" +
        "				FROM ARQUEOS WHERE ARQUEO = 'Z' ORDER BY NUMERO DESC\n" +
        "			end\n" +
        "\n" +
        "			INSERT INTO REM_TRANSACCIONES  WITH(ROWLOCK)\n" +
        "			(TERMINAL, CAJA, CAJANUM, Z, TIPO, ACCION, SERIE, NUMERO, N, FECHA, HORA, FO, IDCENTRAL)  VALUES\n" +
        "			(HOST_NAME(), '', @arg, @arg2, 12, 0, '', @text_18, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 1) \n" +
        "		end\n" +
        "		else\n" +
        "		begin\n" +
        "			/*\n" +
        "			SELECT TOP 1 @text_24=CODCLIENTE  FROM CLIENTES  WHERE DESCATALOGADO = 'F' AND CODCLIENTE BETWEEN(select min(cast(valor as bigint)) from parametros where usuario in ('1', '11')and subclave = 'MINIM') AND(select max(cast(valor as bigint)) valor from parametros where usuario in ('1','11')and subclave = 'MAXIM') ORDER BY CODCLIENTE DESC\n" +
        "			\n" +
        "			set @text_24=isnull(@text_24,'0');\n" +
        "			set @num7=cast(@text_24 as int)\n" +
        "			set @num7=@num7+1;\n" +
        "\n" +
        "			\n" +
        "			set @text_25=GETDATE();\n" +
        "			UPDATE CONTADORES SET valor = @num7 where codigo = 1;\n" +
        "\n" +
        "			INSERT CLIENTES (\n" +
        "			CODCLIENTE , CODCONTABLE , NOMBRECOMERCIAL , DIRECCION1 , PROVINCIA , PAIS , TELEFONO1 , E_MAIL , \n" +
        "			CIF , NOMBRECLIENTE , CODPAIS , FECHAMODIFICADO , NIF20 , CODCONTABLEDMN , CANTPORTESPAG , TIPOPORTES , \n" +
        "			RIESGOCONCEDIDO , TIPO , RECARGO , FACTURARSINIMPUESTOS , DTOCOMERCIAL , REGIMFACT , CODMONEDA , DESCATALOGADO , \n" +
        "			TRANSPORTE , CONDENTREGAEDI , CONDENTREGA , LOCAL_REMOTA , CARGOSFIJOSA , NOCALCULARCARGO1ARTIC , NOCALCULARCARGO2ARTIC , ESCLIENTEDELGRUPO , \n" +
        "			REGIMRET , RET_TIPORETENCIONIVA , CAMPOSLIBRESTOTALIZAR , CARGOSEXTRASA , RECC , BLOQUEADO , SUBNORMA , SECUENCIAADEUDO , \n" +
        "			PERSONAJURIDICA , RECIBIRINFORMACION)\n" +
        "			\n" +
        "			VALUES (@num7,@text_15+right(replicate('0',7) + @num7,7),@text,@p_direccion,@p_provincia, @p_pais,@p_phone,@p_email,\n" +
        "			@p_nro_documento, @text_3, @p_codigopais, CURRENT_TIMESTAMP, @p_nro_documento, @text_15+right(replicate('0',7) + @num7,7), 0, \n" +
        "			'D', 0, 0, 'F', 'F', 0,case when @text_14='sunat' then '6' else '1' end, 1, 'F', 0, 'X2', 'EP', 'L',  0, 0, 0, 0, '', 0, 0, 2, 0, 'F', 1, 2, 'F', 0)\n" +
        "			*/\n" +
        "			UPDATE \n" +
        "			CLIENTESCAMPOSLIBRES \n" +
        "			SET TIPODOCCLIENTE=case when @text_14='sunat' then '6' else '1' end,  \n" +
        "			CL_ESTADOSUNAT = @p_estado, \n" +
        "			HABIDO = @p_condicion, \n" +
        "			CLI_MAILPROMO = @p_promociones, \n" +
        "			CL_NOM_EDITADO =  @text_2\n" +
        "			WHERE CODCLIENTE  =@num7;\n" +
        "\n" +
        "			/*insercion final*/\n" +
        "			if @p_aplicacion=0\n" +
        "			begin\n" +
        "				SELECT TOP 1 @arg= ISNULL(CAJA,'0') ,@arg2= (ISNULL(NUMERO,'0') +1) \n" +
        "				FROM ARQUEOS WHERE CAJA = (SELECT CAJA FROM TERMINALES WHERE TERMINAL = HOST_NAME()) AND ARQUEO = 'Z' ORDER BY NUMERO DESC ;\n" +
        "			end\n" +
        "			else\n" +
        "			begin\n" +
        "				SELECT TOP 1 @arg= '999', @arg2=(ISNULL(NUMERO,'0') +1)                     \n" +
        "				FROM ARQUEOS WHERE ARQUEO = 'Z' ORDER BY NUMERO DESC\n" +
        "			end\n" +
        "\n" +
        "			INSERT INTO REM_TRANSACCIONES  WITH(ROWLOCK)\n" +
        "			(TERMINAL, CAJA, CAJANUM, Z, TIPO, ACCION, SERIE, NUMERO, N, FECHA, HORA, FO, IDCENTRAL)  VALUES\n" +
        "			(HOST_NAME(), '', @arg, @arg2, 12, 0, '', @num7, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 1) \n" +
        "			\n" +
        "		end\n" +
        "	end \n" +
        "	else\n" +
        "	begin\n" +
        "	\n" +
        "		\n" +
        "		UPDATE CLIENTES \n" +
        "		SET \n" +
        "		NOMBRECOMERCIAL =  @text, \n" +
        "		NOMBRECLIENTE = CASE WHEN (select ccl.CL_NOM_EDITADO FROM CLIENTESCAMPOSLIBRES CCL INNER JOIN CLIENTES CL ON CCL.CODCLIENTE = CL.CODCLIENTE WHERE CL.DESCATALOGADO='F' AND CL.CIF = @p_nro_documento) = 'SI' THEN NOMBRECLIENTE  else @text_3 END, \n" +
        "		DIRECCION1 = @p_direccion, \n" +
        "		PROVINCIA = @p_provincia, \n" +
        "		PAIS = @p_pais, \n" +
        "		TELEFONO1 = @p_phone,\n" +
        "		E_MAIL = @p_email, \n" +
        "		REGIMFACT = case when @text_14='sunat' then '6' else '1' end , \n" +
        "		CODPAIS =@p_codigopais                \n" +
        "		WHERE CIF = @p_nro_documento AND DESCATALOGADO = 'F';\n" +
        "\n" +
        "		UPDATE CCL \n" +
        "		SET CCL.TIPODOCCLIENTE=case when @text_14='sunat' then '6' else '1' end, \n" +
        "		CCL.CL_ESTADOSUNAT = @p_estado, \n" +
        "		CCL.HABIDO = @p_condicion, \n" +
        "		CLI_MAILPROMO = @p_promociones , \n" +
        "		ccl.CL_NOM_EDITADO = @text_2  \n" +
        "		FROM CLIENTESCAMPOSLIBRES CCL INNER JOIN \n" +
        "		CLIENTES CL ON CCL.CODCLIENTE = CL.CODCLIENTE  \n" +
        "		WHERE CL.CIF = @p_nro_documento  ;\n" +
        "\n" +
        "		SELECT TOP 1 @cod_cliente=CODCLIENTE FROM CLIENTES WHERE DESCATALOGADO = 'F' AND CIF = @p_nro_documento;\n" +
        "\n" +
        "		/*insercion final*/\n" +
        "		if @p_aplicacion=0\n" +
        "		begin\n" +
        "			SELECT TOP 1 @arg= ISNULL(CAJA,'0') ,@arg2= (ISNULL(NUMERO,'0') +1) \n" +
        "			FROM ARQUEOS WHERE CAJA = (SELECT CAJA FROM TERMINALES WHERE TERMINAL = HOST_NAME()) AND ARQUEO = 'Z' ORDER BY NUMERO DESC ;\n" +
        "		end\n" +
        "		else\n" +
        "		begin\n" +
        "			SELECT TOP 1 @arg= '999', @arg2=(ISNULL(NUMERO,'0') +1)                     \n" +
        "			FROM ARQUEOS WHERE ARQUEO = 'Z' ORDER BY NUMERO DESC\n" +
        "		end\n" +
        "\n" +
        "		INSERT INTO REM_TRANSACCIONES  WITH(ROWLOCK)\n" +
        "		(TERMINAL, CAJA, CAJANUM, Z, TIPO, ACCION, SERIE, NUMERO, N, FECHA, HORA, FO, IDCENTRAL)  VALUES\n" +
        "		(HOST_NAME(), '', @arg, @arg2, 12, 0, '', @cod_cliente, '', CURRENT_TIMESTAMP, CURRENT_TIMESTAMP, 0, 1) \n" +
        "	end\n" +
        "\n" +
        "\n" +
        "	select a.CIF nro_documento,a.NOMBRECLIENTE nombre,a.DIRECCION1 direccion,a.PROVINCIA provincia,a.PAIS pais,a.CODPAIS codpais,\n" +
        "	a.TELEFONO1 telefono,a.E_MAIL email,b.CLI_MAILPROMO promociones,b.CL_ESTADOSUNAT estado,b.HABIDO habido\n" +
        "	from CLIENTES a inner join\n" +
        "	CLIENTESCAMPOSLIBRES b on a.CODCLIENTE=b.CODCLIENTE\n" +
        "	where a.CIF=@p_nro_documento;\n" +
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
