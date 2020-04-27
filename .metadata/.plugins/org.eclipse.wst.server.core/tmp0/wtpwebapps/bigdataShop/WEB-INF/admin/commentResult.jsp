<%@page import="kr.multi.bigdataShop.product.comment.CommentResultDTO"%>
<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet" type="text/css" href="/bigdataShop/common/css/jqcloud.css" />
    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.4.4/jquery.min.js"></script>
    <script type="text/javascript" src="/bigdataShop/common/js/jqcloud-1.0.4.js"></script>
    <script type="text/javascript">
      /*!
       * Create an array of word objects, each representing a word in the cloud
       */
      var word_array = [
    	   <%-- <% List<CommentResultDTO> list = (List<CommentResultDTO>)request.getAttribute("result");
    	   	int size = list.size();
    	  	for(int i=0; i<size-1; ++i){
    	  		CommentResultDTO row = list.get(i); %>
    	  		{text: "<%=row.getWord()%>", weight: "<%=row.getCount()%>"},
    	  	<%} %>
    	  	<% CommentResultDTO row = list.get(size-1);%>
    	  	{text: "<%=row.getWord()%>", weight: "<%=row.getCount()%>"}  --%>
    	  	
    	  	<c:forEach var="item" items="${result}">
				{text: "${item.word}", weight: "${item.count}"},
			</c:forEach>
    	];

      $(function() {
        // When DOM is ready, select the container element and call the jQCloud method, passing the array of words as the first argument.
        $("#example").jQCloud(word_array);
      });
    </script>
  </head>
<body>
<div class="row">
	<div class="col-md-5">
		<h1>상품댓글분석</h1>
		<table class="table">
			<thead> 
				<tr>
					<th>키워드</th>
					<th>반복횟수</th>
				</tr>
			</thead>
			<tbody>
				<!-- var이름으로 접근한다 -->
				<c:forEach var="item" items="${result}">
					<tr>
						<td>${item.word}</td>
						<td>${item.count}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
  	<div class="col-md-6" id="example" style="width: 550px; height: 350px;">
  		<!-- word cloud -->
  	</div>
</div>
</body>
</html>