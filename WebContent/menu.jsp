<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<div class="sidebar">
<nav>
<ul>
<%-- メニューの内容 --%>
<li><a href="${pageContext.request.contextPath}/main">メニュー</a></li>
<li><a href="${pageContext.request.contextPath}/main">学生管理</a></li>
<li>成績管理</li>
<li><a href="${pageContext.request.contextPath}/main">成績登録</a></li>
<li><a href="${pageContext.request.contextPath}/main">成績参照</a></li>
<li><a href="${pageContext.request.contextPath}/main">科目管理</a></li>

<%-- 未ログインの場合は非表示 --%>
<%-- ログインしている場合は表示 --%>

</ul>
</nav>
</div>

