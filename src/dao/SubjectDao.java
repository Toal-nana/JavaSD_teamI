package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;

public class SubjectDao extends Dao {


	// 科目番号を指定して科目インスタンスを一件取得するメソッド
	public Subject get(String cd, School school) {
		Subject subject = new Subject();
		// DBに接続
		Connection connection = getConnection();
		// SQLの準備をする変数
		PreparedStatement statement = null;

		try {
			// SQL文をセット
			statement = connection.prepareStatement("select * from subject where no=?");
			// PrepareStatementに学生番号を入れる
			statement.setString(1, no);
			// PrepareStatementを実行
			ResultSet rSet = statement.executeQuery();
			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 学生インスタンスに検索結果をセット
				subject.setNo(rSet.getString("no"));
				subject.setName(rSet.getString("name"));
				subject.setEntYear(rSet.getInt("ent_year"));
				subject.setClassNum(rSet.getString("class_num"));
				subject.setAttend(rSet.getBoolean("is_attend"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 学生インスタンスにnullをセット
				subject = null;
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
		return subject;
	}

	// 学校を指定して科目一覧を取得するメソッド
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文のソート
		String order = "order by no asc";

		try {
			// SQL文を準備
			statement = connection.prepareStatement("select * from subject where school_cd=?" + order);
			// SQL文に学校コードを入れる
			statement.setString(1, school.getCd());
			// SQL文の実行
			rSet = statement.executeQuery();

			// 実行結果をリストに格納
			while (rSet.next()) {
				Subject subject = new Subject();
				subject.setSchool(school);
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				list.add(subject);
			}
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
			// connectionを閉じる
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


	// 科目インスタンスをDBに保存するメソッド
	// 科目の変更と更新が出来る
	// 変更件数が1件以上だとtrue、0件だとfalseを返す
	public boolean save(Subject subject) {
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			//DBから学生を取得
			Subject old = get(subject.getCd());
			if (old == null) {
				// 学生が存在しなかった場合
				// PreparedStatementにINSERT文をセット(新規登録)
				statement = connection.prepareStatement("insert into subject(no,name,ent_year,class_num,is_attend,school_cd) values(?,?,?,?,?,?)");
				// PreparedStatementに値をバインド
				statement.setString(1, subject.getNo());
				statement.setString(2, subject.getName());
				statement.setInt(3, subject.getEntYear());
				statement.setString(4, subject.getClassNum());
				statement.setBoolean(5, subject.isAttend());
				statement.setString(6, subject.getSchool().getCd());
			} else {
				// 学生が存在した場合
				// UPDATE文をセット(上書き保存)
				statement = connection.prepareStatement("update subject set name=?, ent_year=?, class_num=?, is_attend=? where no=?");
				// PreparedStatementに値をバインド
				// PreparedStatementに値をバインド
				statement.setString(1, subject.getName());
				statement.setInt(2, subject.getEntYear());
				statement.setString(3, subject.getClassNum());
				statement.setBoolean(4, subject.isAttend());
				statement.setString(5, subject.getNo());
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

	public boolean delete(Subject subject) {

	}
}