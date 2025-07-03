<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">
  <c:param name="title">学生情報編集</c:param>
  <c:param name="body">
    <div class="container mt-2">
      <h2 class="px-3 py-2 me-3 bg-light">学生情報変更</h2>

      <form action="${pageContext.request.contextPath}/student/update_execute" method="post">


        <input type="hidden" name="no" value="${student.no}">

		<%-- 入学年度の表示 (変更不可) --%>
        <div class="mb-3" >
          <label class="form-label">入学年度</label>
          <input type="text" name="entYear" value="${student.entYear}" class=" ms-3  form-control-plaintext" readonly>
        </div>

		<%-- 学生番号の表示 (変更不可) --%>
        <div class="mb-3">
          <label class="form-label">学生番号</label>
          <%-- 学生番号は主キーなので変更不可 (readonly) にする --%>
          <input type="text" value="${student.no}" class="ms-3 form-control-plaintext" readonly>
        </div>

		<%-- 氏名の入力フォーム --%>
        <div class="mb-3">
          <label class="form-label">氏名</label>
          <input type="text" name="name" value="${student.name}" class="form-control" required>
          <%-- 氏名のサーバーサイドエラーを表示 --%>
          <c:if test="${not empty errors.name}">
            <p class="text-warning d-block">${errors.name}</p>
          </c:if>
        </div>

		<%-- クラスの選択フォーム --%>
        <div class="mb-4">
          <label class="form-label">クラス</label>
          <select name="classNum" class="form-select" required>
            <option value="">選択してください</option>
            <c:forEach var="cls" items="${classList}">

              <option value="${cls}" <c:if test="${cls == student.classNum}">selected</c:if>>${cls}</option>
            </c:forEach>
            <%-- クラスのサーバーサイドエラー表示 --%>
	         <c:if test="${not empty errors.classNum}">
	           <p class="text-danger d-block mt-1">${errors.classNum}</p>
	         </c:if>
          </select>
        </div>

		<%-- 在学状況のチェックボックス --%>
        <div class="form-check mb-4">
          <input type="checkbox" class="form-check-input" id="isAttendCheck" name="isAttend" value="true" <c:if test="${student.attend}">checked</c:if>>
          <label class="form-check-label" for="isAttendCheck">在学中</label>
        </div>

		<div class="mt-4">
			  <button type="submit" class="btn btn-primary">変更</button>

			  <div class="mt-2">
			  <%-- 学生一覧画面へ戻るためのリンク --%>
			    <a href="${pageContext.request.contextPath}/student/list">戻る</a>
			  </div>
</div>

      </form>
    </div>
  </c:param>
</c:import>