package exceltoxsd.v4.xsd;

public class Execute {

	public static void main(String[] args) {
		Schema schema = new Schema("http://test2.gome.com", "http://test2.gome.com", "DT_TEST006");
		
		Attribute c1 = new Attribute("C1", Constants.XSD_TYPE_STRING, null, Constants.ATTRIBUTE_USE_REQUIRED);
		Attribute d1 = new Attribute("D1", Constants.XSD_TYPE_STRING, "Attribute D1");
		Attribute e1 = new Attribute("E1", Constants.XSD_TYPE_STRING, 4, null);
		Attribute f1 = new Attribute("F1", Constants.XSD_TYPE_STRING, 6, null);
		Attribute ga1 = new Attribute("GA1", Constants.XSD_TYPE_STRING, null);
		Attribute gb1 = new Attribute("GB1", Constants.XSD_TYPE_STRING, null);
		Attribute gc1 = new Attribute("GC1", Constants.XSD_TYPE_STRING, 4, null);
		Attribute gd1 = new Attribute("GD1", Constants.XSD_TYPE_STRING, 6, null);
		Attribute h1 = new Attribute("H1", Constants.XSD_TYPE_STRING, null);
		Attribute h2 = new Attribute("H2", Constants.XSD_TYPE_STRING, 7, null);
		
		
		
		Element a = new Element("A", Constants.XSD_TYPE_STRING, "Element A");
		Element b = new Element("B", Constants.XSD_TYPE_STRING, null, 2);
		Element c = new Element("C", Constants.XSD_TYPE_STRING, null);
		Element d = new Element("D", Constants.XSD_TYPE_STRING, null, 3);
		Element e = new Element("E", Constants.XSD_TYPE_STRING, null);
		Element f = new Element("F", Constants.XSD_TYPE_STRING, null, 5);
		Element ga = new Element("GA", Constants.XSD_TYPE_STRING, null);
		Element gb = new Element("GB", Constants.XSD_TYPE_STRING, null, 3);
		Element gc = new Element("GC", Constants.XSD_TYPE_STRING, null);
		Element gd = new Element("GD", Constants.XSD_TYPE_STRING, null, 5);
		Element h = new Element("H", Constants.XSD_TYPE_STRING, null);
		
		ga.setParents(new String[]{"G"});
		gb.setParents(new String[]{"G"});
		gc.setParents(new String[]{"G"});
		gd.setParents(new String[]{"G"});
		
		c.addAttribute(c1);
		d.addAttribute(d1);
		e.addAttribute(e1);
		f.addAttribute(f1);
		ga.addAttribute(ga1);
		gb.addAttribute(gb1);
		gc.addAttribute(gc1);
		gd.addAttribute(gd1);
		h.addAttribute(h1);
		h.addAttribute(h2);
		
		schema.addElement(a);
		schema.addElement(b);
		schema.addElement(c);
		schema.addElement(d);
		schema.addElement(e);
		schema.addElement(f);
		schema.addElement(ga);
		schema.addElement(gb);
		schema.addElement(gc);
		schema.addElement(gd);
		schema.addElement(h);
		
		System.out.println(schema);
	}

}
