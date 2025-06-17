<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">
  <c:param name="title">学生情報編集</c:param>
  <c:param name="body">
    <div class="container mt-2">
      <h4 class="bg-light border shadow-sm p-3">学生情報変更フォーム</h4>

      <%-- 更新実行サーブレットにデータを送信
      		ここのactionはあとで変更　--%>
      <form action="${pageContext.request.contextPath}/student/update_execute" method="post">

        <%-- 更新対象の学生番号を hidden で送信 (主キーなので変更させない) --%>
        <input type="hidden" name="no" value="${student.no}">

        <div class="mb-3">
          <label class="form-label">入学年度</label>
          <input type="text" name="entYear" value="${student.entYear}" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">学生番号</label>
          <%-- 学生番号は主キーなので変更不可 (readonly) にする --%>
          <input type="text" value="${student.no}" class="form-control" readonly>
        </div>

        <div class="mb-3">
          <label class="form-label">氏名</label>
          <input type="text" name="name" value="${student.name}" class="form-control" required>
        </div>

        <div class="mb-4">
          <label class="form-label">クラス</label>
          <select name="classNum" class="form-select" required>
            <option value="">選択してください</option>
            <c:forEach var="cls" items="${classList}">
              <%-- 学生の現在のクラスと一致する場合、selected属性を付与 --%>
              <option value="${cls}" <c:if test="${cls == student.classNum}">selected</c:if>>${cls}</option>
            </c:forEach>
          </select>
        </div>

        <div class="form-check mb-4">
          <%-- student.attend が true の場合、チェックを入れる --%>
          <input type="checkbox" class="form-check-input" id="isAttendCheck" name="isAttend" value="true" <c:if test="${student.attend}">checked</c:if>>
          <label class="form-check-label" for="isAttendCheck">在学中</label>
        </div>

        <div class="mt-4">
          <button type="submit" class="btn btn-primary">変更を保存する</button>
          <a href="${pageContext.request.contextPath}/student/STDM001.jsp" class="btn btn-secondary">戻る</a>
        </div>
      </form>
    </div>
  </c:param>
</c:import>