package student;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet("/student/update_execute")
public class StudentUpdateExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {


	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// ログインチェック
        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/login/LOGI001.jsp");
            return;
        }

        //学校の取得
        School school = teacher.getSchool();

        // エラーメッセージを格納するマップを初期化
        Map<String, String> errors = new HashMap<>();

        // フォームから送信されたデータを取得
        String no = req.getParameter("no"); // hiddenフィールドから学生番号を取得
        String entYearStr = req.getParameter("entYear");
        String name = req.getParameter("name");
        String classNum = req.getParameter("classNum");

        // チェックボックスは、チェックされていないとパラメータが送られてこない（nullになる）
        String isAttendStr = req.getParameter("isAttend");
        boolean isAttend = (isAttendStr != null); // nullでなければtrue

        // 数値に変換
        int entYear = Integer.parseInt(entYearStr);

        // Studentオブジェクトに新しいデータをセット
        Student student = new Student();
        student.setNo(no); // 学生番号は変更しない
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        //エラーが起きた場合、マップをセット
        if (entYearStr != null && !entYearStr.isEmpty()) {
            student.setEntYear(Integer.parseInt(entYearStr));
        }

        // 氏名のバリデーション（10文字チェック）
        if (name == null || name.isEmpty()) {
            errors.put("name", "氏名を入力してください");
        } else if (name.length() > 10) {
            errors.put("name", "氏名は10文字以内で入力してください");
        }
        // クラスのバリデーション（未選択チェック）
        if (classNum == null || classNum.isEmpty()) {
        	errors.put("classNum", "クラスを選択してください");
        }

     // エラーマップが空でない場合
        if (!errors.isEmpty()) {
            // エラーメッセージと入力値をリクエストスコープに設定
            req.setAttribute("errors", errors);
            req.setAttribute("student", student);

            // 編集画面を再表示するために必要なクラス一覧を再取得してリクエストスコープに設定
            ClassNumDao classNumDao = new ClassNumDao();
            List<String> classList = classNumDao.filter(teacher.getSchool());
            req.setAttribute("classList", classList);

            // 編集フォームのJSPにフォワードする
            req.getRequestDispatcher("/student/STDM004.jsp").forward(req, resp);
            return;
        }

        // StudentDaoを使ってDBを更新
        StudentDao sDao = new StudentDao();
        sDao.save(student,school);

        // 更新完了後、学生一覧画面にリダイレクト
        //resp.sendRedirect(req.getContextPath() + "/student/list");

        // 完了画面にフォワード
        req.getRequestDispatcher("/student/STDM005.jsp").forward(req, resp);
    }



	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
