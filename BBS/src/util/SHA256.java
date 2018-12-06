package util;

import java.security.MessageDigest;

public class SHA256 {
	public static String getSHA256(String input) {//이메일값에 해쉬가 적용된 값을 반환시킴
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] salt = "Hello! This is Salt.".getBytes();//SHA256사용하면 해커에 의해서 해킹이 이루어질 수 있어서 보호하는 것
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) {
					result.append("0");//16진수 형태로 출력되게
				}
				result.append(hex);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}
}
