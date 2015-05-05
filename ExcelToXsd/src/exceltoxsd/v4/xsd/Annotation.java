package exceltoxsd.v4.xsd;

public class Annotation {
	private String documentation;

	public Annotation(String documentation) {
		this.documentation = documentation;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xsd:annotation><xsd:documentation>");
		sb.append(documentation);
		sb.append("</xsd:documentation></xsd:annotation>");
		return sb.toString();
	}
}
