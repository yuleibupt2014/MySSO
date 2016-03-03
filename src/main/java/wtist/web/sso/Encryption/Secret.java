package wtist.web.sso.Encryption;

import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

import javax.crypto.Cipher;

public class Secret {
	private Key publicKey;
	private Key privateKey;

	public Key getPublicKey() {
		return publicKey;
	}

	public void setPublicKey(Key publicKey) {
		this.publicKey = publicKey;
	}

	public Key getPrivateKey() {
		return privateKey;
	}

	public void setPrivateKey(Key privateKey) {
		this.privateKey = privateKey;
	}

	public Secret init() {
		try {
			KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
			SecureRandom ran = new SecureRandom();
			pairgen.initialize(32, ran);
			KeyPair kp = pairgen.generateKeyPair();
			publicKey = kp.getPublic();
			privateKey = kp.getPrivate();
			return this;
		} catch (Exception e) {
		}
		return this;
	}

	public String encrypt(String content) {
		try {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.WRAP_MODE, publicKey);
			byte[] wrappedkey = cipher.wrap(publicKey);

		} catch (Exception e) {
		}
		return null;
	}

}
