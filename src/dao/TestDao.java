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
		// 処理対象がなければ何もしない
	    if (list == null || list.isEmpty()) {
	        return false;
	    }

	    Connection connection = getConnection();
	    boolean result = false;

	    try {
	        // トランザクションを開始
	        connection.setAutoCommit(false);

	        // リストの要素を一つずつ処理
	        for (Test test : list) {
	            // 内部用のsaveメソッドを呼び出す
	            this.save(test, connection);
	        }

	        // 全ての処理が成功したらコミット
	        connection.commit();
	        result = true;

	    } catch (Exception e) {
	        // エラーが発生したらロールバック
	        connection.rollback();
	        throw e; // エラーを呼び出し元にスロー
	    } finally {
	        // 後処理
	        if (connection != null) {
	            try {
	                connection.setAutoCommit(true); // オートコミットをデフォルトに戻す
	                connection.close(); // コネクションを閉じる
	            } catch (SQLException sqle) {
	                // ここでの例外は無視して良い
	            }
	        }
	    }
	    return result;
	}

	// 更新後のデータを取得する
	private boolean save(Test test, Connection connection) throws Exception {
		PreparedStatement statement = null;
	    int count = 0;

	    try {
	        // ★ Step 1: 削除フラグをチェック
	        if (test.isToDelete()) {
	            // 削除処理
	            statement = connection.prepareStatement(
	                "DELETE FROM test WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?");
	            statement.setString(1, test.getStudent().getNo());
	            statement.setString(2, test.getSubject().getCd());
	            statement.setString(3, test.getSchool().getCd());
	            statement.setInt(4, test.getNo());

	            count = statement.executeUpdate();

	        } else {
	            // ★ Step 2: 既存の登録・更新処理
	            // まずUPDATEを試みる
	            statement = connection.prepareStatement(
	                    "UPDATE test SET point=? WHERE student_no=? AND subject_cd=? AND school_cd=? AND no=?");
	            statement.setInt(1, test.getPoint());
	            statement.setString(2, test.getStudent().getNo());
	            statement.setString(3, test.getSubject().getCd());
	            statement.setString(4, test.getSchool().getCd());
	            statement.setInt(5, test.getNo());

	            count = statement.executeUpdate();

	            // UPDATEで更新された行が0件なら、INSERTを実行
	            if (count == 0) {
	                // 古いstatementを閉じてから新しいものを作成
	                statement.close();

	                statement = connection.prepareStatement(
	                        "INSERT INTO test(student_no, subject_cd, school_cd, no, point, class_num) VALUES(?, ?, ?, ?, ?, ?)");
	                statement.setString(1, test.getStudent().getNo());
	                statement.setString(2, test.getSubject().getCd());
	                statement.setString(3, test.getSchool().getCd());
	                statement.setInt(4, test.getNo());
	                statement.setInt(5, test.getPoint());
	                statement.setString(6, test.getClassNum());

	                count = statement.executeUpdate();
	            }
	        }
	    } catch (Exception e) {
	        throw e; // エラーは呼び出し元のトランザクション処理に任せる
	    } finally {
	        // statementは毎回閉じる
	        if (statement != null) {
	            try {
	                statement.close();
	            } catch (SQLException sqle) {
	                throw sqle;
	            }
	        }
	    }

	    return count > 0;
	}
}