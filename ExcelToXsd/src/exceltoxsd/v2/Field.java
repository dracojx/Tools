package exceltoxsd.v2;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Field {
	private String name;
	private boolean attr = false;
	private String type;
	private int[] lengths = new int[0];
	private boolean required = true;
	private String remark;
	private String[] parents = new String[0];
	private boolean multi = false;
	private Map<String, Field> attrs = new LinkedHashMap<String, Field>();
	private Map<String, Field> fields = new LinkedHashMap<String, Field>();

	public Field(Row row) {
		name = getName(row);
		attr = getAttr(row);
		type = getType(row);
		lengths = getLengths(row);
		required = getRequired(row);
		remark = getRemark(row);
		parents = getParents(row);
	}

	public Field(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean isAttr() {
		return attr;
	}

	public void setAttr(boolean isAttr) {
		this.attr = isAttr;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public int[] getLengths() {
		return lengths;
	}

	public void setLengths(int[] lengths) {
		this.lengths = lengths;
	}

	public boolean isRequired() {
		return required;
	}

	public void setRequired(boolean required) {
		this.required = required;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String[] getParents() {
		return parents;
	}

	public void setParents(String[] parents) {
		this.parents = parents;
	}

	public boolean isMulti() {
		return multi;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	public Map<String, Field> getAttrs() {
		return attrs;
	}

	public void setAttrs(Map<String, Field> attrs) {
		this.attrs = attrs;
	}

	public Map<String, Field> getFields() {
		return fields;
	}

	public void setFields(Map<String, Field> fields) {
		this.fields = fields;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		for(int i = 0; i < parents.length; i++) {
			sb.append(parents[i]).append(".");
		}
		sb.append(name).append("\t");
		sb.append(attr ? " Attr" : " Elem").append("\t");
		sb.append(type == null ? "" : type).append("\t");
		if(lengths.length > 0)
			sb.append("(").append(lengths[0]);
		if(lengths.length == 2) 
			sb.append(",").append(lengths[1]);
		if(lengths.length > 0)
			sb.append(")");
		sb.append("\t").append(required ? "1" : "0").append("\t");
		sb.append(multi ? "n " : "1 ").append("\t");
		sb.append(remark == null ? "" : remark).append("\n");
		
		Iterator<Field> attrsIt = attrs.values().iterator();
		while(attrsIt.hasNext()) {
			sb.append(attrsIt.next().toString());
		}
		
		Iterator<Field> fieldsIt = fields.values().iterator();
		while(fieldsIt.hasNext()) {
			sb.append(fieldsIt.next().toString());
		}
		
		return sb.toString();
	}

	private String getName(Row row) {
		String name = row.getCell(0).getStringCellValue();
		return name;
	}

	private boolean getAttr(Row row) {
		String attr = row.getCell(1).getStringCellValue();
		return attr.equalsIgnoreCase("ATTRIBUTE");
	}

	private String getType(Row row) {
		String type = row.getCell(2).getStringCellValue();
		return type;
	}

	private int[] getLengths(Row row) {
		int[] lengths = new int[0];
		Cell cell = row.getCell(3);
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				lengths = new int[] { (int) cell.getNumericCellValue() };
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String[] values = cell.getStringCellValue().split(",");
				lengths = new int[values.length];
				for (int i = 0; i < values.length; i++) {
					lengths[i] = Integer.parseInt(values[i]);
				}
			}
		}
		return lengths;
	}

	private boolean getRequired(Row row) {
		String required = row.getCell(4).getStringCellValue();
		return required.equalsIgnoreCase("Y");
	}

	private String getRemark(Row row) {
		Cell cell = row.getCell(5);
		return cell == null ? null : cell.getStringCellValue();
	}

	private String[] getParents(Row row) {
		String[] parents = new String[0];
		Cell cell = row.getCell(6);
		if(cell != null) {
			parents = cell.getStringCellValue().split("\\.");
		}
		return parents;
	}

}
