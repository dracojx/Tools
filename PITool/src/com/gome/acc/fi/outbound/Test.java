package com.gome.acc.fi.outbound;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private static SI_FI_Out_AsyServiceStub.MT_FI_Req create() {
		SI_FI_Out_AsyServiceStub.MT_FI_Req req = new SI_FI_Out_AsyServiceStub.MT_FI_Req();
		SI_FI_Out_AsyServiceStub.DT_FI_Req dt = new SI_FI_Out_AsyServiceStub.DT_FI_Req();
		SI_FI_Out_AsyServiceStub.DT_HEADER header = new SI_FI_Out_AsyServiceStub.DT_HEADER();
		SI_FI_Out_AsyServiceStub.XML_DATA_type0 xmlData = new SI_FI_Out_AsyServiceStub.XML_DATA_type0();
		SI_FI_Out_AsyServiceStub.DT_FI008 fi008 = new SI_FI_Out_AsyServiceStub.DT_FI008();
		
		
		SI_FI_Out_AsyServiceStub.INTERFACE_ID_type1 interfaceId = new SI_FI_Out_AsyServiceStub.INTERFACE_ID_type1();
		interfaceId.setINTERFACE_ID_type0("FI008");
		header.setINTERFACE_ID(interfaceId);
		xmlData.setFI008(fi008);
		
		dt.setHEADER(header);
		dt.setXML_DATA(xmlData);
		
		
		req.setMT_FI_Req(dt);
		
		return req;
	}
}
