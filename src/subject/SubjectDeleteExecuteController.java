package subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/subject/deleteexecute")
public class SubjectDeleteExecuteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {

	}

	@Override
	protected void post(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		HttpSession session = req.getSession();

        // ログインチェック
        if (session.getAttribute("session_user") == null) {
            resp.sendRedirect(req.getContextPath() + "/LOGI001.jsp");
            return;
        }

        try {
            SubjectDao subjectDao = new SubjectDao();

            // フォームから送信されたIDでSubjectインスタンスを作成
            Subject subjectToDelete = new Subject();
            subjectToDelete.setCd(req.getParameter("cd"));

            // DAOのdeleteメソッドでDBから削除
            subjectDao.delete(subjectToDelete);

            req.getRequestDispatcher("/subject/SBJM007.jsp").forward(req, resp);
        } catch (Exception e) {
            throw new ServletException(e);
        }

	}

	@Override
	protected void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

	}

}
