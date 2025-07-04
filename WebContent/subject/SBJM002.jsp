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
      </div>

      <c:if test="${error != null}">
        <p class="text-warning">${error}</p>
      </c:if>

      <%-- 科目名 --%>
      <div class="mb-3">
        <label for="subjectName" class="form-label">科目名</label>
        <input type="text" class="form-control" id="subjectName" name="name" value="<c:out value='${subject.name}'/>" placeholder="科目名を入力してください" required>
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