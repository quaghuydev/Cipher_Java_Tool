package model;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.DigestInputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {
	public String convertStringToHash(String input) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] output = md.digest(input.getBytes());
			BigInteger hexString = new BigInteger(1, output);

			return hexString.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
			return null;
		}
	}

	public String convertFileToHash(String filePath) {
		try {
			MessageDigest md = MessageDigest.getInstance("MD5");
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

	public boolean checkFile(String pathFile, String hashFile) {
		String hashMD5 = convertFileToHash(pathFile);
		return hashFile.equals(hashMD5);

	}

	public void main(String[] args) {
		String input = "Hello, MD5!";
		MD5 md = new MD5();
		String md5Hash = convertStringToHash(input);
		if (md5Hash != null) {
			System.out.println("MD5 Hash: " + md5Hash);
		} else {
			System.out.println("Error calculating MD5 hash.");
		}

		String filePath = "D://Mobile.zip";
		String md5HashFile = convertFileToHash(filePath);
		if (md5HashFile != null) {
			System.out.println("MD5 Hash for the file: " + md5HashFile);
		} else {
			System.out.println("Error calculating MD5 hash for the file.");
		}
	}
}
