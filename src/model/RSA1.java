package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import javax.crypto.Cipher;

public class RSA1 {
	public KeyPair createKeyPair(int limitKey) throws Exception {
		KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("RSA");
		keyPairGenerator.initialize(limitKey);
		KeyPair keyPair = keyPairGenerator.generateKeyPair();
		return keyPair;

	}

	public PrivateKey createPrivateKey(KeyPair keyPair) throws Exception {

		PrivateKey privateKey = keyPair.getPrivate();
		return privateKey;
	}

	public PublicKey createPublicKey(KeyPair keyPair) throws Exception {
		PublicKey publicKey = keyPair.getPublic();
		return publicKey;
	}

	public String encryptToBase64(String text, PublicKey publicKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
		cipher.init(Cipher.ENCRYPT_MODE, publicKey);
		byte[] plainText = text.getBytes();
		byte[] cipherText = cipher.doFinal(plainText);
		return Base64.getEncoder().encodeToString(cipherText);
	}

	public String decryptFromBase64(String encryptedText, PrivateKey privateKey) throws Exception {
		Cipher cipher = Cipher.getInstance("RSA");
		cipher.init(Cipher.DECRYPT_MODE, privateKey);
		byte[] encryptedBytes = Base64.getDecoder().decode(encryptedText);
		byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
		return new String(decryptedBytes);
	}

	public void encryptFileRSA(String srcFile, String desFile, PublicKey publicKey) throws Exception {
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("RSA/ECB/PKCS1Padding");
			cipher.init(Cipher.ENCRYPT_MODE, publicKey);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[256]; // Độ dài tối đa cho mã hóa RSA với khóa 2048-bit
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
			fos.close();

			System.out.println("encrypted");
		} else {
			throw new FileNotFoundException("Source file not found.");
		}
	}

	public void decryptFileRSA(String srcFile, String desFile, PrivateKey privateKey) throws Exception {
		File file = new File(srcFile);
		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("RSA");
			cipher.init(Cipher.DECRYPT_MODE, privateKey);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(desFile);

			byte[] input = new byte[256]; // Độ dài tối đa cho giải mã RSA với khóa 2048-bit
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
			fos.close();

			System.out.println("decrypted");
		} else {
			throw new FileNotFoundException("Source file not found.");
		}
	}

	public static void main(String[] args) throws Exception {
		RSA1 rsa = new RSA1();

		// Load public and private keys in Base64 format
		KeyPair keyPair = rsa.createKeyPair(1045);
//do dai key thuong tu 1024 tro len
//        // Lấy khóa công khai và khóa riêng tư
		PublicKey publicKey = rsa.createPublicKey(keyPair);
		PrivateKey privateKey = rsa.createPrivateKey(keyPair);

		// Encrypt and decrypt text
		String text = "This is a secret message.";
		String encryptedText = rsa.encryptToBase64(text, publicKey);
		String decryptedText = rsa.decryptFromBase64(encryptedText, privateKey);

		System.out.println("Original Text: " + text);
		System.out.println("Encrypted Text: " + encryptedText);
		System.out.println("Decrypted Text: " + decryptedText);
		rsa.encryptFileRSA("D:\\Untitled.sql", "D:\\Untitled1.sql", publicKey);
		rsa.decryptFileRSA("D:\\Untitled1.sql", "D:\\Untitled2.sql", privateKey);
	}
}
