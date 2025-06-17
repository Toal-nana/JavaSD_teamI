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
@WebServlet("/student/StudentCreateExecuteController")// ★ JSPのaction属性と一致させる
public class StudentCreateExecuteController extends CommonServlet {

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このメソッドの中身は変更不要です
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        // (以下、元のコードのまま)
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login/LOGI001.jsp");
            return;
        }

        String entYearStr = request.getParameter("year");
        String no = request.getParameter("number");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class");
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

        response.sendRedirect(request.getContextPath() + "/STDM001");
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // GETで直接アクセスされた場合は一覧画面に戻す（このままでOK）
        response.sendRedirect(request.getContextPath() + "/STDM001");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このクラスでは使用しません
    }
}