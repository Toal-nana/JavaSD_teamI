<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar">

<c:choose>

<%-- ログインしている場合は表示 --%>
<c:when test="${not empty session_user}">
<nav>
<ul>
<%-- メニューの内容 --%>
	<li><a href="${pageContext.request.contextPath}/main">メニュー</a></li>
	<li><a href="${pageContext.request.contextPath}/main">学生管理</a></li>
	<li>成績管理</li>
	<li><a href="${pageContext.request.contextPath}/main">成績登録</a></li>
	<li><a href="${pageContext.request.contextPath}/main">成績参照</a></li>
	<li><a href="${pageContext.request.contextPath}/main">科目管理</a></li>
</ul>
</nav>
</c:when>

<%-- 未ログインの場合は非表示 --%>

</c:choose>

</div>

