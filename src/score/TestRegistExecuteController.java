package score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
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
import bean.Test;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/score/testexecute" })
public class TestRegistExecuteController extends CommonServlet {

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		// ログイン状態の確認と基本情報の取得
		HttpSession session = req.getSession();

		Teacher teacher = (Teacher) session.getAttribute("session_user");

		//認証チェック
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		School school = teacher.getSchool();

		// セッションから必須情報を取得
		String subjectCd = (String) session.getAttribute("f3_selected");
		String testNoStr = (String) session.getAttribute("f4_selected");

		@SuppressWarnings("unchecked")
		List<Test> searchResults = (List<Test>) session.getAttribute("searchResults");

		//必須情報がセッションにない場合はエラーとして扱う
		if (subjectCd == null || testNoStr == null || searchResults == null) {

			req.setAttribute("page_error", "セッションが切れました。再度検索からやり直してください。");

			//executeで画面遷移の処理
			this.execute(req, resp);

			return;
		}

		//入力値の解析とバリデーション

		//前画面から引き継いだテスト回数を文字型から整数型に変換
		int testNo = Integer.parseInt(testNoStr);

		//入力値のチェック中にエラーが発生した場合、どの学生でどんなエラーが発生したのかを保存する
		Map<String, String> errors = new HashMap<>();
		//エラー発生時に再表示する際、ユーザーの入力値も再表示できるよう保存する
		Map<String, String> inputValues = new HashMap<>();

		//DB更新の際に、入力値を一時的に保存しておく場所
		List<Test> testsToProcess = new ArrayList<>();

		//削除対象の学生かを判断し保存する
		String[] deleteStudentNos = req.getParameterValues("delete_students");
		Set<String> deleteSet = (deleteStudentNos != null) ? new HashSet<>(Arrays.asList(deleteStudentNos))
				: new HashSet<>();

		//画面に表示された学生情報を繰り返しで入手
		for (Test displayedTest : searchResults) {

			//学生番号とテスト点数を取り出す
			String studentNo = displayedTest.getStudent().getNo();
			String pointStr = req.getParameter("point_" + studentNo);

			//学生番号とテスト点数を格納
			inputValues.put(studentNo, pointStr);

			//削除対象の判断
			boolean isDeleteTarget = deleteSet.contains(studentNo);
			//点数入力欄の判断
			boolean hasPointInput = pointStr != null && !pointStr.isEmpty();

			if (isDeleteTarget || hasPointInput) {

				Test test = new Test();

				if (isDeleteTarget) {

					test.setToDelete(true);

				} else {

					//点数の入力値のエラー処理
					try {

						int point = Integer.parseInt(pointStr);

						//0～100の数値以外だった場合
						if (point < 0 || point > 100) {

							errors.put(studentNo, "0～100で入力してください。");

						} else {

							test.setPoint(point);

						}

						//数値以外だった場合
					} catch (NumberFormatException e) {

						errors.put(studentNo, "数値を入力してください。");

					}
				}

				//入力情報に問題が無かった場合、データベースに値を渡すための準備をする。
				if (!errors.containsKey(studentNo)) {

					//学生情報を型にセット
					Student student = new Student();
					student.setNo(studentNo);
					test.setStudent(student);
					test.setClassNum(displayedTest.getClassNum());

					//科目情報を型にセット
					Subject subject = new Subject();
					subject.setCd(subjectCd);

					//テスト情報を型にセット
					test.setSubject(subject);
					test.setSchool(school);
					test.setNo(testNo);

					//DB更新のための情報を保存
					testsToProcess.add(test);
				}
			}
		}

