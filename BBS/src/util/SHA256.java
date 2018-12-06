package util;

import java.security.MessageDigest;

public class SHA256 {
	public static String getSHA256(String input) {//�̸��ϰ��� �ؽ��� ����� ���� ��ȯ��Ŵ
		StringBuffer result = new StringBuffer();
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA-256");
			byte[] salt = "Hello! This is Salt.".getBytes();//SHA256����ϸ� ��Ŀ�� ���ؼ� ��ŷ�� �̷���� �� �־ ��ȣ�ϴ� ��
			digest.reset();
			digest.update(salt);
			byte[] chars = digest.digest(input.getBytes("UTF-8"));
			for(int i = 0; i < chars.length; i++) {
				String hex = Integer.toHexString(0xff & chars[i]);
				if(hex.length() == 1) {
					result.append("0");//16���� ���·� ��µǰ�
				}
				result.append(hex);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return result.toString();
	}
}
