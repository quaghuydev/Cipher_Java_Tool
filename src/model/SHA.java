package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SHA {
	public String convertStringToHash(String input, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			byte[] output = md.digest(input.getBytes());
			BigInteger hexString = new BigInteger(1, output);

			return hexString.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String convertFileToHash(String filePath, String algorithm) {
		try {
			MessageDigest md = MessageDigest.getInstance(algorithm);
			DigestInputStream dis = new DigestInputStream(
					new BufferedInputStream(new FileInputStream(new File(filePath))), md);
			byte[] read = new byte[1024];
			int i;
			do {
				i = dis.read(read);
			} while (i != -1);
			BigInteger ouput = new BigInteger(1, dis.getMessageDigest().digest());
			return ouput.toString(16);
		} catch (NoSuchAlgorithmException | IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean checkFile(String filePath, String expectedHash, String type) {
		String actualHash = convertFileToHash(filePath, type);
		return expectedHash.equals(actualHash);
	}

	public static void main(String[] args) {

		SHA sha = new SHA();
//		String input = "Hello, tao là bùi huy!";
//		String sha256Hash =sha.convertToHashSHA(input, "SHA-256");
//		String sha1Hash = sha.convertToHashSHA(input, "SHA-1");
//		String sha512Hash = sha.convertToHashSHA(input, "SHA-512");
//
//		if (sha256Hash != null) {
//			System.out.println("SHA-256 Hash: " + sha256Hash);
//		} else {
//			System.out.println("Error calculating SHA-256 hash.");
//		}
//
//		if (sha1Hash != null) {
//			System.out.println("SHA-1 Hash: " + sha1Hash);
//		} else {
//			System.out.println("Error calculating SHA-1 hash.");
//		}
//
//		if (sha512Hash != null) {
//			System.out.println("SHA-512 Hash: " + sha512Hash);
//		} else {
//			System.out.println("Error calculating SHA-512 hash.");
//		}
//		System.out.println(sha.checkString("quang huy", "SHA-256"));

		String filePath = "D://2.docx";
		String sha256Hashf = sha.convertFileToHash(filePath, "SHA-256");
		System.out.println(sha256Hashf);

	}

}
