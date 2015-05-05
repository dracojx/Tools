package exceltoxsd.v1;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Collection;

import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;


public class Execute {
	public static final String PATH = "Template.xls";

	public static void main(String[] args) {
		Workbook wb = ReadExcel.getWorkbook(PATH);
		for(int i = 0; i < wb.getNumberOfSheets(); i++) {
			Sheet sheet = wb.getSheetAt(i);
			String dataType = ReadExcel.getDataType(sheet);
			String namespace = ReadExcel.getNamespace(sheet);
			Collection<Field> fields = ReadExcel.getFields(sheet);
			String filename = dataType + ".xsd";
			String xsd = WriteXsd.getXsd(dataType, namespace, fields);
			try {
				WriteXsd.writeFile(filename, xsd);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (Exception e) {
				try {
					String error = "Write File + " + filename + " Fail!";
					System.err.println(error);
					BufferedWriter out = new BufferedWriter(new FileWriter("error.txt"));
					out.write(error);
					out.flush();
					out.close();
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			System.out.println("Write File " + filename + " Success!");
		}
	}

}
