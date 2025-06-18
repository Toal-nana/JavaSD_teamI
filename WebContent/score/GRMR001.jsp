<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">成績参照検索</c:param>
	<c:param name="body">

		<h2 class="h2 bg-body-tertiary mt-3 p-2 ps-4">成績参照</h2>

		<%-- 科目情報による検索 --%>
		<div class="border p-2 m-3 rounded">
			<form action="${pageContext.request.contextPath}/score/testlist" method="post" class="border-bottom mb-3">
				<div class="row mb-2">
					<div class="col-md-2 ms-4">
				    	<label class="form-label mt-4">科目情報</label>
					</div>

					<div class="col-md-2">
						<label class="form-label">入学年度</label>
						<select name="f1" class="form-select" >
							<option value="" selected>--------</option>
							<c:forEach var="student" items="${studentList}">
								<option value="${student.entYear}">${student.entYear}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-2">
						<label class="form-label">クラス</label>
						<select name="f2" class="form-select" >
							<option value="">--------</option>
							<c:forEach var="course" items="${classNumList}">
								<option value="${course.class_num}">${course.class_num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-4">
						<label class="form-label">科目</label>
						<select name="f3" class="form-select">
							<option>--------</option>
							<c:forEach var="subject" items="${subjectList}">
								<option value="${subject.cd}">${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<button type="submit" class="btn btn-secondary ms-2 mt-3">検索</button>
					</div>
					<input type="hidden" name="f" value="sj">
				</div>
			</form>


			<%-- 学生情報による検索 --%>
			<form action="${pageContext.request.contextPath}/score/testlist" method="get" class="mb-2">
			<div class="row">
				<div class="col-md-2 ms-4">
					<label class="form-label">学生情報</label>
				</div>

				<div class="col-md-4">
					<label class="form-label">学生番号</label>
					<input type="text" name="f4" value="${f4}" placeholder="学生番号を入力してください" class="form-control">
				</div>
				<div class="col">
					<button type="submit" class="btn btn-secondary ms-2 mt-3">検索</button>
				</div>
			</div>
			<input type="hidden" name="f" value="st">
			</form>
		</div>

		<%-- リストを受け取っていた場合、科目別検索結果を表示 --%>
		<c:if test="${not empty testListSubject}">
			<table border="1"><thead><th>入学年度</th><th>クラス</th><th>学生番号</th><th>氏名</th><th>1回</th><th>2回</th></thead>
			<c:forEach var="tLS" items="${testListSubject}">
				<tr><td>${tLS.entYear}</td><td>${tLS.classNum}</td><td>${tLS.studentNo}</td><td>${tLS.studentName}</td><td>"-"</td><td>"-"</td></tr>
			</c:forEach>
			</table>
		</c:if>

		<%-- リストを受け取っていた場合、学生別検索結果を表示 --%>
		<c:if test="${not empty testListStudent}">
			<p>氏名：${student.name}(${student.no})</p>
			<table border="1"><thead><th>科目名</th><th>科目コード</th><th>回数</th><th>点数</th></thead>
			<c:forEach var="tLS" items="${testListStudent}">
				<tr><td>${tLS.subjectName}</td><td>${tLS.subjectCd}</td><td>${tLS.num}</td><td>${tLS.point}</td></tr>
			</c:forEach>
			</table>
		</c:if>

		<%-- リストを受け取っていなかった場合、以下の文章を表示 --%>
		<c:if test="${empty testListSubject}">
			<p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
		</c:if>
	</c:param>

</c:import>