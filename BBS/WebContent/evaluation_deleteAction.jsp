<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="user.UserDAO" %>
<%@ page import="evaluation.EvaluationDAO" %>
<%@ page import="likey.LikeyDTO" %>
<%@ page import="java.io.PrintWriter"%>

<%
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
		request.setCharacterEncoding("UTF-8");
		String evaluationID = null;
		if(request.getParameter("evaluationID") != null){
			evaluationID = request.getParameter("evaluationID");
		}
		
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		if(userID.equals(evaluationDAO.getUserID(evaluationID))){//동일인일때
			int result = new EvaluationDAO().delete(evaluationID);
			if(result == 1){//성공적으로 삭제되었을때
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('삭제가 완료되었습니다.');");
				script.println("location.href = 'evaluation.jsp'");
				script.println("</script>");
				script.close();
				return;
			}
			else {
				PrintWriter script = response.getWriter();
				script.println("<script>");
				script.println("alert('데이터베이스 오류가 발생했습니다..');");
				script.println("history.back();");
				script.println("</script>");
				script.close();
				return;
			}
		}
		
		else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('자신이 쓴 글만 삭제 가능합니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
%>