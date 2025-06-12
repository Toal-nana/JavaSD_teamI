<%-- insertSuccess.jsp: 学生情報登録完了画面 --%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:import url="/base.jsp">

  <c:param name="title">登録完了</c:param>

 <%-- メイン表示内容（body） --%>
  <c:param name="body">

    <h2>登録完了</h2>
    <p>学生情報の登録が完了しました。</p>

 <li><a href="${pageContext.request.contextPath}/student/STDM001">戻る</a></li>

 <li><a href="${pageContext.request.contextPath}/student/STDM002">学生入力</a></li>
  </c:param>
</c:import>
