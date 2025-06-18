<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">科目別成績一覧</c:param>

	<c:param name="body">

		<p>科目：${scuject.name}</p>

		<table border="1"><thead><th>入学年度</th><th>クラス</th><th>学生番号</th><th>氏名</th><th>1回</th><th>2回</th></thead>
			<c:forEach var="tLS" items="${testListSubject}">
				<tr><td>${tLS.entYear}</td><td>${tLS.classNum}</td><td>${tLS.studentNo}</td><td>${tLS.studentName}</td><td>"-"</td><td>"-"</td></tr>
			</c:forEach>
		</table>

	</c:param>

</c:import>