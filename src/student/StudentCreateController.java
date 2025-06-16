package student;

import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao; // クラスリスト作成のために使用
import tool.CommonServlet;

@WebServlet("/STDM002")
public class StudentCreateController extends CommonServlet {

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("teacher");

        // ログインチェック
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/login/login.jsp");
            return;
        }

        // --- JSPでクラスのドロップダウンリストを表示するための処理 ---
        StudentDao sDao = new StudentDao();
        School school = teacher.getSchool();

        // 学校に所属する全学生のリストを取得
        List<Student> allStudents = sDao.filter(school, false);

        // 全学生リストから、重複しない「クラス番号」のリストを作成する
        List<String> classList = allStudents.stream()
            .map(Student::getClassNum)  // 各Studentからクラス番号を取得
            .distinct()                 // 重複を除外
            .sorted()                   // 昇順にソート
            .collect(Collectors.toList()); // リストに変換

        // 作成したクラスリストをリクエストにセットしてJSPに渡す
        request.setAttribute("classList", classList);

        // ご提示いただいたJSPファイルのパスにフォワードします。
        // パスは実際のプロジェクト構成に合わせてください。
        request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
    }

    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        get(request, response);
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このクラスでは使用しません
    }
}