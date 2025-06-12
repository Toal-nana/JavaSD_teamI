<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<%--学生情報を変更するために入力するフォーム --%>
<c:import url="/base.jsp">

  <c:param name="title">学生情報編集</c:param>

  <c:param name="body">
    <h2>学生情報の変更</h2>

<%--form actionのリンクは後で変えます
	valueのとこも違うので変えます --%>
    <form action="updatecontroller" method="post">
      <input type="hidden" name="id" value="${student.student_id}">

      <div>入学年度：
        <input type="text" name="year" value="${student.year}" required>
      </div>

      <div>学生番号：
        <input type="text" name="number" value="${student.number}" required>
      </div>

      <div>氏名：
        <input type="text" name="name" value="${student.name}" required>
      </div>

      <div>クラス：
        <input type="text" name="class" value="${student.class}" required>
      </div>

      <input type="submit" value="変更を保存">
    </form>

    <a href="link">戻る</a>
  </c:param>
</c:import>
