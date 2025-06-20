package student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.ClassNumDao;
import dao.StudentDao;
import tool.CommonServlet;

/**
 * 学生情報の作成処理を行うサーブレット
 */
@WebServlet("/student/create_execute")
public class StudentCreateExecuteController extends CommonServlet {

    /**
     * POSTリクエスト処理: 学生情報を登録／バリデーションを行う
     */
    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログイン中の教師情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // 未ログインの場合はログイン画面にリダイレクト
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/account/LOGI001");
            return;
        }

        // フォームから送信されたパラメータを取得
        String entYearStr = request.getParameter("year");   // 入学年度
        String num = request.getParameter("number");       // 学生番号
        String name = request.getParameter("name");        // 学生名
        String classNum = request.getParameter("class");   // クラス番号

        StudentDao sDao = new StudentDao();  // 学生DAO生成

        // 入力値を仮保存するオブジェクト
        Student student = new Student();
        student.setNo(num);
        student.setName(name);
        student.setClassNum(classNum);

        // バリデーション：入学年度未選択かつ学生番号重複
        if (entYearStr.isEmpty() && sDao.get(num) != null) {
            request.setAttribute("error1", "入学年度を選択してください");
            request.setAttribute("error2", "学生番号が重複しています");
            request.setAttribute("student", student);
            // クラスリストと年度リストを再設定
            this.execute(request, response);
            // 入力画面にフォワード
            request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);

        // バリデーション：学生番号重複のみ
        } else if (sDao.get(num) != null) {
            request.setAttribute("error2", "学生番号が重複しています");
            request.setAttribute("student", student);
            this.execute(request, response);
            request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);

        // バリデーション：入学年度未選択のみ
        } else if (entYearStr.isEmpty()) {
            request.setAttribute("error1", "入学年度を選択してください");
            request.setAttribute("student", student);
            this.execute(request, response);
            request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);

        // バリデーション通過後の処理
        } else {
            // 共通属性を設定
            this.execute(request, response);

            // 入学年度文字列を整数に変換
            int entYear = Integer.parseInt(entYearStr);

            // 新規学生オブジェクトを作成し、プロパティを設定
            Student newStudent = new Student();
            newStudent.setNo(num);
            newStudent.setName(name);
            newStudent.setEntYear(entYear);
            newStudent.setClassNum(classNum);
            newStudent.setAttend(true);                 // 出席ステータスはtrue
            newStudent.setSchool(teacher.getSchool());  // 教師の所属校を設定

            // 永続化
            sDao.save(newStudent);

            // 完了画面にフォワード
            request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);
        }
    }

    /**
     * GETリクエスト時は一覧画面にリダイレクト
     */
    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(request.getContextPath() + "/student/list");
    }

    /**
     * 共通処理: 教室リストと年度リストを準備
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログイン中の教師情報を再取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // 所属校に応じたクラス番号リストを取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(teacher.getSchool());
        request.setAttribute("classList", classList);

        // 年度リストを作成（現在年の前後10年分）
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear + 10; i >= currentYear - 10; i--) {
            entYearSet.add(i);
        }
        request.setAttribute("entYearSet", entYearSet);
    }
}
