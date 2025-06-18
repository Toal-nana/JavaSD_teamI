<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">学生別成績一覧</c:param>

	<c:param name="body">

		<p>氏名：${student.name}(${student.no})</p>

		<table border="1"><thead><th>科目名</th><th>科目コード</th><th>回数</th><th>点数</th></thead>
			<c:forEach var="tLS" items="${testListStudent}">
				<tr><td>${tLS.subjectName}</td><td>${tLS.subjectCd}</td><td>${tLS.num}</td><td>${tLS.point}</td></tr>
			</c:forEach>
		</table>
	</c:param>

</c:import>