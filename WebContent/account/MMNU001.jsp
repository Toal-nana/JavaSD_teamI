<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">

    <c:param name="title">得点管理システム</c:param>

    <c:param name="body">
      <div class="mb-5 pb-5">
        <%-- メニュータイトル --%>
        <h2 class="px-3 py-2 mb-5 me-3 bg-light">メニュー</h2>

        <%-- メニューオプションをグリッドレイアウトで配置 --%>
        <div class="row text-center gx-3 ms-1 me-3 mb-5 pb-5">

            <%-- 学生管理へのリンクボックス --%>
            <div class="col-lg-4 mb-4">
                <div class="px-5 py-4 rounded shadow-sm h-100 d-flex align-items-center justify-content-center" style="background-color: #d9baba;">
                    <%-- 学生管理画面へのナビゲーションリンク --%>
                    <h4>
                        <a href="${pageContext.request.contextPath}/student/STDM001">学生管理</a>
                    </h4>
                </div>
            </div>

            <%-- 成績管理エリア: 登録・参照のサブメニュー --%>
            <div class="col-lg-4 mb-4">
                <div class="px-5 py-4 rounded shadow-sm h-100" style="background-color: #c3e0c4;">
                    <%-- セクションタイトル --%>
                    <h5 class="mb-3 h4">成績管理</h5>
                    <%-- 成績登録リンク --%>
                    <div class="d-block mb-3 h4"><a href="${pageContext.request.contextPath}/score/GRMU001">成績登録</a></div>
                    <%-- 成績参照リンク --%>
                    <div class="d-block mb-3 h4"><a href="${pageContext.request.contextPath}/score/GRMR001">成績参照</a></div>
                </div>
            </div>

            <%-- 科目管理へのリンクボックス --%>
            <div class="col-lg-4 mb-4">
                <div class="px-5 py-4 rounded shadow-sm h-100 d-flex align-items-center justify-content-center" style="background-color: #c2c2e0;">
                    <%-- 科目管理画面へのナビゲーションリンク --%>
                    <h5 class="mb-0 h4">
                        <a href="${pageContext.request.contextPath}/subject/list">科目管理</a>
                    </h5>
                </div>
            </div>

        </div>
      </div>
    </c:param>

</c:import>
