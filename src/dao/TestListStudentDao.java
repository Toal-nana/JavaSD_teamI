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
	// 学校ごとに表示するために必要
	private String baseSql = "select * from test where school_cd=?";

	//検索後のリストへの格納処理をするメソッド
	private List<TestListStudent> postFilter(ResultSet rSet) throws Exception {
		List<TestListStudent> list = new ArrayList<>();
		try {
			// 科目Daoを初期化
			SubjectDao subjectDao = new SubjectDao();
			while (rSet.next()) {
				TestListStudent testListStudent = new TestListStudent();
				// テストリストインスタンスに検索結果をセット
				// subjectDao.get(rSet.getString("subject_cd"))で検索で引っかかった科目のインスタンスが返ってくる
				// つまりSubject.getName()が実行されている状態
				testListStudent.setSubjectName(subjectDao.get(rSet.getString("subject_cd")).getName());
				// こっちで科目コードのセット
				testListStudent.setSubjectCd(subjectDao.get(rSet.getString("subject_cd")).getCd());
				// 回数のセット
				testListStudent.setNum(rSet.getInt("num"));
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
		// SQL文の条件
		String condition = "and student_no=?";
		// SQL文のソート
		String order = "order by no asc";

		try {
			// SQL文をセット 学校と学生番号による絞り込み
			statement = connection.prepareStatement(baseSql + condition + order);
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
