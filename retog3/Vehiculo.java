package retog3;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;

import javax.xml.parsers.ParserConfigurationException;

import org.xml.sax.SAXException;

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
	
	/**Atributo de la tabla en la base de datos.*/
	private static int numAsientos;
	
	/**Atributo de la tabla en la base de datos.*/
	private static float precio;
	
	/**Atributo de la tabla en la base de datos.*/
	private static String serie;
		
	/** Con el constructor en {@code private} evitamos instanciar la clase.*/
	private Vehiculo() {}
	
	/**
	 * El usuario usando este método introducirá de forma manual los datos a la base
	 * de datos. El proceso es automático y solo se necesita los datos introducidos
	 * para meterlos a la base de datos. Dispone de dos formas para lograr esto: Una es
	 * de forma manual y otra es usando el método de la clase {@code XML} para insertarlo.
	 * 
	 * @param conexion - La conexión a la base de datos donde se introducirán los datos.
	 * @throws SQLException
	 * @throws IOException 
	 * @throws SAXException 
	 * @throws ParserConfigurationException 
	 */
	
	public static void comprarVehiculo(Connection conexion) throws SQLException, ParserConfigurationException, SAXException, IOException {
				
		System.out.println("Seleccione un método para insertar los vehículos.");
		System.out.println("1)Manual.\n2)Importación de un registro.\n3)Cancelar operación.");
		int opcion;
		do {
			opcion = Console.readInt();
			
			if(opcion < 1 || opcion >3) System.out.println("Número incorrecto, inténtelo de nuevo.");
		} while(opcion <1 || opcion >3);
		
		if(opcion == 2) {
			XML.importarRegistro(conexion);
		} else if (opcion == 1) {
			System.out.println("Escriba los datos del vehículo a comprar:");
			
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
				
				String sql2=("INSERT INTO COCHE (matricula, numPuertas, capMaletero) VALUES ('" + matricula + "', " + numPuertas + ", " + capMaletero + ");");
				PreparedStatement ps2 = conexion.prepareStatement(sql2);	
				ps2.executeUpdate();
						
			} else if (tipo.equalsIgnoreCase("CAMION")) {
				
				System.out.println("CARGA:");
				String carga=Console.readString();
				
				System.out.println("TIPO DE MERCANCIA (G: General.\nP: Peligrosa.\nA: Árido.");
				
				char tipoMercancia;
				do {
					
					tipoMercancia = Console.readChar();
					
					if(tipoMercancia != 'A' && tipoMercancia != 'G' && tipoMercancia != 'P') 
						System.err.println("Valor incorrecto, inténtelo de nuevo.");
					
				} while (tipoMercancia != 'A' && tipoMercancia != 'G' && tipoMercancia != 'P');
				
				String sql3=("INSERT INTO retog3.camion (matricula, carga, tipoMercancia) VALUES ('" + matricula + "', "  + carga + ", '" + tipoMercancia + "');");
				PreparedStatement ps3 = conexion.prepareStatement(sql3);	
				ps3.executeUpdate();
				
			} else {
					
				System.err.println("Valor inválido.");
			}
				
			System.out.println("Valores insertados.");

		} else if (opcion == 3) {
			System.out.println("Cancelando operación...");
		}
	}
	
	/**
	 * Este método elimina un vehiculo de la base de datos dada su clave primaria
	 * (Su matrícula).
	 * 
	 * @param conexion
	 * @throws SQLException
	 * @throws NumberFormatException
	 */
			
	public static void venderVehiculo(Connection conexion) throws SQLException, NumberFormatException {
		
		System.out.println("Seleccione un método para vender un vehículo.");
		System.out.println("1)Manual.\n2)Cancelar operación.");
		String local = "\n" + System.getProperty("user.name") + "\\menú\\vender>";


		int opcion;
		do {
			System.out.print(local);
			opcion = Console.readInt();
			
			if(opcion != 1 && opcion != 2) System.out.println("Número incorrecto, inténtelo de nuevo.");
			
		} while(opcion != 1 && opcion != 2);
		
		if(opcion == 1) {
			
			System.out.println("Escriba la matrícula del coche que desea vender:");
			
			System.out.println("MATRICULA:");
			matricula = Console.readString();
			String tabla = "";
			
			Statement st = conexion.createStatement();
			ResultSet rs = st.executeQuery("SELECT tipo FROM retog3.vehiculo WHERE matricula = '" + matricula + "';");
			rs.next();
			Object value = rs.getObject(1);
			
			
			if(value.toString().equalsIgnoreCase("coche")) {		
				tabla = "DELETE FROM retog3.coche WHERE matricula ='" + matricula + "';";	
			} else if (value.toString().equalsIgnoreCase("camion")) {
				tabla = "DELETE FROM retog3.camion WHERE matricula ='" + matricula + "';";
			}
			
			String sql =("DELETE FROM retog3.vehiculo WHERE matricula ='" + matricula + "';");
			PreparedStatement ps = conexion.prepareStatement(sql);
			ps.executeUpdate();
			
			PreparedStatement ps2 = conexion.prepareStatement(tabla);
			ps2.executeUpdate();
			
			System.out.println("Coche vendido.");
		} else if (opcion == 2) {
			System.out.println("Cancelando operación...");
		}
	}
	
	/**
	 * Modifica el vehículo para tener un diferente pintado. Cambiará el atributo {@code esPintado}
	 * para dejar en constancia que se ha hecho la modificación.
	 * 
	 * @param conexion
	 * @throws SQLException
	 */
	public static void pintarVehiculo(Connection conexion) throws SQLException {
		
		
		System.out.println("Seleccione una opción.\n1)Pintar\n2)Cancelar operación");
		int op;
		
		do {
			op = Console.readInt();
			
			if(op != 1 && op != 2) System.err.println("Valor incorrecto, inténtelo de nuevo.");
		} while (op != 1 && op != 2);
		
		if(op == 1) {
			System.out.println("Escriba la matrícula del vehículo que quiera pintar:");
			
			System.out.println("MATRICULA:");
			matricula = Console.readString();
					
			System.out.println("COLOR:");
			String color =Console.readString();
					
			String sql=("UPDATE retog3.vehiculo SET color = '" + color + "', esPintado = " + (true) + " WHERE matricula ='"+ matricula +"';");
			System.out.println(sql);
			PreparedStatement ps = conexion.prepareStatement(sql) ;	
			ps.executeUpdate();	
		} else {
			System.out.println("Cancelando operación...");
		}
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