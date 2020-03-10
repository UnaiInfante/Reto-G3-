package database;

import java.io.*;
import javax.xml.transform.*;

import java.sql.Connection;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.Formatter;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.dom.DOMSource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import com.mysql.jdbc.ResultSet;

public class XML {
	
	static private String stringRegistro;
	static private String path = System.getProperty("user.home") + "\\Desktop\\registro.xml";


	public static void exportarRegistro(ResultSet rs, Connection conexion) throws SQLException, ParserConfigurationException, TransformerException, InstantiationException, IllegalAccessException, IOException {
		
		Formatter file = new Formatter(path);
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
		    stringRegistro = sw.toString();
		    
			BufferedWriter writer = new BufferedWriter (new FileWriter(path));
			writer.write(stringRegistro);
			writer.close();
			System.out.println("Registro exportado.\n");
			
			conexion.close();
		    rs.close();
		    file.close();
	}
	
	public static String importarRegistro() {
		return null;
	}
}