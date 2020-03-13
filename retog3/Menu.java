package retog3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.soap.Node;
import javax.xml.transform.TransformerException;

import org.w3c.dom.DOMException;
import org.xml.sax.SAXException;

public class Menu {
	
	public void printMenu(){
		System.out.println("CONCESIONARIO ZUBIRI");
		System.out.print("1. Ver Stock");
		System.out.print("2. Comprar Vehiculo");
		System.out.print("3. Vender Vehiculo");
		System.out.print("4. Pintar vehiculo");
		System.out.print("5. Salir");
		int op =Console.readInt();
		
	}
	
	public static void main(String[] args) {

		try {
			
			Connection conexion = Conexion.conectarBase();

			/*
			Statement sentencia = conexion.createStatement();
			ResultSet rs = sentencia.executeQuery("SELECT * FROM student;");
			XML.exportarRegistro(rs, conexion);*/
			XML.importarRegistro(conexion);
		
		} catch (ParserConfigurationException e) {
			System.err.println("Error de analización: " + e.getMessage());
		} catch (IOException e) {
			System.err.println("Error de I/O: " + e.getMessage());
		} catch (SAXException e) {
			System.err.println("Error SAX: " + e.getMessage());
		} catch (DOMException e) {
			System.err.println("ERROR DOM:" + e.getMessage());
		} catch (SQLException e) {
			//System.err.println("ERROR SQL:" + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		}
	}
}