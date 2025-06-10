<%-- 科目管理ページ --%>
<%@page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">
<c:param name="title" value="科目管理" />
<c:param name="body">

<table border="1" >
	<tr><th>科目コード</th><th>科目名</th></tr>

	<c:forEach var="student" items="${studentList}">
	<tr>
	<td><c:out value="${student.studentId}" /></td>
	<td><c:out value="${student.studentName}" /></td>
	</tr>
	</c:forEach>
</table>
		<a href="${pageContext.request.contextPath}/student/index">Topページに戻る</a>
</c:param>
</c:import>