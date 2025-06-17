package subject;

import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet(urlPatterns = { "/subject/list" })
public class SubjectListController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		 HttpSession session = req.getSession();
	        Teacher teacher = (Teacher) session.getAttribute("session_user");

	        // ログインチェック
	        if (teacher == null) {
	        	resp.sendRedirect(req.getContextPath() + "/account/login");
	        }

	        try {
	            SubjectDao subjectDao = new SubjectDao();
	            School school = teacher.getSchool();
	            List<Subject> subjectList = subjectDao.filter(school);

	            req.setAttribute("subjectList", subjectList);
	            req.getRequestDispatcher("SBJM001.jsp").forward(req, resp);
	        } catch (Exception e) {
	            // エラーが発生した場合、ServletExceptionにラップして上位にスロー
	            throw new ServletException(e);
	        }

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
