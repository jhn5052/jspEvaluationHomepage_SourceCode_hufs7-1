<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ page import="java.io.PrintWriter" %>
<%@ page import="java.lang.String" %>
<%@ page import="bbs.BbsDTO" %>
<%@ page import="bbs.BbsDAO" %>
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
		int bbsID = 0;
		if(request.getParameter("bbsID") != null){
				bbsID = Integer.parseInt(request.getParameter("bbsID"));
		}
		if(bbsID == 0){
			PrintWriter script = response.getWriter();
			script.println("<script>");
			script.println("alert('유효하지 않은 글입니다')");
			script.println("location.href = 'bbs.jsp'");
			script.println("</script>");
		}
		BbsDTO bbs =  new BbsDAO().getBbs(bbsID);
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
						<a class="dropdown-item" href="login.jsp">로그인</a>
						<a class="dropdown-item" href="join.jsp">회원가입</a>
						<a class="dropdown-item" href="bbs.jsp">게시판</a>
						<a class="dropdown-item" href="evaluation.jsp">강의평가</a>
				<%
					}else{
				%>
						<a class="dropdown-item" href="logoutAction.jsp">로그아웃</a>
						<a class="dropdown-item active" href="bbs.jsp">게시판</a>
						<a class="dropdown-item" href="evaluation.jsp">강의평가</a>
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
		<div class="row">
			<table class="table table-striped" style="text-align: center; border: 1px solid #dddddd">
				<thead>
					<tr>
						<th colspan="3" style="background-color: #eeeeee; text-align : center;">게시판 글 보기</th>											</tr>
					</thead>
				<tbody>
					<tr>
						<td style="width : 200px">글 제목</td>
						<td colspan="2"><%= bbs.getBbsTitle().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
					<tr>
						<td>작성자</td>
						<td colspan="2"><%= bbs.getUserID() %></td>
					</tr>
					<tr>
						<td>작성일자</td>
						<td colspan="2"><%= bbs.getBbsDate().substring(0,11) + bbs.getBbsDate().substring(11,13) + "시" + bbs.getBbsDate().substring(14,16) + "분" %></td>
					</tr>
					<tr>
						<td>내용</td>
						<td colspan="2" style="min-height:200px; text-align: left;"><%= bbs.getBbsContent().replaceAll(" ", "&nbsp;").replaceAll("<","&lt;").replaceAll(">","&gt;").replaceAll("\n","<br>") %></td>
					</tr>
				</tbody>
			</table>
			<a href = "bbs.jsp" class="btn btn-primary">목록</a>&nbsp;
			<%
				if(userID != null && userID.equals(bbs.getUserID())){//본인이면 삭제하고 수정가능하게
			%>
					<a href="update.jsp?bbsID=<%= bbsID%>" class="btn btn-primary">수정</a>&nbsp;
					<a onclick="return confirm('정말로 삭제하시겠습니까?')" href="deleteAction.jsp?bbsID=<%= bbsID%>" class="btn btn-primary">삭제</a>
			<%	
				}//onclick은 삭제할건지 메세지 나오는 것
			%>
			
		</div>
	</div>
	<footer class="bg-dark mt-4 p-5 text-center" style="color: #FFFFFF;">
		Copyright &copy; 2018 한민지 All Rights Reserved.
	</footer>
	<script src="https://code.jquery.com/jquery-3.1.1.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.min.js"></script>
	<script src="./js/popper.js"></script>>	
</body>
</html>