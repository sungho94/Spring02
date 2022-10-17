<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>** Jo Update Spring_MVC2 **</title>
<link rel="stylesheet" type="text/css" href="/resources/myLib/myStyle.css">
</head>
<body>
<h2>** Jo Update Spring_MVC2 **</h2>

<form action="jupdate" method="post">
    <table>
        <tr height="40"><td bgcolor="lightpuple">Jno</td>
        <td><input type="text" name="jno" size="20" value="${apple.jno}" readonly></td></tr>
        <tr height="40"><td bgcolor="lightpuple">Chief</td>
        <td><input type="text" name="chief" size="20" value="${apple.chief}"></td></tr>
        <tr height="40"><td bgcolor="lightpuple">JName</td>
        <td><input type="text" name="jname" size="20" value="${apple.jname}"></td></tr>
        <tr height="40"><td bgcolor="lightpuple">Note</td>
        <td><textarea rows="5" cols="50" name="note">${apple.note}</textarea></td></tr>
        
        <tr><td></td>
            <td><input type="submit" value="글수정">&nbsp;&nbsp;
                 <input type="reset" value="취소">
            </td>
        </tr>
    </table>
</form>
<c:if test="${not empty message}">
<hr>
${message}<br>
</c:if>
<hr>
<c:if test="${not empty loginID}">
	&nbsp;&nbsp;<a href="jdelete?jno=${apple.jno}">[조삭제]</a>
</c:if>
&nbsp;&nbsp;<a href="jlist">joList</a>
&nbsp;&nbsp;<a href="javascript:history.go(-1)">이전으로</a>
&nbsp;&nbsp;<a href="home">[Home]</a>

</body>
</html>