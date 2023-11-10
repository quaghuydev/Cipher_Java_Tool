package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Des {

	public SecretKey createkey() {
		KeyGenerator keyGenerator;
		SecretKey key = null;
		try {
			keyGenerator = KeyGenerator.getInstance("DES");
			keyGenerator.init(56);
			key = keyGenerator.generateKey();

		} catch (NoSuchAlgorithmException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return key;

	}

//	private String exportKey() {
//		return Base64.getEncoder().encodeToString(key.getEncoded());
//	}
//
//	public byte[] encrypt(String text) throws Exception {
//		if (key == null)
//			return new byte[] {};
//		Cipher cipher = Cipher.getInstance("DES");
//		cipher.init(Cipher.ENCRYPT_MODE, key);
//		byte[] plaintText = text.getBytes("UTF-8");
//		byte[] cipherText = cipher.doFinal(plaintText);
//		return cipherText;
//
//	}
	public SecretKey convertToSecretKey(String key) throws Exception {
		byte[] keyBytes = key.getBytes("UTF-8");
		SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");

		return secretKey;
	}

	public String encryptToBase64(String text, SecretKey key) throws Exception {
		if (key == null)
			return "";
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plaintText = text.getBytes("UTF-8");
		byte[] cipherText = cipher.doFinal(plaintText);
		return Base64.getEncoder().encodeToString(cipherText);

	}

	public static String encryptToBase64(String text, String key) throws Exception {
		byte[] by = key.getBytes("UTF-8");
		if (key == null || by.length != 8) {
			throw new IllegalArgumentException("Key must be 8 characters long");
		}

		// Chuyển chuỗi key thành đối tượng SecretKey
		SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "DES");

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.ENCRYPT_MODE, secretKey);

		byte[] plaintextBytes = text.getBytes(StandardCharsets.UTF_8);
		byte[] cipherText = cipher.doFinal(plaintextBytes);

		return Base64.getEncoder().encodeToString(cipherText);
	}

	public static String decryptFromBase64(String encryptedText, String key) throws Exception {
		byte[] by = key.getBytes("UTF-8");
		if (key == null || by.length != 8) {
			throw new IllegalArgumentException("Key must be 8 characters long");
		}

		SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "DES");

		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);

		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);

		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public void encryptFile(String srcFile, String desFile, SecretKey key) throws Exception {
		if (key == null)
			throw new FileNotFoundException("key not found");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null) {
					fos.write(output);
				}
			}
			byte[] output = cipher.doFinal();
			if (output != null) {
				fos.write(output);
			}
			fis.close();
			fos.flush();
			System.out.println("encrypted");
		}

	}

	public void encryptFile(String srcFile, String desFile, String keyString) throws Exception {
		if (keyString == null)
			throw new FileNotFoundException("Key not found");

		byte[] keyBytes = keyString.getBytes("UTF-8");
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");

		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, keySpec);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null) {
					fos.write(output);
				}
			}

			byte[] output = cipher.doFinal();
			if (output != null) {
				fos.write(output);
			}

			fis.close();
			fos.flush();
			System.out.println("Encrypted");
		}
	}

	public void decryptFile(String srcFile, String desFile, String keyString) throws Exception {
		if (keyString == null)
			throw new FileNotFoundException("Key not found");

		byte[] keyBytes = keyString.getBytes("UTF-8");
		SecretKeySpec keySpec = new SecretKeySpec(keyBytes, "DES");

		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, keySpec);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null) {
					fos.write(output);
				}
			}

			byte[] output = cipher.doFinal();
			if (output != null) {
				fos.write(output);
			}

			fis.close();
			fos.flush();
			System.out.println("Decrypted");
		}
	}

	public void decryptFile(String srcFile, String desFile, SecretKey key) throws IOException, NoSuchPaddingException,
			NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
		if (key == null)
			throw new FileNotFoundException("key not found");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(new File(desFile));

			byte[] input = new byte[64];
			int bytesRead = 0;

			while ((bytesRead = fis.read(input)) != -1) {
				byte[] output = cipher.update(input, 0, bytesRead);
				if (output != null) {
					fos.write(output);
				}
			}
			byte[] output = cipher.doFinal();
			if (output != null) {
				fos.write(output);
			}
			fis.close();
			fos.flush();
			System.out.println("decrypted");
		}

	}

//	public String decrypt(byte[] text) throws Exception {
//		if (key == null)
//			return null;
//		Cipher cipher = Cipher.getInstance("DES");
//		cipher.init(Cipher.DECRYPT_MODE, key);
//		byte[] plaintText = cipher.doFinal(text);
//		String output = new String(plaintText, "UTF-8");
//		return output;
//
//	}

	public String decryptFromBase64(String text, SecretKey key) throws Exception {
		if (key == null)
			return "";
		Cipher cipher = Cipher.getInstance("DES");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] plaintText = cipher.doFinal(Base64.getDecoder().decode(text));
		String output = new String(plaintText, "UTF-8");
		return output;

	}

	public static void main(String[] args) throws Exception {
		Des des = new Des();
//		 SecretKey key = des.createkey();
		String key = "asdfghjk";
//		String output = des.encryptToBase64("xin chào nông lâm", key);
//		System.out.println(output);
//		System.out.println(des.decryptFromBase64(output, key));
//
////		byte[] text = des.encrypt("xin chÃ o nÃ´ng lÃ¢m");
////		String text1 = new String(text, "UTF-8");
////		System.out.println(des.decrypt(text));
//
		des.encryptFile("D:\\Mobile.zip", "D:\\Mobile1.zip", key);
		des.decryptFile("D:\\Mobile1.zip", "D:\\Mobile2.zip", key);

	}

}
