<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="科目情報登録" />

  <c:param name="body">

    <%-- 画面見出し --%>
    <h2 class="px-3 py-2 me-3 mb-3 bg-light">科目情報登録</h2>

    <form action="createexecute" method="post">

      <%-- 科目コード --%>
      <div class="mb-3">
        <label for="subjectId" class="form-label">科目コード</label>
        <input type="text" class="form-control" id="subjectId" name="cd" value="<c:out value='${subject.cd}'/>" placeholder="科目コードを入力してください" required>

        <%-- 科目コード関連のエラーをここに表示 --%>
        <c:if test="${cd_error != null}">
          <div class="form-text text-warning">${cd_error}</div>
        </c:if>
      </div>

      <%-- 以前この位置にあった汎用エラー表示を削除 --%>
      <%--
      <c:if test="${error != null}">
        <p class="text-warning">${error}</p>
      </c:if>
      --%>

      <%-- 科目名 --%>
      <div class="mb-3">
        <label for="subjectName" class="form-label">科目名</label>
        <input type="text" class="form-control" id="subjectName" name="name" value="<c:out value='${subject.name}'/>" placeholder="科目名を入力してください" required>

      	<c:if test="${name_error != null}">
          <div class="form-text text-warning">${name_error}</div>
      	</c:if>
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