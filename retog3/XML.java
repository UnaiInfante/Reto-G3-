package retog3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import test.Console;

import org.w3c.dom.Node;

/**
 * Esta clase se encarga de importar y exportar los datos de la base MySQL
 * en formato XML. No necesita recibir instrucciones en forma de argumentos, y no 
 * tiene que ser instanciada.
 * 
 * @author G3
 *@version 1.0
 */
public final class XML {
	
	/**
	 * Es el {@code String} que usará para insertar el objeto {@code Document} en 
	 * el archivo que crearemos. 
	 */
	private static String string;
	
	/**
	 * Es la ruta del archivo <b>.xml</b> que se quiere importar. Está creado para funcionar
	 * de forma manual o ir al escritorio del usuario.
	 */
	private static String path;
	
	/** Evitamos instanciar la clase con el constructor en {@code private}. */
	private XML() {}
	
	/**
	 * Exporta un registro XML a un archivo a una ruta específica o en el escritorio. Este 
	 * proceso es automático: Crea un archivo con el registro o lo sobreescribe si ya existe
	 * uno. El usuario puede elegir entre escribir la ruta del archivo o suponer que está 
	 * en el escritorio, facilitando el acceso al registro. El usuario puede elegir entre exportar
	 * el registro de la base de datos o el historial de la base de datos.
	 * 
	 * @param conexion La conexión de la base de datos.
	 * 
	 * @throws SQLException -
	 * @throws ParserConfigurationException -
	 * @throws TransformerException -
	 * @throws InstantiationException -
	 * @throws IllegalAccessException -
	 * @throws IOException -
	 */
	public static void exportarRegistro(Connection conexion) throws IOException, ParserConfigurationException, SQLException, TransformerException {
		
		/*
		 * Aspectos generales: Se crean los objetos File y Document que servirán para la 
		 * exportación del registro.
		 */
		String local = "\n" + System.getProperty("user.name") + "\\menú\\XML>";
		String tabla1 = "", tabla2 = "";
		System.out.println("Seleccione el tipo de registro.\n1)Registro de la base de datos.\n2)Historial.\n3)Cancelar operación");
		int opcion;
		
		do {
			
			System.out.print(local);
			opcion = Console.readInt();
			System.out.println(opcion);
			if(opcion < 1 || opcion > 3) System.err.println("Valor incorrecto.");
			
		}while (opcion < 1 || opcion > 3);
		
		if(opcion == 1) {
			tabla1 = "retog3.VEHICULO, retog3.COCHE";
			tabla2 = "retog3.VEHICULO, retog3.CAMION";
		} else if (opcion == 2) {
			tabla1 = "retog3.VEHICULO_R, retog3.COCHE_R";
			tabla2 = "retog3.VEHICULO_R, retog3.CAMION_R";
		} 
		
		if(opcion != 3) {
			File file = new File(System.getProperty("user.home") + "/Desktop/registro.xml");
			FileWriter fw = new FileWriter(file);
			
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		    DocumentBuilder builder = factory.newDocumentBuilder();
		    Document doc = builder.newDocument();
		    
		    /*
		     * El documento separará coches y camiones, poniendo coches primero.
		     */
		    Statement st = conexion.createStatement();
		    
			ResultSet rs = st.executeQuery("SELECT matricula, numBastidor, serie, esPintado, color, numAsientos, precio, tipo, "
					+ "numPuertas, capMaletero FROM " + tabla1 + " WHERE vehiculo.matricula = coche.matricula;");
			
		    Element results = doc.createElement("Database");
		    doc.appendChild(results);
		  	ResultSetMetaData rsmd = rs.getMetaData();
			int colCount = rsmd.getColumnCount();
			
			while (rs.next()) {
				
				Element row = doc.createElement("vehiculo");
			    results.appendChild(row);
			    for (int i = 1; i <= colCount; i++) {
			    	
			    	String columnName = rsmd.getColumnName(i);
				    Object value = rs.getObject(i);
				    if(rsmd.getColumnName(i).equalsIgnoreCase("TIPO")) {
				    	
				    	row.setAttribute("type", value.toString());
					    
					} else {
						
						Element node = doc.createElement(columnName);
					    node.appendChild(doc.createTextNode(value.toString()));
					    row.appendChild(node);
						
					}
				    
			    }
			}
			
			/*
			 * Sección de camiones.
			 */
			Statement st2 = conexion.createStatement();
			ResultSet rs2 = st2.executeQuery("SELECT vehiculo.matricula, numBastidor, serie, esPintado, color, numAsientos, precio, tipo, "
					+ "carga, tipoMercancia FROM " + tabla2 + " WHERE vehiculo.matricula = camion.matricula");
			
			while (rs2.next()) {
				
					Element row = doc.createElement("vehiculo");
					results.appendChild(row);
					ResultSetMetaData rsmd2 = rs2.getMetaData();
					int colCount2 = rsmd2.getColumnCount();
					
					for (int i = 1; i <= colCount2; i++) {
						
						String columnName = rsmd2.getColumnName(i);
						Object value = rs2.getObject(i);
						if(rsmd.getColumnName(i).equalsIgnoreCase("TIPO")) {
							
							row.setAttribute("type", value.toString());
							
						} else {
							
							Element node = doc.createElement(columnName);
							node.appendChild(doc.createTextNode(value.toString()));
							row.appendChild(node);
							
						}
						
					}	
				}
			   
			    DOMSource domSource = new DOMSource(doc);
			    TransformerFactory tf = TransformerFactory.newInstance();
			    Transformer transformer = tf.newTransformer();
			    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
			    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			    transformer.setOutputProperty(OutputKeys.ENCODING, "UTF-8");
			    StringWriter sw = new StringWriter();
			    StreamResult sr = new StreamResult(sw);
			    transformer.transform(domSource, sr);
			    string = sw.toString();
			    
			    BufferedWriter bw = new BufferedWriter(fw);
			    bw.write(string);
			    bw.flush();
			    bw.close();
			    rs.close();
			    System.out.println("Registro exportado.");
		} else {
			System.out.println("Cancelando operación...");
		}
	} 
	
