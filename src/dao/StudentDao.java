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


	// 学生番号を指定して学生インスタンスを一件取得するメソッド
	public Student get(String no) throws Exception {
		Student student = new Student();
		// DBに接続
		Connection connection = getConnection();
		// SQLの準備をする変数
		PreparedStatement statement = null;

		try {
			// PrepareStatementにSQL文をセット
			statement = connection.prepareStatement("select * from student where no=?");
			// PrepareStatementに学生番号をバインド
			statement.setString(1, no);
			// PrepareStatementを実行
			ResultSet rSet = statement.executeQuery();
			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学生インスタンスに検索結果をセット
				student.setNo(rSet.getString("no"));
				student.setName(rSet.getString("name"));
				student.setEntYear(rSet.getInt("ent_year"));
				student.setClassNum(rSet.getString("class_num"));
				student.setAttend(rSet.getBoolean("is_attend"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				student = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// PrepareStatementを閉じる
			if (statement != null) {
				try{
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try{
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		return student;
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

	//学生管理で使用
	//学校、入学年度、クラス番号、在学フラグを指定して学生の一覧を取得するメソッド
	public List<Student> filter(School school,int entYear,String classNum,boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQL文を準備する変数
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
			// PreparedStatementを閉じる
			if (statement != null) {
				try{
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
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

	// 学校、入学年度、在学フラグを指定して学生の一覧を取得するメソッド
	public List<Student> filter(School school,int entYear,boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文の条件
		String condition = "and ent_year=?";
		// SQL文のソート
		String order = "order by no asc";

		// SQL文の在学フラグ
		String conditionIsAttend = "";
		// 在学フラグがtrueだった場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		try {
			// PrepareStatementにSQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// PrepareStatementに学校コードをバインド
			statement.setString(1, school.getCd());
			// PrepareStatementに入学年度をバインド
			statement.setInt(2, entYear);
			// PrepareStatementを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			// PrepareStatementを閉じる
			if (statement != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
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

	// 学校、在学フラグを指定して学生の一覧を取得するメソッド
	public List<Student> filter(School school,boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文の条件
		String order = "order by no asc";

		// SQL文の在学フラグ
		String conditionIsAttend = "";
		// 在学フラグがtrueだった場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		try {
			// PrepareStatementにSQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			// PrepareStatementに学校コードをバインド
			statement.setString(1, school.getCd());
			// PrepareStatementを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			// PrepareStatementを閉じる
			if (statement != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
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

	// 学生インスタンスをDBに保存するメソッド
	public boolean save(Student student) throws Exception {
		// DBに接続
		Connection connection = getConnection();
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			//DBから学生を取得
			Student old = get(student.getNo());
			if (old == null) {
				// 学生が存在しなかった場合
				// PreparedStatementにINSERT文をセット
				statement = connection.prepareStatement("insert into student(no,name,ent_year,class_num,is_attend,school_cd) values(?,?,?,?,?,?)");
				// PreparedStatementに値をバインド
				statement.setString(1, student.getNo());
				statement.setString(2, student.getName());
				statement.setInt(3, student.getEntYear());
				statement.setString(4, student.getClassNum());
				statement.setBoolean(5, student.isAttend());
				statement.setString(6, student.getSchool().getCd());
			} else {
				// 学生が存在した場合
				// PreparedStatementにUPDATE文をセット
				statement = connection.prepareStatement("update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				// PreparedStatementに値をバインド
				// PreparedStatementに値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
			}
			// PreparedStatementを実行
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			// PrepareStatementを閉じる
			if (statement != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
			// コネクションを閉じる
			if (connection != null) {
				try {
					connection.close();
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
