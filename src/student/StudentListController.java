package student;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Student;
import bean.Teacher;
import dao.StudentDao;
import tool.CommonServlet;

@WebServlet("/student/list")
public class StudentListController extends CommonServlet {

    /**
     * GET/POSTリクエストの共通処理
     * @param request
     * @param response
     * @throws Exception
     */

    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
    	  HttpSession session = request.getSession();
    	     // 他のサーブレット（StudentCreateControllerなど）のコード
    	        Teacher teacher = (Teacher) session.getAttribute("session_user");

    	        // ログインチェック
    	        if (teacher == null) {
    	            // ログインページへリダイレクト（パスは環境に合わせてください）
    	            response.sendRedirect(request.getContextPath() + "/account/LOGI001.jsp");
    	            return;
    	        }

    	        // DAOのインスタンス化
    	        StudentDao sDao = new StudentDao();
    	        // ログイン中の教員の学校情報を取得
    	        School school = teacher.getSchool();

    	        // --- DAOを変更しないための対応 ---
    	        // JSPのドロップダウンリスト用のデータをサーブレット側で作成する

    	        // 1. まず、学校に所属する全学生のリストを取得する (在学中かどうかは問わない)
    	        //    sDao.filter(school, false) を呼び出し、在学生・卒業生の両方を取得
    	        List<Student> allStudents = sDao.filter(school, false);

    	        // 2. 全学生リストから、重複しない「入学年度」のリストを作成する
    	        List<Integer> entYearSet = allStudents.stream()
    	            .map(Student::getEntYear) // 各StudentオブジェクトからentYearを取得
    	            .distinct()               // 重複を除外
    	            .sorted(Collections.reverseOrder()) // 降順にソート (新しい年度が上)
    	            .collect(Collectors.toList());    // リストに変換

    	        // 3. 全学生リストから、重複しない「クラス番号」のリストを作成する
    	        List<String> classNumSet = allStudents.stream()
    	            .map(Student::getClassNum) // 各StudentオブジェクトからclassNumを取得
    	            .distinct()                // 重複を除外
    	            .sorted()                  // 昇順にソート
    	            .collect(Collectors.toList());     // リストに変換


    	        // --- ここから絞り込み処理 ---

    	        // フォームから送信された絞り込み条件を取得
    	        String entYearStr = request.getParameter("f1");
    	        String classNum = request.getParameter("f2");
    	        String isAttendStr = request.getParameter("f3");

    	        int entYear = 0;
    	        if (entYearStr != null && !entYearStr.isEmpty()) {
    	            try {
    	                entYear = Integer.parseInt(entYearStr);
    	            } catch (NumberFormatException e) {
    	                entYear = 0;
    	            }
    	        }

    	        boolean isAttend = (isAttendStr != null && isAttendStr.equals("t"));

    	        // 学生リストを検索（この時点では絞り込みはまだ行わない）
    	        List<Student> students = null;

    	        // 条件に応じてDAOの適切なfilterメソッドを呼び出す
    	        if (entYear > 0 && classNum != null && !classNum.isEmpty() && !classNum.equals("0")) {
    	            students = sDao.filter(school, entYear, classNum, isAttend);
    	        } else if (entYear > 0) {
    	            students = sDao.filter(school, entYear, isAttend);
    	        } else {
    	            // クラス番号のみの絞り込みはDAOにないので、全件検索と同じ扱い
    	            students = sDao.filter(school, isAttend);
    	        }

    	        // --- JSPにデータを渡す ---

    	        // 絞り込み用の選択肢リスト
    	        request.setAttribute("ent_year_set", entYearSet);
    	        request.setAttribute("class_num_set", classNumSet);

    	        // 検索結果の学生リスト
    	        request.setAttribute("students", students);

    	        // 絞り込み条件を画面に保持するためにセット
    	        request.setAttribute("f1", entYearStr);
    	        request.setAttribute("f2", classNum);
    	        if (isAttend) {
    	            request.setAttribute("f3", "t");
    	        }

    	        // JSPへフォワード（パスは環境に合わせてください）
    	        request.getRequestDispatcher("/student/STDM001.jsp").forward(request, response);
    	    }



    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {

    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // このクラスでは使用しない
    }
}