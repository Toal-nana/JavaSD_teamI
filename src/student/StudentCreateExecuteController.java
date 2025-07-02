package student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
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

        School school = teacher.getSchool();
        StudentDao sDao = new StudentDao();

        // フォームから送信されたパラメータを取得
        String entYearStr = request.getParameter("year");
        String num = request.getParameter("number");
        String name = request.getParameter("name");
        String classNum = request.getParameter("class");

        Map<String, String> errors = new HashMap<>();

        // 入学年度のバリデーション
        if (entYearStr == null || entYearStr.isEmpty()) {
            errors.put("year", "入学年度を選択してください");
        }

        // 学生番号のバリデーション
        if (num != null && !num.isEmpty()) {
        	// 正規表現で「半角数字」であるかをチェック
            if (!num.matches("^[0-9]+$")) {
                errors.put("number", "半角数字で入力してください");
            } else {
                // 半角数字であることが確認できた後で、桁数チェックを行う
                if (num.length() != 7) {
                    errors.put("number", "7桁で入力してください");
                } else {
                    // 形式と桁数が正しい場合のみ、重複チェックを行う
                    if (sDao.get(num, school) != null) {
                        errors.put("number", "学生番号が重複しています");
                    }
                }
            }
        }

        // 氏名のバリデーション（未入力はrequiredに任せる）
        if (name != null && !name.isEmpty()) {
            // 値が入力されている場合のみ、文字数チェックを行う
            if (name.length() > 10) { // データベースの制限が10文字の場合
                errors.put("name", "氏名は10文字以内で入力してください");
            }
        }

        // クラスのバリデーション
        if (classNum == null || classNum.isEmpty()) {
            errors.put("class", "クラスを選択してください");
        }

        // 入力値を保持するためのStudentオブジェクトを作成
        Student student = new Student();
        student.setNo(num);
        student.setName(name);
        student.setClassNum(classNum);
        if (entYearStr != null && !entYearStr.isEmpty()) {
            try {
                student.setEntYear(Integer.parseInt(entYearStr));
            } catch (NumberFormatException e) {
                // do nothing
            }
        }

        // エラーマップが空でない場合
        if (!errors.isEmpty()) {
            request.setAttribute("errors", errors);
            request.setAttribute("student", student);
            this.execute(request, response); // プルダウンのリスト再設定
            request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
            return;
        }

        // 登録処理
        int entYear = Integer.parseInt(entYearStr);
        Student newStudent = new Student();
        newStudent.setNo(num);
        newStudent.setName(name);
        newStudent.setEntYear(entYear);
        newStudent.setClassNum(classNum);
        newStudent.setAttend(true);
        newStudent.setSchool(school);
        sDao.save(newStudent, school);

        request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);
    }

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(request.getContextPath() + "/student/list");
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	//セッション情報の確認
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        //クラスの一覧を取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(teacher.getSchool());
        request.setAttribute("classList", classList);

        //入学年度の一覧を取得
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear + 10; i >= currentYear - 10; i--) {
            entYearSet.add(i);
        }
        request.setAttribute("entYearSet", entYearSet);
    }
}