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

public class TestDao extends Dao {
	private String baseSql = "select s.ent_year, s.no as student_no, s.name as student_name, s.class_num, t.point from student as s left join test as t on s.no = t.student_no and t.subject_cd = ? and t.no = ? ";

	// 学生番号、科目番号、学校番号、回数で指定したtestインスタンスを一件返す
	public Test get(Student student, Subject subject, School school, int no) throws Exception {
		Test test = new Test();
		// DBに接続
		Connection connection = getConnection();
		// SQLの準備をする変数
		PreparedStatement statement = null;

		try {
			// SQL文をセット
			statement = connection.prepareStatement(
					"select * from test where student_no=? and subject_cd=? and school_cd=? and no=?");
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
				// 学生インスタンスをセット
				test.setStudent(student);
				// クラス番号をセット
				test.setClassNum(student.getClassNum());
				// 科目インスタンスをセット
				test.setSubject(subject);
				// 学校インスタンスをセット
				test.setSchool(school);
				// テスト回数をセット
				test.setNo(no);
				// 点数をセット
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
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// DBを切断
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return test;
	}

	// 検索結果の格納
	private List<Test> postFilter(ResultSet rSet, School school, Subject subjectInfo, int testNo) throws Exception {
		List<Test> list = new ArrayList<>();
		try {
			while (rSet.next()) {
				Test test = new Test();
				// 学生インスタンスの要素はこの中に入れる
				Student student = new Student();
				// 入学年度
				student.setEntYear(rSet.getInt("ent_year"));
				// 学生番号
				student.setNo(rSet.getString("student_no"));
				// 氏名
				student.setName(rSet.getString("student_name"));
				// クラス
				test.setClassNum(rSet.getString("class_num"));
				// 点数
				test.setPoint(rSet.getInt("point"));
				// 学校
				test.setSchool(school);
				// 作った学生インスタンスをテストインスタンスに入れる
				test.setStudent(student);

				// 学生を主にしたテーブル合体を行った際に追加したもの
				test.setSubject(subjectInfo);
				test.setNo(testNo);

				// 作ったテストインスタンスを追加
				list.add(test);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	// 入学年度、クラス番号、科目、回数、学校を指定して検索をかける
	public List<Test> filter(int entYear, String classNum, Subject subject, int num, School school) throws Exception {
		List<Test> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQL文を準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQLの条件 学校番号、クラス番号、学生番号、テスト回数による絞り込み
		String condition = "where s.school_cd = ? and s.ent_year = ? and s.class_num = ?";
		// SQL文のソート
		String order = " order by s.no asc";

		try {
			// SQL文をセット 学校と学生番号による絞り込み
			statement = connection.prepareStatement(baseSql + condition + order);
			// SQLに学校を入れる
			statement.setString(1, subject.getCd());
			// SQL文に入学年度を入れる
			statement.setInt(2, num);
			// SQLにクラス番号を入れる
			statement.setString(3, school.getCd());
			// SQLに科目番号を入れる
			statement.setInt(4, entYear);
			// SQLにテスト回数を入れる
			statement.setString(5, classNum);

			// SQLの実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school, subject, num);
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL文の入力を終了
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// DBを切断
			if (connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return list;

	}

	// 更新後のデータを受け取って更新をかける
	public boolean save(List<Test> list) throws Exception {
		Connection connection = getConnection();
		// 回数分saveを使う
		// 検索結果を受け取り、リストの要素数を取得して、要素数によってループをかける
		int count = 0;
		// 一個ずつ取り出す
		for (Test test : list) {
			this.save(test, connection);
			count++;
		}
		// DBを切断
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException sqle) {
				throw sqle;
			}
		}
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

	// 更新後のデータを取得する
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;
		// 点数の更新だけ出来る
		// 受け取った点数情報を書き換えることが出来る
		try {
			// SQL文にupdate文を加え、テストの更新を行う
			statement = connection.prepareStatement(
					"update test set point=? where student_no=? and subject_cd=? and school_cd=? and no=?");
			// SQL文の条件文に値をセット
			// 受け取ったテストインスタンスから得点をセット
			statement.setInt(1, test.getPoint());
			statement.setString(2, test.getStudent().getNo());
			statement.setString(3, test.getSubject().getCd());
			statement.setString(4, test.getSchool().getCd());
			statement.setInt(5, test.getNo());
			// SQL文を実行
			count = statement.executeUpdate();

			// 更新された行が0件の場合、追加を実行
			if (count == 0) {
				// 前のstatementを閉じる
				statement.close();

				// 追加処理を行う
				statement = connection.prepareStatement(
						"insert into test(student_no, subject_cd, school_cd, no, point, class_num) values(?, ?, ?, ?, ?, ?)");

				statement.setString(1, test.getStudent().getNo());
				statement.setString(2, test.getSubject().getCd());
				statement.setString(3, test.getSchool().getCd());
				statement.setInt(4, test.getNo());
				statement.setInt(5, test.getPoint());
				statement.setString(6, test.getClassNum());

				// SQL文を実行
				count = statement.executeUpdate();
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL入力を終了
			if (statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}