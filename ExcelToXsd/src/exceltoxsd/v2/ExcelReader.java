package exceltoxsd.v2;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	private Workbook wb;

	public static void main(String[] args) {
		String file = "Templatev2.xlsx";
		ExcelReader er = new ExcelReader(file);
		DataType[] dts = er.getDataTypes();
		for(int i = 0; i < dts.length; i++) {
			System.out.println(dts[i].toString());
		}
	}
	
	public ExcelReader(String file) {
		wb = getWorkbook(file);
	}
	
	public DataType[] getDataTypes() {
		int size = wb.getNumberOfSheets();
		DataType[] dts = new DataType[size];
		for(int i = 0; i < size; i++) {
			dts[i] = getDataType(i);
		}
		return dts;
	}

	private Workbook getWorkbook(String file) {
		try {
			InputStream is = new FileInputStream(file);
			return WorkbookFactory.create(is);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvalidFormatException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	private DataType getDataType(int index) {
		DataType dt = new DataType(wb.getSheetAt(index));
		return dt;
	}

}
