package score;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

		// Teacherオブジェクトを取得
		Teacher teacher = (Teacher) session.getAttribute("session_user");

		// teacherがnullの場合はログイン画面にリダイレクト
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		// エラーメッセージを格納するMap (キー:学生番号, 値:エラーメッセージ)
		Map<String, String> errors = new HashMap<>();
		// ユーザーの入力値を保持するMap (キー:学生番号, 値:入力された点数)
		Map<String, String> inputValues = new HashMap<>();

		List<Test> testsToSave = new ArrayList<>();

		// セッションから検索条件や学校情報を取得
		// これらはDBに保存する際にTestオブジェクトにセットするために必要
		School school = teacher.getSchool();
		String subjectCd = (String) session.getAttribute("f3_selected");
		int testNo = Integer.parseInt((String) session.getAttribute("f4_selected"));

		// 全リクエストパラメータのMapを取得します
		Map<String, String[]> parameterMap = req.getParameterMap();

		// セッションから画面に表示されている検索結果リストを取得
		@SuppressWarnings("unchecked")
		List<Test> searchResults = (List<Test>) session.getAttribute("searchResults");

		// 学生番号をキー、クラス番号を値とするMapを作成して、後でクラス番号を簡単に取り出せるようにする
		Map<String, String> classNumMap = new HashMap<>();
		if (searchResults != null) { // searchResultsがnullでないことを確認
			for (Test t : searchResults) {
				classNumMap.put(t.getStudent().getNo(), t.getClassNum());
			}
		}

		// "point_" で始まるパラメータをループしてチェック
		for (String paramName : parameterMap.keySet()) {
			if (paramName.startsWith("point_")) {
				// "point_12345" から学生番号 "12345" を抽出
				String studentNo = paramName.substring("point_".length());
				String pointStr = parameterMap.get(paramName)[0];

				// どの学生の入力値も保持しておく（エラー時の再表示のため）
				inputValues.put(studentNo, pointStr);

				// 入力値が空文字でなければバリデーションを実行
				if (pointStr != null && !pointStr.isEmpty()) {
					try {
						int point = Integer.parseInt(pointStr);
						if (point < 0 || point > 100) {
							// 0-100の範囲外の場合
							errors.put(studentNo, "0～100の数値を入力してください。");
						} else {
							// 正常な場合、保存用リストに追加するTestオブジェクトを作成
							Test test = new Test();
							Student student = new Student();
							student.setNo(studentNo);
							test.setStudent(student);

							// 先ほど作ったMapから、この学生のクラス番号を取得してセットする
							// これにより、DAOでINSERTが必要になった場合にclass_numを登録できる
							test.setClassNum(classNumMap.get(studentNo));

							Subject subject = new Subject();
							subject.setCd(subjectCd);
							test.setSubject(subject);

							test.setSchool(school);
							test.setNo(testNo);
							test.setPoint(point);

							testsToSave.add(test);
						}
					} catch (NumberFormatException e) {
						// そもそも数値でない場合
						errors.put(studentNo, "数値を入力してください。");
					}
				}
				// 空文字の場合は何もしない（DB更新対象外とする）
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
			req.setAttribute("studentList", session.getAttribute("studentList"));
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

			// DAOを使ってDBに保存
			testDao.save(testsToSave);

			// 完了画面にリダイレクト
			resp.sendRedirect("GRMU002.jsp");
		}
	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
