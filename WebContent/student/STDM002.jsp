<%-- /student/STDM002.jsp --%>
<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
  <%-- タイトルを base.jsp に渡す --%>
  <c:param name="title">学生情報登録</c:param>

  <%-- 本文（body）を base.jsp に渡す --%>
  <c:param name="body">
    <div class="container mt-2">
      <h4 class="bg-light border shadow-sm p-3">学生情報登録フォーム</h4>


      <form action="${pageContext.request.contextPath}/student/create_execute" method="post">
        <div class="mb-3">
          <label class="form-label">入学年度</label>
          <%-- ★ value属性で入力値を再表示。0の場合は表示しない --%>
          <input type="text" name="year" class="form-control" value="<c:out value='${student.entYear > 0 ? student.entYear : ""}'/>" required>
        </div>

        <div class="mb-3">
          <label class="form-label">学生番号</label>
          <%-- ★ value属性で入力値を再表示 --%>
          <input type="text" name="number" class="form-control" value="<c:out value='${student.no}'/>" required>
          <%-- ★ 学生番号のエラーメッセージのみ表示 --%>
        </div>

        <c:if test="${error != null}">
        	<p class="text-warning">${error}</p>
      	</c:if>

        <div class="mb-3">
          <label class="form-label">氏名</label>
          <%-- ★ value属性で入力値を再表示 --%>
          <input type="text" name="name" class="form-control" value="<c:out value='${student.name}'/>" required>
        </div>

        <div class="mb-4">
          <label class="form-label">クラス</label>
          <select name="class" class="form-select" required>
            <option value="">選択してください</option>
            <c:forEach var="cls" items="${classList}">
              <%-- ★ c:ifを使って選択された値を保持 (selected属性を付与) --%>
              <option value="${cls}" <c:if test="${student.classNum == cls}">selected</c:if>>${cls}</option>
            </c:forEach>
          </select>
        </div>

        <div class="mt-4">
          <button type="submit" class="btn btn-secondary">登録して終了</button>
          <div class="mt-2">
            <a href="${pageContext.request.contextPath}/student/list">戻る</a>
          </div>
        </div>
      </form>
    </div>
  </c:param>
</c:import>