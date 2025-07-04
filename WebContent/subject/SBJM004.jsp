<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%-- base.jsp を共通レイアウトとしてインポート --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを渡す --%>
  <c:param name="title" value="科目情報変更" />

  <%-- ここから本文コンテンツを param.body として渡す --%>
  <c:param name="body">

    <%-- 画面見出し --%>

    <h2 class="px-3 py-2 me-3 mb-3 bg-light">科目情報変更</h2>


    <%-- 科目情報変更フォーム --%>
    <form action="updateexecute" method="post">

      <%-- 科目コード（変更不可） --%>
      <div class="mb-3">
        <label for="subjectId" class="form-label">科目コード</label>
        <%-- readonly 属性を追加して、ユーザーによる編集を防ぐ --%>
        <input type="text" class="form-control-plaintext ms-3" id="subjectId" name="cd" value="<c:out value='${subject.cd != null ? subject.cd : subject_cd}'/>" readonly>
      </div>

      <c:if test="${error != null}">
        <p class="text-warning">${error}</p>
      </c:if>

      <%-- 科目名 --%>
      <div class="mb-3">
        <label for="subjectName" class="form-label">科目名</label>
        <input type="text" class="form-control" id="subjectName" name="name" value="<c:out value='${subject.name}'/>" placeholder="科目名を入力してください" required>
      </div>

      <%-- 変更ボタン --%>
      <div class="mt-4">
        <button type="submit" class="btn btn-primary">変更</button>
      </div>
    </form>

    <%-- 科目一覧ページへ戻るリンク --%>
    <div class="mt-2 mb-5">
      <a href="${pageContext.request.contextPath}/subject/list">戻る</a>
    </div>

  </c:param>
</c:import>