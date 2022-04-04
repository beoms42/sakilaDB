<%@page import="vo.ActorInfo"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*"%>
<%@ page import="dao.*"%>
<%

	int currentPage = 1;
	if(request.getParameter("currentPage") !=  null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}

	int rowPerPage = 5;
	int beginRow = (currentPage - 1) * rowPerPage;
	
	ActorInfoDao ai = new ActorInfoDao();
	List<ActorInfo> list = ai.selectActorInfoListByPage(beginRow, rowPerPage);
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Store List</title>
<link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@4.6.1/dist/css/bootstrap.min.css">
</head>
<body>
<div class="jumbotron text-center" style="margin-bottom:0">
	  <h1>SAKILA DB</h1>
	 <jsp:include page="/inc/upMenu.jsp"></jsp:include>
	</div>

	<br>
	<br>
	<br>
	 
	
	<div class="row">
	
	  <div class="container col-sm-1">
	  </div>
	  <div class="container col-sm-1">
		
		<jsp:include page="/inc/leftMenu.jsp"></jsp:include>
	  </div>
	  <div class="container col-sm-1">
	  </div>
	  
	  <div class="col-sm-5 container">
		
	<h1>Store List</h1>
	<table border="1" class="table table-striped">
		<thead>
			<tr>
				<th>actorId</th>
				<th>firstName</th>
				<th>lastName</th>
				<th>filmInfo</th>
			</tr>
		</thead>
		<tbody>
			<%
				for(ActorInfo a : list) {
			%>
					<tr>
						<td><%=a.getActorId()%></td>
						<td><%=a.getFirstName()%></td>
						<td><%=a.getLastName()%></td>
						<td><%=a.getFilmInfo()%></td>
					</tr>
			<%
				}
			%>
		</tbody>
		
		<% if(currentPage > 1) {%>
		<A href="<%=request.getContextPath()%>/actorList.jsp?currentPage=<%=currentPage-1%>" class="btn btn-secondary">[이전]</A>
		<%}%>
		<A href="<%=request.getContextPath()%>/actorList.jsp?currentPage=<%=currentPage+1%>" class="btn btn-primary">[다음]</A>
			
	</table>
	  </div>
	  
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>
</html>