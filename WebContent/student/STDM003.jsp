<%-- insertSuccess.jsp: 学生情報登録完了画面 --%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<%-- <c:import url="/base.jsp">
  <%-- タイトル
  <c:param name="title">登録完了</c:param>

  <%-- メイン表示内容（body）
  <c:param name="body">

    <h2>登録完了</h2>
    <p>学生情報の登録が完了しました。</p>
    <a href="${pageContext.request.contextPath}/student/index">Topページへ戻る</a>
  </c:param>
</c:import>

 --%>

 <%@ page contentType="text/html; charset=UTF-8" %>

<!DOCTYPE html>
<html lang="ja">
<head>
  <meta charset="UTF-8">
  <title>登録完了</title>
</head>
<body>

  <h1>登録が完了しました</h1>
  <p>学生情報の登録が正常に完了しました。</p>
  <a href="${pageContext.request.contextPath}/student/index">Topページへ戻る</a>

</body>
</html>
