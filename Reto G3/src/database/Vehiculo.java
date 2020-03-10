package database;

abstract class Vehiculo {
	
	private String color;
	private boolean isPainted;
	private int numAsientos;
	private float precio;
	private String serie;
	
	
	Vehiculo(String color, boolean isPainted, int numAsientos, float precio, String serie) {
		this.color = color;
		this.isPainted = isPainted;
		this.numAsientos = numAsientos;
		this.precio = precio;
		this.serie = serie;
	}
	
	@Override 
	public String toString() {
		return "Information: \n" +
	    "Serie: " + serie + "\n" +
		"Color: " + color + "\n" + "| Pintado por el taller: " + isPainted + "\n" +
	    "Número de asientos: " + numAsientos + "\n" + 
		"Precio: " + precio;
	}
}