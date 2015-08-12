package com.gome.test2;

import java.rmi.RemoteException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.axis2.AxisFault;
import org.apache.axis2.client.Options;
import org.apache.axis2.transport.http.HTTPConstants;
import org.apache.axis2.transport.http.HttpTransportProperties;

import com.gome.Constants;

public class Call {
	public static void main(String[] args) {
		callWebservice();
	}
	
	public static void callWebservice() {
		HttpTransportProperties.Authenticator authenticator = new HttpTransportProperties.Authenticator();
		authenticator.setUsername(Constants.USERNAME);
		authenticator.setPassword(Constants.PASSWORD);
		
		try {
			BC_TEST_SI_TEST_Out_AsyStub s = new BC_TEST_SI_TEST_Out_AsyStub();
			Options opt = s._getServiceClient().getOptions();
			opt.setProperty(HTTPConstants.SO_TIMEOUT, new Integer(1000 * 60 * 10));
			opt.setProperty(HTTPConstants.AUTHENTICATE, authenticator);
			
			s.sI_TEST_Out_Asy(createMtTestReq());
		} catch (AxisFault e) {
			e.printStackTrace();
		} catch (RemoteException e) {
			e.printStackTrace();
		}
	}
	
	private static BC_TEST_SI_TEST_Out_AsyStub.MT_TEST_Req createMtTestReq() {
		BC_TEST_SI_TEST_Out_AsyStub.MT_TEST_Req mtTestReq = new BC_TEST_SI_TEST_Out_AsyStub.MT_TEST_Req();
		BC_TEST_SI_TEST_Out_AsyStub.DT_TEST_Req dtTestReq = new BC_TEST_SI_TEST_Out_AsyStub.DT_TEST_Req();
		BC_TEST_SI_TEST_Out_AsyStub.HEADER_type0 header = new BC_TEST_SI_TEST_Out_AsyStub.HEADER_type0();
		BC_TEST_SI_TEST_Out_AsyStub.XML_DATA_type0 xmlData = new BC_TEST_SI_TEST_Out_AsyStub.XML_DATA_type0();
		
		dtTestReq.setHEADER(header);
		dtTestReq.setXML_DATA(xmlData);
		mtTestReq.setMT_TEST_Req(dtTestReq);
		
		header.setINTERFACE_ID("TEST001");
		header.setDTSEND(now());
		
		for(int i = 1; i <= 10; i++) {
			BC_TEST_SI_TEST_Out_AsyStub.TEST001_type0 test001 = new BC_TEST_SI_TEST_Out_AsyStub.TEST001_type0();
			test001.setA("ItemA " + i);
			test001.setB("ItemB " + i);
			xmlData.addTEST001(test001);
		}
		
		return mtTestReq;
	}
	
	private static String now() {
		DateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");
		Date date = new Date();
		return df.format(date);
	}
}
