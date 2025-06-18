<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を読み込み、タイトルと本文をパラメータとして渡す --%>
<c:import url="/base.jsp">
  <%-- ページタイトルを指定 --%>
  <c:param name="title">得点管理システム</c:param>

  <%-- body に表示するコンテンツを渡す --%>
  <c:param name="body">
      <!-- タイトル -->
      <div class="bg-body-secondary p-3 my-4">
        <h2 class="mb-0">科目管理</h2>
      </div>

      <!-- 新規登録リンク -->
      <div class="text-end mb-3">
        <a href="${pageContext.request.contextPath}/subject/create" class="btn btn-link">新規登録</a>
      </div>

      <!-- 科目一覧テーブル -->
      <table class="table table-bordered">
        <thead class="table-light">
          <tr>
            <th>科目コード</th>
            <th>科目名</th>
            <th></th> <%-- 変更・削除ボタン用の見出し --%>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="subject" items="${subjectList}">
            <tr>
              <td><c:out value="${subject.cd}" /></td>
              <td><c:out value="${subject.name}" /></td>
              <td>
                <a href="${pageContext.request.contextPath}/subject/update?cd=${subject.cd}&scd=${subject.school.cd}">変更</a>
                <a href="${pageContext.request.contextPath}/subject/delete?cd=${subject.cd}&scd=${subject.school.cd}">削除</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
  </c:param>
</c:import>