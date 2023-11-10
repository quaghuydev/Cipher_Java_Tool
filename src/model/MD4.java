package model;

import java.io.FileInputStream;
import java.io.IOException;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.MD4Digest;
import org.bouncycastle.util.encoders.Hex;

public class MD4 {
	public String convertToHashMD4(String input) {
		byte[] data = input.getBytes();
		Digest md4 = new MD4Digest();
		md4.update(data, 0, data.length);

		byte[] md4Hash = new byte[md4.getDigestSize()];
		md4.doFinal(md4Hash, 0);

		return new String(Hex.encode(md4Hash));
	}

	public String convertToHashMD4FromFile(String filePath) {
		try {
			FileInputStream fileInputStream = new FileInputStream(filePath);
			byte[] buffer = new byte[1024];
			int bytesRead;
			Digest md4 = new MD4Digest();

			while ((bytesRead = fileInputStream.read(buffer)) != -1) {
				md4.update(buffer, 0, bytesRead);
			}

			byte[] md4Hash = new byte[md4.getDigestSize()];
			md4.doFinal(md4Hash, 0);

			return new String(Hex.encode(md4Hash));
		} catch (IOException e) {
			e.printStackTrace();
			return null;
		}
	}

	public boolean checkFile(String pathFile, String hashFile) {
		String hashMD4 = convertToHashMD4FromFile(pathFile);
		return hashFile.equals(hashMD4);

	}

	public static void main(String[] args) {
		String input = "Hello, MD4!";
		MD4 md = new MD4();
		String md4Hash = md.convertToHashMD4(input);
		System.out.println("MD4 Hash: " + md4Hash);

		String filePath = "D:\\Mobile.zip";
		String md4Hashfile = md.convertToHashMD4FromFile(filePath);

		if (md4Hashfile != null) {
			System.out.println("MD4 Hash file: " + md4Hashfile);
		} else {
			System.out.println("Error calculating MD4 hash for the file.");
		}
		System.out.println(md.checkFile(filePath, "766837957b883066b45d90e7bd26a074"));
	}
}
