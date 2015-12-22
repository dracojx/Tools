package draco.pi.http.client;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class TEST001 {
	public static void main(String[] args) {
		String xml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
					+ "<ns0:MT_TEST001_Req xmlns:ns0=\"http://test2.gome.com\">"
					+ "<HEADER><INTERFACE_ID/><MESSAGE_ID/><SENDER/><RECEIVER/><DTSEND/></HEADER>"
					+ "<XML_DATA><TEST001><ID/><AMOUNT/><DATE/></TEST001></XML_DATA>"
					+ "</ns0:MT_TEST001_Req>";
		String target = "http://10.128.11.6:50000/sap/xi/adapter_plain"
					+ "?namespace=http://test2.gome.com&interface=SI_TEST001_Out&service=BC_TEST&party=&agency=&scheme="
					+ "&QOS=BE&sap-user=jingxuan&sap-password=Abc1234&sap-client=001&sap-language=EN";
		try {
			URL url = new URL(target);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setDoInput(true);
			conn.setRequestMethod("POST");
			conn.setUseCaches(false);
			conn.setInstanceFollowRedirects(true);
			conn.setRequestProperty("Content-Type", "application/xml");
			conn.connect();
			DataOutputStream out = new DataOutputStream(conn.getOutputStream());
			out.writeBytes(xml);
			out.flush();
			out.close();
			
			System.out.println("Status Code: " + conn.getResponseCode());
			System.out.println("Status Message: " + conn.getResponseMessage());
			System.out.println("============Response============");
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while((line=br.readLine()) != null) {
				System.out.println(line);
			}
			
			conn.disconnect();
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
