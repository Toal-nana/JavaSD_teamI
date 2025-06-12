<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>科目情報変更</title>
</head>
<body>

  <h2>科目情報変更</h2>

  <form action="${pageContext.request.contextPath}/subject/update" method="post">
    <table border="1">
      <tr>
        <th>科目コード</th>
        <td>
          <input type="text" name="subjectId" value="${subject.subjectId}" required />
        </td>
      </tr>
      <tr>
        <th>科目名</th>
        <td>
          <input type="text" name="subjectName" value="${subject.subjectName}" required />
        </td>
      </tr>
    </table>
    <br>
    <input type="submit" value="変更">
  </form>

  <br><a href="${pageContext.request.contextPath}/subject/list">戻る</a>

</body>

<footer>
	<p>@ 2023 TIC</p>
	<p>大原学園</p>
</footer>
</html>
