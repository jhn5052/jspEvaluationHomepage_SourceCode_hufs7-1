<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDTO"%>
<%@ page import="user.UserDAO"%>
<%@ page import="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
		request.setCharacterEncoding("UTF-8");
		String userID = null;
		String userPassword = null;
		String userEmail = null;
		String userCountry = null;
		if(request.getParameter("userID") != null){
			userID = request.getParameter("userID");
		}
		if(request.getParameter("userPassword") != null){
			userPassword = request.getParameter("userPassword");
		}
		if(request.getParameter("userEmail") != null){
			userEmail = request.getParameter("userEmail");
		}
		if(request.getParameter("userCountry") != null){
			userCountry = request.getParameter("userCountry");
		}
		if(userID == null || userPassword == null || userEmail == null || userCountry == null || userID == "" || userPassword == "" || userEmail == "" || userCountry == ""){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("location.href = 'join.jsp'");
			script.println("</script>");
			script.close();
			return;
		}
		
		UserDAO userDAO = new UserDAO();
		String email_result = userDAO.emailOverlap(userEmail);
		if(email_result != null) {//성공적인 회원가입이 안됨
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 존재하는 이메일 입니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
		int result = userDAO.join(new UserDTO(userID, userPassword, userEmail, SHA256.getSHA256(userEmail), false, userCountry, userID));
		if(result == -1) {//성공적인 회원가입이 안됨
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('이미 존재하는 아이디 입니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		} else {
			session.setAttribute("userID", userID);
			session.setAttribute("userCountry", userCountry);
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'emailSendAction.jsp'");//바로 이메일 인증 받게
			script.println("</script>");
			script.close();
			return;
		}
%>