package database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import com.mysql.jdbc.Connection;
import console.Console;

abstract class Vehiculo {
	
	private String color;
	private boolean isPainted;
	private int numAsientos;
	private float precio;
	private String serie;
	
	public String getIsPainted() {
		if(isPainted) {
			return "Pintado.";
		} else {
			return "No pintado.";
		}
	}
	
	Vehiculo(String color, boolean isPainted, int numAsientos, float precio, String serie) {
		this.color = color;
		this.isPainted = isPainted;
		this.numAsientos = numAsientos;
		this.precio = precio;
		this.serie = serie;
	}
	
	public void comprarVehiculo(char tipo, Connection conexion) throws SQLException {
		System.out.println("Inserta los datos del vehículo: ");
		
		PreparedStatement ps = conexion.prepareStatement("INSERT INTO retog3.tabla (ATRIBUTOS) VALUES " + 
				" ('" + color + "', " + "" + numAsientos + ");");	
		
		int status = ps.executeUpdate();	
		if(status != 0) {
			System.out.println("Datos insertados.");
		}
}
	
	public void venderVehiculo(char tipo, Connection conexion) throws SQLException {

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
	
	public void display() {
		System.out.println(toString());
	}
	
	@Override 
	public String toString() {
		return "Information: \n" +
	    "Serie: " + serie + "\n" +
		"Color: " + color + "\n" + "| Pintado por el taller: " + getIsPainted() + "\n" +
	    "Número de asientos: " + numAsientos + "\n" + 
		"Precio: " + precio;
	}
}