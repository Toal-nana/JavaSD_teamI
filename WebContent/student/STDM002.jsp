<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
  <%-- タイトルを base.jsp に渡す --%>
  <c:param name="title">学生情報登録</c:param>

  <%-- 本文（body）を base.jsp に渡す --%>
  <c:param name="body">

    <div class="container mt-5">
      <h2 class="text-center mb-4">学生情報登録フォーム</h2>

      <%--formのactionのリンクはサーブレットができてから変えます --%>
      <form action="${pageContext.request.contextPath}/student/insert" method="post" class="p-4 border rounded bg-light shadow-sm">
        <div class="mb-3">
          <label class="form-label">入学年度：</label>
          <input type="text" name="year" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">学生番号：</label>
          <input type="text" name="number" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">氏名：</label>
          <input type="text" name="name" class="form-control" required>
        </div>

        <div class="mb-4">
          <label class="form-label">クラス：</label>
          <input type="text" name="class" class="form-control" required>
        </div>

        <div class="d-flex justify-content-between">
          <input type="submit" value="登録して終了" class="btn btn-primary">
          <a href="${pageContext.request.contextPath}/student/STDM001">戻る</a>
        </div>
      </form>
    </div>

  </c:param>
</c:import>
