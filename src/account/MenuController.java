package account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import tool.CommonServlet;

/**
 * メニュー画面の表示を行うコントローラクラス
 * GET リクエストでメニュー画面（MMNU001.jsp）をフォワードし表示する
 */
@WebServlet(urlPatterns = { "/account/menu" })
public class MenuController extends CommonServlet {

    /**
     * HTTP GET リクエストを受け取ったときの処理
     * メニュー画面を表示するため、対応する JSP にフォワードする
     */
    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // メニュー画面に遷移
        req.getRequestDispatcher("MMNU001.jsp").forward(req, resp);
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

}
