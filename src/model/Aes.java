package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class Aes {
//ho tro khóa 128,192,256
	public SecretKey createKey(int limitkey) {
		KeyGenerator keyGenerator;
		SecretKey key = null;
		try {
			keyGenerator = KeyGenerator.getInstance("AES");
			keyGenerator.init(limitkey);
			key = keyGenerator.generateKey();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return key;
	}

	public String exportKey(SecretKey key) {
		return Base64.getEncoder().encodeToString(key.getEncoded());
	}

	public String encryptToBase64(String text, SecretKey key) throws Exception {
		if (key == null)
			return "";
		Cipher cipher = Cipher.getInstance("AES");
		cipher.init(Cipher.ENCRYPT_MODE, key);
		byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptFromBase64(String encryptedText, SecretKey key) throws Exception {
		if (key == null)
			return "";
		Cipher cipher = Cipher.getInstance("AES");
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
			Cipher cipher = Cipher.getInstance("AES");
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
			System.out.println("Encrypted");
		}
	}

	public void decryptFile(String srcFile, String desFile, SecretKey key) throws Exception {
		if (key == null)
			throw new FileNotFoundException("Key not found");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("AES");
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
			System.out.println("Decrypted");
		}
	}

	public String encryptToBase64(String text, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Aes");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);
		byte[] plainText = text.getBytes(StandardCharsets.UTF_8);
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptFromBase64(String encryptedText, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Aes");
		Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5PADDING");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes, StandardCharsets.UTF_8);
	}

	public void encryptFile(String srcFile, String desFile, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Aes");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("AES");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

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

	public void decryptFile(String srcFile, String desFile, String key) throws Exception {
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Key is empty");

		SecretKey secretKeySpec = new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "Aes");
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("AES");
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
		Aes aes = new Aes();
//		String input = "0123456789112345";
		String key = "asdfghjklqwertyu";

		// Tạo khóa
//		SecretKey key = aes.convertToSecretKey(key1)?;
//		System.out.println(aes.exportKey(key));
		// Mã hóa và giải mã văn bản
		String text = "T tên là bùi quang huy";
		String encryptedText = aes.encryptToBase64(text, key);
		String decryptedText = aes.decryptFromBase64(encryptedText, key);

		System.out.println("Original Text: " + text);
		System.out.println("Encrypted Text: " + encryptedText);
		System.out.println("Decrypted Text: " + decryptedText);

		// Mã hóa và giải mã tệp tin
		String inputFile = "D://Mobile.zip";
		String encryptedFile = "D://Mobile1.zip";
		String decryptedFile = "D://Mobile2.zip";

		aes.encryptFile(inputFile, encryptedFile, key);
		aes.decryptFile(encryptedFile, decryptedFile, key);
	}
}
