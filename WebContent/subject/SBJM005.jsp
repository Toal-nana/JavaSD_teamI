<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を共通レイアウトとして読み込む --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="科目変更（変更完了）" />

  <%-- 表示する本文の内容 --%>
  <c:param name="body">

    <h2>科目情報変更</h2>

    <%-- 変更完了メッセージ表示 --%>
    <p style="color: green;">変更が完了しました</p>

    <%-- 科目一覧テーブル --%>
    <table border="1"></table>
    <br><br>

    <%-- 科目一覧に戻るリンク --%>
    <a href="${pageContext.request.contextPath}/subject/SBJM001">科目一覧</a>
  </c:param>
</c:import>