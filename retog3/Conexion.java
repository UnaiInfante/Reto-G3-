package retog3;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * La clase {@code Conexion} es la responsable de establecer la 
 * conexi�n a la base de datos. Esta clase no puede ser heredada
 *  ni instanciada por la funci�n que tiene en el programa.
 * 
 * @author G3
 * @version 1.0
 */
 public final class Conexion {
	
	 /** El objecto tipo {@code Connection} que usaremos en la conexi�n.*/
	private static Connection conexion;
	
	/**
	 * El nombre de la base de datos. Este valor ser� de uso del programador para 
	 * la base de datos local que se use y el usuario no tendr� que interactuar con
	 * este campo.
	 */
	private static String name = "retog3";
	
	/**
	 * La direcci�n URL donde se encuentra la base de datos. Esta base 
	 * se concatena con {@code name} para formar la direcci�n completa.
	 */
	private static String url;
	
	/**
	 * El nombre de ususario del servidor MySQL. Puede ser usado directo al 
	 * conectar con el servidor siempre y cuando siempre se use la misma cuenta.
	 * En servidores locales es recomendable tener el usuario <em>root</em> para 
	 * facilitar la conexi�n.
	 */
	private final static String username = "root";
	
	/**
	 * La contrase�a correspondiente del usuario {@code username}. 
	 */
	private static String password = "root";
	
	/** Con el constructor en {@code private}, evitamos instanciar la clase. */
	private Conexion() {}
	
/**
 * M�todo est�tico que devuelve el objeto que har� de conexi�n en MySQL. Para simplificar
 * el c�digo en la clase {@code Menu} las {@code Exception} posibles se recogen aqu�
 * mediante un {@code catch/try}, con mensajes personalizados del problema en
 * ingl�s.
 * 
 * @return  El objeto que servir� de conexi�n para la base de datos.
 * @throws ClassNotFoundException 
 * @throws IllegalAccessException 
 * @throws InstantiationException 
 * @throws SQLException 
 */
	public static Connection conectarBase() throws InstantiationException, IllegalAccessException, ClassNotFoundException, SQLException {
			
		Class.forName("com.mysql.jdbc.Driver").newInstance();
		
		/*
		System.out.print("\nContrase�a: ");
		password = Console.readString();
		System.out.print("\nID de la base de datos: ");
		name = Console.readString();
		*/
		
		System.out.println("Conectando...");
		url = "jdbc:mysql://localhost:3306/" + name + "?characterEncoding=latin1";
		conexion = DriverManager.getConnection(url, username, password);
		System.out.println("Conexi�n completada.");
		return conexion;
	}
}