<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<div class="sidebar">

	<nav>
		<ul class="nav flex-column">
			<%-- メニューの内容 --%>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/MMNU001">メニュー</a></li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/student/STDM001">学生管理</a></li>
			<li class="nav-item"><span class="d-block py-2 px-3">成績管理</span>
				<ul class="nav flex-column ps-3">
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/score/GRMU001">成績登録</a></li>
					<li class="nav-item"><a class="nav-link"
						href="${pageContext.request.contextPath}/score/GRMR001">成績参照</a></li>
				</ul></li>
			<li class="nav-item"><a class="nav-link"
				href="${pageContext.request.contextPath}/subject/SBJM001">科目管理</a></li>
		</ul>
	</nav>

</div>
