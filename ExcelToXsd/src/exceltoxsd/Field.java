package exceltoxsd;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

public class Field {
	private String name;
	private String type;
	private int[] lengths;
	private boolean required = true;
	private boolean multi = false;
	private String remark;
	private String[] parents;
	private Map<String, Field> children = new LinkedHashMap<String, Field>();

	public Field(Row row) {
		name = getAttr(row, 0);
		type = getAttr(row, 1);
		lengths = getLengths(row);
		required = "Y".equalsIgnoreCase(getAttr(row, 3));
		remark = getAttr(row, 4);
		parents = getParents(row);
	}

	public Field(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String[] getParents() {
		return parents;
	}

	public Map<String, Field> getChildren() {
		return children;
	}

	public void setMulti(boolean multi) {
		this.multi = multi;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();

		// xsd:element
		sb.append("<xsd:element name=\"").append(name).append("\"");
		// type
		if (lengths != null && lengths.length == 0) {
			sb.append(" type=\"xsd:").append(type).append("\"");
		}
		// required
		if (!required) {
			sb.append(" minOccurs=\"0\"");
		}
		// multi
		if (multi) {
			sb.append(" maxOccurs=\"unbounded\"");
		}
		sb.append(">");

		// remark
		if (remark != null) {
			sb.append("<xsd:annotation>");
			sb.append("<xsd:documentation>").append(remark)
					.append("</xsd:documentation>");
			sb.append("</xsd:annotation>");
		}

		// simpleType
		if (lengths != null && lengths.length > 0) {
			sb.append("<xsd:simpleType>");
			sb.append("<xsd:restriction base=\"xsd:").append(type)
					.append("\">");

			if (type.equals("string")) {
				switch (lengths.length) {
				case 1:
					sb.append("<xsd:maxLength value=\"").append(lengths[0])
							.append("\" />");
					break;
				case 2:
					sb.append("<xsd:minLength value=\"").append(lengths[0])
							.append("\" />");
					sb.append("<xsd:maxLength value=\"").append(lengths[1])
							.append("\" />");
					break;
				}
			} else if (type.equals("decimal")) {
				switch (lengths.length) {
				case 1:
					sb.append("<xsd:totalDigits value=\"").append(lengths[0])
							.append("\" />");
					break;
				case 2:
					sb.append("<xsd:totalDigits value=\"").append(lengths[0])
							.append("\" />");
					sb.append("<xsd:fractionDigits value=\"")
							.append(lengths[1]).append("\" />");
					break;
				}
			}

			sb.append("</xsd:restriction>");
			sb.append("</xsd:simpleType>");
		}
		
		// complexType
		if(!children.isEmpty()) {
			sb.append("<xsd:complexType>");
			sb.append("<xsd:sequence>");
			Iterator<Field> it = children.values().iterator();
			while(it.hasNext()) {
				sb.append(it.next().toString());
			}
			sb.append("</xsd:sequence>");
			sb.append("</xsd:complexType>");
		}

		sb.append("</xsd:element>");
		return sb.toString();
	}

	private int[] getLengths(Row row) {
		int[] lengths = new int[0];
		Cell cell = row.getCell(2);
		if (cell != null) {
			if (cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
				lengths = new int[] { (int) cell.getNumericCellValue() };
			} else if (cell.getCellType() == Cell.CELL_TYPE_STRING) {
				String[] strs = cell.getStringCellValue().split(",");
				lengths = new int[strs.length];
				for (int i = 0; i < strs.length; i++) {
					lengths[i] = Integer.parseInt(strs[i]);
				}
			}
		}
		return lengths;
	}

	private String[] getParents(Row row) {
		String[] parents = new String[0];
		Cell cell = row.getCell(5);
		if (cell != null && cell.getCellType() == Cell.CELL_TYPE_STRING) {
			parents = cell.getStringCellValue().trim().split("\\.");
		}
		return parents;
	}

	private String getAttr(Row row, int index) {
		String attr = null;
		Cell cell = row.getCell(index);
		if (cell != null) {
			attr = cell.getStringCellValue().trim();
		}
		return attr;
	}
}
