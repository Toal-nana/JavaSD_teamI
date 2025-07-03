<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<c:import url="/base.jsp">

	<c:param name="title">学生情報登録</c:param>

	<c:param name="body">
		<div class="container mt-2">
			<h2 class="px-3 py-2 me-3 bg-light">学生情報登録</h2>

			<form
				action="${pageContext.request.contextPath}/student/create_execute"
				method="post">

				<%-- 入学年度 --%>
				<div class="mb-3">
					<label class="form-label">入学年度</label> <select name="year"
						class="form-select">
						<option value="">-------</option>
						<c:forEach var="year" items="${entYearSet}">
							<option value="${year}"
								<c:if test="${student.entYear == year}">selected</c:if>>
								${year}</option>
						</c:forEach>
					</select>
					<%-- 入学年度のサーバーサイドエラー表示 --%>
					<c:if test="${not empty errors.year}">
						<p class="text-warning d-block">${errors.year}</p>
					</c:if>
				</div>

				<%-- 学生番号 --%>
				<div class="mb-3">
					<label class="form-label">学生番号</label>
					<%-- required属性で未入力をブラウザでチェック --%>
					<input type="text" name="number" class="form-control"
						value="<c:out value='${student.no}'/>" required>
					<%-- 学生番号のサーバーサイドエラー(桁数、重複)を表示 --%>
					<c:if test="${not empty errors.number}">
						<p class="text-warning d-block">${errors.number}</p>
					</c:if>
				</div>

				<%-- 氏名 --%>
				<div class="mb-3">
					<label class="form-label">氏名</label>
					<%-- required属性で未入力をブラウザでチェック --%>
					<input type="text" name="name" class="form-control"
						value="<c:out value='${student.name}'/>" required>
					<%-- 氏名のサーバーサイドエラー（文字数オーバー）を表示する --%>
					<c:if test="${not empty errors.name}">
						<p class="text-warning d-block">${errors.name}</p>
					</c:if>
				</div>

				<%-- クラス --%>
				<div class="mb-4">
					<label class="form-label">クラス</label> <select name="class"
						class="form-select" required>
						<option value="">選択してください</option>
						<c:forEach var="cls" items="${classList}">
							<option value="${cls}"
								<c:if test="${student.classNum == cls}">selected</c:if>>${cls}</option>
						</c:forEach>
					</select>
					<%-- クラスのサーバーサイドエラー表示 --%>
					<c:if test="${not empty errors['class']}">
						<p class="text-warning d-block">${errors['class']}</p>
					</c:if>
				</div>

				<div class="mt-4">
					<button type="submit" class="btn btn-secondary">登録して終了</button>
					<div class="mt-2">
						<a href="${pageContext.request.contextPath}/student/list">戻る</a>
					</div>
				</div>
			</form>
		</div>
	</c:param>
</c:import>