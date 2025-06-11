<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<c:import url="/base.jsp">

	<c:param name="title">成績管理一覧</c:param>

	<c:param name="body">

		<%-- 画面タイトル --%>
		<h2>成績管理</h2>

		<div>
			<table>

				<tr>
					<%-- ヘッダー：入学年度 --%>
					<th>入学年度</th>
					<%-- ヘッダー：クラス --%>
					<th>クラス</th>
					<%-- ヘッダー：科目 --%>
					<th>科目</th>
					<%-- ヘッダー：回目 --%>
					<th>回数</th>
				</tr>

				<tr>
					<%-- セレクトボックス：入学年度 --%>
					<th>
						<select name="f1">
	                        <option value="">--------</option>
	                        <c:forEach var="years" items="${years}">
	    						<option value="${years.year}">${years.year}</option>
	  						</c:forEach>
	                    </select>
					</th>
					<%-- セレクトボックス：クラス --%>
					<th>
						<select name="f2">
	                        <option value="">--------</option>
	                        <c:forEach var="course" items="${courses}">
	    						<option value="${course.num}">${course.num}</option>
	  						</c:forEach>
	                    </select>
					</th>
					<%-- セレクトボックス：科目 --%>
					<th>
						<select name="f3">
	                        <option value="">--------</option>
	                        <c:forEach var="subject" items="${subjects}">
	    						<option value="${subject.cd}">${subject.cd}</option>
	  						</c:forEach>
	                    </select>
					</th>
					<%-- セレクトボックス：回目 --%>
					<th>
						<select name="f4">
	                        <option value="">--------</option>
	                        <c:forEach var="count" items="${counts}">
	    						<option value="${count.num}">${count.num}</option>
	  						</c:forEach>
	                    </select>
					</th>
					<%-- ボタン：検索 --%>
					<th>
	                    <button class="favorite styled" type="button">検索</button>
	                </th>
				</tr>

			</table>
		</div>

		<div class="search-results">
		    <c:if test="${not empty searchResults}">
		      <table>
		        <thead>
		          <tr>
		            <th>入学年度</th>
		            <th>クラス</th>
		            <th>学生番号</th>
		            <th>氏名</th>
		            <th>成績</th>
		          </tr>
		        </thead>
		        <tbody>
		          <c:forEach var="result" items="${searchResults}">
		            <tr>
		              <td>${result.year}</td>
		              <td>${result.className}</td>
		              <td>${result.studentId}</td>
		              <td>${result.name}</td>
		              <td>${result.grade}</td>
		            </tr>
		          </c:forEach>
		        </tbody>
		      </table>
		    </c:if>
  		</div>

	</c:param>

</c:import>

<%-- サーブレットが出来次第、セレクトボックスの中身を書き直す必要がある --%>