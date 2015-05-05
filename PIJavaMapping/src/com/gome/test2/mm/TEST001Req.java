package com.gome.test2.mm;

import java.io.InputStream;
import java.util.List;

import javax.xml.bind.JAXBElement;

import com.gome.test2.function.XmlUtil;
import com.gome.test2.mt.test.req.DTTESTReq;
import com.gome.test2.mt.test.req.ObjectFactory;
import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;

public class TEST001Req extends AbstractTransformation {

	@Override
	public void transform(TransformationInput input, TransformationOutput output)
			throws StreamTransformationException {
		InputStream is = input.getInputPayload().getInputStream();
		DTTESTReq reqIn = XmlUtil.unmarshal(is, DTTESTReq.class);

		try {
			if(reqIn.getHEADER() == null) {
				throw new Exception("HEADER MISSING");
			}
			if(reqIn.getXMLDATA() == null ) {
				throw new Exception("XMLDATA MISSING");
			}
			if(reqIn.getXMLDATA().getTEST001().isEmpty()) {
				throw new Exception("TEST001 MISSING");
			}

			ObjectFactory factory = new ObjectFactory();
			DTTESTReq reqOut = factory.createDTTESTReq();
			
			reqOut.setHEADER(reqIn.getHEADER());
			reqOut.setXMLDATA(factory.createDTTESTReqXMLDATA());

			List<DTTESTReq.XMLDATA.TEST001> test001In = reqIn.getXMLDATA().getTEST001();
			List<DTTESTReq.XMLDATA.TEST001> test001Out = reqOut.getXMLDATA().getTEST001();

			StringBuffer sbA = new StringBuffer();
			StringBuffer sbB = new StringBuffer();
			for (int i = 0; i < test001In.size(); i++) {
				DTTESTReq.XMLDATA.TEST001 it = test001In.get(i);
				sbA.append(" ").append(it.getA());
				sbB.append(" ").append(it.getB());
			}

			DTTESTReq.XMLDATA.TEST001 itOut = factory.createDTTESTReqXMLDATATEST001();
			itOut.setA(sbA.toString().trim());
			itOut.setB(sbB.toString().trim());
			test001Out.add(itOut);
			JAXBElement<DTTESTReq> element = factory.createMTTESTReq(reqOut);

			XmlUtil.marshal(element, DTTESTReq.class, output.getOutputPayload().getOutputStream());
		} catch (Exception e) {
			ObjectFactory factory = new ObjectFactory();
			DTTESTReq reqOut = factory.createDTTESTReq();
			JAXBElement<DTTESTReq> element = factory.createMTTESTReq(reqOut);
			XmlUtil.marshal(element, DTTESTReq.class, output.getOutputPayload().getOutputStream());
			getTrace().addWarning(e.getMessage());
		}
	}

}
