package exceltoxsd.v1;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

public class ReadExcel {
	
	public static Workbook getWorkbook(String path) {
		try {
			InputStream is = new FileInputStream(path);
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
	
	public static String getDataType(Sheet sheet) {
		return sheet.getSheetName();
	}
	
	public static String getNamespace(Sheet sheet) {
		return sheet.getRow(0).getCell(6).getStringCellValue();
	}
	
	public static Collection<Field> getFields(Sheet sheet) {
		Map<String, Field> map = new LinkedHashMap<String, Field>();
		for(int i = 1; i <= sheet.getLastRowNum(); i++) {
			Row row = sheet.getRow(i);
			String name = row.getCell(0).getStringCellValue().trim();
			String type = row.getCell(1).getStringCellValue();
			boolean required = row.getCell(3).getStringCellValue().equalsIgnoreCase("Y");
			String remark = row.getCell(4) == null ? null : row.getCell(4).getStringCellValue();
			Integer lengths[] = new Integer[0];
			Cell lengthCell = row.getCell(2);
			if(lengthCell != null) {
				if(lengthCell.getCellType() == Cell.CELL_TYPE_STRING) {
					String str = lengthCell.getStringCellValue();
					lengths = stringToInt(str);
				} else if(lengthCell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					lengths = new Integer[1];
					lengths[0] = (int)lengthCell.getNumericCellValue();
				}
			}
	
			Field field = new Field();
			field.setName(name);
			field.setType(type);
			field.setLengths(lengths);
			field.setRequired(required);
			field.setRemark(remark);
			
			if(row.getCell(5) != null) {
				String[] nodes = row.getCell(5).getStringCellValue().split("\\.");
				Field parent = getParent(map, nodes);
				if(nodes.length > 1 || sheet.getSheetName().indexOf(parent.getName()) > -1) {
					parent.setMulti(true);
				}
				parent.getMap().put(name, field);
			} else {
				map.put(name, field);
			}
			
		}
		return map.values();
	}
	
	private static Field getParent(Map<String, Field> map, String[] nodes) {
		return getParent(map, nodes, 0);
	}
	
	private static Field getParent(Map<String, Field> map, String[] nodes, int index) {
		String name = nodes[index];
		Field field = map.get(name);
		if(field == null) {
			field = new Field();
			field.setName(name);
			field.setRequired(true);
			field.setMap(new LinkedHashMap<String, Field>());
			map.put(name, field);
		}
		if(index + 1 < nodes.length) {
			return getParent(field.getMap(), nodes, index + 1);
		} else {
			return field;
		}
	}
	
	private static Integer[] stringToInt(String str) {
		String[] strs = str == null ? new String[0] : str.split(",");
		Integer[] ints = new Integer[strs.length];
		for(int i = 0; i < ints.length; i++) {
			ints[i] = Integer.parseInt(strs[i]);
		}
		return ints;
	}
}
