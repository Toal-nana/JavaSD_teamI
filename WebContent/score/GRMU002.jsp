<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">成績登録完了</c:param>

	<c:param name="body">

		<%-- 画面タイトル --%>
		<h2 class="px-3 py-2 mx-3 bg-light">成績登録完了</h2>

		<%-- 登録完了メッセージ --%>
		<p class="alert text-center alert-success shadow-sm  py-2"
		   role="alert" style="background-color: #8ab79a;">
			<label class="mb-0">変更が完了しました。</label>
		</p>

		<%-- リンク --%>
		<div class="mt-4">
			<%-- 成績管理一覧画面へ遷移するリンク --%>
			<a href="${pageContext.request.contextPath}/score/test">戻る</a>

			<%-- 成績参照検索画面へ遷移するリンク --%>
			<a class="mx-5" href="${pageContext.request.contextPath}/score/testlist">成績参照</a>
		</div>
	</c:param>

</c:import>