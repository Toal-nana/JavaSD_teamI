<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">
  <c:param name="title">学生情報編集</c:param>

  <c:param name="body">
    <div class="container mt-5">
      <h2 class="mb-4 text-center">学生情報の変更</h2>

      <form action="updatecontroller" method="post">
        <input type="hidden" name="id" value="${student.student_id}">

        <div class="mb-3">
          <label class="form-label">入学年度</label>
          <input type="text" name="year" value="${student.year}" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">学生番号</label>
          <input type="text" name="number" value="${student.number}" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">氏名</label>
          <input type="text" name="name" value="${student.name}" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">クラス</label>
          <input type="text" name="stuclass" value="${student.stuclass}" class="form-control" required>
        </div>

        <div class="form-check mb-3">
          <input type="checkbox" class="form-check-input" id="enrollCheck" name="enroll" value="true"
            <c:if test="${student.enroll}">checked</c:if>>
          <label class="form-check-label" for="enrollCheck">在学中</label>
        </div>


          <input type="submit" value="変更" class="btn btn-primary">
          <a href="${pageContext.request.contextPath}/student/STDM001">戻る</a>

      </form>
    </div>
  </c:param>
</c:import>

