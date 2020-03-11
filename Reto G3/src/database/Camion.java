package database;

public class Camion extends Vehiculo {
	
	private int carga;
	private String tipoMercancia;
	
	Coche(String color, boolean isPainted, int numAsientos, float precio, String serie, int carga,String tipoMercancia) {
		super(color, isPainted, numAsientos, precio, serie);
		this.carga = carga;
		this.tipoMercancia = tipoMercancia;
	}
	void hacerChu(int n) {
		for(int x=0;x<n;x++) {
			System.out.println("Chuu Chuuu");
		}
	}
	
	 @Override 
	 public String toString() {
		 return super.toString() + "\n" +
				"Capacidad de maletero: " + carga + "\n" + 
				"Número de puertas: " + tipoMercancia;
				
	 }
}