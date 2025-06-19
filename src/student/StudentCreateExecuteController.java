package student;


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

@WebServlet("/student/create_execute")
public class StudentCreateExecuteController extends CommonServlet {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login/LOGI001.jsp");
            return;
        }

        // 1. パラメータの取得
        String entYearStr = request.getParameter("year");
        String no = request.getParameter("number");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class");

        StudentDao sDao = new StudentDao();

        // 2. 学生番号の重複チェック
        if (sDao.get(no) != null) {
            // ★重複していた場合の処理

            // 2-1. 入力された値を保持するためのStudentオブジェクトを作成
            Student student = new Student();
            student.setNo(no);
            student.setName(name);
            student.setClassNum(classNum);
            try {
                // 入学年度も数値に変換して保持しておく
                student.setEntYear(Integer.parseInt(entYearStr));
            } catch (NumberFormatException e) {
                // ここでエラーになることは基本的にないが念のため
                student.setEntYear(0);
            }

            // 2-2. エラーメッセージと入力内容をリクエストスコープに保存
            request.setAttribute("error", "学生番号が重複しています"); // エラーメッセージ
            request.setAttribute("student", student); // 入力値

            // 2-3. JSPでクラス一覧のプルダウンを表示するために、再度クラス一覧を取得してセットする
            ClassNumDao classNumDao = new ClassNumDao();
            List<String> classList = classNumDao.filter(teacher.getSchool());
            request.setAttribute("classList", classList);

            // 2-4. 入力フォームにフォワード
            request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
            return; // ★重要: これ以降の処理に進まないようにここで処理を終了する
        }

        // 3. エラーがない場合の登録処理
        // (ここに来るのは学生番号が重複していない場合のみ)

        int entYear = Integer.parseInt(entYearStr); // ここではエラーチェックは不要

        Student newStudent = new Student();
        newStudent.setNo(no);
        newStudent.setName(name);
        newStudent.setEntYear(entYear);
        newStudent.setClassNum(classNum);
        newStudent.setAttend(true);
        newStudent.setSchool(teacher.getSchool());

        sDao.save(newStudent);

        // 完了ページにフォワード
        request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);
    }

    // (get, executeメソッドは変更なし)
    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(request.getContextPath() + "/student/list");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    }
}