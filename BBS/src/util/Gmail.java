package util;

import javax.mail.Authenticator;
import javax.mail.PasswordAuthentication;

public class Gmail extends Authenticator  {//���������� ��, ������ �������ִ� Authenticator
	
	@Override
	protected PasswordAuthentication getPasswordAuthentication() {
		return new PasswordAuthentication("HUFSBBS@gmail.com","qlrqod1011");//������ ���� ���̵�� ��й�ȣ
	}
}
