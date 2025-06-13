<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を継承し、共通レイアウトを利用する --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="科目情報登録" />

  <%-- ページ本体のHTMLを "body" パラメータとして渡す --%>
  <c:param name="body">

    <%-- 画面見出し（登録完了ページとスタイルを統一） --%>
    <div class="bg-body-secondary p-3 rounded my-4">
      <h2 class="mb-0">科目情報登録</h2>
    </div>

    <form action="${pageContext.request.contextPath}/subject/update" method="post">

      <%-- 科目コード --%>
      <div class="mb-3">
        <label for="subjectId" class="form-label">科目コード</label>
        <input type="text" class="form-control" id="subjectId" name="subjectId" value="<c:out value='${subject.subjectId}'/>" placeholder="科目コードを入力してください" required>
      </div>

      <%-- 科目名 --%>
      <div class="mb-3">
        <label for="subjectName" class="form-label">科目名</label>
        <input type="text" class="form-control" id="subjectName" name="subjectName" value="<c:out value='${subject.subjectName}'/>" placeholder="科目名を入力してください" required>
      </div>

      <%-- 登録ボタン --%>
      <div class="mt-4">
        <button type="submit" class="btn btn-primary">登録</button>
      </div>

    </form>

    <%-- 戻るリンク --%>
    <div class="mt-2 mb-5">
      <a href="${pageContext.request.contextPath}/subject/list">戻る</a>
    </div>

  </c:param>
</c:import>