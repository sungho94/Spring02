<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
	<title>Home</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h1>Hello Spring !!!</h1>
<P>  The time on the server is ${serverTime}. </P>
<hr>
<c:if test="${not empty loginID}">
=> ${loginName} 님 안녕하세요 !!! <br>
</c:if>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
<img alt="main" src="resources/images/tulips.png" width="300" height="200">
<hr>
<!-- Login 전 --> 
<c:if test="${empty loginID}">
	&nbsp;&nbsp;<a href="loginf">LoginF</a>
	&nbsp;&nbsp;<a href="joinf">JoinF</a>
</c:if>
<!-- Login 후  -->
<c:if test="${not empty loginID}">
	&nbsp;&nbsp;<a href="logout">Logout</a>
	&nbsp;&nbsp;<a href="mdetail">내정보보기</a>
	&nbsp;&nbsp;<a href="mdetail?jCode=U">내정보수정</a>
	&nbsp;&nbsp;<a href="mdelete">회원탈퇴</a>
</c:if>
<br>
&nbsp;&nbsp;<a href="mlist">memberList</a>
&nbsp;&nbsp;<a href="blist">boardList</a><br>
&nbsp;&nbsp;<a href="bcrilist">BCriList</a>
&nbsp;&nbsp;<a href="mcrilist">MCriList</a><br>
&nbsp;&nbsp;<a href="jlist">joList</a>


</body>
</html>
