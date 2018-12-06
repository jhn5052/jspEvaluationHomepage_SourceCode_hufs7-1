<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="user.UserDAO" %>
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
		if(userID != null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인이 된 상태입니다.');");
			script.println("location.href = 'main.jsp';");
			script.println("</script>");
			script.close();
			return;
		}
	%>
	<nav class="navbar navbar-expand lg navbar-light bg-light">
		<a class="navbar-brand" href="main.jsp">HUFS 7+1</a>
		<button class="navbar-toggler" type="button" data-toggle = "collapse" data-target="#navbar">
			<span class="navbar-toggler-icon"></span>
		</button>
		<div id="navbar" class="collapse navbar-collapse">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item">
					<a class="nav-link" href="main.jsp">메인</a>
				<li class="nav-item dropdown">
					<a class="nav-link drop toggle" id="dropdown" data-toggle="dropdown">
						메뉴
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item active" href="login.jsp">로그인</a>
						<a class="dropdown-item" href="join.jsp">회원가입</a>
						<a class="dropdown-item" href="bbs.jsp">게시판</a>
						<a class="dropdown-item" href="evaluation.jsp">강의평가</a>
						
					</div>
				</li>
			</ul>
			<form action="./evaluation.jsp" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name ="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	<section class="container mt-3" style="max-width: 560px;">
		<form method="post" action="./loginAction.jsp">
			<h3 style="text-align: center;">로그인 화면</h3>
				<div class="form-group">
					<label>아이디</label>
					<input type="text" name="userID" class="form-control" >
				</div>
				<div class="form-group">	
					<label>비밀번호</label>
					<input type="password" name="userPassword" class="form-control">
				</div>
				<button type="submit" class="btn btn-primary">로그인</button>
				<a href="join.jsp" class="btn btn-success">회원 가입</a>
				<a href="findID.jsp" class="btn btn-success">아이디 찾기</a>
				<a href="findPassword.jsp" class="btn btn-success">비밀번호 찾기</a>
		</form>
	</section>
	
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2018 한민지 All Rights Reserved.
	</footer>	
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/popper.js"></script>
</body>
</html>