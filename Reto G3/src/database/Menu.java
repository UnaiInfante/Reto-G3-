
		
	
		import java.util.Scanner;
		 
		public class Menu {
			static Scanner sn = new Scanner(System.in);
			 static {
			     
					
						System.out.println("+---------------------+");
			            System.out.println("|"+"CONCESIONARIO ZUBIRI "+"|");
						System.out.println("+---------------------+");
			            System.out.println("|"+"1. Ver               "+"|");
			            System.out.println("|"+"2. Comprar           "+"|");
			            System.out.println("|"+"3. Vender            "+"|");
			            System.out.println("|"+"4. Pintar            "+"|");
			            System.out.println("|"+"5. Salir             "+"|");
			            System.out.println("+---------------------+");
			         
			      	}

		    public static void main(String[] args) {
		    	int num=sn.nextInt();
		    	   try {
		               new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
		           } catch (Exception e) {
		           }
		           
		              
		           
		    	
		    	while(num!=5) {
		    		System.out.println("+--------------------------------------------------------------------------+");
		    		
		 if(num==1){
			 
		 }
		 else {
		 if(num==2){
			 	System.out.print("|");
				System.out.print("1. Añadir un coche                                                        ");
				System.out.println("|"); 
				System.out.print("|");
				System.out.print("2. Añadir varios                                                          ");
				System.out.println("|");
				System.out.println("+--------------------------------------------------------------------------+");
				num=sn.nextInt();
				switch(num){
				
				case(1):///comprarVehiculo();break;
				case(2)://Clase ImportaarVarios()
				
				}
				while(num!=0);
				 System.out.println("+--------------------------------------------------------------------------+");
		 }
		 
		 
		 
		 else {
		 
		 if(num==3){
			 	System.out.print("|");
				System.out.print("1. Vender un coche                                                        ");
				System.out.println("|"); 
				System.out.print("|");
				System.out.print("2. Vender varios                                                          ");
				System.out.println("|");
				System.out.println("+--------------------------------------------------------------------------+");
				num=sn.nextInt();
				
				switch(num){
				
				case(1):///comprarVehiculo();break;
				case(2)://Clase ImportaarVarios()
				
				}
				while(num!=0);
				 System.out.println("+--------------------------------------------------------------------------+");
		 }else {
		 if(num==4){
	 
		 }
		 }
		    
		     
		 }
		 }
		 System.out.print("|");
		 System.out.println("+--------------------------------------------------------------------------+");
		 num=sn.nextInt(); 
		 }
		    }}
		    
		
		 
		
	

