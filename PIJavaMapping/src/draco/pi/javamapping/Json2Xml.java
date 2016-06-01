package draco.pi.javamapping;

import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

public class Json2Xml extends AbstractTransformation {

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {

		InputStream is = input.getInputPayload().getInputStream();
		Scanner scanner = new Scanner(is, "UTF-8");
		String jsonString = scanner.useDelimiter("\\A").next();
		
		JSONObject json = JSONObject.fromObject(jsonString);
		
		XMLSerializer xml = new XMLSerializer();
		xml.setRootName("MT_SCF016_Resp");
		xml.setTypeHintsEnabled(false);
		String xmlString = xml.write(json);
		
		try {
			output.getOutputPayload().getOutputStream().write(xmlString.getBytes());
		} catch (IOException e) {
			getTrace().addWarning(e.getStackTrace().toString());
		} finally {
			scanner.close();
		}
	}

	public static void main(String[] args) {
		String jsonString = "{\"head\":{\"interfaceId\":\"001\",\"messageId\":\"****uuid****\"},"
				+ "\"body\":{\"tranStatus\":\"S\",\"tranResult\":\"返回结果\"}}";

		JSONObject json = JSONObject.fromObject(jsonString);
		
		XMLSerializer xml = new XMLSerializer();
		xml.setRootName("MT_SCF016_Resp");
		xml.setTypeHintsEnabled(false);
		
		
		String xmlString = xml.write(json);
		System.out.println(xmlString);
	}

}
