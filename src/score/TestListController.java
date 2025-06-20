package score;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

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

// 成績参照検索画面
@WebServlet(urlPatterns = { "/score/testlist" })
public class TestListController extends CommonServlet {
	private Teacher teacher;
	private School school;

	// 画面表示に使う
	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログイン確認とTeacherインスタンスからschoolを受け取る
		this.execute(req, resp);

		// 入学年度一覧を受け取る ログインしている先生の学校コードを入れる
		StudentDao studentDao = new StudentDao();
		List<Student> studentList=studentDao.filter(school, true);

		// 学生リストから入学年度を重複なく抽出し、ソートする
		List<Integer> entYearList = studentList.stream().map(Student::getEntYear)
				.distinct()                    // 重複を除去する
			    .sorted()                      // 昇順にソートする
			    .collect(Collectors.toList()); // 結果をListに変換する

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
		req.setAttribute("entYearList", entYearList);
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);

		// 成績参照検索の画面に飛ぶ
		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);
	}


	// jspから受け取った値をTestListSubjectEcecuteControllerに送る
	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログイン確認とTeacherインスタンスからschoolを受け取る
		this.execute(req, resp);

		// 科目別、学生別どちらで検索したかの判定用
		String check = req.getParameter("f");

		// 科目別検索の時の処理
		if ("sj".equals(check)) {

			// jspから検索条件を受け取り、検索実行用のサーブレットにフォワード

			String entYear = req.getParameter("f1");
			String classNum = req.getParameter("f2");
			String subjectCd = req.getParameter("f3");


			// --------が三つのどこかに入っていたらGRMR001に戻り、エラー文の表示
			if (entYear.isEmpty() || classNum.isEmpty() || subjectCd.isEmpty()) {
	            req.setAttribute("sjError", "入学年度とクラスと科目を選択してください");

	            // JSPが「選択状態の保持」と「科目名表示」のために使う全ての値をセットする
	    		req.setAttribute("entYear", entYear);    	  // String型の入学年度
	    		req.setAttribute("classNum", classNum);       // String型のクラス番号
	    		req.setAttribute("subjectCd", subjectCd);     // String型の科目コード

	            this.get(req, resp);
				// 成績参照検索の画面に飛ぶ
				return;
			}



			// 選択された値を送る
			req.setAttribute("entYear", entYear);
			req.setAttribute("classNum", classNum);
			req.setAttribute("subjectCd", subjectCd);

			req.getRequestDispatcher("/score/testlistsubject").forward(req, resp);

			// 学生別検索の時の処理
		}else {
			req.getRequestDispatcher("/score/testliststudent").forward(req, resp);
		}
	}


	// get,postに共通する処理をまとめて書く (ログイン確認等)
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
