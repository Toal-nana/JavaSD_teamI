package score;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.ClassNum;
import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.TestListSubject;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListSubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/score/testlistsubject" })
public class TestListSubjectExecuteController extends CommonServlet {

	private Teacher teacher;
	private School school;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログイン確認とTeacherインスタンスからschoolを受け取る
		this.execute(req, resp);

		SubjectDao subjectDao = new SubjectDao();

		// サーブレットから値を受け取る
		String entYearStr = (String)req.getAttribute("entYear");
		int entYear = Integer.parseInt(entYearStr);
		String classNum = (String)req.getAttribute("classNum");
		String subjectCd = (String)req.getAttribute("subjectCd");
		Subject subject = subjectDao.get(subjectCd, school);

		// 受け取った検索条件を使って検索を実行
		TestListSubjectDao testListSubjectDao = new TestListSubjectDao();
		List<TestListSubject> testListSubject = testListSubjectDao.filter(entYear, classNum, subject, school);

		// 検索結果が入ったリストを渡す
		req.setAttribute("testListSubject",testListSubject);


		// ドロップダウン保存用の処理
		// 入学年度一覧を受け取る ログインしている先生の学校コードを入れる
		StudentDao studentDao = new StudentDao();
		List<Student> studentList=studentDao.filter(school, true);
		// クラス一覧を受け取る
		ClassNumDao classNumDao = new ClassNumDao();

		List<Subject> subjectList = subjectDao.filter(school);
		List<String> classList = classNumDao.filter(school);

		// StringリストをClassNumオブジェクトのリストに変換
		List<ClassNum> classNumList = new ArrayList<>();
		for (String classNumStrs : classList) {
			ClassNum classNumForPullDown = new ClassNum();
			classNumForPullDown.setClass_num(classNumStrs);
			classNumList.add(classNumForPullDown);
		}

		// 受け取った一覧をjspに渡す
		req.setAttribute("studentList", studentList);
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);

		// 選択された値を送る
		req.setAttribute("entYear", entYear);
		req.setAttribute("classNum", classNum);
		req.setAttribute("subject", subject);

		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		get(req, resp);
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
