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
		// 現在のセッションを取得（存在しない場合は新規作成）
		HttpSession session = req.getSession();
		// Teacherオブジェクトを取得
		teacher = (Teacher) session.getAttribute("session_user");

		// teacherがnullの場合はログイン画面にリダイレクト
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		// DAOの準備
		StudentDao studentDao = new StudentDao();
		TestListStudentDao testListStudentDao = new TestListStudentDao();

		// 値を取得
		String studentNo = req.getParameter("f4");

		//入力値の検証
		if (studentNo == null || studentNo.isEmpty()) {
			req.setAttribute("error_student", "学生番号を入力してください。");
			// エラーがあっても検索画面は表示し続けるため、GRMR001.jspにフォワード
			req.getRequestDispatcher("/score/GRMR001.jsp").forward(req, resp);
			return;
		}

		// 学生情報を取得
		Student student = studentDao.get(studentNo);

		//学生の存在チェック
		if (student == null) {
			req.setAttribute("error_student", "指定された学生番号の学生は存在しません。");
			req.setAttribute("f4", studentNo); // 入力された番号をフォームに保持
			req.getRequestDispatcher("/score/GRMR001.jsp").forward(req, resp);
			return;
		}

		// 受け取った学生情報から検索を実行
		List<TestListStudent> testListStudent = testListStudentDao.filter(student);

		// 検索結果をリクエスト属性にセット
		req.setAttribute("student", student);
		req.setAttribute("testListStudent", testListStudent);
		req.setAttribute("f4", studentNo);

		// フォワード
		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		get(req, resp);

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}

}
