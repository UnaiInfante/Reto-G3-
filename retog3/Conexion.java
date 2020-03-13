package retog3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code Conexion} es la responsable de establecer la 
 * conexión a la base de datos. Esta clase no puede ser heredada
 *  ni instanciada por la función que tiene en el programa.
 * 
 * @author G3
 * @version 1.0
 */
 public final class Conexion {
	
	 /** El objecto tipo {@code Connection} que usaremos en la conexión.*/
	private static Connection conexion;
	
	/**
	 * El nombre de la base de datos. Este valor será de uso del programador para 
	 * la base de datos local que se use y el usuario no tendrá que interactuar con
	 * este campo.
	 */
	private static String name = "retog3";
	
	/**
	 * La dirección URL donde se encuentra la base de datos. Esta base 
	 * se concatena con {@code name} para formar la dirección completa.
	 */
	private static String url;
	
	/**
	 * El nombre de ususario del servidor MySQL. Puede ser usado directo al 
	 * conectar con el servidor siempre y cuando siempre se use la misma cuenta.
	 * En servidores locales es recomendable tener el usuario <em>root</em> para 
	 * facilitar la conexión.
	 */
	private final static String username = "root";
	
	/**
	 * La contraseña correspondiente del usuario {@code username}. 
	 */
	private static String password = "root";
	
	/** Con el constructor en {@code private}, evitamos instanciar la clase. */
	private Conexion() {}
	
/**
 * Método estático que devuelve el objeto que hará de conexión en MySQL. Para simplificar
 * el código en la clase {@code Menu} las {@code Exception} posibles se recogen aquí
 * mediante un {@code catch/try}, con mensajes personalizados del problema en
 * inglés.
 * 
 * @return  El objeto que servirá de conexión para la base de datos.
 * @throws ClassNotFoundException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SQLException 
 */
	public static Connection conectarBase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		/*
		System.out.print("\nContraseña: ");
		password = Console.readString();
		System.out.print("\nID de la base de datos: ");
		name = Console.readString();
		*/
		
		System.out.println("Conectando...");
		url = "jdbc:mysql://localhost:3306/" + name + "?characterEncoding=latin1";
		conexion = DriverManager.getConnection(url, username, password);
		System.out.println("Conexión completada.");
		return conexion;
	}
}