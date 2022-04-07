<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "vo.*" %>
<%
	StoreDao storeDao = new StoreDao();
	List<Integer> storeIdList = storeDao.selectStoreIdList();
	
	
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>filmSearchForm</title>
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
		
		<h1>대여 상세 검색</h1>
			
		<form action="<%=request.getContextPath()%>/searchForm/rentalSearchAction.jsp" method="post">
				<table border="1">
					<tr>
						<td>스토어 ID</td>
						<td>
							<%
								for(int i : storeIdList) {
									
							%>
								<div><input type="radio" name="storeId" value="<%=i%>"><%=i%>번 가게</div>
							<%
								}
							%>
						</td>
					</tr>
					
					<tr>
						<td>고객이름</td>
						<td><input type="text" name="customerName"></td>
					</tr>
					
					<tr>
						<td>대여일자</td>
						<td><input type="date" name="beginDate"> ~ <input type="date" name="endDate"></td>
					</tr>
					
					<tr>
						<td>제출</td>
						<td><button type="submit">제출하기</button></td>
					</tr>
				</table>
		</form>
	  </div>
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>
</html>


