<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jspを使用して共通レイアウトを適用 --%>
<c:import url="/base.jsp">

  <%-- ページタイトルの指定 --%>
  <c:param name="title" value="科目情報削除" />

  <%-- ページ本文の内容 --%>
  <c:param name="body">

    <h2>科目情報削除</h2>

    <%-- 削除確認メッセージ：対象の科目名を表示 --%>
    <p>
      「<strong><c:out value="${subject.subjectName}"/></strong>」を削除してもよろしいですか？
    </p>

    <%-- 削除を確定するフォーム --%>
    <form action="${pageContext.request.contextPath}/subject/delete" method="post">
      <%-- 削除対象の科目IDを非表示で送信 --%>
      <input type="hidden" name="subjectId" value="${subject.subjectId}" />
      <input type="submit" value="削除">
    </form>

    <br>

    <%-- 一覧に戻るリンク --%>
    <a href="${pageContext.request.contextPath}/subject/SBJM001">一覧に戻る</a>

  </c:param>
</c:import>
