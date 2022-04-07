<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.*" %>
<%@ page import = "dao.*" %>
<%@ page import = "vo.*" %>
<%

	int currentPage = 1;
	
	if(request.getParameter("currentPage") !=  null) {
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	
	int rowPerPage = 5;
	int beginRow = (currentPage - 1) * rowPerPage;
	int lastPage = 0;

	int storeId = -1;
	if(!request.getParameter("storeId").equals("")) {
		storeId = Integer.parseInt(request.getParameter("storeId"));
	}
	String customerName ="";
	if(!request.getParameter("customerName").equals("")) {
		customerName = request.getParameter("customerName");
	}
	String beginDate = "";
	if(!request.getParameter("beginDate").equals("")) {
		beginDate = request.getParameter("beginDate");
	}
	String endDate = "";
	if(!request.getParameter("endDate").equals("")) {
		endDate = request.getParameter("endDate");
	}
	
	//
	System.out.println(storeId+"<----------storeId");
	System.out.println(customerName+"<----------customerName");
	System.out.println(beginDate+"<----------beginDate");
	System.out.println(endDate+"<----------endDate");
	
	RentalDao rd = new RentalDao();
	List<Map<String, Object>> list = rd.selectRentalList(storeId, customerName, beginDate, endDate, beginRow, rowPerPage);
	
	int count = 999999;
	count = rd.totalRentalDao(storeId, customerName, beginDate, endDate);
	
	
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
		<table border="1">
						<tr>
							<th>rentalId</th>
							<th>rentalDate</th>
							<th>inventoryId</th>
							<th>customerId</th>
							<th>returnDate</th>
							<th>staffId</th>
							<th>lastUpdate</th>
							<th>cName</th>
							<th>storeId</th>
							<th>title</th>
						</tr>
				<%
					for(Map m : list) {
				%>
						<tr>
							<td><%=m.get("rentalId")%></td>
							<td><%=m.get("rentalDate")%></td>
							<td><%=m.get("inventoryId")%></td>
							<td><%=m.get("customerId")%></td>
							<td><%=m.get("returnDate")%></td>
							<td><%=m.get("staffId")%></td>
							<td><%=m.get("lastUpdate")%></td>
							<td><%=m.get("cName")%></td>
							<td><%=m.get("storeId")%></td>
							<td><%=m.get("title")%></td>
						
						</tr>
				<%		
					} 
				%>

			</table>
				<% if(currentPage > 1) {%>
				<A href="<%=request.getContextPath()%>/searchForm/rentalSearchAction.jsp?currentPage=<%=currentPage-1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>" class="btn btn-primary"">[이전]</A>
				<%}%>
				<A href="<%=request.getContextPath()%>/searchForm/rentalSearchAction.jsp?currentPage=<%=currentPage+1%>&storeId=<%=storeId%>&customerName=<%=customerName%>&beginDate=<%=beginDate%>&endDate=<%=endDate%>" class="btn btn-primary">[다음]</A>
		</div>
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>
</html>


