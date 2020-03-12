package retog3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;




public class Menu {
	/**
	 * Metodo que imprime el menu
	 */
	public void printMenu(){
		System.out.println("CONCESIONARIO ZUBIRI");
		System.out.print("1. Ver Stock");
		System.out.print("2. Comprar Vehiculo");
		System.out.print("3. Vender Vehiculo");
		System.out.print("4. Pintar vehiculo");
		System.out.print("5. Salir");
		int op =Console.readInt();
		
	}
	
	
	
	/**
	 *El objeto conexion sw crea  en el main del menu
	 * para se mantenga conectada y no se borre.
	 * @param args
	 */
	
	public static void main(String[] args) {

		Connection conexion = Conexion.conectarBase();
					
		try {
			
			/*
			 * PRUEBAS
			 */
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM student;");
			XML.exportarRegistro(rs, conexion);
			
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