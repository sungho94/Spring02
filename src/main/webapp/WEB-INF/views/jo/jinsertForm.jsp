<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>** Jo Insert Spring_MVC2 **</title>
	<link rel="stylesheet" type="text/css" href="resources/myLib/myStyle.css">
</head>
<body>
<h2>** Jo Insert Spring_MVC2 **</h2>
<form action="jinsert" method="Post">
<table>
	<tr height="40"><td bgcolor="Linen">Jno</td>
		<td><input type="text" name="jno" size="20"></td></tr>
	<tr height="40"><td bgcolor="Linen">Chief</td>
		<td><input type="text" name="chief" placeholder="반드시 입력하세요~~" size="20"></td></tr>
	<tr height="40"><td bgcolor="Linen">JoName</td>
		<td><input type="text" name="jname" placeholder="반드시 입력하세요~~" size="20"></td></tr>
	<tr height="40"><td bgcolor="Linen">Note</td>
		<td><input type="text" name="note" placeholder="반드시 입력하세요~~" size="20"></td></tr>
	<tr><td></td>
		<td><input type="submit" value="등록">&nbsp;&nbsp;
			<input type="reset" value="취소">
		</td></tr>
</table>
</form>

<c:if test="${not empty message}">
<hr>
${message}<br>
</c:if>
<hr>
&nbsp;&nbsp;<a href="jlist">joList</a>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>
</body>
</html>