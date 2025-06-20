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

@WebServlet(urlPatterns = { "/score/test" })
public class TestRegistController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// ログイン状態の確認と基本情報の取得
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("session_user");

		//認証チェック
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		School school = teacher.getSchool();

		// DAOの準備
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();
		TestDao testDao = new TestDao();

		// フォームから送信された検索条件を取得
		String entYearStr = req.getParameter("f1");
		String classNumStr = req.getParameter("f2");
		String subjectCd = req.getParameter("f3");
		String testCountStr = req.getParameter("f4");

		// JSPに追加した隠しフィールドの値を取得
		String searchFlag = req.getParameter("search");

		List<Test> searchResults = null;

		// 「フォームが送信されたか(searchFlagがnullでないか)」で処理を分岐
		if (searchFlag != null) {

			// 検索ボタンが押された場合
			// すべての項目が入力されているかチェック (空文字チェック)
			boolean isAllFieldsFilled = !entYearStr.isEmpty() && !classNumStr.isEmpty() && !subjectCd.isEmpty()
					&& !testCountStr.isEmpty();

			if (isAllFieldsFilled) {
				// 全項目入力済みの場合、DB検索を実行
				System.out.println("検索処理を実行します。");

				int entYear = Integer.parseInt(entYearStr);
				int testNo = Integer.parseInt(testCountStr);
				Subject selectedSubject = subjectDao.get(subjectCd, school);

				searchResults = new ArrayList<>();

				if (selectedSubject != null) {

					searchResults = testDao.filter(entYear, classNumStr, selectedSubject, testNo, school);
					req.setAttribute("selectedSubjectName", selectedSubject.getName());
					req.setAttribute("selectedCount", testNo);

					session.setAttribute("f1_selected", entYearStr);
					session.setAttribute("f2_selected", classNumStr);
					session.setAttribute("f3_selected", subjectCd);
					session.setAttribute("f4_selected", testCountStr);
					session.setAttribute("searchResults", searchResults);
					session.setAttribute("selectedSubjectName", selectedSubject.getName());
					session.setAttribute("selectedCount", testNo);

				}
			} else {
				//一つでも選択項目が欠けていた場合、エラーを表示
				req.setAttribute("error_message", "入学年度とクラスと科目と回数を選択してください");
			}
		} else {
			// 初回アクセスの場合のセッションクリア処理
			session.removeAttribute("f1_selected");
			session.removeAttribute("f2_selected");
			session.removeAttribute("f3_selected");
			session.removeAttribute("f4_selected");
			session.removeAttribute("searchResults");
			session.removeAttribute("selectedSubjectName");
			session.removeAttribute("selectedCount");
		}

		// JSP表示に必要なドロップダウンリストのデータを準備
		List<String> classList = classNumDao.filter(school);
		List<ClassNum> classNumList = new ArrayList<>();

		for (String classNumStrs : classList) {
			ClassNum classNum = new ClassNum();
			classNum.setClass_num(classNumStrs);
			classNumList.add(classNum);
		}

		List<Subject> subjectList = subjectDao.filter(school);
		List<Student> studentList = studentDao.filter(school, true);

		// JSPへ渡すデータをリクエストスコープにセット
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);
		req.setAttribute("studentList", studentList);

		req.setAttribute("searchResults", searchResults);

		req.setAttribute("f1_selected", entYearStr);
		req.setAttribute("f2_selected", classNumStr);
		req.setAttribute("f3_selected", subjectCd);
		req.setAttribute("f4_selected", testCountStr);

		// JSPへフォワード
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
