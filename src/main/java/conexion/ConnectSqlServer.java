
package conexion;

import java.sql.Connection;
import java.sql.DriverManager;
import javax.swing.JOptionPane;

public class ConnectSqlServer {
    Connection conexion;
    
    public ConnectSqlServer(String cadena_conexion){
	String driver = "com.microsoft.sqlserver.jdbc.SQLServerDriver";
	String url = cadena_conexion;
	//String url = "jdbc:sqlserver://10.100.10.110;encrypt=false;databaseName=INTEGRACION;user=sa; password=CEW2019$;sslProtocol=TLSv1;";
	try {
            Class.forName(driver);
            conexion = DriverManager.getConnection(url);
	} catch (Exception exc) {
            JOptionPane.showMessageDialog(null, exc.getMessage());
            JOptionPane.showMessageDialog(null, "No hay conexion con la BD");
            System.out.println(exc);
	}
    }

    public Connection getConexion() {
        return conexion;
    }
}