package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.Student;
import bean.TestListStudent;

public class TestListStudentDao extends Dao {
	// testテーブルとsubjectテーブルのjoin
	private String baseSql = "select name as subject_name,subject_cd,no,point from test as t left  join subject as s on t.subject_cd = s.cd";

	//検索後のリストへの格納処理をするメソッド
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				TestListStudent testListStudent = new TestListStudent();
				// テストリストインスタンスに検索結果をセット
				// 科目名をセット
				testListStudent.setSubjectName("subject_name");
				// 科目コードをセット
				testListStudent.setSubjectCd("subject_cd");
				// 回数のセット
				testListStudent.setNum(rSet.getInt("no"));
				// 点数のセット
				testListStudent.setPoint(rSet.getInt("point"));
				// リストに追加
				list.add(testListStudent);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 受け取った学生インスタンスで検索をかける
	public List<TestListStudent> filter(Student student) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQL文を準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文の条件 (指定した学生の学生番号で絞り込み)
		String condition = "where school_cd=? and student_no=?";
		// SQL文のソート
		String order = "order by subject_cd asc";

		try {
			// SQL文をセット 学校と学生番号による絞り込み
			statement = connection.prepareStatement(
					 baseSql + condition + order);
			// SQL文に学校番号を入れる
			statement.setString(1,student.getSchool().getCd());
			// SQL文に学生番号を入れる
			statement.setString(2, student.getNo());
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet);
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL文の入力を終了
			if (statement != null) {
				try{
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// DBを切断
			if (connection != null) {
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;
	}
}
