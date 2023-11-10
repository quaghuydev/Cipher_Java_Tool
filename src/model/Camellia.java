package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.Key;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

public class Camellia {
	public String createKey(int keySize) throws Exception {
		if (keySize != 128 && keySize != 192 && keySize != 256) {
			throw new IllegalArgumentException("Key size must be 128, 192, or 256 bits.");
		}
		Security.addProvider(new BouncyCastleProvider());
		SecureRandom secureRandom = new SecureRandom();
		byte[] keyBytes = new byte[keySize / 8];
		secureRandom.nextBytes(keyBytes);
		String key = Base64.getEncoder().encodeToString(keyBytes);
		return key;
	}

	public String encryptToBase64(String keyString, String plaintext) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		byte[] keyBytes = keyString.getBytes();
		byte[] plaintextBytes = plaintext.getBytes();
		Key key = new SecretKeySpec(keyBytes, "Camellia");
		Cipher encryptCipher = Cipher.getInstance("Camellia");
		encryptCipher.init(Cipher.ENCRYPT_MODE, key, new IvParameterSpec(new byte[16]));
		byte[] ciphertextBytes = encryptCipher.doFinal(plaintextBytes);
		return Base64.getEncoder().encodeToString(ciphertextBytes);
	}

	public String decryptFromBase64(String keyString, String ciphertextString) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		byte[] keyBytes = keyString.getBytes();
		byte[] ciphertextBytes = Base64.getDecoder().decode(ciphertextString);
		Key key = new SecretKeySpec(keyBytes, "Camellia");
		Cipher decryptCipher = Cipher.getInstance("Camellia");
		decryptCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(new byte[16]));
		byte[] decryptedBytes = decryptCipher.doFinal(ciphertextBytes);

		return new String(decryptedBytes);
	}

	public  void encryptFile(String sourceFilePath, String destinationFilePath, String key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Khóa không hợp lệ");

		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "Camellia");
		File file = new File(sourceFilePath);

		if (file.isFile()) {
			Cipher cipher = Cipher.getInstance("Camellia");
			cipher.init(Cipher.ENCRYPT_MODE, secretKeySpec);

			FileInputStream fis = new FileInputStream(file);
			FileOutputStream fos = new FileOutputStream(destinationFilePath);
			CipherOutputStream cos = new CipherOutputStream(fos, cipher);

			byte[] input = new byte[64];
			int bytesRead;

			while ((bytesRead = fis.read(input)) != -1) {
				cos.write(input, 0, bytesRead);
			}

			cos.close();
			fis.close();

			System.out.println("Tệp đã được mã hóa thành công.");
		}
	}

	public  void decryptFile(String encryptedFilePath, String decryptedFilePath, String key) throws Exception {
		Security.addProvider(new BouncyCastleProvider());
		if (key == null || key.isEmpty())
			throw new IllegalArgumentException("Khóa không hợp lệ");

		SecretKeySpec secretKeySpec = new SecretKeySpec(key.getBytes(), "Camellia");
		Cipher cipher = Cipher.getInstance("Camellia");
		cipher.init(Cipher.DECRYPT_MODE, secretKeySpec);

		FileInputStream fis = new FileInputStream(encryptedFilePath);
		FileOutputStream fos = new FileOutputStream(decryptedFilePath);
		CipherInputStream cis = new CipherInputStream(fis, cipher);

		byte[] input = new byte[64];
		int bytesRead;

		while ((bytesRead = cis.read(input)) != -1) {
			fos.write(input, 0, bytesRead);
		}

		cis.close();
		fos.close();
	}

	public static void main(String[] args) throws Exception {

		Camellia camellia = new Camellia();
		String keyString = camellia.createKey(128);
		System.out.println(keyString.getBytes().length);// 128bit key
		String plaintext = "Tao muốn m ăn cứt cho tao";
		System.out.println(keyString.getBytes().length);
//        key có độ dài 128bit (16 byte), 192bit (24 byte), hoặc 256bit
		String ciphertext = camellia.encryptToBase64(keyString, plaintext);
		String decryptedText = camellia.decryptFromBase64(keyString, ciphertext);

		System.out.println("Original: " + plaintext);
		System.out.println("Ciphertext: " + ciphertext);
		System.out.println("Decrypted: " + decryptedText);
//
////		camellia.encryptFile("D://Mobile.zip", "D://Mobile1.zip", keyString);
////		camellia.decryptFile("D://Mobile1.zip", "D://Mobile2.zip", keyString);
//		camellia.encryptFile("D://2.docx", "D://21.docx", keyString);
//		camellia.decryptFile("D://21.docx", "D://22.docx", keyString);
	}
}
