<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jspを使用して共通レイアウトを適用 --%>
<c:import url="/base.jsp">

  <%-- ページタイトルの指定 --%>
  <c:param name="title" value="科目情報削除" />

  <%-- ページ本文の内容 --%>
  <c:param name="body">

    <%-- 画面見出し（他のページとスタイルを統一） --%>
    <div class="bg-body-secondary p-3 rounded my-4">
      <h2 class="mb-0">科目情報削除</h2>
    </div>

    <%-- 削除確認メッセージ --%>
    <p class="my-4">
      <%-- 画像に合わせて科目コードも表示 --%>
      「<c:out value="${subject.subjectName}"/><c:out value="${subject.subjectId}"/>」を削除してもよろしいですか？
    </p>

    <%-- 削除を確定するフォーム --%>
    <form action="${pageContext.request.contextPath}/subject/delete" method="post">
      <%-- 削除対象の科目IDを非表示で送信 --%>
      <input type="hidden" name="subjectId" value="<c:out value='${subject.subjectId}'/>" />
      <button type="submit" class="btn btn-danger">削除</button>
    </form>

    <%-- 戻るリンク --%>
    <div class="mt-2 mb-5">
      <a href="${pageContext.request.contextPath}/subject/list">戻る</a>
    </div>

  </c:param>
</c:import>