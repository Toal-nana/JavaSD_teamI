<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定 --%>
  <c:param name="title">科目管理</c:param>

  <c:param name="body">
      <%-- ヘッダー部分 --%>

      <h2 class="px-3 py-2 me-3 mb-3 bg-light">科目管理</h2>

      <%-- 新規登録 --%>
      <div class="text-end mb-3">
        <a href="${pageContext.request.contextPath}/subject/create">新規登録</a>
      </div>

      <%-- 科目一覧テーブル --%>
      <table class="table table-hover">
        <thead>
          <tr>
            <th>科目コード</th>
            <th>科目名</th>
            <%-- 変更・削除リンク用の見出し --%>
            <th class="text-end" style="width: 200px;"></th>
          </tr>
        </thead>
        <tbody>
          <c:forEach var="subject" items="${subjectList}">
            <tr>
              <td><c:out value="${subject.cd}" /></td>
              <td><c:out value="${subject.name}" /></td>
              <%-- 変更・削除リンクのセル --%>
              <td >
                <a href="${pageContext.request.contextPath}/subject/update?cd=${subject.cd}&scd=${subject.school.cd}">変更</a>
                <a href="${pageContext.request.contextPath}/subject/delete?cd=${subject.cd}&scd=${subject.school.cd}" class="mx-5 ps-3">削除</a>
              </td>
            </tr>
          </c:forEach>
        </tbody>
      </table>
  </c:param>
</c:import>