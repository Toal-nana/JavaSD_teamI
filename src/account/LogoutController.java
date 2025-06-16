package account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.CommonServlet;

/**
 * ログアウト処理を行うコントローラクラス
 * セッション上のユーザー情報を削除し、ログアウト完了画面へ遷移する
 */
@WebServlet(urlPatterns = { "/account/logout" })
public class LogoutController extends CommonServlet {

    /**
     * HTTP GET リクエストを受け取ったときの処理
     * セッションからユーザー情報を削除し、ログアウト完了画面を表示する
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // セッションを取得（存在しない場合は新規作成される）
        HttpSession session = req.getSession();

        // セッションからユーザー情報を削除
        session.removeAttribute("session_user");

        // ログアウト完了画面にフォワード（サーバ内部でのページ遷移）
        req.getRequestDispatcher("LOGO001.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

}
