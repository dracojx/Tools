package draco.pi.soap.util;

import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.AMOUNT_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.DATE_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.DTSEND_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.ID_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.INTERFACE_ID_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.MESSAGE_ID_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.RECEIVER_type1;
import draco.pi.soap.client.BC_TEST_SI_TEST001_OutStub.SENDER_type1;

public class ConvertUtil {
	public static INTERFACE_ID_type1 interfaceId(String param) {
		INTERFACE_ID_type1 type1 = new INTERFACE_ID_type1();
		type1.setINTERFACE_ID_type0(param);
		return type1;
	}
	
	public static MESSAGE_ID_type1 messageId(String param) {
		MESSAGE_ID_type1 type1 = new MESSAGE_ID_type1();
		type1.setMESSAGE_ID_type0(param);
		return type1;
	}
	
	public static SENDER_type1 sender(String param) {
		SENDER_type1 type1 = new SENDER_type1();
		type1.setSENDER_type0(param);
		return type1;
	}
	
	public static RECEIVER_type1 receiver(String param) {
		RECEIVER_type1 type1 = new RECEIVER_type1();
		type1.setRECEIVER_type0(param);
		return type1;
	}
	
	public static DTSEND_type1 dtSend(String param) {
		DTSEND_type1 type1 = new DTSEND_type1();
		type1.setDTSEND_type0(param);
		return type1;
	}
	
	public static ID_type1 test001Id(String param) {
		ID_type1 type1 = new ID_type1();
		type1.setID_type0(param);
		return type1;
	}
	
	public static AMOUNT_type1 test001Amount(String param) {
		AMOUNT_type1 type1 = new AMOUNT_type1();
		type1.setAMOUNT_type0(param);
		return type1;
	}
	
	public static DATE_type1 test001Date(String param) {
		DATE_type1 type1 = new DATE_type1();
		type1.setDATE_type0(param);
		return type1;
	}
	
}
