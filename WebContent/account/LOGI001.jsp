<%@page contentType="text/html; charset=UTF-8"%>
<%@page errorPage="ERRO001.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">得点管理システム</c:param>

<c:param name="link">${pageContext.request.contextPath}/css/LOGI001.css</c:param>

<c:param name="body">

  <%-- メインコンテンツ: ログインフォーム --%>
  <section>
    <h2>ログイン</h2>

    <form action="loginexecute" method="post">

      <c:if test="${error != null}">
        <p>ログインに失敗しました。IDまたはパスワードが正しくありません</p>
      </c:if>

      <%-- ユーザーID入力欄 --%>
      <div>
        <input type="text" name="id" value="" maxlength="20" style="ime-mode: disabled;" placeholder="半角でご入力ください" required>
      </div>

      <%-- パスワード入力欄 --%>
      <div>
        <input type="password" name="password" id="password" value="" maxlength="20" style="ime-mode: disabled;" placeholder="20文字以内の半角英数字でご入力ください" required>
      </div>

      <%-- パスワード表示切替チェックボックス --%>
      <div>
        <input type="checkbox" name="chk_d_ps" id="chk_d_ps">
        <label for="chk_d_ps">パスワードを表示</label>
      </div>

      <%-- ログイン送信ボタン --%>
      <div>
        <input type="submit" name="login" value="ログイン">
      </div>
    </form>
  </section>

  <%-- チェックボックスでパスワード表示モードを切り替えるスクリプト --%>
  <script>
    document.getElementById('chk_d_ps').addEventListener('change', function(){
      var pwd = document.getElementById('password');
      pwd.type = this.checked ? 'text' : 'password';
    });
  </script>

</c:param>

</c:import>