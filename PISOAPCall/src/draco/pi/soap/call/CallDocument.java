package draco.pi.soap.call;

import java.util.Date;

import org.apache.axiom.om.OMAbstractFactory;
import org.apache.axiom.om.OMElement;
import org.apache.axiom.om.OMFactory;
import org.apache.axiom.om.OMNamespace;
import org.apache.axis2.AxisFault;
import org.apache.axis2.addressing.EndpointReference;
import org.apache.axis2.client.Options;
import org.apache.axis2.client.ServiceClient;
import org.apache.axis2.transport.http.HTTPConstants;

import draco.pi.soap.util.Constants;
import draco.pi.soap.util.DateUtil;
import draco.pi.soap.util.UUIDUtil;

public class CallDocument {

	public static void main(String[] args) {
		testDocument();
	}
	
	private static void testDocument() {
		EndpointReference endpoint = new EndpointReference(Constants.TARGET);

		Options opt = new Options();
		opt.setTo(endpoint);
		opt.setProperty(HTTPConstants.AUTHENTICATE, Constants.AUTH);
		
		try {
			ServiceClient client = new ServiceClient();
			client.setOptions(opt);
			OMElement response = client.sendReceive(createRequestData());
			
			System.out.println(response.toString());
			
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private static OMElement createRequestData() {
		OMFactory factory = OMAbstractFactory.getOMFactory();
		OMNamespace ns = factory.createOMNamespace(Constants.NAMESPACE, "n0");
		
		OMElement method = factory.createOMElement("MT_TEST001_Req", ns);
		
		OMElement header = factory.createOMElement("HEADER", null, method);
		OMElement xmlData = factory.createOMElement("XML_DATA", null, method);
		OMElement test001 = factory.createOMElement("TEST001", null, xmlData);
		
		
		OMElement interfaceId = factory.createOMElement("INTERFACE_ID", null, header);
		OMElement messageId = factory.createOMElement("MESSAGE_ID", null, header);
		OMElement sender = factory.createOMElement("SENDER", null, header);
		OMElement receiver = factory.createOMElement("RECEIVER", null, header);
		OMElement dtSend = factory.createOMElement("DTSEND", null, header);

		OMElement id = factory.createOMElement("ID", null, test001);
		OMElement amount = factory.createOMElement("AMOUNT", null, test001);
		OMElement date = factory.createOMElement("DATE", null, test001);
		
		interfaceId.setText("TEST001");
		messageId.setText(UUIDUtil.createUUID());
		sender.setText("TEST");
		receiver.setText("TEST");
		dtSend.setText(DateUtil.createDTSEND());
		
		id.setText("0123456");
		amount.setText("123");
		date.setText(DateUtil.format(new Date(), "yyyyMMdd"));
		
		method.build();
		
		return method;
	}

}
