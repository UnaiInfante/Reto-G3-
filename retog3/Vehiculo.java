package retog3;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import test.Console;

/**
 * Esta clase tiene los métodos para introducir vehículos de forma manual 
 * a la base de datos, así como su eliminación (ventas) y modificación (pintados). 
 * Dado a que el método no requiere objetos, se ha declarado {@code final} para que
 * no pueda ser heredada y el constructor en {@code private} para evitar la instanciación
 * de la clase.
 * 
 * @author G3
 * @see retog3.XML
 */
public final class Vehiculo {
	
	/**Es la clave primaria de los vehículos. */
	private static String matricula;
	
	/**Atributo de la tabla en la base de datos.*/
	private static int numBastidor;
	
	/**Atributo de la tabla en la base de datos. Este dato puede cambiar si se desea modificar 
	 * (del que se disponeun método.)
	 */
	private static String color;
	
	/**Tiene valor {@code true} si un vehículo ha sido modificado, y {@code false} en 
	 * caso contrario
	 */
	private static boolean isPainted;
	
	/**Atributo de la tabla en la base de datos.*/
	private static int numAsientos;
	
	/**Atributo de la tabla en la base de datos.*/
	private static float precio;
	
	/**Atributo de la tabla en la base de datos.*/
	private static String serie;
	
	
	public String getIsPainted() {
		if(isPainted) {
			return "Pintado.";
		} else {
			return "No pintado.";
		}
	}
		
	/** Con el constructor en {@code private} evitamos instanciar la clase.*/
	private Vehiculo() {}
	
	/**
	 * El usuario usando este método introducirá de forma manual los datos a la base
	 * de datos. El proceso es automático y solo se necesita los datos introducidos
	 * para meterlos a la base de datos.
	 * 
	 * @param conexion - La conexión a la base de datos donde se introducirán los datos.
	 * @throws SQLException
	 */
	
	public static void comprarVehiculo(Connection conexion) throws SQLException {
				
		System.out.println("Write data:");
				
		System.out.println("MATRICULA:");
		matricula = Console.readString();
		
		System.out.println("NUMERO DE BASTIDOR:");
		numBastidor = Console.readInt();
		
		System.out.println("COLOR DEL VEHICULO:");
		color =Console.readString();
		
		System.out.println("NUMERO DE ASIENTOS:");
		numAsientos =Console.readInt();
		
		System.out.println("PRECIO:");
		precio =Console.readFloat();
			
		System.out.println("SERIE:");
		serie =Console.readString();
			
		System.out.println("TIPO:");
		String tipo = Console.readString();
				
				
		String sql="INSERT INTO retog3.vehiculo (matricula, numBastidor, serie, esPintado, color, numAsientos, precio, tipo) "
				+ "VALUES ('" + matricula + "', " + numBastidor + ", '" + serie + "', " + numAsientos + ", '" + color + "', " + numAsientos + ", " + 
				precio + ", '" + tipo + "');";
		PreparedStatement ps = conexion.prepareStatement(sql);	
		ps.executeUpdate();
				
		if (tipo.equalsIgnoreCase("COCHE")) {
					
			System.out.println("NUMERO DE PUERTAS:");
			int numPuertas = Console.readInt();
			System.out.println("CAPACIDA DE MALETERO");
			int capMaletero = Console.readInt();
			
			String sql2=("INSERT INTO COCHE (matricula, numPuertas, capMaletero) VALUES ('" + matricula + "', " + numPuertas + ", " + capMaletero + ") WHERE matricula='" + matricula + "';");
			PreparedStatement ps2 = conexion.prepareStatement(sql2);	
			ps2.executeUpdate();
					
		} else if (tipo.equalsIgnoreCase("CAMION")) {
			
			System.out.println("CARGA:");
			String numPuertas=Console.readString();
			System.out.println("TIPO DE MERCANCIA");
			char tipMercancia = Console.readChar();
			
			String sql3=("INSERT INTO retog3.vehiculo (matricula, carga, tipoMercancia) VALUES ("  + numPuertas + ", '" + tipMercancia + "');");
			PreparedStatement ps3 = conexion.prepareStatement(sql3);	
			ps3.executeUpdate();
			
		} else {
				
			System.err.println("Valor inválido.");
		}
			
		System.out.println("Valores insertados.");

	}
	
	/**
	 * Este método elimina un vehiculo de la base de datos dada su clave primaria
	 * (Su matrícula).
	 * 
	 * @param conexion
	 * @throws SQLException
	 */
			
	public static void venderVehiculo(Connection conexion) throws SQLException {
				
		System.out.println("Write some data:");
				
		System.out.println("MATRICULA:");
		matricula = Console.readString();
				
		String sql =("DELETE retog3.vehiculo WHERE matricula ='" + matricula + "';");
		PreparedStatement ps = conexion.prepareStatement(sql);
		ps.executeUpdate();
			
	}
	
	/**
	 * Modifica el vehículo para tener un diferente pintado. Cambiará el atributo {@code esPintado}
	 * para dejar en constancia que se ha hecho la modificación.
	 * 
	 * @param conexion
	 * @throws SQLException
	 */
	public static void pintarVehiculo(Connection conexion) throws SQLException {
			
		System.out.println("Write data:");
				
		System.out.println("MATRICULA:");
		matricula = Console.readString();
				
		System.out.println("COLOR:");
		String color =Console.readString();
				
		String sql=("UPDATE vehiculo SET color = '" + color + "', esPintado = " + (true) + " WHERE matricula ='"+ matricula +"';");
		System.out.println(sql);
		PreparedStatement ps = conexion.prepareStatement(sql) ;	
		ps.executeUpdate();	
	}
	
	/**
	 * Recoge todos los vehículos de la base de datos y los muestra en la consola.
	 * 
	 * @param conexion
	 * @throws SQLException
	 */
	public static void mostrar(Connection conexion) throws SQLException {
			
		String sql= "SELECT * FROM vehiculo";
		Statement select = conexion.createStatement();
		ResultSet rs = select.executeQuery(sql);
		ResultSetMetaData rsmd = rs.getMetaData();
		int colCount = rsmd.getColumnCount();
				
		System.out.println("\n-----------------STOCK ACTUAL---------------------\n");

		while(rs.next()) {
				
			for(int i = 1; i <= colCount; i++) {
						
				String columnName = rsmd.getColumnName(i);
				Object value = rs.getObject(i);
				String val = value.toString();
				System.out.println(columnName + ": " + val + "\t");
				
			}
			
			System.out.println("\n---------------------");
					
		}
	}
}