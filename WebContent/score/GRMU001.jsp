<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%-- 入学年度の重複除去のためにJSTLのfunctionsライブラリを使う --%>
<%@taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<c:import url="/base.jsp">
	<c:param name="title">成績管理</c:param>
	<c:param name="body">
		<div class="container">
			<h2 class="p-3 mb-4 bg-light rounded">成績管理</h2>

				<%-- sessionが切れた時のエラーメッセージ --%>
				<c:if test="${not empty page_error}">
	                <div class="alert alert-danger" role="alert">
	                    ${page_error}
	                </div>
            	</c:if>

			<%-- 検索フォーム --%>
			<form method="get" action="test">
			<input type="hidden" name="search" value="true">
				<div class="bg-white p-3 rounded shadow-sm border mb-4 container"
					 style="width: fit-content;">
					<table class="table table-borderless mb-0">
						<tbody>

							<tr>

								<%-- 入学年度 --%>
								<td style="width: 180px; vertical-align: bottom;">
									<label for="f1" class="form-label">入学年度</label>
									<select name="f1" id="f1" class="form-select">
										<option value="">--------</option>
										<c:set var="displayedYears" value="" />
										<%-- 入学年度を繰り返しで表示 --%>
										<c:forEach var="student" items="${studentList}">
											<%-- 入学年度が重複しないように判断し表示 --%>
											<c:if test="${not fn:contains(displayedYears, student.entYear)}">
												<option value="${student.entYear}"
													    <c:if test="${student.entYear == f1_selected}">selected</c:if>>
												${student.entYear}</option>
												<c:set var="displayedYears"
													   value="${displayedYears} ${student.entYear}" />
											</c:if>
										</c:forEach>
									</select>
								</td>

								<%-- クラス --%>
								<td style="width: 120px; vertical-align: bottom;">
									<label for="f2" class="form-label">クラス</label>
									<select name="f2" id="f2" class="form-select">
										<option value="">--------</option>
										<%-- クラスを繰り返しで表示 --%>
										<c:forEach var="course" items="${classNumList}">
											<%-- クラスが重複しないように表示 --%>
											<option value="${course.class_num}"
												<c:if test="${course.class_num == f2_selected}">selected</c:if>>
											${course.class_num}</option>
										</c:forEach>
									</select>
								</td>

								<%-- 科目 --%>
								<td style="width: 160px; vertical-align: bottom;">
									<label for="f3" class="form-label">科目</label>
									<select name="f3" id="f3" class="form-select">
										<option value="">--------</option>
										<%-- 科目を繰り返しで表示 --%>
										<c:forEach var="subject" items="${subjectList}">
											<%-- 科目が重複しないように表示 --%>
											<option value="${subject.cd}"
												<c:if test="${subject.cd == f3_selected}">selected</c:if>>
											${subject.name}</option>
										</c:forEach>
									</select>
								</td>

								<%-- 回数 --%>
								<td style="width: 120px; vertical-align: bottom;">
									<label for="f4" class="form-label">回数</label>
									<%-- 回数は登録していない分も表示できるようにしている --%>
									<%-- 理由としては、機能として成績追加が出来る場所がないため、登録もできるように
										 変更をしたから --%>
									<select name="f4" id="f4" class="form-select">
										<option value="">--------</option>
										<option value="1"
											<c:if test="${'1' == f4_selected}">selected</c:if>>1</option>
										<option value="2"
											<c:if test="${'2' == f4_selected}">selected</c:if>>2</option>
									</select>
								</td>

								<%-- 検索ボタン --%>
								<td style="width: 80px; vertical-align: bottom;">
									<button type="submit" class="btn btn-secondary w-100">検索</button>
								</td>

							</tr>

						</tbody>
					</table>

					<%-- 入力エラー表示 --%>
					<c:if test="${not empty error_message}">
						<div class="invalid-feedback d-block">
							${error_message}
						</div>
					</c:if>

				</div>
			</form>




			<%-- 検索結果の表示エリア --%>
			<c:if test="${not empty searchResults}">
				<div class="search-results">
					<div class="mb-3">
						<span class="fw-bold">科目：${selectedSubjectName}（${selectedCount}回）</span>
					</div>

					<form method="post" action="testexecute">
						<%-- table-borderlessでセルの縦横線をすべて消す --%>
						<table class="table table-borderless mb-0" style="vertical-align: middle;">

							<thead>
								<%-- ヘッダー行の下にだけ線を引く --%>
								<tr class="border-bottom">
									<th class="text-center py-2" style="width: 15%;">入学年度</th>
									<th class="text-center py-2" style="width: 15%;">クラス</th>
									<th class="text-center py-2" style="width: 15%;">学生番号</th>
									<th class="text-center py-2" style="width: 25%;">氏名</th>
									<th class="text-center py-2" style="width: 20%;">点数</th>
									<th class="text-center py-2" style="width: 15%;">削除</th>
								</tr>
							</thead>

							<tbody>
								<%-- 取り出したデータを繰り返しで表示 --%>
								<c:forEach var="test" items="${searchResults}" varStatus="loop">
									<%-- 各データ行の下に線を引く (最後の行は除く) --%>
									<tr <c:if test="${!loop.last}">class="border-bottom"</c:if>>
										<%-- 各セルの文字揃えと上下の余白(padding)を調整 --%>
										<td class="text-center py-2">${test.student.entYear}</td>
										<td class="text-center py-2">${test.classNum}</td>
										<td class="text-center py-2">${test.student.no}</td>
										<td class="text-center py-2">${test.student.name}</td>
										<td class="text-center py-2">
										    <input type="text"
										           name="point_${test.student.no}"
										           class="form-control mx-auto ${not empty errors[test.student.no] ? 'is-invalid' : ''}"
										           style="width: 150px; text-align: center;"

										           <%-- 点数表示分岐 --%>
										           <c:choose>
										               <%-- エラー時の再表示値 --%>
										               <c:when test="${not empty inputValues[test.student.no]}">
										                   value="${inputValues[test.student.no]}"
										               </c:when>

										               <%-- DBに点数のデータがある場合 --%>
										               <c:when test="${test.point > 0}">
										                   value="${test.point}"
										               </c:when>

										               <%-- DBに点数のデータがない場合、プレースホルダーを表示 --%>
										               <c:otherwise>
										                   placeholder="点数を入力"
										               </c:otherwise>
										           </c:choose>
										      >

										    <%-- 点数入力のエラー表示 --%>
										    <c:if test="${not empty errors[test.student.no]}">
										        <div class="invalid-feedback d-block">
										            ${errors[test.student.no]}
										        </div>
										    </c:if>
										</td>

										<%-- 削除のチェックボックス --%>
										<td class="text-center py-2">
											<input type="checkbox" name="delete_students" value="${test.student.no}" class="form-check-input">
										</td>

									</tr>
								</c:forEach>
							</tbody>
						</table>

						<%-- 登録ボタン --%>
						<div class="my-4 text">
							<button type="submit" class="btn btn-secondary px-4">登録して終了</button>
						</div>
					</form>

					<%-- 再度入力ボタン --%>
					<div class="my-4 text">
						<button type="submit" class="btn btn-secondary px-4">再度入力ボタン</button>
					</div>

				</div>
			</c:if>
		</div>
	</c:param>
</c:import>