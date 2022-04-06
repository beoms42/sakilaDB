<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*"%>
<%@ page import = "dao.*"%>
<%@ page import = "vo.*"%>
<%
	String category = request.getParameter("category");
	String rating = request.getParameter("rating");
	double price = -1; // price 데이터가 입력되지 않았을때
	if(!request.getParameter("price").equals("")) {
		price = Double.parseDouble(request.getParameter("price"));
	}
	int length = -1; // length 데이터가 입력되지 않았을때
	if(!request.getParameter("length").equals("")) {
		length = Integer.parseInt(request.getParameter("length"));
	}
	String title = request.getParameter("title");
	String actor = request.getParameter("actor");
	
	int currentPage = 1;
	
	if(request.getParameter("currentPage") !=  null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}

	int rowPerPage = 5;
	int beginRow = (currentPage - 1) * rowPerPage;
	int lastPage = 0;
	
	
	FilmDao filmDao = new FilmDao();
	List<FilmList> list = filmDao.selectFilmListSearch(beginRow ,rowPerPage ,category, rating, price, length, title, actor);
	int count = filmDao.totalRow( category, rating, price, length, title, actor);
	
	
	
	
%>

<!DOCTYPE html>
<html>
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
		
			<table border="1">
				<%
					for(FilmList f : list) {
				%>
						<tr>
							<td><%=f.getFid()%></td>
							<td><%=f.getTitle()%></td>
							<td><%=f.getDescription()%></td>
							<td><%=f.getCategory()%></td>
							<td><%=f.getPrice()%></td>
							<td><%=f.getLength()%></td>
							<td><%=f.getRating()%></td>
							<td><%=f.getActors()%></td>
						</tr>
				<%		
					} 
				%>
				<tr>
					<td><%=count %></td>
				<tr>
			</table>
			
			<% if(currentPage > 1) {%>
			<A href="<%=request.getContextPath()%>/searchForm/filmSearchAction.jsp?currentPage=<%=currentPage-1%>" class="btn btn-secondary">[이전]</A>
			<%}%>
			<A href="<%=request.getContextPath()%>/searchForm/filmSearchAction.jsp?currentPage=<%=currentPage+1%>&category=<%=category%>&rating=<%=rating%>&price=<%=price%>&length=<%=length%>&title=<%=title%>&actor=<%=actor%>" class="btn btn-primary">[다음]</A>
		
	
	  </div>
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>
</html>
