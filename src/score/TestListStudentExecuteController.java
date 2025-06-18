package score;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import bean.TestListStudent;
import dao.StudentDao;
import dao.TestListStudentDao;
import tool.CommonServlet;

public class TestListStudentExecuteController extends CommonServlet {

	private Teacher teacher;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログイン確認とTeacherインスタンスからschoolを受け取る
		this.execute(req, resp);

		//DAOの準備
		StudentDao studentDao = new StudentDao();
		TestListStudentDao testListStudentDao = new TestListStudentDao();

		//値を取得
		String studentNo = req.getParameter("studentNo");

		//学生情報を取得
		Student student = studentDao.get(studentNo);

		//受け取った学生情報から検索を実行
		List<TestListStudent> testListStudent = testListStudentDao.filter(student);

		//検索結果が入ったリストを渡す
		req.setAttribute("testListStudent",testListStudent);

		//フォワード
		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

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

	}

}
