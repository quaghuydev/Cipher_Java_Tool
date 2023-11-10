package model;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Random;

public class Hill {
	private String vie = "AĂÂBCDĐEÊFGHIJKLMNOÔPQRSTUƯVWXYZÁÀẢÃẠÉÈẺẼẸÍÌỈĨỊÓÒỎÕỌÚÙỦŨỤ";

	public static void main(String[] args) throws IOException {

		Hill hill = new Hill();
		String cipher = hill.encryptEng("you have the option to create a function that mirrors letter", "hill");
		// YOU HAVE THE OPTION TO CREATE A FUNCTION THAT MIRRORS LETTER
		System.out.println(cipher);
		System.out.println(hill.decryptEng(cipher, "hill"));
		;
//		System.out.println(hill.genKeyEng());

//		System.out.println(hill.genKeyVie());
	}

	public String genKeyEng() {
		Random random = new Random();
		int[][] key2D = new int[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] = random.nextInt(26);
			}
		}
		int determinant = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
		determinant = moduloFunc(determinant, 26);
		int modularInverse = -1;
		for (int i = 0; i < 26; i++) {
			int tempInv = determinant * i;
			if (moduloFunc(tempInv, 26) == 1) {
				modularInverse = i;
				break;
			}
		}

		if (modularInverse == -1) {
			return null;
		}
		String result = "";
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				result += (char) (key2D[i][j] + 65) + " ";
			}
		}
		return result.toLowerCase();
	}

	public String genKeyVie() {
		Random random = new Random();
		int[][] key2D = new int[2][2];
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] = random.nextInt(57);
			}
		}
		int determinant = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
		determinant = moduloFunc(determinant, 57);
		int modularInverse = -1;
		for (int i = 0; i < 57; i++) {
			int tempInv = determinant * i;
			if (moduloFunc(tempInv, 57) == 1) {
				modularInverse = i;
				break;
			}
		}

		if (modularInverse == -1) {
			return null;
		}
		String result = "";
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				result += vie.charAt(key2D[i][j]) + " ";
			}
		}
		return result.toLowerCase();
	}

	public String encryptEng(String text, String key) {
		String temp = text;
		text = text.replaceAll("\\s", "x");
		text = text.toUpperCase();
		System.out.println(text);
		int countLetter = 0;
		if (text.length() % 2 != 0) {
			text += "O";
			countLetter = 1;
		}
		int[][] msg2D = new int[2][text.length()];
		int index1 = 0;
		int index2 = 0;
		for (int i = 0; i < text.length(); i++) {
			if (i % 2 == 0) {
				msg2D[0][index1] = ((int) text.charAt(i)) - 65;
				index1++;
			} else {
				msg2D[1][index2] = ((int) text.charAt(i)) - 65;
				index2++;
			}
		}

		key = key.replaceAll("\\s", "");
		key = key.toUpperCase();
		int[][] key2D = new int[2][2];
		int index3 = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] = (int) key.charAt(index3) - 65;
				index3++;
			}
		}

		int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
		deter = moduloFunc(deter, 26);

		int mulInverse = -1;
		for (int i = 0; i < 26; i++) {
			int tempInv = deter * i;
			if (moduloFunc(tempInv, 26) == 1) {
				mulInverse = i;
				break;
			} else {
				continue;
			}
		}
		if (mulInverse == -1) {
			return "key không hợp lệ";
		}
		String encrypText = "";
		int itrCount = text.length() / 2;
		for (int i = 0; i < itrCount; i++) {
			int temp1 = msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1];
			encrypText += (char) ((temp1 % 26) + 65);
			int temp2 = msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1];
			encrypText += (char) ((temp2 % 26) + 65);
		}
		if (countLetter != 0) {
			encrypText = encrypText.substring(0, encrypText.length() - countLetter);
		}
		String result = "";
		for (int i = 0; i < encrypText.length(); i++) {
			char c = temp.charAt(i);
			char a = encrypText.charAt(i);
			if (Character.isUpperCase(c)) {
				result += Character.toUpperCase(a);
			} else {
				result += Character.toLowerCase(a);
			}

		}
		System.out.println(encrypText);

		return result;
	}

	public String decryptEng(String cipher, String key) {
		String temp = cipher;
		cipher = cipher.toUpperCase();
		int countLetter = 0;
		if (cipher.length() % 2 != 0) {
			cipher += "O";
			countLetter = 1;
		}
		int[][] msg2D = new int[2][cipher.length()];
		int itr1 = 0;
		int itr2 = 0;
		for (int i = 0; i < cipher.length(); i++) {
			if (i % 2 == 0) {
				msg2D[0][itr1] = ((int) cipher.charAt(i)) - 65;
				itr1++;
			} else {
				msg2D[1][itr2] = ((int) cipher.charAt(i)) - 65;
				itr2++;
			}
		}
		key = key.replaceAll("\\s", "");
		key = key.toUpperCase();

		int[][] key2D = new int[2][2];
		int itr3 = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] = (int) key.charAt(itr3) - 65;
				itr3++;
			}
		}
		int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
		deter = moduloFunc(deter, 26);

		int mulInverse = -1;
		for (int i = 0; i < 26; i++) {
			int tempInv = deter * i;
			if (moduloFunc(tempInv, 26) == 1) {
				mulInverse = i;
				break;
			} else {
				continue;
			}
		}
		int swapTemp = key2D[0][0];
		key2D[0][0] = key2D[1][1];
		key2D[1][1] = swapTemp;
		key2D[0][1] *= -1;
		key2D[1][0] *= -1;

		key2D[0][1] = moduloFunc(key2D[0][1], 26);
		key2D[1][0] = moduloFunc(key2D[1][0], 26);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] *= mulInverse;
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] = moduloFunc(key2D[i][j], 26);
			}
		}

		String decrypText = "";
		int itrCount = cipher.length() / 2;
		for (int i = 0; i < itrCount; i++) {
			int temp1 = msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1];
			decrypText += (char) ((temp1 % 26) + 65);
			int temp2 = msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1];
			decrypText += (char) ((temp2 % 26) + 65);
		}
		if (countLetter != 0) {
			decrypText = decrypText.substring(0, decrypText.length() - countLetter);
		}

		String result = "";
		for (int i = 0; i < decrypText.length(); i++) {
			char c = temp.charAt(i);
			char a = decrypText.charAt(i);
			if (Character.isUpperCase(c)) {
				result += Character.toUpperCase(a);
			} else {
				result += Character.toLowerCase(a);
			}

		}
		System.out.println(decrypText);

		return result.replaceAll("x", " ");

	}

	public String encryptVie(String text, String key) {
		String temp = text;
		text = text.replaceAll("\\s", "x");
		text = text.toUpperCase();
		System.out.println(text);
		int countLetter = 0;
		if (text.length() % 2 != 0) {
			text += "A";
			countLetter = 1;
		}
		int[][] msg2D = new int[2][text.length()];
		int itr1 = 0;
		int itr2 = 0;
		for (int i = 0; i < text.length(); i++) {
			if (i % 2 == 0) {
				msg2D[0][itr1] = vie.indexOf(text.charAt(i));
				itr1++;
			} else {
				msg2D[1][itr2] = vie.indexOf(text.charAt(i));
				itr2++;
			}
		}

		key = key.replaceAll("\\s", "");
		key = key.toUpperCase();
		int[][] key2D = new int[2][2];
		int itr3 = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2D[i][j] = vie.indexOf(key.charAt(itr3));
				itr3++;
			}
		}

		int deter = key2D[0][0] * key2D[1][1] - key2D[0][1] * key2D[1][0];
		deter = moduloFunc(deter, 57);
		int mulInverse = -1;
		for (int i = 0; i < 57; i++) {
			int tempInv = deter * i;
			if (moduloFunc(tempInv, 57) == 1) {
				mulInverse = i;
				break;
			}
		}

		if (mulInverse == -1) {
			return "key không hợp lệ";
		}
		String encrypText = "";
		int itrCount = text.length() / 2;
		for (int i = 0; i < itrCount; i++) {
			int temp1 = (msg2D[0][i] * key2D[0][0] + msg2D[1][i] * key2D[0][1]) % 57;
			encrypText += vie.charAt(temp1);
			int temp2 = (msg2D[0][i] * key2D[1][0] + msg2D[1][i] * key2D[1][1]) % 57;
			encrypText += vie.charAt(temp2);
		}
		if (countLetter != 0) {
			encrypText = encrypText.substring(0, encrypText.length() - countLetter);
		}
		String result = "";
		for (int i = 0; i < encrypText.length(); i++) {
			char c = temp.charAt(i);
			char a = encrypText.charAt(i);
			if (Character.isUpperCase(c)) {
				result += Character.toUpperCase(a);
			} else {
				result += Character.toLowerCase(a);
			}
		}
		System.out.println(encrypText);
		return result;
	}

	public String decryptVie(String cipher, String key) {
		String temp = cipher;
		cipher = cipher.toUpperCase();
		int countLetter = 0;
		if (cipher.length() % 2 != 0) {
			cipher += "A";
			countLetter = 1;
		}
		int[][] msg2d = new int[2][cipher.length()];
		int itr1 = 0;
		int itr2 = 0;
		for (int i = 0; i < cipher.length(); i++) {
			if (i % 2 == 0) {
				msg2d[0][itr1] = vie.indexOf(cipher.charAt(i));
				itr1++;
			} else {
				msg2d[1][itr2] = vie.indexOf(cipher.charAt(i));
				itr2++;
			}
		}
		key = key.replaceAll("\\s", "");
		key = key.toUpperCase();

		int[][] key2d = new int[2][2];
		int itr3 = 0;
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2d[i][j] = vie.indexOf(key.charAt(itr3));
				itr3++;
			}
		}
		int deter = key2d[0][0] * key2d[1][1] - key2d[0][1] * key2d[1][0];
		deter = moduloFunc(deter, 57);
		int mulInverse = -1;
		for (int i = 0; i < 57; i++) {
			int tempInv = deter * i;
			if (moduloFunc(tempInv, 57) == 1) {
				mulInverse = i;
				break;
			}
		}
		int swapTemp = key2d[0][0];
		key2d[0][0] = key2d[1][1];
		key2d[1][1] = swapTemp;
		key2d[0][1] *= -1;
		key2d[1][0] *= -1;
		key2d[0][1] = moduloFunc(key2d[0][1], 57);
		key2d[1][0] = moduloFunc(key2d[1][0], 57);
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2d[i][j] *= mulInverse;
			}
		}
		for (int i = 0; i < 2; i++) {
			for (int j = 0; j < 2; j++) {
				key2d[i][j] = moduloFunc(key2d[i][j], 57);
			}
		}
		String decrypText = "";
		int itrCount = cipher.length() / 2;
		for (int i = 0; i < itrCount; i++) {
			int temp1 = (msg2d[0][i] * key2d[0][0] + msg2d[1][i] * key2d[0][1]) % 57;
			decrypText += vie.charAt(temp1);
			int temp2 = (msg2d[0][i] * key2d[1][0] + msg2d[1][i] * key2d[1][1]) % 57;
			decrypText += vie.charAt(temp2);
		}
		if (countLetter != 0) {
			decrypText = decrypText.substring(0, decrypText.length() - countLetter);
		}
		String result = "";
		for (int i = 0; i < decrypText.length(); i++) {
			char c = temp.charAt(i);
			char a = decrypText.charAt(i);
			if (Character.isUpperCase(c)) {
				result += Character.toUpperCase(a);
			} else {
				result += Character.toLowerCase(a);
			}
		}
		System.out.println(decrypText);
		return result.replaceAll("x", " ");
	}

		public int moduloFunc(int a, int b) {
		int result = a % b;
		if (result < 0) {
			result += b;
		}
		return result;
	}

}