package database;

import java.io.*;
import java.sql.*;
import com.mysql.jdbc.ResultSet;
import console.Console;
import javax.xml.parsers.*;
import javax.xml.transform.TransformerException;

/**
 * Conecta la aplicación con la base de datos.
 * 
 * @author Gerardo Ares
 * @see ArchivoXML
 *@version 0.1
 */
public class Conexion {
	
	static Connection conexion = null;
	static String name = "retog3";
	static String url = "jdbc:mysql://localhost:3306/" + name + "?characterEncoding=latin1";
	static String username = "root";
	static String password = "root";
	
	/**
	 * Usa varios elementos de distintos APIs que crean, cargan y actualizan el archivo de 
	 * registro XML.
	 * 
	 * <p>Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed tincidunt purus at odio varius imperdiet. 
	 * Etiam aliquam, sapien id gravida finibus, turpis nulla commodo leo, et ornare velit dolor id ex. 
	 * Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. 
	 * Duis euismod velit non augue blandit consequat. Ut vitae mauris bibendum eros tincidunt porttitor nec ac orci. 
	 * Praesent scelerisque nunc nec nisi scelerisque, ac rhoncus dui suscipit. 
	 * Aliquam volutpat, nisi quis varius sollicitudin, erat sapien faucibus urna, sed interdum odio dui sodales magna.</p>
	 * 
	 * @return Un String con los datos de la base de datos.	
	 */
	
	public void comprarVehiculo(char tipo) throws SQLException {
			System.out.println("Write some data:");
			System.out.println("NAME:");
			String name = Console.readString();
			System.out.println("SCHOOL:");
			String school = Console.readString();
			
			PreparedStatement ps = conexion.prepareStatement("INSERT INTO retog3.student (studentName, studentSchool) VALUES " + 
					" ('" + name + "', " + "'"+ school + "');");	
			
			int status = ps.executeUpdate();	
			if(status != 0) {
				System.out.println("Conexión completa.");
				System.out.println("Los datos fueron INSERTADOS.");
			}
	}
	
	public void venderVehiculo(char tipo) throws SQLException {

			System.out.println("Write some data:");
			System.out.println("NAME:");
			String name = Console.readString();
			System.out.println("SCHOOL:");
			String school = Console.readString();
			
			PreparedStatement ps = conexion.prepareStatement("DROP retog3.student (studentName, studentSchool) VALUES " + 
					" ('" + name + "', " + "'"+ school + "');");	
			
			int status = ps.executeUpdate();	
			if(status != 0) {
				System.out.println("Conexión completa.");
				System.out.println("Los datos fueron INSERTADOS.");
			}
	}
	
	public static void main(String[] args) {
		
		try {
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection(url, username, password);
			
			Statement st = conexion.createStatement();
			
			java.sql.ResultSet rs = st.executeQuery("SELECT * FROM student;");
			XML.exportarRegistro((ResultSet) rs, conexion);
			
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
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