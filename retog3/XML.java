package retog3;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringWriter;
import java.sql.Connection;
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

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.*;

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
		    System.out.println("Registro importado.");
	} 

	
	public static void importarRegistro(ResultSet rs, Connection conexion) throws ParserConfigurationException, SAXException, IOException {
		
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
			
		doc.getDocumentElement().normalize();

		System.out.println("Root element :" + doc.getDocumentElement().getNodeName());
				
		NodeList nList = doc.getElementsByTagName("staff");
				
		System.out.println("----------------------------");
  
		for (int temp = 0; temp < nList.getLength(); temp++) 
		
			Node nNode = nList.item(temp);
		
					
			System.out.println("\nCurrent Element :" + nNode.getNodeName());
					
			
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {

				Element eElement = (Element) nNode;

				System.out.println("Staff id : " + eElement.getAttribute("id"));
				System.out.println("First Name : " + eElement.getElementsByTagName("firstname").item(0).getTextContent());
				System.out.println("Last Name : " + eElement.getElementsByTagName("lastname").item(0).getTextContent());
				System.out.println("Nick Name : " + eElement.getElementsByTagName("nickname").item(0).getTextContent());
				System.out.println("Salary : " + eElement.getElementsByTagName("salary").item(0).getTextContent());
	}
}