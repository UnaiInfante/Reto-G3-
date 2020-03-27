package retog3;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import retog3.Console;
import org.w3c.dom.DOMException;

/**
 * Es la clase donde el usuario ejecutar� el programa. Aqu� se recogen la 
 * mayor�a de las excepciones con un {@code try/catch} para tener mensajes de
 * errores personalizados.
 * 
 * @author G3
 * @see retog3.Conexion
 * @see retog3.XML
 * @see retog3.Vehiculo
 *
 */
public final class Menu {
	
	/**
	 * Es lo que se imprime en la consola para que el usuario sepa de sus opciones. Aqu�
	 * no se recoge la operaci�n del usuario.
	 */
	public static void printMenu(){
		
		String menu = 
		"\n+------------------------+\n" +
		"|CONCESIONARIO ZUBIRI    |\n" + 
		"+------------------------+\n" +
		"|Seleccione una opci�n:  |\n" + 
		"|1. Ver stock            |\n" + 
		"|2. Comprar un veh�culo  |\n" + 
		"|3. Vender un veh�culo   |\n" + 
		"|4. Pintar un veh�culo   |\n" + 
		"|5. Exportar registro    |\n" + 
		"|6. Salir                |\n" + 
		"+------------------------+\n";
		
		System.out.println(menu);
	}
	
	/**
	 * Imprime la fecha y hora al inicio de la ejecuci�n.
	 */
	private static void extra() {
		
		String bienvenida = "Bienvenido. La fecha de hoy es " + LocalDate.now() + " y son la(s) " +
		LocalTime.now();
		
		System.out.println(bienvenida);
		
	}
	
	/**
	 * M�todo main donde el usuario usar� la aplicaci�n. Aqu� es donde se "atrapan" las excepciones.
	 * @param args Array String est�ndar.
	 */
	public static void main(String[] args) {
		
		try {
		
		Connection conexion = Conexion.conectarBase();
		extra();
		String aux = "";
		int op = 0;
		
		do {
			String local = "\n" + System.getProperty("user.name") + "\\men�>";
			printMenu();
			System.out.print(local);
			aux = Console.readString();
			
			if(revisarFormato(aux))  {
				op = Integer.parseInt(aux);
			} else if(!revisarFormato(aux)) {
				System.err.println("Debe insertar un valor num�rico entero.");
			} else if(op < 1 || op > 6) {
				System.err.println("Valor incorrecto, int�ntelo de nuevo.");
			}	
			
			switch(op) {
			
			case(1): 
				Vehiculo.mostrar(conexion);
				break;
			
			case(2):
				Vehiculo.comprarVehiculo(conexion);
				break;
			
			case(3):
				Vehiculo.venderVehiculo(conexion);
				break;
			
			case(4):
				Vehiculo.pintarVehiculo(conexion);
				break;
				
			case(5):
				System.out.println("Exportando registro...");
			    XML.exportarRegistro(conexion);
				break;
				
			case(6):
				System.out.println("Saliendo del men�...");	
			    break;
			}
			
		} while (op != 6);
		
		} catch (DOMException e) {
			System.err.println("ERROR de jDOM: " + e.getMessage());
			e.printStackTrace();
		} catch (SQLException e) {
			System.err.println("ERROR jSQL: " + e.getMessage());
			e.printStackTrace();
		} catch (Exception e) {
			//System.err.println("ERROR: " + e.getMessage());
			e.printStackTrace();
		} finally {
			System.out.println("Cerrando el programa...");
		}
	}
	
	/**
	 * Revisa si la opci�n del usuario es un n�mero entero.
	 * @param option el {@code input} del usuario.
	 * @return {@code true} si es un n�meor y {@code false} en caso contrario.
	 */
	private static boolean revisarFormato(String option) {
		
		try {
			
			Integer.parseInt(option);
			
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}