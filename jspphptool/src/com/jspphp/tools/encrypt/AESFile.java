package com.jspphp.tools.encrypt;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.SecureRandom;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class AESFile {
	// 加密文件
	public static void encryptfile(String pwd, File fileIn) throws Exception {
		try {
			// 读取文件
			FileInputStream fis = new FileInputStream(fileIn);
			byte[] bytIn = new byte[(int) fileIn.length()];
			for (int i = 0; i < fileIn.length(); i++) {
				bytIn[i] = (byte) fis.read();
			}
			// AES加密
			KeyGenerator kgen = KeyGenerator.getInstance("AES");
			kgen.init(128, new SecureRandom(pwd.getBytes()));
			SecretKey skey = kgen.generateKey();
			byte[] raw = skey.getEncoded();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
			// 写文件
			byte[] bytOut = cipher.doFinal(bytIn);
			FileOutputStream fos = new FileOutputStream(fileIn.getPath()
					+ ".aes");
			for (int i = 0; i < bytOut.length; i++) {
				fos.write((int) bytOut[i]);
			}
			fos.close();
			fis.close();
		} catch (Exception e) {
			throw new Exception(e.getMessage());
		}
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) throws Exception {
		AESFile aes = new AESFile();
		String pwd = "123";
		File file = new File("c:/abc.txt");
		aes.encryptfile(pwd, file);
	}
}
