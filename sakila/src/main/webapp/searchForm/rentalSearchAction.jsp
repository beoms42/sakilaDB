<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "vo.*" %>
<%
	int storeId = Integer.parseInt(request.getParameter("storeId"));
	String customerName = request.getParameter("customerName");
	String beginDate = request.getParameter("beginDate");
	String endDate = request.getParameter("endDate");
	
	//
	
	RentalDao rd = new RentalDao();
	List<Map<String, Object>> list = rd.selectRentalList(storeId, customerName, beginDate, endDate);
	
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
		
		<h1>대여 상세 결과</h1>
			
	
	  </div>
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>
</html>


