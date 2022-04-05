<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ page import="vo.*" %>
<%@ page import="dao.*" %>
<%	
	int currentPage = 1;
	if(request.getParameter("currentPage") != null) { // 이전, 다음 링크를 통해서 들어왔다면
		currentPage = Integer.parseInt(request.getParameter("currentPage"));
	}
	System.out.println(currentPage+ " <-- currentPage"); // 디버깅
	
	// 페이지 바뀌면 끝이 아니고, 가지고 오는 데이터가 변경되어야 한다.
	int rowPerPage = 5;
	int beginRow = (currentPage-1)*rowPerPage; // 현재페이지가 변경되면 beginRow도 변경된다. -> 가져오는 데이터 변경된다.
	
	SalesByStoreDao salesByStoreDao = new SalesByStoreDao();
	ArrayList<SalesByStore> list = salesByStoreDao.selectSalesByStoreListByPage(beginRow, rowPerPage);
	
	// 전체 행의수
	int totalCount = salesByStoreDao.selectSalesByStoreListTotalRow();
	
	// 마지막페이지 설정
	int lastPage = 0;
	lastPage = (int)(Math.ceil((double)totalCount / (double)rowPerPage));
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>SalesByStoreList(view)</title>
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
		
	<h1>salesbyStore</h1>
	<table class="table">
		<thead>
			<th>store</th>
			<th>manager</th>
			<th>totalSales</th>
		</thead>
		<tbody>
			<% 
			for(SalesByStore a : list) {	
			%>
				<tr>
					 <td><%=a.getStore()%></td>
					 <td><%=a.getManager()%></td>
					 <td><%=a.getTotalSales()%></td>
				</tr>
			<% 
			}
			%>
		</tbody>
	</table>
	<div class="btn-group">		
	<%
		if(currentPage > 1) { // 현재페이지가 1이면 이전페이지가 존재해서는 안된다.
	%>
			<a class="btn bg-dark text-white" href="<%=request.getContextPath()%>/salesByStoreList(view).jsp?currentPage=<%=currentPage-1%>">이전</a>&nbsp;&nbsp;&nbsp;
	<%	
		}
		if(currentPage < lastPage) { // 마지막페이지가 있다면 
	%>
		<a class="btn bg-dark text-white" href="<%=request.getContextPath()%>/salesByStoreList(view).jsp?currentPage=<%=currentPage+1%>">다음</a>&nbsp;&nbsp;&nbsp;
	<%		
		}
	%>
	</div>
	  </div>
	  <div class="col-sm-4 container">
	  </div>
	</div>
</body>
</html>

