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

@WebServlet("/subject/delete")
public class SubjectDeleteController extends CommonServlet {

    @Override
    protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
        HttpSession session = req.getSession();
        // Teacherオブジェクトとしてユーザー情報を取得
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // ログインチェック
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/account/login");
            return;
        }

        try {
            SubjectDao subjectDao = new SubjectDao();
            String cd = req.getParameter("cd");
            // ログインユーザー情報からSchoolを取得
            School school = teacher.getSchool();

            // 削除対象の科目情報を取得
            Subject subjectToDelete = subjectDao.get(cd, school);

            // JSPに渡すためにリクエストスコープにセット
            req.setAttribute("subject", subjectToDelete);

            req.getRequestDispatcher("/subject/SBJM006.jsp").forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }
    }

    @Override
    protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }

    @Override
    protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

    }
}