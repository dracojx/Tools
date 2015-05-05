package exceltoxsd.v1;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Collection;
import java.util.Iterator;

public class WriteXsd {
	public static String getXsd(String dataType, String namespace,
			Collection<Field> fields) {
		StringBuffer sb = new StringBuffer();
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");
		
		sb.append("<xsd:schema targetNamespace=\"").append(namespace).append("\" ");
		sb.append("xmlns=\"").append(namespace).append("\" ");
		sb.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");
		
		sb.append("<xsd:complexType name=\"").append(dataType).append("\">");
		
		sb.append("<xsd:sequence>");
		Iterator<Field> it = fields.iterator();
		while(it.hasNext()) {
			sb.append(it.next().toString());
		}
		sb.append("</xsd:sequence></xsd:complexType></xsd:schema>");
		
		return sb.toString();
	}
	
	public static void writeFile(String filename, String xsd) throws IOException {
		FileOutputStream fos = new FileOutputStream(filename);
		BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
		out.write(xsd);
		out.flush();
		out.close();
	}
}
