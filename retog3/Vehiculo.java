package retog3;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

  public final class Vehiculo {
	
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
	
	private Vehiculo() {};
	
	
		public void comprarVehiculo(String tipo, Connection conexion) throws SQLException {
		
		System.out.println("Write data:");
		System.out.println("MATRICULA:");
		int matricula = Console.readInt();
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
		
		
		String sql=("INSERT INTO retog3.vehiculo (VALORES) VALUES " + " ('" + matricula + "', " + "'"+numBastidor + "',"+"'"+color+"', " + "'"+numAsientos + "', " + "'"+precio + "', " + "'"+serie + "');");
		PreparedStatement ps = conexion.prepareStatement(sql);	
	
		int status = ps.executeUpdate();	
		if(tipo=="coche" ) {
			System.out.println("NUMERO DE PUERTAS:");
			int numpuertas=Console.readInt();
			System.out.println("CAPACIDA DE MALETERO");
			int capmaletero=Console.readInt();
			String sql2=("INSERT INTO retog3.vehiculo (VALORES) VALUES " + " ('"+ numpuertas+ "',"+"'"+capmaletero+"') WHERE matricula='"+matricula+"';");
			PreparedStatement ps2 = conexion.prepareStatement(sql2);	
			
			 status = ps2.executeUpdate();	
			if(status != 0) {
				System.out.println("Datos insertados.");
							}
			
			}else
				if(tipo=="camion") {
					System.out.println("CARGA:");
					String numPuertas=Console.readString();
					System.out.println("TIPO DE MERCANCIA");
					int tipMercancia=Console.readInt();
					String sql3=("INSERT INTO retog3.vehiculo (VALORES) VALUES " + " ('"+numPuertas+ "',"+"'"+tipMercancia+"') WHERE matricula='"+matricula+"';");
					PreparedStatement ps3 = conexion.prepareStatement(sql3);	
					
					 status = ps3.executeUpdate();	
					if(status != 0) {
						System.out.println("Datos insertados.");
					}
				}else {
					System.out.println("Error valor no aceptado");
				}
		
		}

    
	
	public void venderVehiculo(Connection conexion) throws SQLException {

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
	public void pintarVehiculo(Connection conexion) throws SQLException {
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