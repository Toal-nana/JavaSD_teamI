<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>

<%-- head --%>
<head>
<meta charset="UTF-8">
<title>${ param.title }</title>
<link rel="stylesheet" href="${param.link}">
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz"
	crossorigin="anonymous"></script>
</head>


<%-- body --%>
<body class="container">

	<%-- header --%>
	<header
		class="d-flex p-3 bg-primary bg-opacity-25 text-dark position-relative">
		<div class="col-md-5 mb-2 mb-md-0">
			<h1>得点管理システム</h1>
		</div>
		<%-- ログインしている場合 --%>
		<div
			class="col-md-7 text-end position-absolute bottom-0 end-0 pb-2 mx-3">
			<%--<c:when test="${not empty session_user}">
        <%-- 名前とログアウトを表示名前${session_user.username}
        (ログインしているかしていないかの判断でエラーになったのでコメントアウト) --%>
			<span> 様</span>
			<%-- ログアウト画面へ飛ぶ（ログアウト処理のサーブレットのアノテーションによるので、後で変更） --%>
			<a href="${pageContext.request.contextPath}/accounts/LOGO001">ログアウト</a>
			<%--</c:when>--%>
		</div>
	</header>

<c:choose>
  <%-- ログインしている場合の表示形式 --%>
  <c:when test="${ not empty session_user}">
    <div class="row py-3">
    <%-- サイドメニュー --%>
    <div class="col-md-2 "><c:import url="/menu.jsp"/></div>
    <div class="col-md-1 d-flex flex-row-reverse container m-0 p-0">
      <div class="vr p-0"></div>
    </div>
    <%-- 各要素のbody --%>
    <div class="col-md-8">${ param.body }</div>
    </div>
  </c:when>

  <%-- ログインしていない場合の表示形式 --%>
  <c:when test="${ empty session_user }">
    <div>${param.body}</div>
  </c:when>
</c:choose>

	<%-- footer --%>
	<footer
		class="text-center text-body-secondary bg-secondary bg-opacity-25 py-2 mt-3">
		<p class="m-0">@ 2023 TIC</p>
		<p class="m-0">大原学園</p>
	</footer>

</body>

</html>