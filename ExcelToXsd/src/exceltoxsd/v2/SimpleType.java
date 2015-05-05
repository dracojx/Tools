package exceltoxsd.v2;

public class SimpleType {
	private String name;
	private String type;
	private int[] lengths;
	
	public SimpleType(Field field, String dataTypeName) {
		name = getName(field, dataTypeName);
		type = field.getType();
		lengths = field.getLengths();
	}
	
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
	public int[] getLengths() {
		return lengths;
	}
	public void setLengths(int[] lengths) {
		this.lengths = lengths;
	}
	
	public String toString() {
		StringBuffer sb = new StringBuffer();
		
		sb.append("<xsd:simpleType name=\"").append(name).append("\">");
		sb.append("<xsd:restriction base=\"").append(type).append("\">");
		
		sb.append("</xsd:restriction>");
		sb.append("</xsd:simpleType>");
		return sb.toString();
	}
	
	private String getName(Field field, String dataTypeName) {
		StringBuffer name = new StringBuffer();
		String[] parents = field.getParents();
		
		name.append(dataTypeName).append(".");
		int size = parents.length;
		for(int i = 0; i < size; i++) {
			name.append(parents[i]).append(".");
		}
		name.append(field.getName());
		
		return name.toString();
	}
}
