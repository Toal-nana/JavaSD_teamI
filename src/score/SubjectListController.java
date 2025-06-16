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

@WebServlet(urlPatterns = { "/score/subject" })
public class SubjectListController extends CommonServlet {

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

		// session_userが所属している学校のクラスを取得
		ClassNumDao classNumDao = new ClassNumDao();
		List<String> classList = classNumDao.filter(school);

		// StringリストをClassNumオブジェクトのリストに変換
		List<ClassNum> classNumList = new ArrayList<>();
		for (String classNumStr : classList) {
		    ClassNum classNum = new ClassNum();
		    classNum.setClass_num(classNumStr);
		    classNumList.add(classNum);
		}

		// session_userが所属している学校の科目を取得
		SubjectDao subjectDao = new SubjectDao();
		List<Subject> subjectList = subjectDao.filter(school);

//		session_userが所属している学校の生徒を取得
		StudentDao studentDao = new StudentDao();
		List<Student> studentList = studentDao.filter(school, true);

		// JSPで使用するためにリクエストに設定
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);
		req.setAttribute("studentList", studentList);

		// フォワードする
		req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);
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
