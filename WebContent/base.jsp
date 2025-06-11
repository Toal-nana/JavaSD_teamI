<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>

<%-- head --%>
<head>
	<meta charset="UTF-8">
	<title>${ param.title }</title>
	<link rel="stylesheet" href="${param.link}">
	<link rel="stylesheet" href="${pageContext.request.contextPath}/css/base.css">
</head>

<%-- header --%>
<header>
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

<%-- body --%>
<body>
<%-- サイドメニュー --%>
<c:import url="/menu.jsp"/>
<%-- 各要素のbody --%>
${ param.body }
</body>

<%-- footer --%>
<footer>
	<p>@ 2023 TIC</p>
	<p>大原学園</p>
</footer>

</html>