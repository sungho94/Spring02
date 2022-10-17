<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** MemberDetail Spring_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css" >
</head>
<body>
<h2>** MemberDetail Spring_MVC2 **</h2>
=> Request Member_id : ${param.id}<br> 
<hr>
<c:if test="${not empty apple}"> 
	<table>
		<tr height="40"><td bgcolor="lavender">I D</td><td>${apple.id}</td></tr>
		<tr height="40"><td bgcolor="lavender">Password</td><td>${apple.password}</td></tr>
		<tr height="40"><td bgcolor="lavender">Name</td><td>${apple.name}</td></tr>
		<tr height="40"><td bgcolor="lavender">Info</td><td>${apple.info}</td></tr>
		<tr height="40"><td bgcolor="lavender">Birthday</td><td>${apple.birthday}</td></tr>
		<tr height="40"><td bgcolor="lavender">Jno</td><td>${apple.jno}</td></tr>
		<tr height="40"><td bgcolor="lavender">Age</td><td>${apple.age}</td></tr>
		<tr height="40"><td bgcolor="lavender">Point</td><td>${apple.point}</td></tr>
	</table>
</c:if>
<c:if test="${not empty message}">
<hr>
${message}<br>
</c:if>
<hr>
&nbsp;&nbsp;<a href="mdetail?jCode=U&id=${apple.id}">[내정보수정]</a>
<!-- memberList 에서 요청한 경우는 session 에 보관된 id로는 수정 할 수 없기때문에 parameter로 처리함  -->
&nbsp;&nbsp;<a href="mdelete?id=${apple.id}">[회원탈퇴]</a><br>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>
</body>
</html>