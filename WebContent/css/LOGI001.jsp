<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">

    <c:param name="title">得点管理システム</c:param>

    <c:param name="body">
        <div class="col-md-6 mx-auto mt-5">

            <h2 class="p-3 mb-3 bg-light rounded h4">ログアウト</h2>

            <%-- 「ログアウトしました」のメッセージ --%>
            <%-- 変更点：py-2 を追加して、ボックス内の上下の余白（パディング）を小さくしました --%>
            <p class="alert text-center mb-4 py-2" role="alert" style="background-color: #8ab79a; border-color: #7fa98b;">
                ログアウトしました
            </p>

            <br>

            <%-- ログイン画面へのリンク --%>
            <a href="${pageContext.request.contextPath}/account/LOGI001.jsp">ログイン</a>

        </div>
    </c:param>

</c:import>