package student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.CommonServlet;

// JSPのform actionに合わせてURLアノテーションを設定
@WebServlet("/student/StudentCreateController")
public class StudentCreateExecuteController extends CommonServlet {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        // ログインチェック
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login/LOGI001.jsp");
            return;
        }

        // JSPのname属性に合わせてパラメータを取得
        String entYearStr = request.getParameter("year");
        String no = request.getParameter("number");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class");

        // JSPには在学フラグのチェックボックスがないため、デフォルトで在学中(true)として登録
        boolean isAttend = true;

        // 入学年度をint型に変換
        int entYear = 0;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            // エラー処理：本来はエラーページに飛ばすが、ここでは0のまま進める
            // もしくは、入力エラーとして元の画面に戻す処理が望ましい
            e.printStackTrace();
        }

        // Studentオブジェクトを作成して、取得したデータをセット
        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        // StudentDaoを使ってデータベースに保存
        StudentDao sDao = new StudentDao();
        sDao.save(student);

        // 処理完了後、学生一覧画面にリダイレクト
        // JSPの「戻る」ボタンのリンク先と同じSTDM001に飛ばす
        response.sendRedirect(request.getContextPath() + "/STDM001");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // GETで直接アクセスされた場合は一覧画面に戻す
        response.sendRedirect(request.getContextPath() + "/STDM001");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このクラスでは使用しません
    }
}