<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を共通レイアウトとして読み込む --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを「削除完了」に変更 --%>
  <c:param name="title" value="削除完了" />

  <%-- 表示する本文の内容 --%>
  <c:param name="body">

    <%-- 画面見出しを「科目情報削除」に変更 --%>
    <div class="bg-body-secondary p-3 rounded my-4">
      <h2 class="mb-0">科目情報削除</h2>
    </div>

    <%-- 完了メッセージを「削除が完了しました」に変更 --%>
    <div class="alert alert-success py-2 text-center" role="alert">
      削除が完了しました
    </div>

    <%-- リンクエリア --%>
    <div class="d-flex gap-4 mt-4 mb-5">
      <%-- 削除確認画面に戻るのは不自然なため、「戻る」リンクは削除しました --%>
      <%-- 科目一覧ページへのリンクのみ表示 --%>
      <a href="${pageContext.request.contextPath}/subject/list">科目一覧</a>
    </div>

  </c:param>
</c:import>