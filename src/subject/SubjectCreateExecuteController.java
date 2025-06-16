package subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/subject/CreateExecute")
public class SubjectCreateExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		 HttpSession session = req.getSession();
	        Teacher teacher = (Teacher) session.getAttribute("session_user");

	        // ログインチェック
	        if (teacher == null) {
	            resp.sendRedirect(req.getContextPath() + "/LOGI001.jsp");
	            return;
	        }

	        try {
	            SubjectDao subjectDao = new SubjectDao();

	            // フォームから送信された値でSubjectインスタンスを作成
	            Subject subject = new Subject();
	            subject.setCd(req.getParameter("cd"));
	            subject.setName(req.getParameter("name"));
	            subject.setSchool(teacher.getSchool());

	            // DAOのsaveメソッドでDBに保存
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
