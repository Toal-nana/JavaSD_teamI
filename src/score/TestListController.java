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
import dao.ClassNumDao;
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
		Teacher teacher = (Teacher) session.getAttribute("session_user");

		// teacherがnullの場合はログイン画面にリダイレクト
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		// 所属している学校をTeacherオブジェクトから取得
		School school = teacher.getSchool();

		// 入学年度一覧を受け取る ログインしている先生の学校コードを入れる
		StudentDao studentDao = new StudentDao();
		List<Student> studentList=studentDao.filter(school, true);
		// クラス一覧を受け取る
		ClassNumDao classNumDao = new ClassNumDao();
		// 科目一覧を受け取る
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(school);


		List<String> classList = classNumDao.filter(school);
		// StringリストをClassNumオブジェクトのリストに変換
		List<ClassNum> classNumList = new ArrayList<>();
		for (String classNumStrs : classList) {
			ClassNum classNum = new ClassNum();
			classNum.setClass_num(classNumStrs);
			classNumList.add(classNum);
		}

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
