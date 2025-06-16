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
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/score/subject" })
public class TestRegistController extends CommonServlet {

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

		// DAOの準備
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();
		TestDao testDao = new TestDao();

		// 検索条件をリクエストパラメータから取得
		String entYearStr = req.getParameter("f1");
		String classNumStr = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");
		String testCountStr = req.getParameter("f4");

		// 検索ボタンが押されたかを判断
		boolean isSearchRequest = entYearStr != null && !entYearStr.isEmpty() && classNumStr != null
				&& !classNumStr.isEmpty() && subjectCd != null && !subjectCd.isEmpty() && testCountStr != null
				&& !testCountStr.isEmpty();

		// 検索結果を格納するリスト
		List<Test> searchResults = null;

		// 検索ボタンが押されていた場合の処理
		if (isSearchRequest) {
			System.out.println("検索処理を実行します。"); // 動作確認用ログ

			// 検索条件を適切な型に変換
			int entYear = Integer.parseInt(entYearStr);
			int testNo = Integer.parseInt(testCountStr);
			Subject selectedSubject = subjectDao.get(subjectCd, school);
			searchResults = new ArrayList<>();

			if (selectedSubject != null) {
				searchResults = testDao.filter(entYear, classNumStr, selectedSubject, testNo, school);

				// JSP表示用に選択された科目名と回数をセット
				req.setAttribute("selectedSubjectName", selectedSubject.getName());
				req.setAttribute("selectedCount", testNo);
			}
		}

		// session_userが所属している学校のクラスを取得
		List<String> classList = classNumDao.filter(school);
		// StringリストをClassNumオブジェクトのリストに変換
		List<ClassNum> classNumList = new ArrayList<>();
		for (String classNumStrs : classList) {
			ClassNum classNum = new ClassNum();
			classNum.setClass_num(classNumStrs);
			classNumList.add(classNum);
		}

		// session_userが所属している学校の科目を取得
		List<Subject> subjectList = subjectDao.filter(school);

		// session_userが所属している学校の生徒を取得
		List<Student> studentList = studentDao.filter(school, true);

		// JSPで使用するためにリクエストに設定
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);
		req.setAttribute("studentList", studentList);

		// 検索結果をリクエストに設定 (検索した場合のみ中身が入る)
		req.setAttribute("searchResults", searchResults);

		// 検索後のフォームの選択状態を維持するために、選択された値をリクエストに設定
		req.setAttribute("f1_selected", entYearStr);
		req.setAttribute("f2_selected", classNumStr);
		req.setAttribute("f3_selected", subjectCd);
		req.setAttribute("f4_selected", testCountStr);

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
