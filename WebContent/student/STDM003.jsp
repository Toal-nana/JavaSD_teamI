<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
 	<c:param name="title">学生管理一覧</c:param>
	<c:param name="body">
		<div class="mx-auto">
		<%-- ページの見出しを表示 --%>
			<h2 class="bg-light border  shadow-sm p-3">学生情報登録</h2>


			<div class="alert text-center alert-success shadow-sm  py-2"
				role="alert" style="background-color: #8ab79a;">
				<label class="mb-0">登録が完了しました。</label>

			</div>
		</div>

      <div class="d-flex my-5 py-5">
		<div class="text-center ms-1 me-5">
		<%-- 学生の新規登録画面へ戻るためのリンク --%>
        <a href="${pageContext.request.contextPath}/student/create">戻る</a>
      </div>

      <div class="text-center">
      <%-- 学生情報の一覧画面へ遷移するためのリンク  --%>
        <a href="${pageContext.request.contextPath}/student/list">学生一覧</a>
      </div>

     </div>

  </c:param>
</c:import>