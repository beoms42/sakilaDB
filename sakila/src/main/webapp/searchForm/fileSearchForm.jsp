<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>

<%
	CategoryDao cd = new CategoryDao();
	List<Category> list = cd.selectCategoryList();

	FilmDao fd = new FilmDao();
	List<Double> priceList = fd.selectFilmPriceDistinctList();
%>
    
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
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
	  <h1> 필름 리스트 뷰 검색</h1>
		<form method="post" action="<%=request.getContextPath()%>/procedureReport.jsp">
			<table>
				<tr>
					<td>Category</td>
					<td>	
						<select name="categoryName">
				                  <option value="">카테고리선택</option>
				                  <%
				                     for(Category c : list) {
				                  %>
				                        <option value="<%=c.getName()%>"><%=c.getName()%></option>
				                  <%      
				                     }
				                  %>
		               </select>   
					</td>
				</tr>
				
				<tr>
					<td>Rating</td>
					<td>
						<select name="rating">
							<option value="">등급선택</option>
							<option value="G">G</option>
							<option value="PG">PG</option>
							<option value="PG-13">PG-13</option>
							<option value="R">R</option>
							<option value="NC-17">NC-17</option>
						</select>
					</td>
				</tr>
				
				
				<tr>
					<td>대여료</td>
					<td>
						<%
							for(Double d : priceList) {
						%>
							<input type = "radio" name = "price" value="<%=d%>"><%=d%>
						
						<%
							}
						%>
					</td>
				</tr>
				<tr>
					<td>영화시간</td>
					<td>
						<div><input type="radio" name="length" value="">선택안함</div>
						<div><input type="radio" name="length" value="0">0-1 시간</div>
						<div><input type="radio" name="length" value="1">1-2 시간</div>
						<div><input type="radio" name="length" value="2">2시간 이상</div>
					</td>
				</tr>
				
				<tr>
					<td>제목 검색</td>
					<td><input type="text" name="searchTitle"></td>
				</tr>
				
				<tr>
					<td>배우 검색</td>
					<td><input type="text" name="searchActor"></td>
				</tr>
				
			</table>
		</form>
	  </div>
	  
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>


<body>
	
</body>
</html>