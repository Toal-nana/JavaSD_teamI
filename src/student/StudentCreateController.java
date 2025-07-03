package student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.Teacher;
import dao.ClassNumDao;
import tool.CommonServlet;

@WebServlet("/student/create")
public class StudentCreateController extends CommonServlet {

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	// セッションから先生の情報を取得
        HttpSession session = request.getSession();
        // 他のサーブレット（StudentCreateControllerなど）のコード
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // ログインチェック
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/account/login");
            return;
        }

        // ClassNumDaoを使ってクラスのドロップダウンリストを作成
        ClassNumDao cNumDao = new ClassNumDao();


        // ログインしている教員の学校情報を基にクラス番号の一覧を取得
        List<String> classList = cNumDao.filter(teacher.getSchool());


        // 作成したクラスリストをリクエストにセットしてJSPに渡す
        request.setAttribute("classList", classList);


        List<Integer> entYearSet = new ArrayList<>();
        // 現在の年を取得
        int currentYear = LocalDate.now().getYear();
        // 10年前から10年後までをリストに追加
        for (int i = currentYear + 10; i >= currentYear - 10; i--) {
            entYearSet.add(i);
        }

        // 作成した年度リストをリクエストにセット
        request.setAttribute("entYearSet", entYearSet);



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

    }
}