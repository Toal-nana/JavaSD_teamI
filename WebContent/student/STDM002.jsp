<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html>
<head>

<%--<c:import url="/base.jsp"> --%>
  <%-- タイトルを base.jsp に渡す
  <c:param name="title">学生情報登録</c:param> --%>
</head>
  <%-- 本文（body）を base.jsp に渡す
  <c:param name="body"> --%>

<body>
    <form action="${pageContext.request.contextPath}/student/insert" method="post">
      <div>入学年度：<input type="text" name="year" required></div>
      <div>学生番号：<input type="text" name="number" required></div>
      <div>氏名　　：<input type="text" name="name" required></div>
      <div>クラス　：<input type="text" name="class" required></div>
      <div><input type="submit" value="登録して終了"></div>
    </form>

    <a href="${pageContext.request.contextPath}/student/index">戻る</a>
<%--  </c:param> --%>
<%--</c:import>      --%>
</body>
</html>