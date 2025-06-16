package subject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Subject;
import dao.SubjectDao;
import tool.CommonServlet;

@WebServlet("/subject/Delete")
public class SubjectDeleteController extends CommonServlet {

	@Override
	protected void get(HttpServletRequest req, HttpServletResponse resp) throws Exception {
HttpSession session = req.getSession();

        // ログインチェック
        if (session.getAttribute("session_user") == null) {
            resp.sendRedirect(req.getContextPath() + "/LOGI001.jsp");
            return;
        }

        try {
            SubjectDao subjectDao = new SubjectDao();
            String cd = req.getParameter("cd");

            // 削除対象の科目情報を取得
            Subject subjectToDelete = subjectDao.get(cd);

            // JSPに渡すためにリクエストスコープにセット
            req.setAttribute("subject", subjectToDelete);

            req.getRequestDispatcher("/subject/SBJM006.jsp").forward(req, resp);
        } catch (Exception e) {
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
