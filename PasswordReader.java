/*
 * Name: Sipeng He
 * UCID: 30113342
 * Tutorial: T03
 * 
 * Version: April 9th, 2021
 * -the password reader can read the encrypted password from password.txt
 * 
 * Version: April 10th, 2021
 * -the password reader can decrypt the password and return it back to the login Dialog 
 */

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class PasswordReader {
	private String encrypted;
	private String decrypted;
	private boolean ifDecrypted;

	public PasswordReader() {
		decrypted = new String();
		ifDecrypted = false;
	}

	public String getPassword() {
		if (ifDecrypted == true) {
			return decrypted;
		} else {
			getEncryptedPassword();
			decryptPassword();
			return decrypted;
		}
	}

	public void getEncryptedPassword() {
		try {
			FileReader fr = new FileReader("password.txt");
			BufferedReader br = new BufferedReader(fr);
			encrypted = br.readLine();
			fr.close();
		} catch (FileNotFoundException e) {
			System.out.println("Could not open password.txt");
		} catch (IOException e) {
			System.out.println("Trouble reading from password.txt");
		}
	}

	public void decryptPassword() {
		char[] password = encrypted.toCharArray();
		for (int i = 0; i < encrypted.length(); i++) {
			if (password[i] != 'z') {
				decrypted = decrypted + (char) (password[i] + 1);
			} else {
				decrypted = decrypted + 'a';
			}
		}
		ifDecrypted = true;
	}
}
