<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="user.UserDTO"%>
<%@ page import="user.UserDAO"%>
<%@ page import="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
		request.setCharacterEncoding("UTF-8");
		String Find_userID = null;
		String userEmail = null;
		String userCountry = null;
		if(request.getParameter("Find_userID") != null){
			Find_userID = request.getParameter("Find_userID");
		}
		if(request.getParameter("userEmail") != null){
			userEmail = request.getParameter("userEmail");
		}
		if(request.getParameter("userCountry") != null){
			userCountry = request.getParameter("userCountry");
		}
		if(Find_userID == null || userEmail == null || userCountry == null || Find_userID == "" || userEmail == "" || userCountry == ""){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
		UserDAO userDAO = new UserDAO();
		String result = userDAO.findUserPassword(Find_userID, userEmail, userCountry);
		if(result == null) {//성공적인 회원가입이 안됨
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('데이터베이스 오류가 발생했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		} else {
			session.setAttribute("Find_userID", Find_userID);
			session.setAttribute("userEmail", userEmail);
			session.setAttribute("userCountry", userCountry);
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'findPassword_emailSendAction.jsp'");//바로 이메일 인증 받게
			script.println("</script>");
			script.close();
			return;
		}
%>