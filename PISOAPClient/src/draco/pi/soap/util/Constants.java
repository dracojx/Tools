package draco.pi.soap.util;

import org.apache.axis2.transport.http.HttpTransportProperties.Authenticator;

public class Constants {
	public static final Authenticator AUTH = auth();
	public static final String USERNAME = "jingxuan";
	public static final String PASSWORD = "Abc1234";
	public static final String TARGET = "http://dp0.gome.com:50000/XISOAPAdapter/MessageServlet?senderParty=&senderService=BC_TEST&receiverParty=&receiverService=&interface=SI_TEST001_Out&interfaceNamespace=http%3A%2F%2Ftest2.gome.com";
	public static final String NAMESPACE = "http://test2.gome.com";


	private static Authenticator auth() {
		Authenticator authenticator = new Authenticator();
		authenticator.setUsername(Constants.USERNAME);
		authenticator.setPassword(Constants.PASSWORD);
		return authenticator;
	}
}
