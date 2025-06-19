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
					 		<option value="">--------</option>
        					<c:forEach var="student" items="${studentList}">
                				<option value="${student.entYear}" <c:if test="${student.entYear == entYear}">selected</c:if>>${student.entYear}</option>
        					</c:forEach>
						</select>
					</div>

					<div class="col-md-2">
						<label class="form-label">クラス</label>
						<select name="f2" class="form-select" >
							<option value="">--------</option>
							<c:forEach var="course" items="${classNumList}">
								<%-- 選択されたものを保持 --%>
								<option value="${course.class_num}" <c:if test="${course.class_num == classNum}">selected</c:if>>${course.class_num}</option>
							</c:forEach>
						</select>
					</div>

					<div class="col-md-4">
						<label class="form-label">科目</label>
						<select name="f3" class="form-select">
							<option value="">--------</option>
							<c:forEach var="subject" items="${subjectList}">
								<%-- 選択されたものを保持 --%>
								<option value="${subject.cd}" <c:if test="${subject.cd == subjectCd}">selected</c:if>>${subject.name}</option>
							</c:forEach>
						</select>
					</div>
					<div class="col">
						<button type="submit" class="btn btn-secondary ms-2 mt-3">検索</button>
					</div>
					<input type="hidden" name="f" value="sj">
					</div>
					<c:if test="${not empty sjError}">
					<p class="text-warning ps-2 p-0">${sjError}</p>
					</c:if>
			</form>


			<%-- 学生情報による検索 --%>
			<form action="${pageContext.request.contextPath}/score/testlist" method="post" class="mb-2">
			<div class="row">
				<div class="col-md-2 ms-4">
					<label class="form-label">学生情報</label>
				</div>

				<div class="col-md-4">
					<label class="form-label">学生番号</label>
					<input type="text" name="f4" value="${f4}" placeholder="学生番号を入力してください" class="form-control" required>
				</div>
				<div class="col">
					<button type="submit" class="btn btn-secondary ms-2 mt-3">検索</button>
				</div>
			</div>
			<input type="hidden" name="f" value="st">
			</form>
		</div>

		<%-- Controllerからセットされたエラーメッセージがあれば表示 --%>
		<c:if test="${not empty error_student}">
		    <div class="p-3">
		        <p class="alert alert-danger">${error_student}</p>
		    </div>
		</c:if>

		<%-- リストを受け取っていた場合、科目別検索結果を表示 --%>
		<c:if test="${not empty testListSubject}">
			<c:import url="/score/GRMR002.jsp"/>
		</c:if>

		<%-- リストを受け取っていた場合、学生別検索結果を表示 --%>
		<c:if test="${not empty student}">
			<c:import url="/score/GRMR003.jsp"/>
		</c:if>

		<%-- リストを受け取っていなかった場合、以下の文章を表示 --%>
		<c:if test="${empty testListSubject && empty student && empty error_student && empty sjError}">
			<p class="text-info p-3">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
		</c:if>
	</c:param>

</c:import>