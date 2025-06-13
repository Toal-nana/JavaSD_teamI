package account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;

@WebServlet(urlPatterns = { "/account/logout" })
public class LogoutController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// セッションを取得（存在しない場合は新規作成される）
        HttpSession session = req.getSession();

        // セッションからユーザー情報を削除
        session.removeAttribute("session_user");

        // ログアウト完了画面にフォワード（サーバー内でページ遷移）
        req.getRequestDispatcher("LOGO001.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