	/**
	 * Lee un archivo en formato ".xml" y obtiene todos los datos en ella. En caso de que 
	 * reciba un texto en otro formato lanzará una excepción que recogerá {@code Menu} en
	 * el método main. Para insertar los datos necesita ayuda del método {@code insertData} de 
	 * tipo {@code private void} para simplificar el proceso.
	 * 
	 * @param conexion - El objeto de tipo {@code Connection} para conectarse con la base de datos local.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DOMException
	 * @throws SQLException
	 */
	public static void importarRegistro(Connection conexion) throws ParserConfigurationException, SAXException, IOException, SQLException  {
		
		int opcion;
		do {
			System.out.println("Elija el modo de ruta:\n1) Escribir la ruta manualmente.\n2) Empezar desde el escritorio.");
			opcion = Console.readInt();
			if(opcion == 1) {
				System.out.println("Escriba la ruta: ");
				path = Console.readString();
			} else if (opcion == 2) {
				path = System.getProperty("user.home") + "/Desktop/";
				System.out.println("Escriba el nombre del archivo: ");
				path+= Console.readString();
			} else {
				System.err.println("ERROR: Valor inválido. Inténtelo de nuevo.");
			}
		} while(opcion < 1 || opcion > 2);
		
		/*
		 * Proceso estándar para crear un objeto que representa un 
		 * documento.
		 */
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(path);
		
		NodeList list = doc.getElementsByTagName("vehiculo");
		System.out.println("Importando...\n");
		for(int i = 0; i < list.getLength(); i++) {
			
			//Esta es la lista de donde se sacarán los datos. Representan una tupla de la tabla.
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				String matricula;
				int numBastidor;
				String serie;
				int esPintado;
				String color;
				int numAsientos;
				float precio;
				
				//Creando los nodos "vehiculo"
				Element vehicle = (Element) node;
				String tipo = vehicle.getAttribute("type");
				NodeList vList = vehicle.getChildNodes();
				
				
				ArrayList<String> dataVehiculo = new ArrayList<String>();
				for(int j = 0; j < vList.getLength(); j++) {
					
					//Creando los nodos que contienen los datos.
					Node node2 = vList.item(j);
					if(node2.getNodeType() == Node.ELEMENT_NODE) {
						Element vAtt = (Element) node2;
						
						String data = vAtt.getTextContent();
						dataVehiculo.add(data);
						
					}	
				}
				matricula = (String) dataVehiculo.get(0);
				numBastidor = Integer.parseInt(dataVehiculo.get(1));
				serie = dataVehiculo.get(2);
				esPintado = Integer.parseInt(dataVehiculo.get(3)) ;
				color = dataVehiculo.get(4);
				numAsientos = Integer.parseInt(dataVehiculo.get(5));
				precio = Float.parseFloat(dataVehiculo.get(6)) ;
				
				String insert;
				
				if(tipo.equalsIgnoreCase("COCHE")) {
					
					int numPuertas = Integer.parseInt(dataVehiculo.get(7));
					int capMaletero = Integer.parseInt(dataVehiculo.get(8));
					
					insert = "INSERT INTO coche (matricula, numPuertas, capMaletero) VALUES ('" + matricula + "', " 
					+ numPuertas + ", " + capMaletero + ");";
					PreparedStatement ps2 = conexion.prepareStatement(insert);
					ps2.executeUpdate();
					
				} else if (tipo.equalsIgnoreCase("CAMION")) {
					
					String carga = dataVehiculo.get(7);
					char tipoMercancia = dataVehiculo.get(8).charAt(0);
					
					insert = "INSERT INTO camion (matricula, carga, tipoMercancia) VALUES ('" + matricula + "', '" + carga + "', '" + 
					tipoMercancia + "');";
					PreparedStatement ps2 = conexion.prepareStatement(insert);
					ps2.executeUpdate();
				}
				
				String sql = "INSERT INTO vehiculo (matricula, numBastidor, serie, color, esPintado, numAsientos, precio, tipo) " + 
				"VALUES ('" + matricula + "', " + numBastidor + ", '" + serie + "', '" + color + "', "  + esPintado + ", " + numAsientos + 
				", " + precio + ", '" + tipo + "');";
				
				PreparedStatement ps = conexion.prepareStatement(sql);
				ps.executeUpdate();
			}
		}
		System.out.println("Datos exportados a la base de datos.");
	}

}