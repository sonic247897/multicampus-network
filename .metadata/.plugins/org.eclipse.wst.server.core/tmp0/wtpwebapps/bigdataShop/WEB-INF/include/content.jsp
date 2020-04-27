<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<link rel="stylesheet"
	href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.1.1/jquery.min.js"></script>
<script
	src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
<style>
.carousel-inner>.item>img, .carousel-inner>.item>a>img {
	width: 70%;
	margin: auto;
}
</style>
</head>
<body>
	<br>
	<div class="row">
		<div id="myCarousel" class="carousel slide" data-ride="carousel">
			<!-- Indicators -->
			<ol class="carousel-indicators">
				<li data-target="#myCarousel" data-slide-to="0" class="active"></li>
				<li data-target="#myCarousel" data-slide-to="1"></li>
				<li data-target="#myCarousel" data-slide-to="2"></li>
				<li data-target="#myCarousel" data-slide-to="3"></li>
			</ol>
	
			<!-- Wrapper for slides -->
			<div class="carousel-inner" role="listbox">
				<!-- mystatus =index  -->
				<!-- c:는 jstl을 의미 -->
				<!-- 첫번 째 div는 이 이미지를 보여주고, 나머지는 각자 다른 이미지 보여줘라 -->
				<c:forEach varStatus="mystatus" var="hititem" 
							items="${hitProduct}">
						<!-- when: if문  -->
						<!-- otherwise: else문 -->
					<c:choose> 
						<c:when test="${mystatus.index==0}">
							<div class="item active" style="height: 250px">
								<a href="/bigdataShop/product/read.do?prd_no=${hititem.prd_no}"><img src="/bigdataShop/images/product/${hititem.img_gen_file_nm }" alt="Chania" ></a>
							</div>
						</c:when>
						<c:otherwise>
							<div class="item" style="height: 250px">
								<img src="/bigdataShop/images/product/${hititem.img_gen_file_nm }" alt="Chania" width="460" height="345">
							</div>
						</c:otherwise>
					</c:choose>
				</c:forEach>
				<!-- <div class="item" style="height: 250px">
					<img src="/bigdataShop/resources/images/product/acc_image5.jpg" alt="Chania" width="460" height="345">
				</div>
	
				<div class="item" style="height: 250px">
					<img src="/bigdataShop/resources/images/product/bottom_image3.jpg" alt="Flower" width="460" height="345">
				</div>
	
				<div class="item" style="height: 250px">
					<img src="/bigdataShop/resources/images/product/outer_image5.gif" alt="Flower" width="460" height="345">
				</div> -->
			</div>
	
			<!-- Left and right controls -->
			<a class="left carousel-control" href="#myCarousel" role="button"
				data-slide="prev"> <span class="glyphicon glyphicon-chevron-left"
				aria-hidden="true"></span> <span class="sr-only">Previous</span>
			</a> <a class="right carousel-control" href="#myCarousel" role="button"
				data-slide="next"> <span
				class="glyphicon glyphicon-chevron-right" aria-hidden="true"></span>
				<span class="sr-only">Next</span>
			</a>
		</div>
	</div>
	<br />
	<br />
	<div class="row">
		<!-- EL: 스크립트릿을 안 쓰기 위한 웹의 기술 -->
		<!-- request.setAttribute(newProduct) 등 공유 데이터들을 편하게 사용할 수 있다. -->
		<!-- forEach =향상된 for문: items에 collection이 들어감 -->
		<c:forEach var="newitem" items="${newProduct}">
			<div class="col-sm-4">
					<div class="panel panel-primary">
					<!-- EL: getter메소드에서 get 빼고 첫글자 소문자로 한 것  -->
						<div class="panel-heading">${newitem.prd_nm}</div>
						<div class="panel-body">
							<a href="/bigdataShop/product/read.do?prd_no=${newitem.prd_no}"><img
								src="/bigdataShop/images/product/${newitem.img_gen_file_nm}"
								class="img-responsive" style="width: 70%; height: 70%" alt="Image"></a>
						</div>
						<div class="panel-footer">판매금액(db):${newitem.sell_prc_unit}</div>
					</div>
			</div>
			
		</c:forEach>
		
	</div>
</body>
</html>