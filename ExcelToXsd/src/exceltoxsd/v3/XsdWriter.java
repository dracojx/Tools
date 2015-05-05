package exceltoxsd.v3;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.List;

public class XsdWriter {
	private List<DataType> dataTypes;
	
	public XsdWriter(List<DataType> dataTypes) {
		this.dataTypes = dataTypes;
	}
	
	public void write() {
		for(DataType dataType : dataTypes) {
			try {
				FileOutputStream fos = new FileOutputStream(dataType.getFileName());
				BufferedWriter out = new BufferedWriter(new OutputStreamWriter(fos, "UTF-8"));
				out.write(dataType.toString());
				out.flush();
				out.close();
				System.out.println(dataType.getFileName() + " Successed!");
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
