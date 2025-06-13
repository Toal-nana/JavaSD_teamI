package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	// testテーブルとstudentテーブルのjoin
	private String baseSql = "select ent_year,t.class_num,student_no,name as student_name,point,'-' as point2 from test as t left join student as s on t.student_no = s.no" ;

	// 検索結果の格納作業をする
	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				// 点数セット用にハッシュマップを初期化
				Map<Integer, Integer> pointMap = new HashMap<>();
				TestListSubject testListSubject = new TestListSubject();
				// 入学年度をセット
				testListSubject.setEntYear(rSet.getInt("ent_year"));
				// クラス番号をセット
				testListSubject.setClassNum(rSet.getString("class_num"));
				// 学生番号をセット
				testListSubject.setStudentNo(rSet.getString("student_no"));
				// 学生氏名をセット
				testListSubject.setStudentName(rSet.getString("name"));
				// 点数をセット
				pointMap.put(1, rSet.getInt("point"));
				pointMap.put(2, rSet.getInt("point2"));
				// リストに追加
				list.add(testListSubject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}


	// 入学年度、クラス番号、科目、学校で検索をかける
	public List<TestListSubject> filter(int entYear,String classNum,Subject subject,School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQL文を準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		//SQLの条件 入学年度、クラス番号、科目、学校による条件付け
		String condition = "where t.school_cd=? and ent_year=? and t.class_num=? and t.subject_cd=?";
		// SQL文のソート
		String order = "order by s.no asc";

		try {
			// SQL文をセット 学校と学生番号による絞り込み
			statement = connection.prepareStatement(
					baseSql + condition + order);
			// SQLに学校を入れる
			statement.setString(1, school.getCd());
			// SQL文に入学年度を入れる
			statement.setInt(2,entYear);
			// SQLにクラス番号を入れる
			statement.setString(3,classNum);
			// SQLに科目番号を入れる
			statement.setString(4, subject.getCd());

			// SQLの実行
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