//TODO paquetes necesarios para la ejecución
package database;

import java.io.*;
import java.sql.*;
import com.mysql.jdbc.ResultSet;
import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

/**
 * Conecta la aplicación con la base de datos.
 * 
 * @author Gerardo Ares
 * @version 0.1
 */
public class Conexion {
	
	static Connection conexion = null;
	static String name = "retog3";
	static String url = "jdbc:mysql://localhost:3306/" + name + "?characterEncoding=latin1";
	static String username = "root";
	static String password = "root";
	
	/**
	 * Conecta la aplicación a la base de datos.
	 */
	public static void conectarBase() {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection(url, username, password);
			
			Statement st = conexion.createStatement();
			
			java.sql.ResultSet rs = st.executeQuery("SELECT * FROM student;");
			XML.exportarRegistro((ResultSet) rs, conexion);
			
		} catch (InstantiationException e) {
			//TODO   Auto-generated catch block
			System.err.print("ERROR: No se pude instanciar esta clase.");
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			System.err.print("ERROR: Acceso illegal en [SAMPLE]");
			e.printStackTrace();
			// TODO Auto-generated catch block
		} catch (ClassNotFoundException e) {
			System.err.print("ERROR: No se ha encontrado la clase.");
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			System.err.print("ERROR: Hubo un problema de conexión.");
			e.printStackTrace();
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransformerException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			System.out.println("Saliendo...");
		}
	}
}