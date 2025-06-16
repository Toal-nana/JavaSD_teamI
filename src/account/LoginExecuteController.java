package account;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.TeacherDao;
import tool.CommonServlet;

/**
 * ログイン処理を実行するコントローラクラス
 * フォームから送信されたID／パスワードを検証し、
 * 成功時はメニュー画面へ、失敗時はログイン画面へ遷移する
 */
@WebServlet(urlPatterns = { "/account/loginexecute" })
public class LoginExecuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    /**
     * HTTP POST リクエストを受け取ったときの処理
     * ログインフォームから送信された情報をもとに認証を行い、
     * 結果に応じて画面遷移またはリダイレクトを行う
     */
    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        // セッションを取得（存在しない場合は新規作成）
        HttpSession session = req.getSession();

        // フォームから送信されたパラメータを取得
        String id = req.getParameter("id");
        String password = req.getParameter("password");

        // DAO を利用して認証処理を実行
        TeacherDao dao = new TeacherDao();
        Teacher teacher = dao.login(id, password);

        if (teacher != null) {
            // 認証成功時：
            //  ・ユーザー情報をセッションに保存
            session.setAttribute("session_user", teacher);

            //  ・メニュー画面へリダイレクト（ブラウザに新規リクエストを発行させる）
            resp.sendRedirect(req.getContextPath() + "/account/menu");
        } else {
            // 認証失敗時：
            //  ・入力値を再表示用にリクエスト属性としてセット
            req.setAttribute("id", id);
            req.setAttribute("password", password);

            //  ・エラーメッセージをリクエスト属性に追加
            req.setAttribute("error", "ログインに失敗しました。IDまたはパスワードが正しくありません");

            //  ・ログイン画面へフォワードして再入力を促す
            req.getRequestDispatcher("LOGI001.jsp").forward(req, resp);
        }
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

}
