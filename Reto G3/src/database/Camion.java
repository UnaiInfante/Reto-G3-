<<<<<<< HEAD
package database;

public class Camion extends Vehiculo {

	private int carga;
	private String tipoMercancia;
	
	public Camion (String color, boolean isPainted, int numAsientos, float precio, String serie, int carga, String tipoMercancia) {
=======
public class Camion extends Vehiculo {
	
	private int carga;
	private String tipoMercancia;
	
	Camion(String color, boolean isPainted, int numAsientos, float precio, String serie, int carga,String tipoMercancia) {
>>>>>>> ac4510cb01293c7bc9bb8f57d212134461cbf7a2
		super(color, isPainted, numAsientos, precio, serie);
		this.carga = carga;
		this.tipoMercancia = tipoMercancia;
	}
<<<<<<< HEAD
	
	/*
	@Deprecated
=======
>>>>>>> ac4510cb01293c7bc9bb8f57d212134461cbf7a2
	void hacerChu(int n) {
		for(int x=0;x<n;x++) {
			System.out.println("Chuu Chuuu");
		}
	}
<<<<<<< HEAD
	*/
=======
>>>>>>> ac4510cb01293c7bc9bb8f57d212134461cbf7a2
	
	 @Override 
	 public String toString() {
		 return super.toString() + "\n" +
				"Capacidad de maletero: " + carga + "\n" + 
				"Número de puertas: " + tipoMercancia;
				
	 }
}