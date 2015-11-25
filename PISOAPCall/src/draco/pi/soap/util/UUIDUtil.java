package draco.pi.soap.util;

public class UUIDUtil {
	public static String createUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}
}
