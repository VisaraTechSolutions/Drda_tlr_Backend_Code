package com.csr.csrwebapplication.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class IdGenerator {

	private static final Random RANDOM = new Random();

	public static String generateUserId() {
		int randomInt = RANDOM.nextInt(900) + 100; // Generate a 3-digit number
		return "USER" + randomInt;
	}

	public static String generateCompanyId() {
		int randomInt = RANDOM.nextInt(900) + 100; // Generate a 3-digit number
		return "COMP" + randomInt;
	}

	public static String generateApplicationId() {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");
		String datePart = dateFormat.format(new Date());

		int randomPart = RANDOM.nextInt(900) + 100; // Generate a 3-digit number
		return datePart + randomPart;
	}
	
	public static String generateDescId() {
		int randomInt = RANDOM.nextInt(900) + 100; // Generate a 3-digit number
		return "DESC" + randomInt;
	}
	
	public static String generateDocId() {
		int randomInt = RANDOM.nextInt(900) + 100; // Generate a 3-digit number
		return "DOC" + randomInt;
	}
}
