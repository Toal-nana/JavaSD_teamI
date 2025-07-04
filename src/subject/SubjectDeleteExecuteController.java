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

@WebServlet("/subject/deleteexecute")
public class SubjectDeleteExecuteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        resp.sendRedirect(req.getContextPath() + "/subject/list");
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        // Teacherオブジェクトとしてセッションからユーザー情報を取得
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // ログインチェック
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login");
            return;
        }

        try {
            SubjectDao subjectDao = new SubjectDao();
            String cd = req.getParameter("cd");
            // ログインユーザーから学校情報を取得
            School school = teacher.getSchool();

            // フォームから送信されたIDと学校情報でSubjectインスタンスを作成
            Subject subjectToDelete = new Subject();
            subjectToDelete.setCd(cd);
            subjectToDelete.setSchool(school);

            // DAOのdeleteメソッドでDBから削除
            subjectDao.delete(subjectToDelete);

            req.getRequestDispatcher("/subject/SBJM007.jsp").forward(req, resp);
        } catch (Exception e) {
            // エラーログを出力するとデバッグが楽になります
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        // TODO 自動生成されたメソッド・スタブ
    }
}