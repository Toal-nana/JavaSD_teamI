<%--諸々リンクが違うのであとで作ります。 --%>
<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE>
<html>
<head>
<%--
<c:import url="/base.jsp">

    <c:param name="title">学生管理</c:param>

    <c:param name="scripts"> </c:param>

    <c:param name="content">

    --%>
    </head>

    <body>
    	<section class="mp=4">
    	<h1>学生管理</h1>
    	<div class="my-2 text-end px-4">
    		<a href="STDM002">新規登録</a>
    		</div>
    		<form method="get">
    			 <div class="row border mx-3 mb-3 py-2 align-items-center rounded "id="filter">
    			 <div class="col-4">
    			 <label class="form-label" for="student-f1-select">入学年度</label>
    			 <select class="form-select" id="student-f1-select" name="f1">
    			 <option value="0"></option>
    			 <c:forEach var="year" items="${ent_year_set }">
    			 <option value="${year }"><c:if test="${year==f1 }">selected</c:if>>${year }</option>

    			 </c:forEach>

    			  </select>
    			</div>


    			 <div class="col-4">
    			 <label class="form-label" for="student-f2-select">クラス</label>
    			 <select class="form-select" id="student-f2-select" name="f2">
    			 	<option value="0"></option>
    			 	<c:forEach var="num" items="${class_num_set }">
    			 	<option value="${num }" <c:if test="${num==f2 }">selected</c:if>>${num }</option>

    			 	 		</c:forEach>

    			 	</select>
    			 </div>


    			 <div class="col-2 form-check text-center">在学中</div>
    			 	<label class="form-check-label" for="student-f3-check">

    			 		<input class="form-check-input" type="checkbox" id="student-f3-check" name="f3" value="t"
    			 		<c:if test="${!empty f3}">checked</c:if>/>
    			 		</label>

    			     	</div>


    			 <div class="col-2 text-center">
    			 	<button class="btn btn-secondary" id="filter-button">絞り込み</button>
    			 		</div>
    			 		<div class="mt-2 text-warning">${errors.get("f1") }</div>



    				</form>
				<c:choose>
					<c:when test="${students.size()>0 }">
						<div>検索結果：${students.size() }件</div>
						<table class="table table-hover">
						<tr>
							<th>入学年度</th>
							<th>学生番号</th>
							<th>氏名</th>
							<th>クラス</th>
							<th class="text-center">在学中</th>
							<th></th>
							<th></th>
						</tr>
						<c:forEach var="studet" items="${students }">
							<tr>
								<td>${student.enYear }</td>
								<td>${student.no }</td>
								<td>${student.name }</td>
								<td>${student.classNum }</td>
								<td class="text-center">

								<c:choose>

									<c:when test="${student.isAttend() }">

									</c:when>
									<c:otherwise>
										×
									</c:otherwise>
								</c:choose>

								</td>
								<%--95、96行目のリンクは違うのでサーブレットつくってあとで直します。 --%>
								<td><a href="StudentUpdate.action?no=${student.no }">変更</a></td>
								<td><a href="StudentDelete.action?no=${student.no }">削除</a></td>
								</tr>


							</c:forEach>
							</table>


					</c:when>
					<c:otherwise>
						<div>学生情報が存在しませんでした。</div>
						</c:otherwise>

				</c:choose>



    		</section>
</body>

</html>
<%--
    </c:param>
</c:import>

--%>
