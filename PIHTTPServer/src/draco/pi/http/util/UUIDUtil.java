package draco.pi.http.util;

public class UUIDUtil {
	public static String createUUID() {
		return java.util.UUID.randomUUID().toString().replace("-", "");
	}
}
