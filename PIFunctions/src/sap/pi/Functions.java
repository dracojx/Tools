package sap.pi;

public class Functions {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	public static String split(String separator, int index, String str) {
		String[] values = str.split(separator);
		return index < values.length ? values[index] : "";
	}

}
