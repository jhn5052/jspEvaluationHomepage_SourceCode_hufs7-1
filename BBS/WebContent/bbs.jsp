<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="bbs.BbsDTO" %>
<%@ page import="bbs.BbsDAO" %>
<%@ page import="user.UserDAO" %>
<%@ page import="user.UserDTO" %>
<%@ page import="java.util.ArrayList" %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<link rel="stylesheet" href="css/bootstrap.css">
<link rel="stylesheet" href="css/custom.css">
<title>HUFS 7+1</title>
<style type="text/css">
	a, a:hover{
		color: #000000;
		text-decoration: none;
	}
</style>
</head>
<body>
	<%
		String userID = null;
		if(session.getAttribute("userID") != null){
			userID = (String)session.getAttribute("userID");
		}
		String userCountry = null;
		if(session.getAttribute("userCountry") != null){
			userCountry = (String)session.getAttribute("userCountry");
		}
		int pageNumber = 1;//기본 1페이지
		if(request.getParameter("pageNumber") != null ){
			pageNumber = Integer.parseInt(request.getParameter("pageNumber"));
		}
		if(userID == null){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('로그인을 해주세요.');");
			script.println("location.href = 'login.jsp';");
			script.println("</script>");
			script.close();
			return;
		}
		boolean emailChecked = new UserDAO().getUserEmailChecked(userID);
		if(emailChecked == false){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("location.href = 'emailSendConfirm.jsp';");
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
			<%
				if(userID == null){
			%>
				<li class="nav-item dropdown">
					<a class="nav-link drop toggle" id="dropdown" data-toggle="dropdown">
						메뉴
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item" href="login.jsp">로그인</a>
						<a class="dropdown-item" href="join.jsp">회원가입</a>
						<a class="dropdown-item" href="bbs.jsp">게시판</a>
						<a class="dropdown-item" href="evaluation.jsp">강의평가</a>
						
					</div>
				</li>
			<%
				}else{
			%>
				<li class="nav-item dropdown">
					<a class="nav-link drop toggle" id="dropdown" data-toggle="dropdown">
						메뉴
					</a>
					<div class="dropdown-menu" aria-labelledby="dropdown">
						<a class="dropdown-item" href="logoutAction.jsp">로그아웃</a>
						<a class="dropdown-item active" href="bbs.jsp">게시판</a>
						<a class="dropdown-item" href="evaluation.jsp">강의평가</a>
						
					</div>
				</li>
			<%
				}
			%>
			</ul>
			<form action="./evaluation.jsp" method="get" class="form-inline my-2 my-lg-0">
				<input type="text" name ="search" class="form-control mr-sm-2" type="search" placeholder="내용을 입력하세요." aria-label="Search">
				<button class="btn btn-outline-success my-2 my-sm-0" type="submit">검색</button>
			</form>
		</div>
	</nav>
	
	<div class="container mt-4">
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th style="background-color: #eeeeee; text-align : center;">번호</th>
						<th style="background-color: #eeeeee; text-align : center;">제목</th>
						<th style="background-color: #eeeeee; text-align : center;">작성자</th>
						<th style="background-color: #eeeeee; text-align : center;">작성일</th>
					</tr>
				</thead>
				<tbody>
					<%
						BbsDAO bbsDAO = new BbsDAO();
						ArrayList<BbsDTO> list = bbsDAO.getList(pageNumber);
						for(int i = 0; i < list.size(); i++){
					%>
					<tr>
						<td><%= list.get(i).getBbsID() %></td>
						<td><a href="view.jsp?bbsID=<%= list.get(i).getBbsID() %>"><%= list.get(i).getBbsTitle().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></a></td>
						<td><%= list.get(i).getUserID() %></td>
						<td><%= list.get(i).getBbsDate().substring(0,11) + list.get(i).getBbsDate().substring(11,13) + "시" + list.get(i).getBbsDate().substring(14,16) + "분" %></td>
					</tr>
					<%
						}
					%>
				</tbody>
			</table>
			<%
				if(pageNumber != 1){
			%>
				<a href="bbs.jsp?pageNumber=<%=pageNumber - 1%>" class="btn btn-success btn-arrow-left">이전</a>
			<%
				} if(bbsDAO.nextPage(pageNumber + 1)){
			%>
					<a href="bbs.jsp?pageNumber=<%=pageNumber + 1%>" class="btn btn-success btn-arrow-left">다음</a>
			<% } %>
			&nbsp;<a href="write.jsp" class="btn btn-primary pull-right">글쓰기</a>
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