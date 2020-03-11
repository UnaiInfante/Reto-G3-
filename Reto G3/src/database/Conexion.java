//TODO paquetes necesarios para la ejecución
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code Conexion} es la responsable de establecer la 
 * conexión a la base de datos. <b>El usuario no tendrá ninguna interacción
 * con esta clase más que al iniciar sesión en la base de MySQL</b>. Esta clase
 * no puede ser heredada ni instanciada por la función que tiene en el programa.
 * 
<<<<<<< HEAD
 * @author G3
 * @version 1.0
 * @see Menu
=======
 * @author Gerardo Ares ctm
 * @version 0.1
>>>>>>> ac4510cb01293c7bc9bb8f57d212134461cbf7a2
 */
 public final class Conexion {
	
	 /** El objecto tipo {@code Connection} que usaremos en la conexión.*/
	static Connection conexion;
	
	/** Las siguientes variables de tipo {@code String} componen de datos que necesita el 
	 * tipo de conexión para conectarse con la base de datos:
	 * <p>
	 * <ul>
	 * <li>{@code String name}: Es el nombre de la base de datos.
	 * <li>{@code url}: Es la dirección de la base de datos en el servidor local. Esa variable
	 *     se concatena con {@code name} para formar la url necesaria para la conexión. 
	 * <li>{@code username}: El usuario del servidor.
	 * <li>{@code password}: La contraseña del usuario. Esta contraseña será restringida al administrador
	 *     de la base de datos, y solo se tendrá que escribir una vez para después acceder a la aplicación
	 *     del programa y <em>no</em> interactuará con el usuario de forma alguna.
	 * </ul>
	 * </p>
	 */
	static String name = "retog3";
	static String url = "jdbc:mysql://localhost:3306/" + name + "?characterEncoding=latin1";
	static String username = "root";
	static String password = "root";
	
	/**
	 * Con el método en {@code private}, evitamos instanciar la clase.
	 */
	
	private void Conexion() {}
	
	/**
	 * Crea un objeto de clase {@code Connection} con su Driver necesario
	 * para la conexión de la base de datos.
	 */
	public static void conectarBase() {
		
		try {
			System.out.print("Usuario: ");
			username = Console.readString();
			System.out.print("\nContraseña: ");
			password = Console.readString();
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection(url, username, password);
			System.out.println("Conexión completada.");
			
		} catch (InstantiationException e) {
			System.err.print("ERROR: No se pude instanciar esta clase.");
		} catch (IllegalAccessException e) {
			System.err.print("ERROR: El método no tiene acceso a la definición que intenta leer.");
			// TODO Auto-generated catch block
		} catch (ClassNotFoundException e) {
			System.err.print("ERROR: No se ha encontrado la clase.");
		} catch (SQLException e) {
			System.err.print("ERROR: Hubo un problema en la base de datos.");
		}finally {
			System.out.println("Saliendo...");
		}
	}
}