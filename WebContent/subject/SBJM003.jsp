<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を継承し、共通レイアウトを利用する --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="登録完了" />

  <%-- ページ本体のHTMLを "body" パラメータとして渡す --%>
  <c:param name="body">

    <%-- 画面見出し --%>
    <div class="bg-body-secondary p-3 rounded my-4">
      <h2 class="mb-0">科目情報登録</h2>
    </div>

    <%-- 完了メッセージ（アラート） --%>
    <div class="alert alert-success py-2 text-center" role="alert">
      登録が完了しました
    </div>

    <%-- リンクエリア --%>
    <div class="d-flex gap-4 mt-4 mb-5">
      <%-- 前のページ（登録フォーム）に戻る --%>
      <a href="${pageContext.request.contextPath}/subject/list">戻る</a>
      <%-- 科目一覧ページへのリンク --%>
      <a href="${pageContext.request.contextPath}/subject/list">科目一覧</a>
    </div>

  </c:param>
</c:import>