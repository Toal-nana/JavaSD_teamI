<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="UTF-8">
	<title>${ param.title }</title>
	<link rel="stylesheet" href="${param.link}">
</head>
<body>
<%-- サイドメニュー --%>
<c:import url="/menu.jsp"/>
<%-- 各要素のbody --%>
${ param.body }
</body>
</html>