		// バリデーション結果に応じた処理分岐
		if (!errors.isEmpty()) {

			//バリデーションエラーがあった場合
			req.setAttribute("errors", errors);
			req.setAttribute("inputValues", inputValues);

			// 元の画面を再表示するために必要なデータをリクエストスコープにセット
			req.setAttribute("searchResults", session.getAttribute("searchResults"));
			req.setAttribute("selectedSubjectName", session.getAttribute("selectedSubjectName"));
			req.setAttribute("selectedCount", session.getAttribute("selectedCount"));
			req.setAttribute("f1_selected", session.getAttribute("f1_selected"));
			req.setAttribute("f2_selected", session.getAttribute("f2_selected"));
			req.setAttribute("f3_selected", session.getAttribute("f3_selected"));
			req.setAttribute("f4_selected", session.getAttribute("f4_selected"));

			//executeで画面遷移の処理
			this.execute(req, resp);

			return;

		} else {
			//エラーがなかった場合
			try {
				TestDao testDao = new TestDao();
				testDao.save(testsToProcess);

				// どのボタンが押されたかを取得
				String action = req.getParameter("action");

				if ("continue".equals(action)) {
					// 「再度入力」ボタンが押された場合
					// 成功メッセージをセッションにセット
		            session.setAttribute("success_message", "登録は正常に完了しました");

		         // 検索条件を維持したまま検索画面にリダイレクト
					String entYear = (String) session.getAttribute("f1_selected");
					String classNum = (String) session.getAttribute("f2_selected");
					String redirectUrl = String.format("test?search=true&f1=%s&f2=%s&f3=%s&f4=%s",
													   entYear, classNum, subjectCd, testNoStr);
					resp.sendRedirect(redirectUrl);

				} else {
					// 「登録して終了」ボタンが押された場合

					// 処理完了後に不要になったセッション情報をクリア
					session.removeAttribute("f1_selected");
					session.removeAttribute("f2_selected");
					session.removeAttribute("f3_selected");
					session.removeAttribute("f4_selected");
					session.removeAttribute("searchResults");
					session.removeAttribute("selectedSubjectName");
					session.removeAttribute("selectedCount");

					// 完了画面へリダイレクト
					resp.sendRedirect("GRMU002.jsp");
				}

			} catch (Exception e) {
				e.printStackTrace();
				// DBエラーが発生した場合
				req.setAttribute("page_error", "データベースの更新中にエラーが発生しました。");
				req.setAttribute("inputValues", inputValues);

				// エラー時も元の画面を再表示するためにデータをセット
				req.setAttribute("searchResults", session.getAttribute("searchResults"));
				req.setAttribute("selectedSubjectName", session.getAttribute("selectedSubjectName"));
				req.setAttribute("selectedCount", session.getAttribute("selectedCount"));
				req.setAttribute("f1_selected", session.getAttribute("f1_selected"));
				req.setAttribute("f2_selected", session.getAttribute("f2_selected"));
				req.setAttribute("f3_selected", session.getAttribute("f3_selected"));
				req.setAttribute("f4_selected", session.getAttribute("f4_selected"));

				//executeで画面遷移の処理
				this.execute(req, resp);

			}
		}
	}

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// GETリクエストで直接アクセスされた場合は成績管理トップへ
		resp.sendRedirect(req.getContextPath() + "/score/test");
	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// セッションからteacher情報を再取得するか、doPostから渡す必要がある
		HttpSession session = req.getSession();
		Teacher teacher = (Teacher) session.getAttribute("session_user");

		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		School school = teacher.getSchool();

		// DAOの準備
		ClassNumDao classNumDao = new ClassNumDao();
		SubjectDao subjectDao = new SubjectDao();
		StudentDao studentDao = new StudentDao();

		// クラス一覧の準備
		List<String> classList = classNumDao.filter(school);
		List<ClassNum> classNumList = new ArrayList<>();
		for (String classNumStr : classList) {
			ClassNum classNum = new ClassNum();
			classNum.setClass_num(classNumStr);
			classNumList.add(classNum);
		}

		// 科目一覧と学生一覧の準備
		List<Subject> subjectList = subjectDao.filter(school);
		List<Student> studentList = studentDao.filter(school, true);

		// 学生リストから入学年度を重複なく抽出しソートする
		List<Integer> entYearList = studentList.stream()
				.map(Student::getEntYear)
				.distinct()
				.sorted((y1, y2) -> y2.compareTo(y1)) // 降順ソート
				.collect(Collectors.toList());

		// JSPへ渡すデータをリクエストスコープにセット
		req.setAttribute("classNumList", classNumList);
		req.setAttribute("subjectList", subjectList);
		req.setAttribute("entYearList", entYearList);

		// JSPへフォワード
		req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);

	}

}
