package database;

public class Coche extends Vehiculo {
	
	private int capacidadMaletero;
	private int numPuertas;
	
	Coche(String color, boolean isPainted, int numAsientos, float precio, String serie, int capacidadMaletero, int numPuertas) {
		super(color, isPainted, numAsientos, precio, serie);
		this.capacidadMaletero = capacidadMaletero;
		this.numPuertas = numPuertas;
	}
	
	 @Override 
	 public String toString() {
		 return super.toString() + "\n" +
				"Capacidad de maletero: " + capacidadMaletero + "\n" + 
				"Número de puertas: " + numPuertas;
				
	 }
}
