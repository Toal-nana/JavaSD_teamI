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

			//エラーの場合のドロップダウンリストのデータ取得
			ClassNumDao classNumDaoForError = new ClassNumDao();
			SubjectDao subjectDaoForError = new SubjectDao();
			StudentDao studentDaoForError = new StudentDao();
			
			List<String> classListForError = classNumDaoForError.filter(school);
			List<ClassNum> classNumListForError = new ArrayList<>();
			
			for (String classNumStrs : classListForError) {
				ClassNum classNum = new ClassNum();
				classNum.setClass_num(classNumStrs);
				classNumListForError.add(classNum);
			}
			
			List<Subject> subjectListForError = subjectDaoForError.filter(school);
			List<Student> studentListForError = studentDaoForError.filter(school, true);
			
			req.setAttribute("classNumList", classNumListForError);
			req.setAttribute("subjectList", subjectListForError);
			req.setAttribute("studentList", studentListForError);

			req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);
			
			return;
		}

		//入力値の解析とバリデーション
		int testNo = Integer.parseInt(testNoStr);
		
		Map<String, String> errors = new HashMap<>();
		Map<String, String> inputValues = new HashMap<>();
		
		List<Test> testsToProcess = new ArrayList<>();

		String[] deleteStudentNos = req.getParameterValues("delete_students");
		Set<String> deleteSet = (deleteStudentNos != null) ? new HashSet<>(Arrays.asList(deleteStudentNos))
				: new HashSet<>();

		for (Test displayedTest : searchResults) {
			
			String studentNo = displayedTest.getStudent().getNo();
			String pointStr = req.getParameter("point_" + studentNo);
			
			inputValues.put(studentNo, pointStr);

			boolean isDeleteTarget = deleteSet.contains(studentNo);
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
					
					Student student = new Student();
					student.setNo(studentNo);
					test.setStudent(student);
					test.setClassNum(displayedTest.getClassNum());
					
					Subject subject = new Subject();
					subject.setCd(subjectCd);
					test.setSubject(subject);
					test.setSchool(school);
					test.setNo(testNo);
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

			// ドロップダウンリストのデータ処理
			ClassNumDao classNumDao = new ClassNumDao();
			SubjectDao subjectDao = new SubjectDao();
			StudentDao studentDao = new StudentDao();
			
			List<String> classList = classNumDao.filter(school);
			List<ClassNum> classNumList = new ArrayList<>();
			
			for (String classNumStrs : classList) {
				ClassNum classNum = new ClassNum();
				classNum.setClass_num(classNumStrs);
				classNumList.add(classNum);
			}
			
			List<Subject> subjectList = subjectDao.filter(school);
			List<Student> studentList = studentDao.filter(school, true);
			
			req.setAttribute("classNumList", classNumList);
			req.setAttribute("subjectList", subjectList);
			req.setAttribute("studentList", studentList);

			req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);

		} else {
			//エラーがなかった場合
			try {
				TestDao testDao = new TestDao();
				testDao.save(testsToProcess);

				// 処理完了後に不要になったセッション情報をクリア
				session.removeAttribute("f1_selected");
				session.removeAttribute("f2_selected");
				session.removeAttribute("f3_selected");
				session.removeAttribute("f4_selected");
				session.removeAttribute("searchResults");
				session.removeAttribute("selectedSubjectName");
				session.removeAttribute("selectedCount");

				resp.sendRedirect("GRMU002.jsp");

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

				//ドロップダウンリストのデータ取得（DBエラー時用）
				ClassNumDao classNumDao = new ClassNumDao();
				SubjectDao subjectDao = new SubjectDao();
				StudentDao studentDao = new StudentDao();
				List<String> classList = classNumDao.filter(school);
				List<ClassNum> classNumList = new ArrayList<>();
				for (String classNumStrs : classList) {
					ClassNum classNum = new ClassNum();
					classNum.setClass_num(classNumStrs);
					classNumList.add(classNum);
				}
				List<Subject> subjectList = subjectDao.filter(school);
				List<Student> studentList = studentDao.filter(school, true);
				req.setAttribute("classNumList", classNumList);
				req.setAttribute("subjectList", subjectList);
				req.setAttribute("studentList", studentList);

				req.getRequestDispatcher("GRMU001.jsp").forward(req, resp);
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
		// TODO 自動生成されたメソッド・スタブ

	}

}
