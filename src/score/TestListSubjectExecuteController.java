package score;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/score/testlistsubject" })
public class TestListSubjectExecuteController extends CommonServlet {

	private Teacher teacher;
	private School school;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログイン確認とTeacherインスタンスからschoolを受け取る
		this.execute(req, resp);

		SubjectDao subjectDao = new SubjectDao();

		// サーブレットから値を受け取る
		int entYear = (Integer)req.getAttribute("entYear");
		String classNum = (String)req.getAttribute("classNum");
	    String subjectCd = (String)req.getAttribute("subjectCd");
	    Subject subject = subjectDao.get(subjectCd, school);

		// テスト用
		System.out.println("entYear = " + req.getAttribute("entYear"));  // nullならNG
		System.out.println("classNum = " + req.getAttribute("classNum"));
		System.out.println("subjectCd = " + req.getAttribute("subjectCd"));


		// 受け取った検索条件を使って検索を実行
		TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
		List<TestListSubject> testListSubject = testListSubjectDao.filter(entYear, classNum, subject, school);

		// 検索結果が入ったリストを渡す
		req.setAttribute("testListSubject",testListSubject);

		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 現在のセッションを取得（存在しない場合は新規作成）
		HttpSession session = req.getSession();
		// Teacherオブジェクトを取得
		teacher = (Teacher) session.getAttribute("session_user");

		// teacherがnullの場合はログイン画面にリダイレクト
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		// 所属している学校をTeacherオブジェクトから取得
		school = teacher.getSchool();

	}

}
