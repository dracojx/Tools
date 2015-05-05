package exceltoxsd.v2;

import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

public class DataType {
	private String name;
	private String namespace;
	private String remark;
	private Map<String, Field> fields;
	
	public DataType(Sheet sheet) {
		name = getName(sheet);
		namespace = getNamespace(sheet);
		remark = getRemark(sheet);
		fields = getFields(sheet);
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getNamespace() {
		return namespace;
	}
	public void setNamespace(String namespace) {
		this.namespace = namespace;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Map<String, Field> getFields() {
		return fields;
	}
	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}
	
	@Override
	public String toString() {
		/*
		StringBuffer sb = new StringBuffer();
		sb.append(namespace).append(".");
		sb.append(name);
		sb.append(" ").append(remark == null ? "" : remark).append("\n");
		Iterator<Field> it = fields.values().iterator();
		while(it.hasNext()) {
			Field field = it.next();
			sb.append(field.toString());
		}
		sb.append("\n\n");
		return sb.toString();
		*/
		
		
		StringBuffer sb = new StringBuffer();
		// xsd:schema
		sb.append("<xsd:schema xmlns=\"").append(namespace).append("\"");
		sb.append(" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\"");
		sb.append(" targetNamespace=\"").append(namespace).append("\">");
		
		
		// /xsd:schema
		sb.append("</xsd:schema>");
		return sb.toString();
	}

	private String getName(Sheet sheet) {
		String name = sheet.getSheetName();
		int index = name.indexOf(".");
		if(index > 0) {
			name = name.substring(0, index);
		}
		return name;
	}
	private String getNamespace(Sheet sheet) {
		Row row = sheet.getRow(0);
		String namespace = row.getCell(0).getStringCellValue();
		return namespace == null ? "" : namespace;
	}
	private String getRemark(Sheet sheet) {
		String remark = null;
		String sheetName = sheet.getSheetName();
		int index = sheetName.indexOf(".");
		if(index >= 0 && index < sheetName.length() - 1) {
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
				Field parent = getParent(fields, field.getParents(), field.isAttr());
				if(parent == null) {
					fields.put(field.getName(), field);
				} else if(field.isAttr()) {
					parent.getAttrs().put(field.getName(), field);
				} else {
					parent.getFields().put(field.getName(), field);
				}
			}
		}
		
		return fields;
	}
	
	private Field getParent(Map<String, Field> fields, String[] parents, boolean isAttr) {
		Field field = null;
		for(int i = 0; i < parents.length; i++) {
			field = fields.get(parents[i]);
			if(field == null) {
				String name = parents[i];
				field = new Field(name);
				fields.put(name, field);
			}
			fields = field.getFields();
		}
		if(parents.length > 1) {
			field.setMulti(true);
		}
		return field;
	}
	
}
