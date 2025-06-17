package student;

import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao; // ★ StudentDaoからClassNumDaoに変更
import tool.CommonServlet;

@WebServlet("/student/StudentCreateController") // ★ URLを分かりやすいものに変更
public class StudentCreateController extends CommonServlet {

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {


    	//System.out.println("★★★★★★ StudentCreateControllerが呼ばれました ★★★★★★");

        HttpSession session = request.getSession();
     // 他のサーブレット（StudentCreateControllerなど）のコード
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // ログインチェック
        if (teacher == null) {

        	System.out.println("★★★★★★ ログインしていません！リダイレクトします。 ★★★★★★");
            // ログインページのパスは環境に合わせてください
            response.sendRedirect(request.getContextPath() + "/account/LOGI001.jsp");
            return;
        }

        // --- ClassNumDaoを使ってクラスのドロップダウンリストを作成 ---
        ClassNumDao cNumDao = new ClassNumDao();


        // ログインしている教員の学校情報を基にクラス番号の一覧を取得
        List<String> classList = cNumDao.filter(teacher.getSchool());

        System.out.println("★★★★★★ classListの中身: " + classList);
        System.out.println("★★★★★★ classListの件数: " + classList.size());


        // 作成したクラスリストをリクエストにセットしてJSPに渡す
        request.setAttribute("classList", classList);

        // 登録フォームのJSPにフォワード
        request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);





    }


    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // POSTで直接アクセスされた場合も同じ画面を表示する
        get(request, response);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このクラスでは使用しません
    }
}