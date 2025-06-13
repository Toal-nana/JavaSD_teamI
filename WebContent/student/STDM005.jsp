<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>


<c:import url="/base.jsp">
  <c:param name="title">学生管理一覧</c:param>
  <c:param name="body">
  <div class="mx-auto">
	<h2 class="bg-light border  shadow-sm p-3">学生情報変更</h2>


  <div class="alert text-center alert-success shadow-sm  py-2" role="alert" style="background-color:#8ab79a;">
    <label class="mb-0">変更が完了しました。</label>

    </div>
    </div>


      <a href="${pageContext.request.contextPath}/student/STDM001">学生一覧</a>
  </c:param>
</c:import>
