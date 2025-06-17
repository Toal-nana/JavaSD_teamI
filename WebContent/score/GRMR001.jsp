<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">成績参照検索</c:param>
	<c:param name="body">

		<h2 class="h2 bg-body-tertiary m-3 p-2 ps-4">成績参照</h2>

		<%-- 科目情報による検索 --%>
		<div class="border">
			<form action="${pageContext.request.contextPath}/score/subject" method="get">
				<div class="row mb-3">
					<div class="col-md-2 ms-4 me-2">
				    	<label class="fw-bold">科目情報</label>
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

					<div class="col-md-3">
						<label class="form-label">科目</label>
						<select name="f3" class="form-select">
							<option>--------</option>
							<c:forEach var="subject" items="${subjectList}">
								<option value="${subject.cd}">${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<button type="submit" class="btn btn-secondary m-3 ms-4" style="button {width:10em;}">検索</button>
					</div>
					<input type="hidden" name="f" value="sj">
				</div>
			</form>


			<%-- 学生情報による検索 --%>
			<form action="${pageContext.request.contextPath}/score/" method="get" class="mb-4">
			<div class="row">
				<div class="col-md-2 ms-4">
					<label class="form-label fw-bold">学生情報</label>
				</div>

				<div class="col-md-4">
					<label class="form-label">学生番号</label>
					<input type="text" name="f4" value="${f4}" placeholder="学生番号を入力してください" class="form-control">
				</div>
				<div class="col ms-4 ">
					<button type="submit" class="btn btn-secondary">検索</button>
				</div>
			</div>
			<input type="hidden" name="f" value="st">
			</form>
		</div>
		<p><label>科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</label></p>
	</c:param>

</c:import>