package student;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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

/**
 * 学生情報の作成処理を行うサーブレット
 */
@WebServlet("/student/create_execute")
public class StudentCreateExecuteController extends CommonServlet {

    /**
     * POSTリクエスト処理: 学生情報を登録／バリデーションを行う
     */
    @Override
    public void post(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログイン中の教師情報を取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // 未ログインの場合はログイン画面にリダイレクト
        if (teacher == null) {
            response.sendRedirect(request.getContextPath() + "/account/LOGI001");
            return;
        }

        //学校を取得
        School school = teacher.getSchool();

        // フォームから送信されたパラメータを取得
        String entYearStr = request.getParameter("year");  // 入学年度
        String num = request.getParameter("number");       // 学生番号
        String name = request.getParameter("name");        // 学生名
        String classNum = request.getParameter("class");  // クラス番号

        // 未入力のチェック用
        String entYearEmpty = "";
        Boolean NameEmpty = false;
        Boolean StuNumEmpty = false;
        Boolean ClsNumEmpty = false;

        // 学生番号が入力されていた時のチェック用
        String StuNumError = "";


        StudentDao sDao = new StudentDao();

        // 入力値を仮保存するオブジェクト
        Student student = new Student();
        student.setNo(num);
        student.setName(name);
        student.setClassNum(classNum);


        //未入力のチェック
        if (entYearStr.isEmpty()) {
            entYearEmpty = "入学年度を選択してください";
        }
        // 学生番号
        if (num.isEmpty()) {
            StuNumEmpty = true;
        }else { // 入力されていた時 入力内容のチェック
        	// 数値が入っているかどうかのチェック
        	try{
        		Integer.parseInt(num);
        	}catch (NumberFormatException e) {// 数値が入っていなかったらエラーメッセージを入れる
        		StuNumError = "7桁の数値で入力してください";
            	request.setAttribute("entYearEmpty", entYearEmpty);
            	request.setAttribute("StuNumEmpty", StuNumEmpty);
            	request.setAttribute("StuNumError", StuNumError);

            	// 入力、選択内容の保持
            	request.setAttribute("selectEntYear",entYearStr);
                request.setAttribute("student", student);
            	// クラスリストと年度リストを再設定
            	this.execute(request, response);
            	// 入力画面へフォワード
            	request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
            	return;
        	}

        	// 数値が入っていた時
        	// 入力された数値が7桁じゃなかったとき
        	if (num.length() != 7) {
        		StuNumError = "7桁の数値で入力してください";
        	}else {
        		// 重複チェック(数値エラーがなかったらここに進む)
        		if (StuNumError.isEmpty() && sDao.get(num,school) != null) {
        			StuNumError = "学生番号が重複しています";
        		}
        	}
        }

        // 学生氏名
        if (name.isEmpty()) {
        	NameEmpty = true;
        }

        // クラス番号
        if (classNum.isEmpty()) {
        	ClsNumEmpty = true;
        }

        // 入学年度がエラーもしくは学生番号がエラー
        // warningでの表記をするためにrequiredの送信を停止
        if (!entYearEmpty.isEmpty() || !StuNumError.isEmpty()) {
        	// 詳細な場合分け
        	if (!entYearEmpty.isEmpty()) { //入学年度がエラーの場合
            	request.setAttribute("entYearEmpty", entYearEmpty);
            	request.setAttribute("StuNumEmpty", StuNumEmpty);
            	request.setAttribute("StuNumError", StuNumError);
        	} else if(!StuNumError.isEmpty()) {// 学生番号がエラーの場合
            	request.setAttribute("entYearEmpty", entYearEmpty);
            	request.setAttribute("StuNumEmpty", StuNumEmpty);
            	request.setAttribute("StuNumError", StuNumError);
        	}
        	// 入力、選択内容の保持
        	request.setAttribute("selectEntYear",entYearStr);
            request.setAttribute("student", student);
        	// クラスリストと年度リストを再設定
        	this.execute(request, response);
        	// 入力画面へフォワード
        	request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
        	return;

        }else if(NameEmpty || ClsNumEmpty){// warningエラーが起こらなかった時
        	if (StuNumEmpty) {
        		request.setAttribute("StuNumEmpty", StuNumEmpty);
        	}else if (NameEmpty) {// 名前が未入力の時
            	request.setAttribute("NameEmpty", NameEmpty); //requiredを追加
        	}else if (ClsNumEmpty) {// クラスが未入力の時
            	request.setAttribute("ClsNumEmpty", ClsNumEmpty);
        	}
        	// 入力、選択内容の保持
        	request.setAttribute("selectEntYear",entYearStr);
            request.setAttribute("student", student);
        	// クラスリストと年度リストを再設定
        	this.execute(request, response);
        	// 入力画面へフォワード
        	request.getRequestDispatcher("/student/STDM002.jsp").forward(request, response);
        	return;
        }





        // バリデーション通過後の処理
        if(entYearEmpty.isEmpty() && !StuNumEmpty && StuNumError.isEmpty() && !NameEmpty && !ClsNumEmpty) {
        	// 共通属性を設定
        	this.execute(request, response);

        	// 入学年度文字列を整数に変換
        	int entYear = Integer.parseInt(entYearStr);

        	// 新規学生オブジェクトを作成し、プロパティを設定
        	Student newStudent = new Student();
        	newStudent.setNo(num);
        	newStudent.setName(name);
        	newStudent.setEntYear(entYear);
        	newStudent.setClassNum(classNum);
        	newStudent.setAttend(true);                 // 出席ステータスはtrue
        	newStudent.setSchool(teacher.getSchool());  // 教師の所属校を設定


        	sDao.save(newStudent,school);
        	// 完了画面にフォワード
        	request.getRequestDispatcher("/student/STDM003.jsp").forward(request, response);
        }

    }

    /**
     * GETリクエスト時は一覧画面にリダイレクト
     */
    @Override
    public void get(HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.sendRedirect(request.getContextPath() + "/student/list");
    }

    /**
     * 共通処理: 教室リストと年度リストを準備
     */
    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) throws Exception {
        // セッションからログイン中の教師情報を再取得
        HttpSession session = request.getSession();
        Teacher teacher = (Teacher) session.getAttribute("session_user");

        // 所属校に応じたクラス番号リストを取得
        ClassNumDao classNumDao = new ClassNumDao();
        List<String> classList = classNumDao.filter(teacher.getSchool());
        request.setAttribute("classList", classList);

        // 年度リストを作成（現在年の前後10年分）
        List<Integer> entYearSet = new ArrayList<>();
        int currentYear = LocalDate.now().getYear();
        for (int i = currentYear + 10; i >= currentYear - 10; i--) {
            entYearSet.add(i);
        }
        request.setAttribute("entYearSet", entYearSet);
    }
}
