<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>科目情報削除</title>
</head>
<body>

  <h2>科目情報削除</h2>

  <p>
    「<strong><c:out value="${subject.subjectName}"/></strong>」を削除してもよろしいですか？
  </p>

  <form action="${pageContext.request.contextPath}/subject/delete" method="post">
    <input type="hidden" name="subjectId" value="${subject.subjectId}" />
    <input type="submit" value="削除">
  </form>

  <br>
  <a href="${pageContext.request.contextPath}/subject/SBJM001">一覧に戻る</a>

</body>

<footer>
	<p>@ 2023 TIC</p>
	<p>大原学園</p>
</footer>
</html>
