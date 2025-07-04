package subject;

// Listのimportは不要
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
		resp.sendRedirect(req.getContextPath() + "/subject/list");
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("session_user");

		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		req.setCharacterEncoding("UTF-8");

		try {
			SubjectDao subjectDao = new SubjectDao();
			String cd = req.getParameter("cd");
			String name = req.getParameter("name");
			School school = teacher.getSchool();

			// 更新対象の科目がDBに存在するかをチェック
			Subject existingSubject = subjectDao.get(cd, school);

			// フォームからの入力値を保持するSubjectオブジェクト
			Subject subjectFromForm = new Subject();
			subjectFromForm.setCd(cd);
			subjectFromForm.setName(name);
			subjectFromForm.setSchool(school);

			// 存在チェックの結果で処理を分岐
			if (existingSubject == null) {
				// 存在しなかった場合（DBから削除された場合など）
				req.setAttribute("subject", subjectFromForm);
				req.setAttribute("error", "科目が存在しません");
				req.getRequestDispatcher("SBJM004.jsp").forward(req, resp);

			} else {
				// 存在した場合、そのまま更新処理を実行
				subjectDao.save(subjectFromForm);
				// 完了画面へ遷移
				req.getRequestDispatcher("/subject/SBJM005.jsp").forward(req, resp);
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