package retog3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;

public class Menu {
	public static void main(String[] args) {
		
		Connection conexion = Conexion.conectarBase();
					
		try {
			
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM student;");
			XML.importarRegistro(rs, conexion);
			
		} catch (SQLException e) {
			System.err.println("Error con MySQL: " + e.getMessage());
		} catch (InstantiationException e) {
			System.err.println("Error de instanciación: " + e.getMessage());
		} catch (IllegalAccessException e) {
			System.err.println("Error de acceso: " + e.getMessage());
		} catch (ParserConfigurationException e) {
			System.err.println("Error de analización: " + e.getMessage());
		} catch (TransformerException e) {
			System.err.println("Error con el objeto Transformer: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
		}
	}
}