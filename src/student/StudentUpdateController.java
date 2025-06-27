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

@WebServlet("/student/update")
public class StudentUpdateController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

        HttpSession session = req.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");
        if (teacher == null) {
            resp.sendRedirect(req.getContextPath() + "/login/LOGI001.jsp");
            return;
        }

        //  URLから変更対象の学生番号を取得
        String no = req.getParameter("no");

        StudentDao sDao = new StudentDao();
        ClassNumDao cNumDao = new ClassNumDao();

        //  学生番号を基にDBから学生情報を取得
        Student student = sDao.get(no);

        //  クラスのプルダウン用に、クラスの一覧を取得
        List<String> classList = cNumDao.filter(teacher.getSchool());

        //  取得した学生情報とクラス一覧をリクエストスコープにセット
        req.setAttribute("student", student);
        req.setAttribute("classList", classList);


        req.getRequestDispatcher("/student/STDM004.jsp").forward(req, resp);

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		  get(req, resp);
	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
