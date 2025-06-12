<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/base.jsp">
  <c:param name="title">学生管理一覧</c:param>

  <c:param name="body">
    <h1>変更が完了しました</h1>
    <a href="${pageContext.request.contextPath}/student/STDM001.jsp">学生一覧</a>
  </c:param>
</c:import>
