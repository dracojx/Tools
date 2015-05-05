package exceltoxsd.v5;

import java.util.Collection;
import java.util.Iterator;

public class Execute {

	public static void main(String[] args) {
		DataType dataType = new DataType();
		
		String name = "A";
		String type = "string";
		Integer min = 10;
		Integer max = 20;
		boolean required = false;
		String remark = "Field A";
		String[] parents = new String[]	{"ParentA"};
		
		
		Element A = new Element(name, type, max, min, required, remark);
		
		dataType.addElement(A, parents);
//		int size = dataType.getElements().get("ParentA").getElements().size();
		printElements(dataType.getElements().values());
	}
	
	private static void printElements(Collection<Element> elements) {
		Iterator<Element> it = elements.iterator();
		while(it.hasNext()) {
			Element e = it.next();
			System.out.print(e.getName() + "\t");
			if(e.hasElements()) {
				System.out.print("has children: ");
				printElements(e.getElements().values());
			}
		}
	}

}
