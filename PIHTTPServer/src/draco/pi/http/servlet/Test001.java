package draco.pi.http.servlet;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Writer;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import draco.pi.http.util.DateUtil;
import draco.pi.http.util.UUIDUtil;

/**
 * Servlet implementation class Test001
 */
@WebServlet("/Test001")
public class Test001 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Test001() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuilder sb = new StringBuilder();
		BufferedReader br = request.getReader();
		String line;
		while((line = br.readLine()) != null) {
			sb.append(line);
		}
		
		String url = request.getRequestURL().toString();
		String requestXml = sb.toString();
		System.out.println(url);
		System.out.println(requestXml);
		
		String responseXml = "<?xml version=\"1.0\" encoding=\"UTF-8\"?>"
							+"<ns0:MT_TEST001_Req xmlns:ns0=\"http://test2.gome.com\">"
							+"<HEADER>"
							+"<INTERFACE_ID>TEST001</INTERFACE_ID>"
							+"<MESSAGE_ID>" + UUIDUtil.createUUID() + "</MESSAGE_ID>"
							+"<SENDER>TEST</SENDER>"
							+"<RECEIVER>TEST</RECEIVER>"
							+"<DTSEND>" + DateUtil.createDTSEND() + "</DTSEND>"
							+"</HEADER>"
							+"<XML_DATA>"
							+"<TEST001>"
							+"<ID>0123456</ID>"
							+"<AMOUNT>100</AMOUNT>"
							+"<DATE>20151125</DATE>"
							+"</TEST001>"
							+"</XML_DATA>"
							+"</ns0:MT_TEST001_Req>";
		
		response.setContentType("text/xml;charset=UTF-8");
		Writer out = response.getWriter();
		out.write(responseXml);
		out.close();
	}

}
