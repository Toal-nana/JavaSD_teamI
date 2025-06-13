<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を共通レイアウトとしてインポート --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを渡す（base.jsp 側で ${param.title} に使用） --%>
  <c:param name="title" value="科目情報変更" />

  <%-- ここから本文コンテンツを param.body として渡す --%>
  <c:param name="body">

    <h2>科目情報変更</h2>

    <%-- 科目情報変更フォーム --%>
    <form action="${pageContext.request.contextPath}/subject/update" method="post">
      <table border="1">
        <tr>
          <th>科目コード</th>
          <td>
            <%-- 科目IDを入力（既存データを表示） --%>
            <input type="text" name="subjectId" value="${subject.subjectId}" required />
          </td>
        </tr>
        <tr>
          <th>科目名</th>
          <td>
            <%-- 科目名を入力（既存データを表示） --%>
            <input type="text" name="subjectName" value="${subject.subjectName}" required />
          </td>
        </tr>
      </table>

      <br>

      <%-- 変更ボタン（フォーム送信） --%>
      <input type="submit" value="変更">
    </form>

    <br>

    <%-- 科目一覧ページへ戻るリンク --%>
    <a href="${pageContext.request.contextPath}/subject/list">戻る</a>

  </c:param>
</c:import>
