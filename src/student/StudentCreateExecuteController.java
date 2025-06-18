package student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.CommonServlet;

// (import文は省略)

// JSPのform actionに合わせてURLアノテーションを設定
@WebServlet("/student/create_execute")// ★ JSPのaction属性と一致させる
public class StudentCreateExecuteController extends CommonServlet {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このメソッドの中身は変更不要です
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // (以下、元のコードのまま)
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login/LOGI001.jsp");
            return;
        }

        String entYearStr = request.getParameter("year");
        String no = request.getParameter("number");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class");




        // 重複していなければ、登録処理を続行

        boolean isAttend = true;

        int entYear = 0;
        try {
            entYear = Integer.parseInt(entYearStr);
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }


        Student student = new Student();
        student.setNo(no);
        student.setName(name);
        student.setEntYear(entYear);
        student.setClassNum(classNum);
        student.setAttend(isAttend);
        student.setSchool(teacher.getSchool());

        StudentDao sDao = new StudentDao();
        sDao.save(student);


       // if (sDao.get(no) != null) {
            // ★ 重複していた場合の処理

            // 1-1. エラーメッセージをリクエストスコープにセット
           // request.setAttribute("error", "学生番号 '" + no + "' は既に使用されています。");



        request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // GETで直接アクセスされた場合は一覧画面に戻す（このままでOK）
        response.sendRedirect(request.getContextPath() + "/list");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このクラスでは使用しません
    }
}