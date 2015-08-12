package com.gome.vdp.mdm.outbound;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;
import org.apache.axis2.transport.http.HttpTransportProperties;

import com.gome.Constants;

public class Call {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	

	public static void callWebservice() {
		HttpTransportProperties.Authenticator authenticator = new HttpTransportProperties.Authenticator();
		authenticator.setUsername(Constants.USERNAME);
		authenticator.setPassword(Constants.PASSWORD);
		
		try {
			SI_MD001_Out_SynServiceStub stub = new SI_MD001_Out_SynServiceStub();
			SI_MD001_Out_SynServiceStub.MT_MD001_Resp resp = stub.sI_MD001_Out_Syn(createReq());
		} catch (AxisFault e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (RemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static SI_MD001_Out_SynServiceStub.MT_MD001_Req createReq() {
		
		SI_MD001_Out_SynServiceStub.MT_MD001_Req mt = new SI_MD001_Out_SynServiceStub.MT_MD001_Req();
		SI_MD001_Out_SynServiceStub.DT_MD001_Req dtReq = new SI_MD001_Out_SynServiceStub.DT_MD001_Req();
		SI_MD001_Out_SynServiceStub.DT_HEADER header = new SI_MD001_Out_SynServiceStub.DT_HEADER();
		SI_MD001_Out_SynServiceStub.MD001_type1 md001 = new SI_MD001_Out_SynServiceStub.MD001_type1();
		dtReq.setHEADER(header);
		dtReq.setMD001(md001);
		mt.setMT_MD001_Req(dtReq);
		
		
		
		SI_MD001_Out_SynServiceStub.CorpName_type1 corpName = new SI_MD001_Out_SynServiceStub.CorpName_type1();
		corpName.setCorpName_type0("公司名称");
		md001.setCorpName(corpName);
		
		
		
		
		return mt;
	}

}
