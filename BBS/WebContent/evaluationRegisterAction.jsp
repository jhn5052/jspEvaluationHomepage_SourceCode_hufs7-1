<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="evaluation.EvaluationDTO"%>
<%@ page import="evaluation.EvaluationDAO"%>
<%@ page import="util.SHA256"%>
<%@ page import="java.io.PrintWriter"%>

<%
		request.setCharacterEncoding("UTF-8");
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String) session.getAttribute("userID");
		}
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 해주세요.');");
			script.println("location.href = 'login.jsp'");
			script.println("</script>");
			script.close();
			return;
		}

		String lectureName = null;
		String professorName = null;
		int lectureYear = 0;
		String semesterDivide = null;
		String lectureDivide = null;
		String evaluationTitle = null;
		String evaluationContent = null;
		String totalScore = null;
		String comfortableScore = null;
		String midterm = null;
		String finalterm = null;
		String homework = null;
		String country = null;
		String school = null;

		if(request.getParameter("lectureName") != null){
			lectureName = request.getParameter("lectureName");
		}
		if(request.getParameter("professorName") != null){
			professorName = request.getParameter("professorName");
		}
		if(request.getParameter("lectureYear") != null){
			try{
				lectureYear = Integer.parseInt(request.getParameter("lectureYear"));
			} catch (Exception e){
				System.out.println("강의 연도 데이터 오류");
			}
		}
		if(request.getParameter("semesterDivide") != null){
			semesterDivide = request.getParameter("semesterDivide");
		}
		if(request.getParameter("lectureDivide") != null){
			lectureDivide = request.getParameter("lectureDivide");
		}
		if(request.getParameter("evaluationTitle") != null){
			evaluationTitle = request.getParameter("evaluationTitle");
		}
		if(request.getParameter("evaluationContent") != null){
			evaluationContent = request.getParameter("evaluationContent");
		}
		if(request.getParameter("totalScore") != null){
			totalScore = request.getParameter("totalScore");
		}
		if(request.getParameter("comfortableScore") != null){
			comfortableScore = request.getParameter("comfortableScore");
		}
		if(request.getParameter("midterm") != null){
			midterm = request.getParameter("midterm");
		}
		if(request.getParameter("finalterm") != null){
			finalterm = request.getParameter("finalterm");
		}
		if(request.getParameter("homework") != null){
			homework = request.getParameter("homework");
		}
		if(request.getParameter("country") != null){
			country = request.getParameter("country");
		}
		if(request.getParameter("school") != null){
			school = request.getParameter("school");
		}
		
		if(lectureName == null || professorName == null || semesterDivide == null || evaluationTitle == null || lectureDivide == null || lectureYear == 0 || evaluationContent == null
				|| totalScore == null || comfortableScore == null || midterm == null || finalterm == null || homework == null || country == null || school == null
				|| lectureName.equals("") || professorName.equals("")|| evaluationTitle.equals("") || evaluationContent.equals("") || homework.equals("") || country.equals("") || school.equals("")){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('입력이 안 된 사항이 있습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		}
		EvaluationDAO evaluationDAO = new EvaluationDAO();
		int result = evaluationDAO.write(new EvaluationDTO(0, userID, lectureName, professorName, lectureYear,
				semesterDivide, lectureDivide, evaluationTitle, evaluationContent, totalScore,
				comfortableScore, midterm, finalterm, homework, 0, country, school));
		if(result == -1) {//성공적인 회원가입이 안됨
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('강의 평가 등록에 실패했습니다.');");
			script.println("history.back();");
			script.println("</script>");
			script.close();
			return;
		} else {
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'evaluation.jsp'");//성공하면 다시 강의평가글로 가게
			script.println("</script>");
			script.close();
			return;
		}
%>