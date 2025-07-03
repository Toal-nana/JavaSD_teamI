package subject;

import java.util.List;

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

@WebServlet("/subject/updateexecute")
public class SubjectUpdateExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

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

        try {
            SubjectDao subjectDao = new SubjectDao();
            String name = req.getParameter("name");
            School school = teacher.getSchool();

            // フォームから送信された値でSubjectインスタンスを作成
            Subject subject = new Subject();
            subject.setCd(req.getParameter("cd"));
            subject.setName(name);
            subject.setSchool(school);

            List<Subject> list = subjectDao.filter(school);

            for (Subject s : list) {
				if (name != null && name.equals(s.getName())) {

					// DAOのsaveメソッドでDBを更新
		            subjectDao.save(subject);

		            req.getRequestDispatcher("/subject/SBJM005.jsp").forward(req, resp);
				} else {
					req.setAttribute("subject", subject);
					req.setAttribute("error", "科目が存在していません");
					req.getRequestDispatcher("SBJM004.jsp").forward(req, resp);
				}
			}

            // DAOのsaveメソッドでDBを更新
            subjectDao.save(subject);

            req.getRequestDispatcher("/subject/SBJM005.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
