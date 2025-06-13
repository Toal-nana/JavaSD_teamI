package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Student;
import bean.Subject;
import bean.Test;
import bean.TestListSubject;

public class TestDao extends Dao {
	private String baseSql = "select ent_year,t.class_num,student_no,name as student_name,point from test as t left join student as s on t.student_no = s.no";

	// 学生番号、科目番号、学校番号、回数で指定したtestインスタンスを一件返す
	public Test get(Student student,Subject subject,School school, int no) throws Exception {
		Test test = new Test();
		// DBに接続
		Connection connection = getConnection();
		// SQLの準備をする変数
		PreparedStatement statement = null;

		try {
			// SQL文をセット
			statement = connection.prepareStatement("select * from test where student_no=? and subject_cd=? and school_cd=? and no=?");
			// SQL文に学生番号をセット
			statement.setString(1, student.getNo());
			// SQL文に科目番号をセット
			statement.setString(2, subject.getCd());
			// SQL文に学校番号をセット
			statement.setString(3, school.getCd());
			// SQL文にテスト回数をセット
			statement.setInt(4, no);
			// SQL文を実行
			ResultSet rSet = statement.executeQuery();

			if (rSet.next()) {
				// 検索に引っかかった科目がある場合
				// 学生インスタンスに検索結果をセット
				//学生インスタンスをセット
				test.setStudent(student);
				//クラス番号をセット
				test.setClassNum(student.getClassNum());
				//科目インスタンスをセット
				test.setSubject(subject);
				//学校インスタンスをセット
				test.setSchool(school);
				//テスト回数をセット
				test.setNo(no);
				//点数をセット
				test.setPoint(rSet.getInt("point"));
			} else {
				// 検索に一件も引っかからなかった場合
				// テストインスタンスにnullをセット
				test = null;
			}
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
		return test;
	}

	// 検索結果の格納
	private List<Test> postFilter(ResultSet rSet,School school) throws Exception {
		List<Test> list = new ArrayList<>();
		// 学生Dao、科目Dao、学校Dao
		StudentDao studentDao = new StudentDao();
		SubjectDao subjectDao = new SubjectDao();
		SchoolDao schoolDao = new SchoolDao();

		try {
			while (rSet.next()) {
				Test test = new Test();

				//学生インスタンスをセット
				test.setStudent(studentDao.get(rSet.getString("student_no")));
				//クラス番号をセット
				test.setClassNum(rSet.getString("class_num"));
				//学校インスタンスをセット
				test.setSchool(school);
				//回数をセット
				test.setPoint(rSet.getInt("point"));
				//得点をセット

				// リストに追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}


	// 入学年度、クラス番号、科目、学校を指定して検索をかける
	public List<Test> filter(int entYear, String classNum,Subject subject,int num, School school) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQL文を準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		//SQLの条件 学校番号、クラス番号、学生番号、テスト回数による絞り込み
		String condition = "where t.school_cd=? and ent_year=? and t.class_num=? and t.subject_cd=? and t.no=?";
		// SQL文のソート
		String order = "order by t.student_no asc";

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
			// SQLにテスト回数を入れる
			statement.setInt(5, num);

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

	// 指定したテストを保存する
	public boolean save(List<Test> list) {

	}

	private boolean save(Test test, Connection connection) throws Exception {

	}

	public boolean delete(List<Test> list) throws Exception {

	}

	private boolean delete(Test test, Connection connection) throws Exception {

	}
}

