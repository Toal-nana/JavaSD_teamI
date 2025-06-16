<%@page contentType="text/html; charset=UTF-8"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<c:import url="/base.jsp">

    <c:param name="title">得点管理システム</c:param>

    <c:param name="body">

        <%-- メインコンテンツを中央寄せするコンテナ --%>
        <div class="col-md-7 mx-auto mt-3">

            <%-- 見出し: ログアウト完了を示すタイトル --%>
            <h2 class="p-3 mb-3 bg-light">ログアウト</h2>

            <%-- 成功メッセージ: ログアウト完了をユーザーに通知 --%>
            <p class="text-center mb-4 py-2" style="background-color: #8ab79a; border-color: #7fa98b;">
                ログアウトしました
            </p>

            <%-- ログインページへのリンク: 再ログインを促す --%>
            <a href="${pageContext.request.contextPath}/account/LOGI001.jsp">ログイン</a>

        </div>
    </c:param>

</c:import>
