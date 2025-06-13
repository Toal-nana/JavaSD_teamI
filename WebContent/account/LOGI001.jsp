<%@page contentType="text/html; charset=UTF-8"%>
<%@page errorPage="ERRO001.jsp" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:import url="/base.jsp">

<c:param name="title">得点管理システム</c:param>

<c:param name="body">

  <%-- メインコンテンツ: ログインフォーム --%>
  <div class="col-lg-6 col-md-8 mx-auto my-3">

    <%-- カードコンポーネントでフォーム全体をデザイン --%>
    <div class="card shadow-sm border-light">

      <%-- カードヘッダー: ログインタイトル --%>
      <div class="card-header bg-light text-center h2 py-3">
        ログイン
      </div>

      <%-- カードボディ --%>
      <div class="card-body p-4">
        <form action="loginexecute" method="post">

          <c:if test="${empty error}">
            <div class="text-center mt-0 mb-3">
              ログインに失敗しました。IDまたはパスワードが正しくありません
            </div>
          </c:if>

          <%-- ユーザーID入力欄 (フローティングラベル) --%>
          <div class="form-floating mb-3">
            <input type="text" name="id" id="id" class="form-control" value="" placeholder="半角でご入力ください" maxlength="20" style="ime-mode: disabled; background-color: #e6f2ff;" required>
            <label for="id">ID</label>
          </div>

          <%-- パスワード入力欄 (フローティングラベル) --%>
          <div class="form-floating mb-3">
            <input type="password" name="password" id="password" class="form-control" value="" placeholder="20文字以内の半角英数字でご入力ください" maxlength="20" style="ime-mode: disabled; background-color: #e6f2ff;" required>
            <label for="password">パスワード</label>
          </div>

          <div class="form-check d-flex justify-content-center mb-4">
            <div>
              <input type="checkbox" name="chk_d_ps" id="chk_d_ps" class="form-check-input">
              <label for="chk_d_ps" class="form-check-label">パスワードを表示</label>
            </div>
          </div>

          <%-- ログイン送信ボタン --%>
          <div class="text-center">
            <button type="submit" name="login" class="btn btn-primary btn-lg fw-bold px-5">ログイン</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <%-- チェックボックスでパスワード表示モードを切り替えるスクリプト --%>
  <script>
    document.getElementById('chk_d_ps').addEventListener('change', function(){
      var pwd = document.getElementById('password');
      pwd.type = this.checked ? 'text' : 'password';
    });
  </script>

</c:param>

</c:import>