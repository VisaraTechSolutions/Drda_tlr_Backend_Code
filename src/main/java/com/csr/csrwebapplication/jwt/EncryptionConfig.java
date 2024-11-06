package com.csr.csrwebapplication.jwt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;

@Configuration
public class EncryptionConfig {

	@Value("${encryption.algorithm}")
	private String algorithm;

	@Value("${encryption.transformation}")
	private String transformation;

	@Value("${encryption.key}")
	private String key;

	@Value("${encryption.iv}")
	private String iv;

	@Value("${jwt.secret}")
	private String secret_key;

	public static String ALGORITHM;
	public static String TRANSFORMATION;
	public static String KEY;
	public static String IV;
	public static String SECRET_KEY;

	@PostConstruct
	public void init() {
		ALGORITHM = this.algorithm;
		TRANSFORMATION = this.transformation;
		KEY = this.key;
		IV = this.iv;
		SECRET_KEY = this.secret_key;
	}
}
