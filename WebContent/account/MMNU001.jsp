<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">

    <c:param name="title">得点管理システム</c:param>

    <c:param name="body">
        <h2 class="p-3 mb-4 bg-light rounded">メニュー</h2>

        <div class="row text-center">

            <%-- 1. 学生管理ボックス --%>
            <div class="col-lg-4 mb-4">
                <%-- 変更点: d-flex, align-items-center, justify-content-center を追加して中身を中央揃えに --%>
                <div class="p-5 rounded shadow-sm h-100 d-flex align-items-center justify-content-center" style="background-color: #d9baba;">
                    <%-- 変更点: クラスに h4 を追加して文字サイズを大きくする --%>
                    <h5 class="mb-0 h4">
                        <a href="">学生管理</a>
                    </h5>
                </div>
            </div>

            <%-- 2. 成績管理ボックス --%>
            <div class="col-lg-4 mb-4">
                <%-- こちらは複数の要素があるため、中央揃えのクラスは追加しません --%>
                <div class="p-5 rounded shadow-sm h-100" style="background-color: #c3e0c4;">
                    <%-- 変更点: クラスに h4 を追加して文字サイズを大きくする --%>
                    <h5 class="mb-3 h4">成績管理</h5>
                    <div class="d-block mb-3 h4"><a href="">成績登録</a></div>
                    <div class="d-block mb-3 h4"><a href="">成績参照</a></div>
                </div>
            </div>

            <%-- 3. 科目管理ボックス --%>
            <div class="col-lg-4 mb-4">
                <%-- 変更点: d-flex, align-items-center, justify-content-center を追加して中身を中央揃えに --%>
                <div class="p-5 rounded shadow-sm h-100 d-flex align-items-center justify-content-center" style="background-color: #c2c2e0;">
                    <%-- 変更点: クラスに h4 を追加して文字サイズを大きくする --%>
                    <h5 class="mb-0 h4">
                        <a href="">科目管理</a>
                    </h5>
                </div>
            </div>

        </div>
    </c:param>

</c:import>