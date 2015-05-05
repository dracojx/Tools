package sap.pi;
import com.sap.aii.mapping.api.AbstractTransformation;
import com.sap.aii.mapping.api.DynamicConfiguration;
import com.sap.aii.mapping.api.DynamicConfigurationKey;
import com.sap.aii.mapping.api.StreamTransformationConstants;
import com.sap.aii.mapping.api.StreamTransformationException;
import com.sap.aii.mapping.api.TransformationInput;
import com.sap.aii.mapping.api.TransformationOutput;
import com.sap.aii.mappingtool.tf7.rt.Container;

public class JavaMapping extends AbstractTransformation {

	@Override
	public void transform(TransformationInput arg0, TransformationOutput arg1)
			throws StreamTransformationException {
	}

	public String getDateByFilename(Container container)
			throws StreamTransformationException {
		try {
			String date = "";
			@SuppressWarnings("deprecation")
			DynamicConfiguration dc = (DynamicConfiguration) container
					.getTransformationParameters()
					.get(StreamTransformationConstants.DYNAMIC_CONFIGURATION);
			DynamicConfigurationKey dck = DynamicConfigurationKey.create(
					"http:/" + "/sap.com/xi/XI/System/File", "FileName");
			String filename = dc.get(dck);
			int begin = filename.lastIndexOf("_") + 1;
			int end = filename.lastIndexOf(".");
			date = filename.substring(begin, end);
			return date;
		} catch (Exception e) {
			String exception = e.toString();
			return exception;
		}
	}

	public void setUrlParam1(Container container, String param)
			throws StreamTransformationException {
		@SuppressWarnings("deprecation")
		DynamicConfiguration dc = (DynamicConfiguration) container
				.getTransformationParameters()
				.get(StreamTransformationConstants.DYNAMIC_CONFIGURATION);
		DynamicConfigurationKey dck = DynamicConfigurationKey.create(
				"http:/" + "/sap.com/xi/XI/System/HTTP", "URLParamOne");
		dc.put(dck, param);
	}
}
