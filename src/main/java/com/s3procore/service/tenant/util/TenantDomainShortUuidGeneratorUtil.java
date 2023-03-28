package com.s3procore.service.tenant.util;

import java.util.UUID;

public class TenantDomainShortUuidGeneratorUtil {

	public static String[] chars = new String[] { "2", "3", "4", "5", "6", "7", "8", "9", 
			"a", "b", "c", "d", "e", "f", "g", "h", "i", "j", "k", "m", "n", "p", "q", "r", "s", "t", "u", "v", "w", "x", "y", "z",
			"A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "M", "N", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z" };
	
	/**
	  * <li>The short 8-bit UUID idea actually draws on the microblog short domain generation method, but its repetition probability is too high, and each time you generate 4, you need to select one immediately.
           * <li>This algorithm uses 56 printable characters to generate 32-bit UUIDs randomly. Since the UUIDs are all hexadecimal, the UUIDs are divided into 8 groups, each of which is a group, and then operated by modulo 56. Take the character as an index,
	  * <li>This repetition rate is greatly reduced.
	  * <li>Recommendation: The encoding field of the table plus a unique index 
	 * @return
	 */
	public static String generateShortUuid() {
		StringBuffer shortBuffer = new StringBuffer();
		String uuid = UUID.randomUUID().toString().replace("-", "");
		for (int i = 0; i < 8; i++) {
			String str = uuid.substring(i * 4, i * 4 + 4);
			int x = Integer.parseInt(str, 16);
			shortBuffer.append(chars[x % 0x38]);
		}
		return shortBuffer.toString().toLowerCase();
	}
}