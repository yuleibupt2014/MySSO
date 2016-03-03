package wtist.web.sso.Encryption;

import java.math.BigInteger;
import java.security.MessageDigest;

public class Encryption {
	public static Secret genKey() {
		return null;
	}

	public static String encrypt(String content) {
		return null;
	}

	public static String decrypt(String contentEncryption) {
		return null;
	}

	public static String MD5(String str) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			return new BigInteger(1, md.digest()).toString(16);
		} catch (Exception e) {

		}
		return null;
	}
}
