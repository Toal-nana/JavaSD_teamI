<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>科目情報登録（登録完了）</title>
</head>
<body>

  <h2>科目情報登録</h2>

  <!-- 登録完了メッセージ -->
  <p style="color: green;">登録が完了しました</p>

  <table border="1">

    <c:forEach var="subject" items="${subjectList}">
      <tr>
        <td><c:out value="${subject.subjectId}" /></td>
        <td><c:out value="${subject.subjectName}" /></td>
        <td>
          <a href="${pageContext.request.contextPath}/subject/update?subjectId=${subject.subjectId}">変更</a>
          <a href="${pageContext.request.contextPath}/subject/deleteConfirm?subjectId=${subject.subjectId}">削除</a>
        </td>
      </tr>
    </c:forEach>
  </table>

  <br><br>

  <!-- 戻るリンク・一覧リンク -->
  <a href="${pageContext.request.contextPath}/subject/insertForm">戻る</a>
  &nbsp;&nbsp;
  <a href="${pageContext.request.contextPath}/subject/SBJM001">科目一覧</a>

</body>

<footer>
	<p>@ 2023 TIC</p>
	<p>大原学園</p>
</footer>
</html>
