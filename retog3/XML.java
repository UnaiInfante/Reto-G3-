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
	
	/*
	 * Prueba: Serán eliminados más tarde
	 */
	private static String studentName;
	private static int studentAg;
	private static Integer studentId;
	
	private static String path = System.getProperty("user.home") + "/Desktop/registro.xml";
	
	/** Evitamos instanciar la clase con el constructor en {@code private}. */
	private XML() {}
	
	/**
	 * Importa un registro XML a un archivo a un lugar específico. Este proceso
	 * es automático: Crea un archivo con el registro o lo sobreescribe si ya existe
	 * uno.
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
	 * @throws ClassNotFoundException -
	 */
	public static void exportarRegistro(ResultSet rs, Connection conexion) throws SQLException, ParserConfigurationException, TransformerException, InstantiationException, IllegalAccessException, IOException {
		
		File file = new File(path);
		FileWriter fw = new FileWriter(file);
		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
	    DocumentBuilder builder = factory.newDocumentBuilder();
	    Document doc = builder.newDocument();
	    
	    Element results = doc.createElement("Results");
	    doc.appendChild(results);
	  	ResultSetMetaData rsmd = rs.getMetaData();
		   int colCount = rsmd.getColumnCount();

		   while (rs.next()) {
		      Element row = doc.createElement("Row");
		      results.appendChild(row);
		      for (int i = 1; i <= colCount; i++) {
		        String columnName = rsmd.getColumnName(i);
		        Object value = rs.getObject(i);
		        Element node = doc.createElement(columnName);
		        node.appendChild(doc.createTextNode(value.toString()));
		        row.appendChild(node);
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
		    System.out.println("Registro importado.");
	} 
	
	/**
	 * Lee un archivo en formato ".xml" y obtiene todos los datos en ella. En caso de que 
	 * reciba un texto en otro formato lanzará una excepción que recogerá {@code Menu} en
	 * el método main. Para insertar los datos necesita ayuda del método {@code insertData} de 
	 * tipo {@code private void} para simplificar el proceso.
	 * 
	 * @param conexion La conexión a donde enviará los datos.
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws SQLException 
	 * @throws DOMException 
	 */
	public static void importarRegistro(Connection conexion) throws ParserConfigurationException, SAXException, IOException, DOMException, SQLException {
		
		System.out.println("Escribe la dirección del archivo:");
		path = "C:\\Users\\usuario\\Desktop\\registro.xml";
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		Document doc = builder.parse(path);
		NodeList list = doc.getElementsByTagName("Row");
		
		for(int i = 0; i < list.getLength(); i++) {
			
			//Esta es la lista de donde se sacarán los datos. Representan una tupla de la tabla.
			Node node = list.item(i);
			
			if(node.getNodeType() == Node.ELEMENT_NODE) {
				
				//Creating the first nodes
				Element student = (Element) node;
				NodeList studentList = student.getChildNodes();
				System.out.println("----------------------");
				System.out.println("\nStudent: " + (i + 1)+ "\n");
				for(int j = 0; j < studentList.getLength(); j++) {
					
					//Creating the second nodes
					Node node2 = studentList.item(j);
					if(node2.getNodeType() == Node.ELEMENT_NODE) {
						Element studentAtt = (Element) node2;
						insertData(j, studentAtt, conexion);
					}
				}
			}
		}
		System.out.println("-------------------\n");
		System.out.println("Datos exportados a la base de datos.");
		conexion.close();
	}
	
	/**
	 * Método auxiliar que inserta los datos del archivo XML en forma de
	 * {@code Element} y se ejecutan las instrucciones con objetos de tipo
	 * {@code PreparedStatement.}
	 * 
	 * @param j La iteración en la que se inserta los datos
	 * @param studentAtt los datos del archivo XML
	 * @param conexion la conexión donde se enviarán los datos.
	 * @throws SQLException
	 */
	private static void insertData(int j, Element studentAtt, Connection conexion) throws SQLException {
		if(j == 0) {
			studentId = Integer.parseInt(studentAtt.getTextContent()); 
			String statement = "INSERT INTO student (studentId)"
					+ " VALUES (" + studentId.intValue() + ");";

			PreparedStatement ps = conexion.prepareStatement(statement);
			ps.executeUpdate();
			
		} else {

			String statement = "UPDATE student" 
					+ " SET " + studentAtt.getTagName() + "=" + "'" + studentAtt.getTextContent()  + "'"
					+ " WHERE " + "studentId" + " = " + studentId.intValue() + ";";
			
			PreparedStatement ps = conexion.prepareStatement(statement);
			ps.executeUpdate();
			
		}
	}
}