package draco.pi.soap.call;

import java.rmi.RemoteException;
import java.util.Date;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;

import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.DT_TEST001;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.HEADER_type0;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.MT_TEST001_Req;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.TEST001_type0;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.XML_DATA_type0;
import draco.pi.soap.util.Constants;
import draco.pi.soap.util.ConvertUtil;
import draco.pi.soap.util.DateUtil;
import draco.pi.soap.util.UUIDUtil;

public class CallStub {

	public static void main(String[] args) {
		testStub();
	}
	
	private static void testStub() {
		try {
			BC_TEST_SI_TEST001_OutStub stub = new BC_TEST_SI_TEST001_OutStub(Constants.TARGET);
			Options opt = stub._getServiceClient().getOptions();
			opt.setProperty(HTTPConstants.AUTHENTICATE, Constants.AUTH);
			
			MT_TEST001_Req response = stub.sI_TEST001_Out(createRequestData());
			
			System.out.println(parseResponseData(response));
			
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static MT_TEST001_Req createRequestData() {
		MT_TEST001_Req mtTest001Req = new MT_TEST001_Req();
		
		DT_TEST001 dtTest001 = new DT_TEST001();
		
		HEADER_type0 header = new HEADER_type0();
		header.setINTERFACE_ID(ConvertUtil.interfaceId("TEST001"));
		header.setMESSAGE_ID(ConvertUtil.messageId(UUIDUtil.createUUID()));
		header.setSENDER(ConvertUtil.sender("TEST"));
		header.setRECEIVER(ConvertUtil.receiver("TEST"));
		header.setDTSEND(ConvertUtil.dtSend(DateUtil.createDTSEND()));
		
		XML_DATA_type0 xmlData = new XML_DATA_type0();
		
		for(int i = 0; i < 10; i++) {
			TEST001_type0 test001 = new TEST001_type0();
			test001.setID(ConvertUtil.test001Id(Integer.toString(i+1)));
			test001.setAMOUNT(ConvertUtil.test001Amount(Integer.toString((i+1)*111)));
			test001.setDATE(ConvertUtil.test001Date(DateUtil.format(new Date(), "yyyyMMdd")));
			xmlData.addTEST001(test001);
		}
		
		dtTest001.setHEADER(header);
		dtTest001.setXML_DATA(xmlData);
		mtTest001Req.setMT_TEST001_Req(dtTest001);
		
		return mtTest001Req;
	}
	
	private static String parseResponseData(MT_TEST001_Req mtTest001Req) {
		StringBuffer sb = new StringBuffer();
		DT_TEST001 dtTest001 = mtTest001Req.getMT_TEST001_Req();
		HEADER_type0 header = dtTest001.getHEADER();
		sb.append("\nHEADER:\n");
		sb.append("INTERFACE_ID:").append(header.getINTERFACE_ID().getINTERFACE_ID_type0()).append("\n");
		sb.append("MESSAGE_ID:").append(header.getMESSAGE_ID().getMESSAGE_ID_type0()).append("\n");	
		sb.append("SENDER:").append(header.getSENDER().getSENDER_type0()).append("\n");	
		sb.append("RECEIVER:").append(header.getRECEIVER().getRECEIVER_type0()).append("\n");
		sb.append("DTSEND:").append(header.getDTSEND().getDTSEND_type0()).append("\n");	
		
		XML_DATA_type0 xmlData = dtTest001.getXML_DATA();
		TEST001_type0[] test001Array = xmlData.getTEST001();
		for(int i = 0; i < test001Array.length; i++) {
			TEST001_type0 test001 = test001Array[i];
			sb.append("\nTEST001:\n");
			sb.append("ID:").append(test001.getID().getID_type0()).append("\n");
			sb.append("AMOUNT:").append(test001.getAMOUNT().getAMOUNT_type0()).append("\n");	
			sb.append("DATE:").append(test001.getDATE().getDATE_type0()).append("\n");
		}
		return sb.toString();
	}

}
