<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<c:if test="${not empty searchResults}">
				<div class="search-results">
					<div class="mb-3">
						<span class="fw-bold">氏名：${student.name}(${student.no})</span>
					</div>

					<form method="post" action="testexecute">
						<%-- table-borderlessでセルの縦横線をすべて消す --%>
						<table class="table table-borderless mb-0"
							style="vertical-align: middle;">
							<thead>
								<%-- ヘッダー行の下にだけ線を引く --%>
								<tr class="border-bottom">
									<th class="text-center py-2" style="width: 15%;">科目名</th>
									<th class="text-center py-2" style="width: 15%;">科目コード</th>
									<th class="text-center py-2" style="width: 20%;">回数</th>
									<th class="text-center py-2" style="width: 30%;">点数</th>
								</tr>
							</thead>
							<tbody>
								<%-- 取り出したデータを繰り返しで表示 --%>
								<c:forEach var="tLS" items="${testListStudent}" varStatus="loop">
									<%-- 各データ行の下に線を引く (最後の行は除く) --%>
									<tr <c:if test="${!loop.last}">class="border-bottom"</c:if>>
										<%-- 各セルの文字揃えと上下の余白(padding)を調整 --%>
										<td class="text-center py-2">${tLS.subjectName}</td>
										<td class="text-center py-2">${tLS.subjectCd}</td>
										<td class="text-center py-2">${tLS.num}</td>
										<td class="text-center py-2">${tLS.point}</td>
									</tr>
								</c:forEach>
							</tbody>
						</table>
					</form>
				</div>
			</c:if>