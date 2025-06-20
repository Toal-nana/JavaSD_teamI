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
            response.sendRedirect(request.getContextPath() + "/account/LOGI001");
            return;
        }

        // 1. パラメータの取得
        String entYearStr = request.getParameter("year");
        String num = request.getParameter("number");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class");

        StudentDao sDao = new StudentDao();


            // 2-1. 入力された値を保持するためのStudentオブジェクトを作成
            Student student = new Student();
            student.setNo(num);
            student.setName(name);
            student.setClassNum(classNum);

            if (entYearStr == "") {
            	request.setAttribute("error1", "入学年度を選択してください"); // エラーメッセージ
                request.setAttribute("student", student); // 入力値
                this.execute(request, response);

                request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
                return; // ★重要: これ以降の処理に進まないようにここで処理を終了する
            } else if (sDao.get(num) != null) {
            	request.setAttribute("error2", "学生番号が重複しています"); // エラーメッセージ
                request.setAttribute("student", student); // 入力値
                this.execute(request, response);

                request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
                return; // ★重要: これ以降の処理に進まないようにここで処理を終了する
			} else if (entYearStr == "" && sDao.get(num) != null) {
				request.setAttribute("error1", "入学年度を選択してください"); // エラーメッセージ
            	request.setAttribute("error2", "学生番号が重複しています"); // エラーメッセージ
            	request.setAttribute("student", student); // 入力値
            	this.execute(request, response);

            	 request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
                 return; // ★重要: これ以降の処理に進まないようにここで処理を終了する
			} else {
				   // 2-3. JSPでクラス一覧のプルダウンを表示するために、再度クラス一覧を取得してセットする

				this.execute(request, response);


	        // 3. エラーがない場合の登録処理
	        // (ここに来るのは学生番号が重複していない場合のみ)

	        int entYear = Integer.parseInt(entYearStr);

	        Student newStudent = new Student();
	        newStudent.setNo(num);
	        newStudent.setName(name);
	        newStudent.setEntYear(entYear);
	        newStudent.setClassNum(classNum);
	        newStudent.setAttend(true);
	        newStudent.setSchool(teacher.getSchool());

	        sDao.save(newStudent);

	        // 完了ページにフォワード
	        request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);
			}

    }

    // (get, executeメソッドは変更なし)
    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(request.getContextPath() + "/student/list");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	 HttpSession session = request.getSession();
         Teacher teacher = (Teacher) session.getAttribute("session_user");

    	ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(teacher.getSchool());
        request.setAttribute("classList", classList);
    }
}