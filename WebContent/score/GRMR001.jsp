<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">成績参照検索</c:param>
	<c:param name="body">

		<h2 class="h2 bg-body-tertiary mt-3 p-2 ps-4">成績参照</h2>

		<%-- 科目情報による検索フォーム --%>
		<div class="border p-2 m-3 rounded">
			<form action="${pageContext.request.contextPath}/score/testlist" method="post" class="border-bottom mb-3">
				<div class="row mb-2">
					<div class="col-md-2 ms-4">
				    	<label class="form-label mt-4">科目情報</label>
					</div>

					<%-- 入学年度のセレクトボックス --%>
					<div class="col-md-2">
						<label class="form-label">入学年度</label>
						<select name="f1" class="form-select" >
					 		<option value="">--------</option>
        					<c:forEach var="selectEntYear" items="${entYearList}">
        						<%-- 選択されたものを保持 --%>
                				<option value="${selectEntYear}" <c:if test="${selectEntYear == entYear}">selected</c:if>>${selectEntYear}</option>
        					</c:forEach>
						</select>
					</div>

					<%-- クラスのセレクトボックス --%>
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

					<%-- 科目のセレクトボックス --%>
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
					<%-- 科目情報による検索を行う検索ボタン --%>
					<div class="col">
						<button type="submit" class="btn btn-secondary ms-2 mt-3">検索</button>
					</div>
					<%-- 科目別検索が行われた事を知らせる変数 --%>
					<input type="hidden" name="f" value="sj">
				</div>
					<c:if test="${not empty sjError}">
						<p class="text-warning ms-4 mb-0">${sjError}</p>
					</c:if>
			</form>


			<%-- 学生情報による検索フォーム --%>
			<form action="${pageContext.request.contextPath}/score/testlist" method="post" class="mb-2">
				<div class="row">
					<div class="col-md-2 ms-4">
						<label class="form-label mt-4">学生情報</label>
					</div>
					<div class="col-md-4">
						<label class="form-label">学生番号</label>
						<input type="text" name="f4" value="${f4}" placeholder="学生番号を入力してください" class="form-control" required>
					</div>
					<%-- 学生情報による検索を行う検索ボタン --%>
					<div class="col">
						<button type="submit" class="btn btn-secondary ms-2 mt-3">検索</button>
					</div>
				</div>
				<%-- 科目別検索が行われた事を知らせる変数 --%>
				<input type="hidden" name="f" value="st">
			</form>
		</div>


		<%-- フォーム送信後の表示 --%>
		<%-- Controllerからセットされたエラーメッセージがあれば表示 --%>
		<c:if test="${not empty error_student}">
		    <div class="p-3">
				<p class="alert alert-danger">${error_student}</p>
		    </div>
		</c:if>

		<%-- subjectを受け取っていた場合、科目別検索結果を表示 --%>
		<c:if test="${not empty subject}">
			<c:import url="/score/GRMR002.jsp"/>
		</c:if>

		<%-- studentを受け取っていた場合、学生別検索結果を表示 --%>
		<c:if test="${not empty student}">
			<c:import url="/score/GRMR003.jsp"/>
		</c:if>

		<%-- オブジェクトやエラーを受け取っていなかった場合、以下の文章を表示 --%>
		<c:if test="${empty subject && empty student && empty error_student}">
			<p class="text-info">科目情報を選択または学生情報を入力して検索ボタンをクリックしてください</p>
		</c:if>

	</c:param>
</c:import>