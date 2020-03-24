package retog3;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

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
		
		System.out.println("Write data:");
		System.out.println("MATRICULA:");
		int Matricula = Console.readInt();
		System.out.println("NUMERO DE BASTIDOR:");
		int numBastidor = Console.readInt();
		System.out.println("COLOR DEL VEHICULO:");
		String color =Console.readString();
		System.out.println("NUMERO DE ASIENTOS:");
		int numAsientos =Console.readInt();
		System.out.println("PRECIO:");
		int precio =Console.readInt();
		System.out.println("SERIE:");
		int serie =Console.readInt();
		
		
		String sql=("INSERT INTO retog3.vehiculo (VALORES) VALUES " + " ('" + Matricula + "', " + "'"+numBastidor + "',"+"'"+color+"', " + "'"+numAsientos + "', " + "'"+precio + "', " + "'"+serie + "');");
		PreparedStatement ps = conexion.prepareStatement(sql);	
		
		int status = ps.executeUpdate();	
		if(status != 0) {
			System.out.println("Datos insertados.");
			}
		}

    
	
	public void venderVehiculo(char tipo, Connection conexion) throws SQLException {

		System.out.println("Write some data:");
		System.out.println("MATRICULA:");
		String matricula = Console.readString();
		String sql =("DELETE retog3.vehiculo WHERE matricula ='"+matricula+"';");
		PreparedStatement ps = conexion.prepareStatement(sql);
		
		int status = ps.executeUpdate();	
		if(status != 0) {
			System.out.println("Coche vendido.");
		}
	}
	public void pintarVehiculo(char tipo, Connection conexion) throws SQLException {
		System.out.println("Write data:");
		System.out.println("MATRICULA:");
		int matricula = Console.readInt();
		System.out.println("COLOR:");
		String color =Console.readString();
		
		String sql=("UPDATE vehiculo SET color = '" + color + "', esPintado = " + (isPainted = true)+" WHERE matricula ='"+ matricula +"';");
		
		PreparedStatement ps = conexion.prepareStatement(sql) ;	
		int status = ps.executeUpdate();	
		if(status != 0) {
			System.out.println("Pintado.");
		}
	}

	public void display(char tipo, Connection conexion) throws SQLException {
		
		String sql=("SELECT * FROM retog3.vehiculo ");
		PreparedStatement ps = conexion.prepareStatement(sql) ;	
		int status = ps.executeUpdate();
		if(status != 0) {
			System.out.println("Pintado.");
		}
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