<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** JoList Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css" href="/resources/myLib/myStyle.css">
</head>
<body>
<h2>** JoList Spring_MVC2 **</h2>
<br>
<c:if test="${not empty message}">
    ${message}<br>
</c:if>
<hr>
<table width=100%>
    <tr bgcolor="lightblue" height="30">
        <th>Jno</th><th>Chief</th><th>C_Name</th><th>JoName</th><th>Note</th>
    </tr>
        <c:if test="${not empty banana}">
        <c:forEach var="jo" items="${banana}">
            <tr height="30">

            <td>
                <c:if test="${loginID=='admin'}">
                    <a href="jdetail?jno=${jo.jno}">${jo.jno}</a>
                </c:if>
                <c:if test="${loginID!='admin'}">
                    ${jo.jno}
                </c:if>
            </td>
            
            <td><c:if test="${loginID=='admin'}">
					<a href="mdetail?id=${jo.chief}">${jo.chief}</a>
				</c:if> 
				<c:if test="${loginID!='admin'}">
				    ${jo.chief}
				</c:if>
			</td> 
			
            <td>${jo.name}</td>
            <td>${jo.jname}</td>
            <td>${jo.note}</td>
            </tr>
        </c:forEach>
	    </c:if>
</table>
<hr>
<c:if test="${not empty loginID}"> 
&nbsp;&nbsp;<a href="jinsertf">조등록</a>
</c:if>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>