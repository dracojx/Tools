package exceltoxsd.v1;

import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class Field {
	private String name;
	private String type;
	private Integer[] lengths;
	private String remark;
	private boolean required;
	private boolean multi = false;
	private Map<String, Field> map;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public Integer[] getLengths() {
		return lengths;
	}
	public void setLengths(Integer[] lengths) {
		this.lengths = lengths;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public boolean isRequired() {
		return required;
	}
	public void setRequired(boolean required) {
		this.required = required;
	}
	public boolean isMulti() {
		return multi;
	}
	public void setMulti(boolean multi) {
		this.multi = multi;
	}
	public Map<String, Field> getMap() {
		return map;
	}
	public void setMap(Map<String, Field> map) {
		this.map = map;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		//xsd:element
		sb.append("<xsd:element name=\"").append(name).append("\"");
		if(!required) {
			sb.append(" minOccurs=\"0\"");
		}
		if(multi) {
			sb.append(" maxOccurs=\"unbounded\"");
		}
		sb.append(">");
		
		//xsd:annotation
		if(remark != null && !remark.equals("")) {
			sb.append("<xsd:annotation><xsd:documentation>");
			sb.append(remark);
			sb.append("</xsd:documentation></xsd:annotation>");
		}
		
		//xsd:simpleType
		if(map == null) {
			sb.append("<xsd:simpleType><xsd:restriction base=\"xsd:").append(type).append("\">");
			if(type.equalsIgnoreCase("string")) {
				if(lengths.length == 1) {
					sb.append("<xsd:maxLength value=\"").append(lengths[0]).append("\" />");
				} else if(lengths.length == 2) {
					sb.append("<xsd:minLength value=\"").append(lengths[0]).append("\" />");
					sb.append("<xsd:maxLength value=\"").append(lengths[1]).append("\" />");
				}
			} else if(type.equalsIgnoreCase("decimal")) {
				if(lengths.length == 1) {
					sb.append("<xsd:totalDigits value=\"").append(lengths[0]).append("\" />");
				} else if(lengths.length == 2) {
					sb.append("<xsd:totalDigits value=\"").append(lengths[0]).append("\" />");
					sb.append("<xsd:fractionDigits value=\"").append(lengths[1]).append("\" />");
				}
			}
			sb.append("</xsd:restriction></xsd:simpleType>");
		} 

		//xsd:complexType
		else {
			sb.append("<xsd:complexType><xsd:sequence>");
			Iterator<Entry<String, Field>> iterator = map.entrySet().iterator();
			while(iterator.hasNext()) {
				Entry<String, Field> ent = iterator.next();
				sb.append(ent.getValue().toString());
			}
			sb.append("</xsd:sequence></xsd:complexType>");
		}
		sb.append("</xsd:element>");
		
		return sb.toString();
	}
}
