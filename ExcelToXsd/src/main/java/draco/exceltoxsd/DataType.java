package draco.exceltoxsd;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DataType {
	private String namespace;
	private String name;
	private String remark;
	private Map<String, Field> fields;
	private String fileName;

	public DataType(Sheet sheet) {
		namespace = getNamespace(sheet);
		name = getName(sheet);
		remark = getRemark(sheet);
		fields = getFields(sheet);
		fileName = name + ".xsd";
	}

	public String getFileName() {
		return fileName;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		// XML
		sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>");

		// schema
		sb.append("<xsd:schema targetNamespace=\"").append(namespace).append("\" ");
		sb.append("xmlns=\"").append(namespace).append("\" ");
		sb.append("xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">");

		// complexType
		sb.append("<xsd:complexType name=\"").append(name).append("\">");

		// remark
		if (remark != null) {
			sb.append("<xsd:annotation>");
			sb.append("<xsd:documentation>").append(remark).append("</xsd:documentation>");
			sb.append("</xsd:annotation>");
		}

		// sequence
		sb.append("<xsd:sequence>");
		Iterator<Field> it = fields.values().iterator();
		while (it.hasNext()) {
			sb.append(it.next().toString());
		}
		sb.append("</xsd:sequence>");
		sb.append("</xsd:complexType>");
		sb.append("</xsd:schema>");
		return sb.toString();
	}

	private String getNamespace(Sheet sheet) {
		return sheet.getRow(0).getCell(0).getStringCellValue();
	}

	private String getName(Sheet sheet) {
		String name = sheet.getSheetName();
		int index = name.indexOf(".");

		if (index > 0) {
			name = name.substring(0, index);
		}

		return name;
	}

	private String getRemark(Sheet sheet) {
		String remark = null;
		String sheetName = sheet.getSheetName();
		int index = sheetName.indexOf(".");
		if (index > 0 && index < sheetName.length() - 1) {
			remark = sheetName.substring(index + 1);
		}
		return remark;
	}
	
	private Map<String, Field> getFields(Sheet sheet) {
		Map<String, Field> fields = new LinkedHashMap<String, Field>();
		
		int last = sheet.getLastRowNum();
		
		for(int i = 2; i <= last; i++) {
			Row row = sheet.getRow(i);
			if(!row.getZeroHeight()) {
				Field field = new Field(row);
				addFieldToParent(fields, field);
			}
		}
		
		return fields;
	}
	
	private void addFieldToParent(Map<String, Field> fields, Field field) {
		String[] parents = field.getParents();
		Field parent = null;
		for(int i = 0; i < parents.length; i++) {
			parent = fields.get(parents[i]);
			if(parent == null) {
				parent = new Field(parents[i]);
				fields.put(parents[i], parent);
			}
			fields = parent.getChildren();
		}
		if(parent != null && parents.length > 1) {
			parent.setMulti(true);
		}
		fields.put(field.getName(), field);
	}
}
