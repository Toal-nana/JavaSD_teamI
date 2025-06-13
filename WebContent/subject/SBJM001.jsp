<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を読み込み、タイトルと本文をパラメータとして渡す --%>
<c:import url="/base.jsp">
  <%-- ページタイトルを指定 --%>
  <c:param name="title" value="科目管理" />

  <%-- body に表示するコンテンツを渡す --%>
  <c:param name="body">
      <!-- タイトル -->
      <div class="bg-light p-3 rounded">
        <h2 class="mb-0">科目管理</h2>
      </div>

      <!-- 新規登録リンク -->
      <div class="text-end my-3">
        <a href="${pageContext.request.contextPath}/subject/insertForm" class="btn btn-link">新規登録</a>
      </div>

      <!-- 科目一覧テーブル -->
      <table class="table table-bordered">
        <thead class="table-light">
          <tr>
            <th>科目コード</th>
            <th>科目名</th>
		<%-- 変更・削除ボタン用の見出し --%>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="subject" items="${subjectList}">
            <tr>
              <td><c:out value="${subject.subjectId}" /></td>
              <td><c:out value="${subject.subjectName}" /></td>
              <td>
                <a href="${pageContext.request.contextPath}/subject/update?subjectId=${subject.subjectId}" class="btn btn-sm btn-warning">変更</a>
                <a href="${pageContext.request.contextPath}/subject/deleteConfirm?subjectId=${subject.subjectId}" class="btn btn-sm btn-danger">削除</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
  </c:param>
</c:import>