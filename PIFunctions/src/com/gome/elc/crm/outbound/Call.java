package com.gome.elc.crm.outbound;

import org.apache.axis2.AxisFault;

public class Call {

	public static void main(String[] args) {
		BS_ELC_SI_ELC072_Out_AsyStub.DT_ELC072 dt = new BS_ELC_SI_ELC072_Out_AsyStub.DT_ELC072();
		BS_ELC_SI_ELC072_Out_AsyStub.ELC072_type0 elc072 = new BS_ELC_SI_ELC072_Out_AsyStub.ELC072_type0();
		BS_ELC_SI_ELC072_Out_AsyStub.ITEMS_type0 items = new BS_ELC_SI_ELC072_Out_AsyStub.ITEMS_type0();
		BS_ELC_SI_ELC072_Out_AsyStub.ITEM_type0 item = new BS_ELC_SI_ELC072_Out_AsyStub.ITEM_type0();
		
		BS_ELC_SI_ELC072_Out_AsyStub.DATE1_type1 date1 = new BS_ELC_SI_ELC072_Out_AsyStub.DATE1_type1();
		date1.setDATE1_type0("date string");
		item.setDATE1(date1);
		items.addITEM(item);
		
		elc072.setITEMS(items);
		dt.addELC072(elc072);
	}

}
