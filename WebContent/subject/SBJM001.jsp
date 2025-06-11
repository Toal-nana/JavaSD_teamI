<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>科目管理</title>
</head>
<body>

  <h2>科目管理</h2>

  <table border="1">
    <tr>
      <th>科目コード</th>
      <th>科目名</th>
    </tr>

    <c:forEach var="subject" items="${subjectList}">
      <tr>
        <td><c:out value="${subject.subjectId}" /></td>
        <td><c:out value="${subject.subjectName}" /></td>
      </tr>
    </c:forEach>
  </table>

  <br>
  	<a href="${pageContext.request.contextPath}/subject/insertForm">新規登録</a><br>
  <br>
  	<a href="${pageContext.request.contextPath}/subject/updatesubjectId=${subject.subjectId}">変更</a>
  	<a href="${pageContext.request.contextPath}/subject/deleteConfirmsubjectId=${subject.subjectId}">削除</a>


</body>
</html>