package draco.pi.javamapping;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Scanner;

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

		getTrace().addDebugMessage(xmlString);
		getTrace().addDebugMessage(jsonString);

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
		String xmlString = "<?xml version=\"1.0\" encoding=\"utf-8\"?><MT_FI_Req>	<HEADER>		<INTERFACE_ID>FI206</INTERFACE_ID>		<MESSAGE_ID>53B01D572AA59C62E10000000A800A6A</MESSAGE_ID>		<SENDER>ECC</SENDER>		<RECEIVER>GMGJ</RECEIVER>		<DTSEND>20160426033931335</DTSEND>	</HEADER>	<XML_DATA>		<FI206>			<ZUONR>A04013170534137675391772</ZUONR>			<STATUS>S</STATUS>			<MESSAGE>凭证过帐成功: BKPFF 1400000006SA012016 PR0CLNT800</MESSAGE>			<BELNR>1400000006</BELNR>		</FI206>	</XML_DATA><MT_FI_Req>";
		InputStream is = new ByteArrayInputStream(xmlString.getBytes());
		Scanner scanner = new Scanner(is, "UTF-8");
		String str = scanner.useDelimiter("\\A").next();

		JSONObject json = (JSONObject) new XMLSerializer().read(str);

		String jsonString = json.toString().replace("[]", "\"\"");
		System.out.println(jsonString);

	}

}
