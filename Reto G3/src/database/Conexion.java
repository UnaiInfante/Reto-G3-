//TODO paquetes necesarios para la ejecuci�n
package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code Conexion} es la responsable de establecer la 
 * conexi�n a la base de datos. <b>El usuario no tendr� ninguna interacci�n
 * con esta clase m�s que al iniciar sesi�n en la base de MySQL</b>. Esta clase
 * no puede ser heredada ni instanciada por la funci�n que tiene en el programa.
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
	
	 /** El objecto tipo {@code Connection} que usaremos en la conexi�n.*/
	static Connection conexion;
	
	/** Las siguientes variables de tipo {@code String} componen de datos que necesita el 
	 * tipo de conexi�n para conectarse con la base de datos:
	 * <p>
	 * <ul>
	 * <li>{@code String name}: Es el nombre de la base de datos.
	 * <li>{@code url}: Es la direcci�n de la base de datos en el servidor local. Esa variable
	 *     se concatena con {@code name} para formar la url necesaria para la conexi�n. 
	 * <li>{@code username}: El usuario del servidor.
	 * <li>{@code password}: La contrase�a del usuario. Esta contrase�a ser� restringida al administrador
	 *     de la base de datos, y solo se tendr� que escribir una vez para despu�s acceder a la aplicaci�n
	 *     del programa y <em>no</em> interactuar� con el usuario de forma alguna.
	 * </ul>
	 * </p>
	 */
	static String name = "retog3";
	static String url = "jdbc:mysql://localhost:3306/" + name + "?characterEncoding=latin1";
	static String username = "root";
	static String password = "root";
	
	/**
	 * Con el m�todo en {@code private}, evitamos instanciar la clase.
	 */
	
	private void Conexion() {}
	
	/**
	 * Crea un objeto de clase {@code Connection} con su Driver necesario
	 * para la conexi�n de la base de datos.
	 */
	public static void conectarBase() {
		
		try {
			System.out.print("Usuario: ");
			username = Console.readString();
			System.out.print("\nContrase�a: ");
			password = Console.readString();
			
			Class.forName("com.mysql.jdbc.Driver").newInstance();
			conexion = DriverManager.getConnection(url, username, password);
			System.out.println("Conexi�n completada.");
			
		} catch (InstantiationException e) {
			System.err.print("ERROR: No se pude instanciar esta clase.");
		} catch (IllegalAccessException e) {
			System.err.print("ERROR: El m�todo no tiene acceso a la definici�n que intenta leer.");
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