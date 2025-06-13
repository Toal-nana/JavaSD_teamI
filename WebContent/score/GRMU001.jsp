<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">成績管理一覧</c:param>

	<c:param name="body">

		<div class="container">
			<%-- 画面タイトル --%>
			<h2 class="p-3 mb-4 bg-light rounded">成績管理</h2>

			<%-- 検索フォーム --%>
			<form method="post" action="">
				<div class="bg-white p-3 rounded shadow-sm border mb-4"
					style="width: fit-content;">
					<table class="table table-borderless mb-0">
						<tbody>
							<tr>
								<%-- 入学年度 --%>
								<td style="width: 180px; vertical-align: bottom;"><label
									for="f1" class="form-label">入学年度</label> <select name="f1"
									id="f1" class="form-select">
										<option value="">--------</option>
										<c:forEach var="years" items="${years}">
											<option value="${years.year}">${years.year}</option>
										</c:forEach>
								</select></td>

								<%-- クラス --%>
								<td style="width: 120px; vertical-align: bottom;"><label
									for="f2" class="form-label">クラス</label> <select name="f2"
									id="f2" class="form-select">
										<option value="">--------</option>
										<c:forEach var="course" items="${courses}">
											<option value="${course.num}">${course.num}</option>
										</c:forEach>
								</select></td>

								<%-- 科目 --%>
								<td style="width: 160px; vertical-align: bottom;"><label
									for="f3" class="form-label">科目</label> <select name="f3"
									id="f3" class="form-select">
										<option value="">--------</option>
										<c:forEach var="subject" items="${subjects}">
											<option value="${subject.cd}">${subject.cd}</option>
										</c:forEach>
								</select></td>

								<%-- 回数 --%>
								<td style="width: 120px; vertical-align: bottom;"><label
									for="f4" class="form-label">回数</label> <select name="f4"
									id="f4" class="form-select">
										<option value="">--------</option>
										<c:forEach var="count" items="${counts}">
											<option value="${count.num}">${count.num}</option>
										</c:forEach>
								</select></td>

								<%-- 検索ボタン --%>
								<td style="width: 80px; vertical-align: bottom;">
									<button type="submit" class="btn btn-secondary w-100">検索</button>
								</td>
							</tr>
						</tbody>
					</table>
				</div>
			</form>


			<%-- 検索にヒットしたら詳細を表示する --%>
			<c:if test="${not empty searchResults}">
				<div class="search-results">
					<%-- 科目情報 --%>
					<div class="mb-3">
						<span class="fw-bold">科目：${selectedSubject}
							（${selectedCount}回）</span>
					</div>

					<%-- 成績入力テーブル --%>
					<form method="post" action="">
						<table class="table table-bordered bg-white">
							<thead class="table-light">
								<tr>
									<th style="width: 120px;">入学年度</th>
									<th style="width: 100px;">クラス</th>
									<th style="width: 120px;">学生番号</th>
									<th style="width: 150px;">氏名</th>
									<th style="width: 120px;">点数</th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="result" items="${searchResults}"
									varStatus="status">
									<tr>
										<td class="text-center">${result.year}</td>
										<td class="text-center">${result.className}</td>
										<td class="text-center">${result.studentId}</td>
										<td>${result.name}</td>
										<td><input type="number" name="score_${result.studentId}"
											class="form-control" value="${result.score}" min="0"
											max="100" style="width: 80px;"></td>
									</tr>
								</c:forEach>
							</tbody>
						</table>

						<%-- 登録ボタン --%>
						<div class="mb-4">
							<button type="submit" class="btn btn-secondary px-4">登録して終了</button>
						</div>
					</form>
				</div>
			</c:if>

		</div>
	</c:param>

</c:import>

<%-- サーブレットが出来次第、セレクトボックスの中身を書き直す必要がある --%>