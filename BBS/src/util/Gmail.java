package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator  {//계정정보가 들어감, 인증을 수행해주는 Authenticator
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("HUFSBBS@gmail.com","qlrqod1011");//관리자 메일 아이디와 비밀번호
	}
}
