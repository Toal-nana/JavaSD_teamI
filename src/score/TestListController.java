package score;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import dao.ClassNumDao;
import dao.SchoolDao;
import dao.StudentDao;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/score/testlist" })
public class TestListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 現在のセッションを取得（存在しない場合は新規作成）
		HttpSession session = req.getSession();
		// Teacherオブジェクトを取得
//		Teacher teacher = (Teacher) session.getAttribute("session_user");
		School school = new School();
		SchoolDao schoolDao = new SchoolDao();
		school = schoolDao.get("oom");


		// 入学年度一覧を受け取る ログインしている先生の学校コードを入れる
		StudentDao studentDao = new StudentDao();
		List<Student> studentList=studentDao.filter(school, true);
		// クラス一覧を受け取る
		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classNumList = classNumDao.filter(school);
		// 科目一覧を受け取る
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(school);

		// 受け取った一覧をjspに渡す
		req.setAttribute("studentList", studentList);
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);

		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);
	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
