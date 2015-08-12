package formatattendancerecords.v2;

import java.io.File;
import java.io.FileFilter;

public class FileUtil {
	
	public static String[] getFilenames() {
	    FileFilter PDFFilter = new FileFilter() {
	        public boolean accept(File file) {
	            if (file.getName().endsWith(".pdf")) {
	                return true;
	            }
	            return false;
	        }
	    };

		File file = new File(".");
		File[] files = file.listFiles(PDFFilter);

		String[] filenames = new String[files.length];
				
		for(int i = 0; i < files.length; i++) {
			filenames[i] = files[i].getName();
		}
		return filenames;
	}

}
