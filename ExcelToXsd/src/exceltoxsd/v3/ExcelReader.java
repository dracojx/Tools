package exceltoxsd.v3;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ExcelReader {
	private List<DataType> dataTypes;
	
	public ExcelReader(String path) {
		try {
			InputStream is = new FileInputStream(path);
			Workbook wb = WorkbookFactory.create(is);
			dataTypes = getDataTypes(wb);
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
	}
	
	public List<DataType> getDataTypes() {
		return dataTypes;
	}

	private List<DataType> getDataTypes(Workbook wb) {
		List<DataType> dataTypes = new ArrayList<DataType>();
		
		int max = wb.getNumberOfSheets();
		for(int i = 0; i < max; i++) {
			if(!wb.isSheetHidden(i)) {
				Sheet sheet = wb.getSheetAt(i);
				DataType dataType = new DataType(sheet);
				dataTypes.add(dataType);
			}
		}
		
		return dataTypes;
	}
}
