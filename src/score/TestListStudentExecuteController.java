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
import bean.TestListStudent;
import dao.ClassNumDao;
import dao.StudentDao;
import dao.SubjectDao;
import dao.TestListStudentDao;
import tool.CommonServlet;

@WebServlet(urlPatterns={"/score/testliststudent"})
public class TestListStudentExecuteController extends CommonServlet {

	private Teacher teacher;

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// 現在のセッションを取得（存在しない場合は新規作成）
		HttpSession session = req.getSession();
		// Teacherオブジェクトを取得
		teacher = (Teacher) session.getAttribute("session_user");

		// teacherがnullの場合はログイン画面にリダイレクト
		if (teacher == null) {
			resp.sendRedirect(req.getContextPath() + "/account/login");
			return;
		}

		// DAOの準備
		StudentDao studentDao = new StudentDao();
		TestListStudentDao testListStudentDao = new TestListStudentDao();

		// 値を取得
		String studentNo = req.getParameter("f4");

		//入力値の検証
		if (studentNo == null || studentNo.isEmpty()) {
			req.setAttribute("error_student", "学生番号を入力してください。");
			// エラーがあっても検索画面は表示し続けるため、GRMR001.jspにフォワード
			req.getRequestDispatcher("/score/GRMR001.jsp").forward(req, resp);
			return;
		}

		//学校コードの取得
        School school = teacher.getSchool();

		// 学生情報を取得
		Student student = studentDao.get(studentNo, school);

		//学生情報がない場合はエラーメッセージを表示
		if (student == null) {
            // 学生が見つからなかった場合の処理
            req.setAttribute("error_student", "指定された学生番号の学生は存在しません");
            // JSP側で ${testListStudent} がエラーにならないよう、空のリストをセットしておく
            req.setAttribute("testListStudent", new ArrayList<TestListStudent>());

        } else {
            // 学生が見つかった場合の処理 (従来の処理)
            List<TestListStudent> testListStudent = testListStudentDao.filter(student);
            req.setAttribute("testListStudent", testListStudent);
        }

		// 検索結果をリクエスト属性にセット
		req.setAttribute("student", student);
		req.setAttribute("f4", studentNo);

	     ClassNumDao classNumDao = new ClassNumDao();
	     SubjectDao subjectDao = new SubjectDao();

	     // ドロップダウン用のリストを取得
	     List<Student> studentListForDropdown = studentDao.filter(school, true);
	     // 学生リストから入学年度を重複なく抽出し、ソートする
	  	List<Integer> entYearList = studentListForDropdown.stream().map(Student::getEntYear)
	  					.distinct()                    // 重複を除去する
	  				    .sorted()                      // 昇順にソートする
	  				    .collect(Collectors.toList()); // 結果をListに変換する
	     List<String> classListStr = classNumDao.filter(school);
	     List<Subject> subjectList = subjectDao.filter(school);

	     // ClassNumリストに変換
	     List<ClassNum> classNumList = new ArrayList<>();
	     for (String classNumStrs : classListStr) {
	            ClassNum classNum = new ClassNum();
	            classNum.setClass_num(classNumStrs);
	            classNumList.add(classNum);
	     }

	     // ドロップダウン用リストをリクエストスコープにセット
	     req.setAttribute("entYearList", entYearList);
	     req.setAttribute("classNumList", classNumList);
	     req.setAttribute("subjectList", subjectList);

		// フォワード
		req.getRequestDispatcher("GRMR001.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		get(req, resp);

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}

}
