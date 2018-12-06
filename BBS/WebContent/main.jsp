<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="user.UserDAO" %>
<%@ page import="evaluation.EvaluationDTO" %>
<%@ page import="evaluation.EvaluationDAO" %>
<%@ page import="java.net.URLEncoder" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>HUFS 7+1</title>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String)session.getAttribute("userID");
		}
	%>
	<nav class="navbar navbar-expand lg navbar-light bg-light">
		<a class="navbar-brand" href="main.jsp">HUFS 7+1</a>
		<button class="navbar-toggler" type="button" data-toggle = "collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active">
				<a class="nav-link" href="main.jsp">메인</a>
				<li class="nav-item dropdown">
					<a class="nav-link drop toggle" id="dropdown" data-toggle="dropdown">
						메뉴
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
				<%
					if(userID == null){
				%>
						<a class="dropdown-item" href="./login.jsp">로그인</a>
						<a class="dropdown-item" href="./join.jsp">회원가입</a>
						<a class="dropdown-item" href="./bbs.jsp">게시판</a>
						<a class="dropdown-item" href="./evaluation.jsp">강의평가</a>
				<%
					}else{
				%>
						<a class="dropdown-item" href="./logoutAction.jsp">로그아웃</a>
						<a class="dropdown-item" href="./bbs.jsp">게시판</a>
						<a class="dropdown-item" href="./evaluation.jsp">강의평가</a>
				<%
					}
				%>
					</div>
				</li>
			</ul>
			<form action="./evaluation.jsp" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name ="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	

	<div class="container">
		<p style="text-align: center;">
			<img src="images/HUFS-meet-the-world.jpg">
		</p>
	</div>
	<div class="container  ">
		<div class="jumbotron">
			<div class="container">
				<h1>HUFS 7+1</h1>
				<p>한국외대 7+1 전용 게시판입니다.</p>
				<p><a class="btn btn-primary btn-pull" href="http://oia.hufs.ac.kr/" role="button">자세히 알아보기</a></p>
			</div>
		</div>
	</div>
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2018 한민지 All Rights Reserved.
	</footer>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/popper.js"></script>
</body>
</html>