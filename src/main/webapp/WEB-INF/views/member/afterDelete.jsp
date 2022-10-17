<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>    
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** After Delete Web_MVC2 **</title>
</head>
<body>
<h2>** After Delete Web_MVC2 **</h2>
<br>
<c:if test="${not empty message}">
=> ${message}<br>
</c:if>
<hr>
&nbsp;&nbsp;<a href="/Web02/index.jsp">[Home]</a>
</body>
</html>