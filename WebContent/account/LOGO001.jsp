<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">

    <c:param name="title">得点管理システム</c:param>

    <c:param name="body">
        <%-- 変更点: 上の余白を mt-5 から mt-3 に変更しました --%>
        <div class="col-md-7 mx-auto mt-3">

            <h2 class="p-3 mb-3 bg-light rounded h4">ログアウト</h2>

            <p class="alert text-center mb-4 py-2" role="alert" style="background-color: #8ab79a; border-color: #7fa98b;">
                ログアウトしました
            </p>

            <a href="${pageContext.request.contextPath}/account/LOGI001.jsp">ログイン</a>

        </div>
    </c:param>

</c:import>