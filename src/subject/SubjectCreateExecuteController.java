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

	            String cd = req.getParameter("cd");
	            String name = req.getParameter("name");
	            School school = teacher.getSchool();

	            // フォームから送信された値でSubjectインスタンスを作成
	            Subject subject = new Subject();
	            subject.setCd(cd);
	            subject.setName(name);
	            subject.setSchool(school);

	            Subject subject2 = subjectDao.get(cd, school);

	            if (subject2 == null && cd.length() == 3) {
	            	 // DAOのsaveメソッドでDBに保存
		            subjectDao.save(subject);

		            // 完了画面にフォワード
		            req.getRequestDispatcher("/subject/SBJM003.jsp").forward(req, resp);
				} else {
					req.setAttribute("subject", subject);

					if (subject2 != null) {
						req.setAttribute("error", "科目コードが重複しています");
					} else {
						req.setAttribute("error", "科目コードは3文字で入力してください");
					}
					req.getRequestDispatcher("SBJM002.jsp").forward(req, resp);
				}

	        } catch (Exception e) {
	            throw new ServletException(e);
	        }
	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
