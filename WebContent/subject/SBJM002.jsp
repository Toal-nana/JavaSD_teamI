<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<%-- base.jsp を継承し、共通レイアウトを利用する --%>
<c:import url="/base.jsp">

  <%-- ページタイトルを指定（base.jsp 内の ${param.title} で使われる） --%>
  <c:param name="title" value="科目情報登録" />

  <%-- ページ本体のHTMLを "body" パラメータとして渡す（base.jsp 側で ${param.body} で出力） --%>
  <c:param name="body">

    <h2>科目情報登録</h2> <%-- 画面見出し --%>

    <%-- 科目情報を送信するフォーム。送信先は /subject/update にPOSTで送る --%>
    <form action="${pageContext.request.contextPath}/subject/update" method="post">

      <table border="1">
        <tr>
          <th>科目コード</th>
          <td>
            <%-- 科目コードの入力欄。事前にセットされた subject オブジェクトの値を表示 --%>
            <input type="text" name="subjectId" value="${subject.subjectId}" required />
          </td>
        </tr>
        <tr>
          <th>科目名</th>
          <td>
            <%-- 科目名の入力欄 --%>
            <input type="text" name="subjectName" value="${subject.subjectName}" required />
          </td>
        </tr>
      </table>

      <br>

      <%-- 登録ボタン（フォームを送信） --%>
      <input type="submit" value="登録">
    </form>

    <br>

    <%-- 一覧ページへ戻るリンク --%>
    <a href="${pageContext.request.contextPath}/subject/list">戻る</a>

  </c:param>
</c:import>
