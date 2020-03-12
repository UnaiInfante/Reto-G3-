package retog3;
public class Camion extends Vehiculo{
int carga;
String tipoCarga;

Camion(String color, boolean isPainted, int numAsientos, float precio, String serie,int carga, String tipoCarga) {
		super(color, isPainted, numAsientos, precio, serie);
		this.tipoCarga=tipoCarga;
		this.carga=carga;
		}
	
	
	
	
}