//Console class for new jdk's
package retog3;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Console {

	
	public static byte readByte(){
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		

		Byte x = 0;
		try {
			x = Byte.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return x.byteValue();

	}

	public static short readShort() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		Short x = 0;
		try {
			x = Short.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return x.shortValue();

	}
	
	public static int readInt() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		Integer x = 0;
		try {
			x = Integer.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return x.intValue();

	}
	
	public static long readLong() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		Long x = 0L;
		try {
			x = Long.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return x.longValue();

	}
	

	public static float readFloat() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		Float x = 0f;
		try {
			x = Float.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return x.floatValue();

	}

	public static double readDouble() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		Double x = (double) 0;
		try {
			x = Double.valueOf(in.readLine());
		} catch (NumberFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		return x.doubleValue();

	}
	
	public static char readChar() {
		
		
		java.io.BufferedInputStream b = new BufferedInputStream(System.in);
		char character = 0;
		try {
			character = (char) (b.read());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return character;
	}

	public static String readString() {
		
		BufferedReader in=new BufferedReader(new InputStreamReader(System.in));
		String str = "";
		try {
			str = in.readLine();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return str;

	}
	
	
}
