package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;

public class StudentDao extends Dao {
	// Studentテーブルからデータを取得するSQL
	private String baseSql = "select * from student where school_cd=?";

	public Student get(String no) throws Exception {

	}

	//フィルター後のリストへの格納処理をするメソッド
	private List<Student> postFilter(ResultSet rSet,School school) throws Exception {
		List<Student> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				Student student = new Student();
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				student.setSchool(school);
				// リストに追加
				list.add(student);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	//学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得するメソッド
	public List<Student> filter(School school,int entYear,String classNum,boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		Connection connection = getConnection();
		// SQL文格納用
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year=? and class_num=?";
		// SQL文のソート
		String order = "order by no asc";

		// SQL文の在学フラグ条件
		String conditionIsAttend = "";
		// 在学フラグがtrueの場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}

		try {
			// PreparedStatementにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// PreparedStatementに学校コードをバインド
			statement.setString(1,school.getCd());
			// PreparedStatementに入学年度をバインド
			statement.setInt(2, entYear);
			// PreparedStatementにクラス番号をバインド
			statement.setString(3, classNum);
			// プライベートステートメントを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) {
				try{
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
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

	public List<Student> filter(School school,int entYear,boolean isAttend) throws Exception {

	}

	public List<Student> filter(School school,boolean isAttend) throws Exception {

	}

	public boolean save(Student student) throws Exception {

	}
}
