package retog3;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.w3c.dom.DOMException;

import test.Conexion;
import test.Console;

public final class Menu {
	
	public static void printMenu(){
		
		String menu = 
		"\n+------------------------+\n" +
		"|CONCESIONARIO ZUBIRI    |\n" + 
		"+------------------------+\n" +
		"|Seleccione una opción:  |\n" + 
		"|1. Ver stock            |\n" + 
		"|2. Comprar un vehículo  |\n" + 
		"|3. Vender un vehículo   |\n" + 
		"|4. Pintar un vehículo   |\n" + 
		"|5. Salir                |\n" + 
		"+------------------------+\n";
		
		System.out.println(menu);
	}
	
	private static void extra() {
		
		String bienvenida = "Bienvenido. Hoy es " + LocalDate.now() + " y son la(s) " +
		LocalTime.now();
		
		System.out.println(bienvenida);
		
	}
	
	public static void main(String[] args) {
		
		try {
		
		Connection conexion = Conexion.conectarBase();
		extra();
		String aux;
		int op = 0;
		
		do {
			String local = "\n" + System.getProperty("user.name") + "\\menú>";
			printMenu();
			System.out.print(local);
			aux = Console.readString();
			
			if(revisarFormato(aux))  {
				op = Integer.parseInt(aux);
			} else if(!revisarFormato(aux)) {
				System.err.println("Debe insertar un valor numérico entero.");
			} else if(op < 1 || op > 5) {
				System.err.println("Valor incorrecto, inténtelo de nuevo.");
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
				System.out.println("Saliendo del menú...");	
			    break;
			}
			
		} while (op != 5);
		
		} catch (DOMException e) {
			System.err.println("ERROR de jDOM: " + e.getMessage());
		} catch (SQLException e) {
			System.err.println("ERROR jSQL: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("ERROR: " + e.getMessage());
		} finally {
			System.out.println("Cerrando el programa...");
		}
	}
	
	private static boolean revisarFormato(String option) {
		
		try {
			
			Integer.parseInt(option);
			
		} catch(NumberFormatException e) {
			return false;
		}
		return true;
	}
}