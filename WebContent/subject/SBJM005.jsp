<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を共通レイアウトとして読み込む --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="変更完了" />

  <%-- 表示する本文の内容 --%>
  <c:param name="body">
  <div class="mb-5 pb-5">
    <%-- 画面見出し（他のページとスタイルを統一） --%>
    <div class="bg-body-secondary p-3  mb-4 mt-3">
      <h2 class="mb-0">科目情報変更</h2>
    </div>

    <%-- 変更完了メッセージ（BootstrapのAlertを使用） --%>
    <div class="alert alert-success py-2 text-center mb-5 rounded-0" role="alert" style="background-color: #8ab79a; border-color: #7fa98b;">
      変更が完了しました
    </div>

    <%-- リンクエリア --%>
    <div class="mt-5 pt-5 mb-5">
      <%-- 科目一覧ページへのリンク --%>
      <a href="${pageContext.request.contextPath}/subject/list">科目一覧</a>
    </div>
	</div>
  </c:param>
</c:import>