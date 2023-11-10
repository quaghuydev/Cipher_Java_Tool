package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Blowfish {
//ho tro khóa từ 32-448
	public SecretKey createKey(int keySize) {
		SecretKey key = null;
		try {
			if (keySize < 32 || keySize > 448) {
				throw new IllegalArgumentException("Kích thước key không hợp lệ cho Blowfish.");
			}

			KeyGenerator keyGenerator = KeyGenerator.getInstance("Blowfish");
			keyGenerator.init(keySize);
			key = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		}
		return key;
	}
	public  SecretKey convertToSecretKey(String key) throws Exception {
		  byte[] keyBytes = key.getBytes("UTF-8");
		  SecretKey secretKey = new SecretKeySpec(keyBytes, 0, keyBytes.length, "AES");

		  return secretKey;
		}

	public String encryptToBase64(String text, SecretKey key) throws Exception {
		if (key == null)
			return "";
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptFromBase64(String encryptedText, SecretKey key) throws Exception {
		if (key == null)
			return "";
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, key);
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public void encryptFile(String srcFile, String desFile, SecretKey key) throws Exception {
		if (key == null)
			throw new FileNotFoundException("Key not found");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, key);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[128];
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

	public void decryptFile(String srcFile, String desFile, SecretKey key) throws Exception {
		if (key == null)
			throw new FileNotFoundException("Key not found");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, key);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(new File(desFile));

			byte[] input = new byte[128];
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
			System.out.println("Decrypted");
		}
	}

	public String encryptToBase64(String text, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptFromBase64(String encryptedText, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKey = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Blowfish");
		Cipher cipher = Cipher.getInstance("Blowfish");
		cipher.init(Cipher.DECRYPT_MODE, secretKey);
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public void encryptFile(String srcFile, String desFile, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Blowfish");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[128];
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

	public void decryptFile(String srcFile, String desFile, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Blowfish");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("Blowfish");
			cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

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
			System.out.println("Decrypted");
		}
	}

	public static void main(String[] args) throws Exception {
		Blowfish low = new Blowfish();
		SecretKey key = low.createKey(128);
//		String key = "asdfghjklqwertyu";
		System.out.println(low.encryptToBase64("quang huy", key));
		System.out.println(low.decryptFromBase64(low.encryptToBase64("quang huy", key), key));
//		low.encryptFile("D:\\Mobile.zip", "D:\\Mobile1.zip", key);
//		low.decryptFile("D:\\Mobile1.zip", "D:\\Mobile2.zip", key);
	}
}
