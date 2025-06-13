<%-- insertSuccess.jsp: 学生情報登録完了画面 --%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>



<c:import url="/base.jsp">

  <c:param name="title">登録完了</c:param>
 <%-- メイン表示内容（body） --%>
  <c:param name="body">
  <div class="mx-auto">
	<h2 class="bg-light border  shadow-sm p-3">学生情報登録</h2>


  <div class="alert text-center alert-success shadow-sm  py-2" role="alert" style="background-color:#8ab79a;">
    <label class="mb-0">登録が完了しました。</label>

    </div>
    </div>

 <li><a href="${pageContext.request.contextPath}/student/STDM001">学生一覧</a></li>

 <li><a href="${pageContext.request.contextPath}/student/STDM002">戻る</a></li>

  </c:param>
</c:import>
