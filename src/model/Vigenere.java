package model;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.security.SecureRandom;

public class Vigenere {

	  private  int ALPHABET_SIZE = 26;

	public Vigenere() {
	}

	private String getAlphabetVie() {
		return "AĂÂBCDĐEÊGHIKLMNOÔƠPQRSTUƯVXYaăâbcdđeêghiklmnoôơpqrstuưvxy";
	}

	public String createKeyVie(int length) {
		String characters = getAlphabetVie();
		SecureRandom secureRandom = new SecureRandom();
		StringBuilder key = new StringBuilder();
		for (int i = 0; i < length; i++) {
			int randomIndex = secureRandom.nextInt(characters.length());
			char randomChar = characters.charAt(randomIndex);
			key.append(randomChar);
		}

		return key.toString();
	}

	public String createKeyEng(int length) {
		StringBuilder key = new StringBuilder();
		SecureRandom random = new SecureRandom();

		for (int i = 0; i < length; i++) {
			char randomChar = (char) (random.nextInt(26) + 'A');
			key.append(randomChar);
		}

		return key.toString();
	}

	public String encryptVie(String key, String plaintext) {
		String vie = getAlphabetVie();
		int vieLength = vie.length();
		StringBuilder ciphertext = new StringBuilder();
		int keyLength = key.length();
		int textLength = plaintext.length();

		for (int i = 0; i < textLength; i++) {
			char plainChar = plaintext.charAt(i);
			char keyChar = key.charAt(i % keyLength);

			int plainIndex = vie.indexOf(plainChar);
			int keyIndex = vie.indexOf(keyChar);

			if (plainIndex != -1) {
				int encryptedIndex = (plainIndex + keyIndex) % vieLength;
				char encryptedChar = vie.charAt(encryptedIndex);
				ciphertext.append(encryptedChar);
			} else {
				ciphertext.append(plainChar);
			}
		}

		return ciphertext.toString();
	}

	public String decryptVie(String key, String ciphertext) {
		String vie = getAlphabetVie();
		int vieLength = vie.length();
		StringBuilder plaintext = new StringBuilder();
		int keyLength = key.length();
		int textLength = ciphertext.length();

		for (int i = 0; i < textLength; i++) {
			char cipherChar = ciphertext.charAt(i);
			char keyChar = key.charAt(i % keyLength);

			int cipherIndex = vie.indexOf(cipherChar);
			int keyIndex = vie.indexOf(keyChar);

			if (cipherIndex != -1) {
				int decryptedIndex = (cipherIndex - keyIndex + vieLength) % vieLength;
				char decryptedChar = vie.charAt(decryptedIndex);
				plaintext.append(decryptedChar);
			} else {
				plaintext.append(cipherChar);
			}
		}

		return plaintext.toString();
	}

	    public  String encryptEng(String key,String plaintext) {
	        StringBuilder ciphertext = new StringBuilder();
	        key = key.toUpperCase();

	        int keyIndex = 0;

	        for (int i = 0; i < plaintext.length(); i++) {
	            char currentChar = plaintext.charAt(i);

	            if (Character.isLetter(currentChar)) {
	                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
	                int plainValue = currentChar - base;
	                int keyValue = key.charAt(keyIndex) - 'A';

	                int encryptedValue = (plainValue + keyValue) % ALPHABET_SIZE;
	                char encryptedChar = (char) (base + encryptedValue);

	                ciphertext.append(encryptedChar);

	                keyIndex = (keyIndex + 1) % key.length();
	            } else {
	                ciphertext.append(currentChar);
	            }
	        }

	        return ciphertext.toString();
	    }

	    public  String decryptEng(String key,String ciphertext) {
	        StringBuilder plaintext = new StringBuilder();
	        key = key.toUpperCase();

	        int keyIndex = 0;

	        for (int i = 0; i < ciphertext.length(); i++) {
	            char currentChar = ciphertext.charAt(i);

	            if (Character.isLetter(currentChar)) {
	                char base = Character.isUpperCase(currentChar) ? 'A' : 'a';
	                int encryptedValue = currentChar - base;
	                int keyValue = key.charAt(keyIndex) - 'A';

	                int decryptedValue = (encryptedValue - keyValue + ALPHABET_SIZE) % ALPHABET_SIZE;
	                char decryptedChar = (char) (base + decryptedValue);

	                plaintext.append(decryptedChar);

	                keyIndex = (keyIndex + 1) % key.length();
	            } else {
	                plaintext.append(currentChar);
	            }
	        }

	        return plaintext.toString();
	    }

	public void encryptFile(String srcFile, String desFile, String key) throws Exception {
		byte[] data = Files.readAllBytes(Paths.get(srcFile));
		byte[] keyBytes = key.getBytes();
		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (data[i] + keyBytes[i % keyBytes.length]);
		}
		Files.write(Paths.get(desFile), data);

		System.out.println("Vigenere encrypted");
	}

	public void decryptFile(String srcFile, String desFile, String key) throws Exception {
		byte[] data = Files.readAllBytes(Paths.get(srcFile));
		byte[] keyBytes = key.getBytes();

		for (int i = 0; i < data.length; i++) {
			data[i] = (byte) (data[i] - keyBytes[i % keyBytes.length]);
		}

		Files.write(Paths.get(desFile), data);

		System.out.println("Vigenere decrypted");
	}

	public static void main(String[] args) throws Exception {
		String plaintext = "Con chó";

		Vigenere cipher = new Vigenere();
		String key = cipher.createKeyVie(15);
		System.out.println("key: " + key);
		String ciphertext = cipher.encryptVie(key,plaintext);
		System.out.println("Ciphertext: " + ciphertext);

		String decryptedText = cipher.decryptVie(key,ciphertext);
		System.out.println("Decrypted text: " + decryptedText);

//		cipher.encryptFile("D://2.docx", "D://21.docx", key);
//		cipher.decryptFile("D://21.docx", "D://22.docx", key);
	}
}
