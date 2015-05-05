package exceltoxsd.v3;

public class Execute {

	public static void main(String[] args) {
		String fileName = "Template.xlsx";
		if(args.length > 0) {
			fileName = args[1];
		}
		ExcelReader er = new ExcelReader(fileName);
		XsdWriter xw = new XsdWriter(er.getDataTypes());
		xw.write();
	}

}
