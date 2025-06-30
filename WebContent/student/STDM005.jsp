<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/base.jsp">
	<c:param name="title">学生管理一覧</c:param>
	<c:param name="body">
		<div class="mx-auto">
		<%-- ページの見出しを表示 --%>
			   <h2 class="px-3 py-2 me-3 bg-light">学生情報変更</h2>


			<div class="alert text-center alert-success shadow-sm  py-2"
				role="alert" style="background-color: #8ab79a;">
				<label class="mb-0">変更が完了しました。</label>

			</div>
		</div>

		    <%-- 学生情報の一覧画面へ遷移するためのリンク  --%>
		<a href="${pageContext.request.contextPath}/student/list">学生一覧</a>
	</c:param>
</c:import>
