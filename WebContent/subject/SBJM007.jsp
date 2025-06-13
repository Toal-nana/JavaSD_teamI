<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- 共通レイアウトを適用するため base.jsp をインポート --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="科目情報削除（削除完了）" />

  <%-- ページ本文の指定 --%>
  <c:param name="body">

    <h2>科目情報削除</h2>

    <%-- 削除完了メッセージを表示 --%>
    <p style="color: green;">削除が完了しました</p>

    <br><br>

    <%-- 科目一覧への戻りリンク --%>
    <a href="${pageContext.request.contextPath}/subject/SBJM001">科目一覧</a>

  </c:param>
</c:import>
