package subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/subject/createexecute")
public class SubjectCreateExecuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect(req.getContextPath() + "/subject/create");
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // ログインチェック
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login");
            return;
        }

        req.setCharacterEncoding("UTF-8");

        String cd = req.getParameter("cd");
        String name = req.getParameter("name");
        School school = teacher.getSchool();

        // フォームの入力値を保持するインスタンスを作成
        Subject subject = new Subject();
        subject.setCd(cd);
        subject.setName(name);
        subject.setSchool(school);

        // エラーチェック用のフラグ
        boolean hasError = false;

        // 科目コードの文字数チェック (3文字以外はエラー)
        if (cd == null || cd.length() != 3) {
            req.setAttribute("cd_error", "科目コードは3文字で入力してください");
            hasError = true;
        }

        // 科目名の文字数チェック (20文字より多い場合はエラー)
        if (name != null && name.length() > 20) {
            req.setAttribute("name_error", "20文字以内で入力してください");
            hasError = true;
        }

        // 科目コードの重複チェック（文字数エラーがない場合のみ実行）
        if (!hasError) {
            try {
                SubjectDao subjectDao = new SubjectDao();
                Subject existingSubject = subjectDao.get(cd, school);
                if (existingSubject != null) {
                    req.setAttribute("cd_error", "科目コードが重複しています");
                    hasError = true;
                }
            } catch (Exception e) {
                // DB関連のエラーは別途処理
                throw new ServletException(e);
            }
        }

        // エラーが一つでもあった場合
        if (hasError) {
            // 入力値をリクエストスコープにセットして、登録画面に戻す
            req.setAttribute("subject", subject);
            req.getRequestDispatcher("SBJM002.jsp").forward(req, resp);
            return;
        }


        // エラーがなかった場合、登録処理を実行
        try {
            SubjectDao subjectDao = new SubjectDao();
            subjectDao.save(subject);

            // 完了画面にフォワード
            req.getRequestDispatcher("/subject/SBJM003.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
    }
}