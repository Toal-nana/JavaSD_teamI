<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<%-- body --%>
<body>

<%-- head --%>
<head>
	<meta charset="UTF-8">
	<title>${ param.title }</title>
	<link rel="stylesheet" href="${param.link}">
	<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-9ndCyUaIbzAi2FUVXJi0CjmCapSmO7SnpJef0486qhLnuZ2cdeRhO02iuK6FUUVM" crossorigin="anonymous">
	<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
</head>

<%-- header --%>
<header class="p-3 text-bg-dark">
	 <h1>得点管理システム</h1>
	<%-- ログインしている場合 --%>
	<%--<c:when test="${not empty session_user}">
        <%-- 名前とログアウトを表示：名前${session_user.username}
        (ログインしているかしていないかの判断でエラーになったのでコメントアウト) --%>
        <span> 様</span>
        <%-- ログアウト画面へ飛ぶ（ログアウト処理のサーブレットのアノテーションによるので、後で変更） --%>
        <a href="${pageContext.request.contextPath}/accounts/LOGO001">ログアウト</a>
    <%--</c:when>--%>
</header>


<%-- サイドメニュー --%>
<c:import url="/menu.jsp"/>
<%-- 各要素のbody --%>
${ param.body }


<%-- footer --%>
<footer>
	<p>@ 2023 TIC</p>
	<p>大原学園</p>
</footer>

</body>

</html>