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
import org.w3c.dom.Node;

/**
 * Esta clase se encarga de importar y exportar los datos de la base MySQL
 * en formato XML. dado que no tiene constancia sobre qué se inserta (solo la
 * instrucción que le llega), los archivos que maneje tienen un formato general
 * y puede desde el registro completo hasta un historial de la base de datos.
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
	 * Importa un registro XML a un archivo a un lugar específico. Este proceso
	 * es automático: Crea un archivo con el registro o lo sobreescribe si ya existe
	 * uno. El usuario puede elegir entre escribir la ruta del archivo o suponer que está 
	 * en el escritorio, facilitando el acceso al registro.
	 * 
	 * @param rs La instrucción SQL que tomará de referencia
	 * @param conexion La conexión de la base de datos.
	 * 
	 * @throws SQLException -
	 * @throws ParserConfigurationException -
	 * @throws TransformerException -
	 * @throws InstantiationException -
	 * @throws IllegalAccessException -
	 * @throws IOException -
	 */
	public static void exportarRegistro(ResultSet rs, Connection conexion) throws SQLException, ParserConfigurationException, TransformerException, InstantiationException, IllegalAccessException, IOException {
		
		File file = new File(path);
		FileWriter fw = new FileWriter(file);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.newDocument();
	    
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
			      System.out.println("object");
		       if(rsmd.getColumnName(i) != "tipo") {
		    	   System.out.println("Check");
		    	   Element node = doc.createElement(columnName);
			       node.appendChild(doc.createTextNode(value.toString()));
			       row.appendChild(node);
			       
		       } else {
		    	   System.out.println("attribute");
		    	   row.setNodeValue("type=\"" + value.toString() + "\"");
		       }
		      }
		    }
		   
		    DOMSource domSource = new DOMSource(doc);
		    TransformerFactory tf = TransformerFactory.newInstance();
		    Transformer transformer = tf.newTransformer();
		    transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, "yes");
		    transformer.setOutputProperty(OutputKeys.METHOD, "xml");
		    transformer.setOutputProperty(OutputKeys.ENCODING, "ISO-8859-1");
		    StringWriter sw = new StringWriter();
		    StreamResult sr = new StreamResult(sw);
		    transformer.transform(domSource, sr);
		    string = sw.toString();
		    
		    BufferedWriter bw = new BufferedWriter(fw);
		    bw.write(string);
		    bw.flush();
		    bw.close();
		    rs.close();
		    conexion.close();
		    System.out.println("Registro exportado.");
	} 
	
	/**
	 * Lee un archivo en formato ".xml" y obtiene todos los datos en ella. En caso de que 
	 * reciba un texto en otro formato lanzará una excepción que recogerá {@code Menu} en
	 * el método main. Para insertar los datos necesita ayuda del método {@code insertData} de 
	 * tipo {@code private void} para simplificar el proceso.
	 * 
	 * @param conexion
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DOMException
	 * @throws SQLException
	 */
	public static void importarRegistro(Connection conexion) throws ParserConfigurationException, SAXException, IOException, DOMException, SQLException {
		
		int opcion;
		do {
			System.out.println("Elija el módo de ruta:\n1) Escribir la ruta manualmente.\n2) Empezar desde el escritorio.");
			opcion = Console.readInt();
			if(opcion == 1) {
				System.out.println("Escriba la ruta: ");
				path = Console.readString();
			} else if (opcion == 2) {
				path = System.getProperty("user.home") + "/Desktop/";
				System.out.println("Escriba el nombre del archivo con la extensión \".xml\"");
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
					
					String carga = (String) dataVehiculo.get(7);
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
		conexion.close();
	}

}