<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="javax.mail.Transport"%>
<%@ page import="javax.mail.Message"%>
<%@ page import="javax.mail.Address"%>
<%@ page import="javax.mail.internet.InternetAddress"%>
<%@ page import="javax.mail.internet.MimeMessage"%>
<%@ page import="javax.mail.Session"%>
<%@ page import="javax.mail.Authenticator"%>
<%@ page import="java.util.Properties"%>
<%@ page import="user.UserDAO"%>
<%@ page import="util.SHA256"%>
<%@ page import="util.Gmail"%>
<%@ page import="java.io.PrintWriter"%>

<%
//이메일을 보내는 함수
		UserDAO userDAO = new UserDAO();
		String userID = null;
		if(session.getAttribute("userID") != null){//세션값이 유효할때
			userID = (String) session.getAttribute("userID");
		}
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 해주세요.');");
			script.println("locatioin.href = 'login.jsp'");
			script.println("</script>");
			script.close();
			return;
		}
		boolean emailChecked = userDAO.getUserEmailChecked(userID);
		if(emailChecked == true){//성공적인 회원가입이 됨
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 인증된 회원입니다.');");
			script.println("location.href = 'main.jsp';");
			script.println("</script>");
			script.close();
			return;
		}
		String host = "http://localhost:8080/BBS/";
		String from = "hufsbbs@gmail.com";
		String to = userDAO.getUserEmail(userID);
		String subject = "회원가입을 위한 인증 메일입니다.";
		String content = "다음 링크에 접속하여 이메일 인증을 진행하세요." +
			"<a href='" + host + "emailCheckAction.jsp?code=" + new SHA256().getSHA256(to) + "'>이메일 인증하기</a>";
	
		Properties p = new Properties();
		p.put("mail.smtp.user", from);
		p.put("mail.smtp.host", "smtp.googlemail.com");
		p.put("mail.smtp.port", "465");//정해져있는 포트번호
		p.put("mail.smtp.starttls.enable", "true");
		p.put("mail.smtp.auth", "true");
		p.put("mail.smtp.debug", "true");
		p.put("mail.smtp.socketFactory.port", "465");
		p.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
		p.put("mail.smtp.socketFactory.fallback", "false");
	
		try{//메일을 실제로 전송하는 부분
			Authenticator auth = new Gmail();
			Session ses = Session.getInstance(p, auth);
			ses.setDebug(true);
			MimeMessage msg = new MimeMessage(ses);
			msg.setSubject(subject);
			Address fromAddr = new InternetAddress(from);
			msg.setFrom(fromAddr);//보내는 사람 정보
			Address toAddr = new InternetAddress(to);
			msg.addRecipient(Message.RecipientType.TO, toAddr);
			msg.setContent(content, "text/html;charset=UTF8");
			Transport.send(msg);		
		}catch(Exception e){
			e.printStackTrace();
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('오류가 발생했습니다.');");
			script.println("history.back();");//바로 이메일 인증 받게
			script.println("</script>");
			script.close();
			return;
		}
%>

<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>HUFS 7+1</title>
</head>
<body>
	<nav class="navbar navbar-expand lg navbar-light bg-light">
		<a class="navbar-brand" href="main.jsp">HUFS 7+1</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item"><a class="nav-link" href="main.jsp">메인</a>
				<li class="nav-item dropdown"><a class="nav-link drop toggle"
					id="dropdown" data-toggle="dropdown"> 메뉴 </a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item" href="join.jsp">회원가입</a> <a
							class="dropdown-item" href="bbs.jsp">게시판</a> <a
							class="dropdown-item" href="evaluation.jsp">강의평가</a>

					</div></li>
			</ul>
			<form action="./evaluation.jsp" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name ="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	<section class="container mt-3" style="max-width: 560px;">
		<div class ="alert alert-success mt-4" role="alert">
			이메일 주소 인증 메일이 전송되었습니다. 회원가입시 입력했던 이메일에 들어가셔서 인증해주세요.
		</div>
	</section>

	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2018 한민지 All Rights Reserved. </footer>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/popper.js"></script>
</body>
</html>
