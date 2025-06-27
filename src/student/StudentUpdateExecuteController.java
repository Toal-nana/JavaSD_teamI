package student;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Student;
import bean.Teacher;
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

        // StudentDaoを使ってDBを更新
        StudentDao sDao = new StudentDao();
        sDao.save(student);

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
