package score;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Teacher;
import bean.Test;
import dao.TestDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/score/testexecute" })
public class TestRegistExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {

		HttpSession session = req.getSession();
		TestDao testDao = new TestDao();

		// Teacherオブジェクトを取得（認証チェック）
		Teacher teacher = (Teacher) session.getAttribute("session_user");
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		// 処理対象のTestオブジェクトを格納する単一のリスト
		List<Test> testsToProcess = new ArrayList<>();

		// エラーメッセージとユーザーの入力値を保持するMap
		Map<String, String> errors = new HashMap<>();
		Map<String, String> inputValues = new HashMap<>();

		// セッションから検索条件や学校情報を取得
		School school = teacher.getSchool();
		String subjectCd = (String) session.getAttribute("f3_selected");
		int testNo = Integer.parseInt((String) session.getAttribute("f4_selected"));

		// セッションから画面に表示されていた検索結果リストを取得
		@SuppressWarnings("unchecked")
		List<Test> searchResults = (List<Test>) session.getAttribute("searchResults");

		// 削除対象としてチェックされた学生番号のリストを取得し、高速検索のためにSetに変換
		String[] deleteStudentNos = req.getParameterValues("delete_students");
		Set<String> deleteSet = new HashSet<>();
		if (deleteStudentNos != null) {
			deleteSet.addAll(Arrays.asList(deleteStudentNos));
		}

		// 画面に表示されていた全学生をループして、入力値をチェック
		if (searchResults != null) {
			for (Test displayedTest : searchResults) {
				String studentNo = displayedTest.getStudent().getNo();

				// "point_学籍番号" というname属性を持つ入力フィールドの値を取得
				String pointStr = req.getParameter("point_" + studentNo);

				// エラー時の再表示のために、入力された値を保持
				inputValues.put(studentNo, pointStr);

				// 処理対象かどうかを判定するフラグ
				boolean isTarget = false;

				// DAOに渡すための新しいTestオブジェクトを生成
				Test test = new Test();

				// 【判断ロジック】
				// 優先順位1: 削除チェックボックスがONの場合
				if (deleteSet.contains(studentNo)) {
					test.setToDelete(true); // Test Beanの削除フラグを立てる
					isTarget = true;

				// 優先順位2: 点数が入力されている場合（削除チェックされていない場合のみ）
				} else if (pointStr != null && !pointStr.isEmpty()) {
					try {
						int point = Integer.parseInt(pointStr);
						if (point < 0 || point > 100) {
							// 0-100の範囲外の場合、エラーメッセージをセット
							errors.put(studentNo, "0～100の数値を入力してください。");
						} else {
							// 正常な場合、点数をセット
							test.setPoint(point);
							isTarget = true;
						}
					} catch (NumberFormatException e) {
						// そもそも数値でない場合、エラーメッセージをセット
						errors.put(studentNo, "数値を入力してください。");
					}
				}

				// 処理対象（削除 or 点数入力あり）の場合、リストに追加
				if (isTarget) {
					// どのTestオブジェクトにも共通の情報をセット
					Student student = new Student();
					student.setNo(studentNo);
					test.setStudent(student);

					// クラス番号をセット（元の表示データから取得）
					test.setClassNum(displayedTest.getClassNum());

					Subject subject = new Subject();
					subject.setCd(subjectCd);
					test.setSubject(subject);

					test.setSchool(school);
					test.setNo(testNo);

					// 処理リストに追加
					testsToProcess.add(test);
				}
			}
		}

		// バリデーション結果による分岐
		if (!errors.isEmpty()) {
			// エラーが1つでもあった場合

			// エラー情報と入力値をリクエストスコープにセット
			req.setAttribute("errors", errors);
			req.setAttribute("inputValues", inputValues);

			// 元の画面を再表示するために必要なデータをリクエストスコープに戻す
			req.setAttribute("searchResults", session.getAttribute("searchResults"));
			req.setAttribute("selectedSubjectName", session.getAttribute("selectedSubjectName"));
			req.setAttribute("selectedCount", session.getAttribute("selectedCount"));
			// 検索フォームの選択状態を維持するためのデータも戻す
			req.setAttribute("classNumList", session.getAttribute("classNumList"));
			req.setAttribute("subjectList", session.getAttribute("subjectList"));
			req.setAttribute("f1_selected", session.getAttribute("f1_selected"));
			req.setAttribute("f2_selected", session.getAttribute("f2_selected"));
			req.setAttribute("f3_selected", subjectCd);
			req.setAttribute("f4_selected", String.valueOf(testNo));

			// 元のJSPにフォワード
			req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);

		} else {
			// エラーがなかった場合
			try {
				// DAOのsaveメソッドに処理リストを渡して、DBに保存（登録・更新・削除）
				testDao.save(testsToProcess);

				// 完了画面にリダイレクト
				resp.sendRedirect("GRMU002.jsp");

			} catch (Exception e) {
				// データベース処理でエラーが発生した場合
				e.printStackTrace(); // サーバーのコンソールにエラー詳細を出力
				req.setAttribute("error_message", "データベース処理中にエラーが発生しました。");

				// エラーメッセージを持って元の画面に戻る（フォワード処理は上記エラー時と同じ）
				req.setAttribute("searchResults", session.getAttribute("searchResults"));
				req.setAttribute("selectedSubjectName", session.getAttribute("selectedSubjectName"));
				// ... (以下、再表示に必要なデータをセット)
				req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);
			}
		}
	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
