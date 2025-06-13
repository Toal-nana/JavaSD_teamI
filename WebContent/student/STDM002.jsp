<%@page contentType="text/html; charset=UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
  <%-- タイトルを base.jsp に渡す --%>
  <c:param name="title">学生情報登録</c:param>

  <%-- 本文（body）を base.jsp に渡す --%>
  <c:param name="body">

    <div class="container mt-2">
      <h4 class="bg-light border  shadow-sm p-3">学生情報登録フォーム</h4>

      <%--formのactionのリンクはサーブレットができてから変えます --%>
      <form action="${pageContext.request.contextPath}/student/insert" method="post" >
        <div class="mb-3">
          <label class="form-label">入学年度</label>
          <input type="text" name="year" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">学生番号</label>
          <input type="text" name="number" class="form-control" required>
        </div>

        <div class="mb-3">
          <label class="form-label">氏名</label>
          <input type="text" name="name" class="form-control" required>
        </div>

        <div class="mb-4">
          <label class="form-label">クラス</label>

          <select name="class" class="form-control" required>
            <option value="">選択してください</option>
            <c:forEach var="cls" items="${classList}">
              <option value="${cls}">${cls}</option>
            </c:forEach>
          </select>
          
        </div>



        <div>
         <a href="${pageContext.request.contextPath}/student/STDM001">戻る</a>
        </div>

      </form>
    </div>

  </c:param>
</c:import>
