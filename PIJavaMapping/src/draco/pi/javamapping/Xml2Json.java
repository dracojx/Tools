package draco.pi.javamapping;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.xml.XMLSerializer;

import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class Xml2Json extends AbstractTransformation {

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {

		InputStream is = input.getInputPayload().getInputStream();
		Scanner scanner = new Scanner(is, "UTF-8");
		String xmlString = scanner.useDelimiter("\\A").next();

		JSONObject json = (JSONObject) new XMLSerializer().read(xmlString);

		String jsonString = json.toString().replace("[]", "\"\"");

		output.getOutputHeader().setContentType(
				"application/json;charset=UTF-8");
		try {
			output.getOutputPayload().getOutputStream()
					.write(jsonString.getBytes("UTF-8"));
		} catch (IOException e) {
			getTrace().addWarning(e.getStackTrace().toString());
		} finally {
			scanner.close();
		}
	}

	public static void main(String[] args) {
		md021();
	}

	public static void md021() {
		String xmlString = "<MT_MDM_Req>"
				+ "<header>"
				+ "<interface_id>md021</interface_id>"
				+ "<message_id>0123456789abcdef</message_id>"
				+ "</header>"
				+ "<mainData>"
				+ "<md021>"
				+ "<item><a>a1</a><b>b1</b></item>"
				+ "</md021></mainData>"
				+ "</MT_MDM_Req>";
		InputStream is = new ByteArrayInputStream(xmlString.getBytes());
		Scanner scanner = new Scanner(is, "UTF-8");
		String str = scanner.useDelimiter("\\A").next();

		JSONObject json = (JSONObject) new XMLSerializer().read(str);
		Object md021 = json.getJSONObject("mainData").get("md021");
		if(md021 instanceof JSONObject) {
			JSONObject obj = (JSONObject) md021;
			JSONArray array = new JSONArray();
			array.add(obj.getJSONObject("item"));
			json.getJSONObject("mainData").put("md021", array);
			
		}

		String jsonString = json.toString().replace("[]", "\"\"");
		System.out.println(jsonString);
	}

}
