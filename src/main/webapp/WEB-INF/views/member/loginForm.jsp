<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** LoginForm Web_MVC2 **</title>
</head>
<body>
<h2>** LoginForm Web_MVC2 **</h2>
<br>
<form action="login" method="post">
<table>
	<tr><td bgcolor="PaleTurquoise">I D</td>
		<td><input type="text" name="id" value="banana"></td></tr>
	<tr><td bgcolor="PaleTurquoise ">Password</td>
		<td><input type="password" name="password" value="12345!"></td></tr>
	<tr><td></td>
		<td><input type="submit" value="Login">&nbsp;&nbsp;
			<input type="reset" value="Reset"></td>
	</tr> 
</table>
</form><br>
<c:if test="${not empty message}">
<hr>
${message}<br>
</c:if>
<hr>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>
</body>
</html>