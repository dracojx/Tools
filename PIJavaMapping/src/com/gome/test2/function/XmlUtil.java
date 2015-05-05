package com.gome.test2.function;

import java.io.InputStream;
import java.io.OutputStream;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBElement;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.transform.Source;
import javax.xml.transform.stream.StreamSource;

public class XmlUtil {
	public static <T> T unmarshal(Source source, Class<T> c) {
		try {
			JAXBContext jc = JAXBContext.newInstance(c);
			Unmarshaller um = jc.createUnmarshaller();
			return um.unmarshal(source, c).getValue();
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static <T> T unmarshal(InputStream is, Class<T> c) {
		Source source = new StreamSource(is);
		return unmarshal(source, c);
	}
	
	public static <T> void marshal(JAXBElement<T> element, Class<T> c, OutputStream os) {
		try {
			JAXBContext jc = JAXBContext.newInstance(c);
			Marshaller ma = jc.createMarshaller();
			ma.marshal(element, os);
		} catch (JAXBException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
