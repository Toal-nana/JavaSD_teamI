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
			// SQL文をセット
			statement = connection.prepareStatement("select * from student where no=?");
			// SQL文に学生番号を入れる
			statement.setString(1, no);
			// SQL文を実行
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
				student.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				student = null;
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
		return student;
	}

	//検索後のリストへの格納処理をするメソッド
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
			// SQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// SQL文に学校コードを入れる
			statement.setString(1,school.getCd());
			// SQL文に入学年度を入れる
			statement.setInt(2, entYear);
			// SQL文にクラス番号を入れる
			statement.setString(3, classNum);
			// プライベートステートメントを実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
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
			// SQL文をセット
			statement = connection.prepareStatement(baseSql + condition + conditionIsAttend + order);
			// SQL文に学校コードを入れる
			statement.setString(1, school.getCd());
			// SQL文に入学年度を入れる
			statement.setInt(2, entYear);
			// SQL文を実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL文の入力を終了
			if (statement != null) {
				try {
					connection.close();
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

	// 学校、在学フラグを指定して学生の一覧を取得するメソッド
	public List<Student> filter(School school,boolean isAttend) throws Exception {
		List<Student> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文のソート
		String order = "order by no asc";

		// SQL文の在学フラグ
		String conditionIsAttend = "";
		// 在学フラグがtrueだった場合
		if (isAttend) {
			conditionIsAttend = "and is_attend=true";
		}
		try {
			// SQL文をセット
			statement = connection.prepareStatement(baseSql + conditionIsAttend + order);
			// SQL文に学校コードを入れる
			statement.setString(1, school.getCd());
			// SQL文を実行
			rSet = statement.executeQuery();
			// リストへの格納処理を実行
			list = postFilter(rSet, school);
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL入力を終了
			if (statement != null) {
				try {
					connection.close();
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


	// 学生インスタンスをDBに保存するメソッド
	// 学生の変更と更新が出来る
	// 変更件数が1件以上だとtrue、0件だとfalseを返す
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
				// PreparedStatementにINSERT文をセット(新規登録)
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
				// PreparedStatementにUPDATE文をセット(上書き保存)
				statement = connection.prepareStatement("update student set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				// PreparedStatementに値をバインド
				// PreparedStatementに値をバインド
				statement.setString(1, student.getName());
				statement.setInt(2, student.getEntYear());
				statement.setString(3, student.getClassNum());
				statement.setBoolean(4, student.isAttend());
				statement.setString(5, student.getNo());
			}
			// SQL文を実行
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL入力を終了
			if (statement != null) {
				try {
					connection.close();
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

		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}

}
