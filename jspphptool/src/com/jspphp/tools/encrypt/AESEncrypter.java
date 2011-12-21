package com.jspphp.tools.encrypt;

/**
 * @author 史金波
 * 创建日期：2009-09-09 Email:JSPPHP@126.COM
 */
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

public class AESEncrypter {

	public static byte[] iv = new byte[] { 82, 22, 50, 44, -16, 124, -40, -114, -87, -40, 37, 23, -56, 23, -33, 75 };

	private static AESEncrypter aes = null;

	public static byte[] key1 = new byte[] { -42, 35, 67, -86, 19, 29, -11, 84, 94, 111, 75, -104, 71, 46, 86, -21, -119, 110, -11, -32, -28, 91, -33, -46, 99, 49, 2, 66, -101, -11, -8, 56 };

	public static byte[] w_key = new byte[] { -32, 70, -42, 101, 19, -41, 18, 45, 90, 8, -108, 14, -124, -89, -113, 119 };

	public static byte[] r_key = new byte[] { 80, -88, 107, -86, -32, -12, 119, -104, -17, 24, -3, 106, 83, -90, -83, -75 };

	public static byte[] c_key = new byte[] { 20, 67, 45, -95, -123, -39, -35, 16, 85, 58, -86, -107, 119, -29, 100, -51 };

	public static byte[] s_key = new byte[] { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };

	public static byte[] t_key = new byte[] { -18, 18, 26, -75, 60, 38, 24, 103, -114, 93, -15, 49, 113, 61, 16, 17 };

	private AESEncrypter() {

	}

	public static synchronized AESEncrypter getInstance() {
		if (aes == null) {
			aes = new AESEncrypter();
		}
		return aes;
	}

	public String encrypt(String msg) {
		String str = "";
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key1));
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			SecretKey key = kgen.generateKey();
			Cipher ecipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
			str = asHex(ecipher.doFinal(msg.getBytes()));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return str;
	}

	public String decrypt(String value) {
		try {
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(key1));
			AlgorithmParameterSpec paramSpec = new IvParameterSpec(iv);
			SecretKey key = kgen.generateKey();
			Cipher dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
			return new String(dcipher.doFinal(asBin(value)));
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		}
		return "";
	}

	public static String asHex(byte buf[]) {
		StringBuffer strbuf = new StringBuffer(buf.length * 2);
		int i;

		for (i = 0; i < buf.length; i++) {
			if (((int) buf[i] & 0xff) < 0x10)
				strbuf.append("0");

			strbuf.append(Long.toString((int) buf[i] & 0xff, 16));
		}

		return strbuf.toString();
	}

	public static byte[] asBin(String src) {
		if (src.length() < 1)
			return null;
		byte[] encrypted = new byte[src.length() / 2];
		for (int i = 0; i < src.length() / 2; i++) {
			int high = Integer.parseInt(src.substring(i * 2, i * 2 + 1), 16);
			int low = Integer.parseInt(src.substring(i * 2 + 1, i * 2 + 2), 16);
			encrypted[i] = (byte) (high * 16 + low);
		}
		return encrypted;
	}

	public static void main(String[] args) {
		String str = AESEncrypter.getInstance().encrypt("shijinbo");
		System.out.println(str);
		System.out.println(str.length());
		System.out.println(AESEncrypter.getInstance().decrypt(str));
		// String strs = "64";
		// byte[] shi = asBin(strs);
		// for (int i = 0; i < shi.length; i++) {
		// System.out.println(shi[i]);
		// }

	}
}
