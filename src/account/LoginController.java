package account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

/**
 * ログイン画面の表示を行うコントローラクラス
 */
@WebServlet(urlPatterns = { "/account/login" })
public class LoginController extends CommonServlet {

    /**
     * HTTP GET リクエストを受け取ったときの処理
     * ログイン画面（LOGI001.jsp）をフォワードで表示する
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // ログイン画面に遷移
        req.getRequestDispatcher("LOGI001.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}
