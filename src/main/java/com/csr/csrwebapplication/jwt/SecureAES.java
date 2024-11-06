package com.csr.csrwebapplication.jwt;

import java.util.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

public class SecureAES {

	// Encrypt data using AES-256 with a specific key and IV
	public static String encrypt(String data) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(EncryptionConfig.KEY.getBytes(), EncryptionConfig.ALGORITHM);
		IvParameterSpec ivSpec = new IvParameterSpec(EncryptionConfig.IV.getBytes());
		Cipher cipher = Cipher.getInstance(EncryptionConfig.TRANSFORMATION);
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, ivSpec);
		byte[] encryptedBytes = cipher.doFinal(data.getBytes());
		return Base64.getEncoder().encodeToString(encryptedBytes);
	}

	// Decrypt data using AES-256 with a specific key and IV
	public static String decrypt(String encryptedData) throws Exception {
		SecretKeySpec secretKey = new SecretKeySpec(EncryptionConfig.KEY.getBytes(), EncryptionConfig.ALGORITHM);
		IvParameterSpec ivSpec = new IvParameterSpec(EncryptionConfig.IV.getBytes());
		Cipher cipher = Cipher.getInstance(EncryptionConfig.TRANSFORMATION);
		cipher.init(Cipher.DECRYPT_MODE, secretKey, ivSpec);
		byte[] decodedBytes = Base64.getDecoder().decode(encryptedData);
		byte[] decryptedBytes = cipher.doFinal(decodedBytes);
		return new String(decryptedBytes);
	}

//	public static void main(String[] args) {
//		try {
//			// Data to encrypt
//			String originalData = "aWF0IjoxNzI1MDAxMjY1fQ.DKZGnLyRV02EsURhqk8JrLqEZZBr8LjN-jXW8YlKTJU";
//			System.out.println("Original Data: " + originalData);
//
//			// Encrypt
//			String encryptedData = encrypt(originalData);
//			System.out.println("Encrypted Data: " + encryptedData);
//
//			// Decrypt
//			String decryptedData = decrypt(encryptedData);
//			System.out.println("Decrypted Data: " + decryptedData);
//
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//	}

}
