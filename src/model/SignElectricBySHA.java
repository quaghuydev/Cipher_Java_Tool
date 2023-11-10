package model;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;

public class SignElectricBySHA {

	public static boolean verifyFileIntegrity(String filePath, String expectedHash, String type)
			throws NoSuchAlgorithmException, IOException {
		SHA sha = new SHA();
		String actualHash = sha.convertFileToHash(filePath, type);
		return expectedHash.equals(actualHash);
	}

	public static void main(String[] args) {
		String filePath = "D://2.docx";
		String expectedHash = "beae14c5ac216a1d0ec7e3506ce6c43920b6c03076fa4b97d9b8b58b9e2e50f0"; // Thay thế bằng mã
																									// hash bạn muốn
																									// kiểm tra
		SHA sha = new SHA();
		try {
			boolean isIntegrityValid = verifyFileIntegrity(filePath, expectedHash, "SHA-256");
			if (isIntegrityValid) {
				System.out.println("Tệp tin không bị thay đổi.");
			} else {
				System.out.println("Tệp tin đã bị thay đổi hoặc mã hash không khớp.");
			}
		} catch (IOException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
	}
}
