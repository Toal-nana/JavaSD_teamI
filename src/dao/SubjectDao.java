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
	// 指定した番号が存在しなかったらnullが入る
	public Subject get(String cd) throws Exception {
		Subject subject = new Subject();
		// DBに接続
		Connection connection = getConnection();
		// SQLの準備をする変数
		PreparedStatement statement = null;

		try {
			// SQL文をセット
			statement = connection.prepareStatement("select * from subject where cd=?");
			// SQL文に科目番号を入れる
			statement.setString(1, cd);
			// SQL文を実行
			ResultSet rSet = statement.executeQuery();
			// 学校Daoを初期化 科目インスタンスに学校コードをセットするため
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				// リザルトセットが存在する場合
				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				// 検索で引っかかった科目テーブルから学校番号を持ってきて、セット
				subject.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				// リザルトセットが存在しない場合
				// 科目インスタンスにnullをセット
				subject = null;
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// SQL文を終了
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
		return subject;
	}

	// 学校ごとの科目一覧を検索
	// 学校を指定して科目一覧を取得するメソッド
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		ResultSet rSet = null;
		// SQL文のソート
		String order = "order by cd asc";

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
	public boolean save(Subject subject) throws Exception {
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
				// 科目が存在しなかった場合
				// SQL文にINSERT文をセット(新規登録)
				statement = connection.prepareStatement("insert into subject(school_cd,cd,name) values(?,?,?)");
				// SQL文に値を入れる
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			} else {
				// 科目が存在した場合
				// UPDATE文をセット(上書き保存)
				statement = connection.prepareStatement("update subject set name=? where cd=?");
				// SQL文に値を入れる
				statement.setString(1, subject.getCd());
				statement.setString(2, subject.getName());
			}
			// SQL文を実行し、更新件数を記録
			count = statement.executeUpdate();
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

		//実行できたかどうかの判定
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}


	// 指定した科目インスタンスを削除する
	public boolean delete(Subject subject) throws Exception {
		// DBに接続
		Connection connection = getConnection();
		// SQLを準備する変数
		PreparedStatement statement = null;
		// 実行件数
		int count = 0;

		try {
			// delete文をセット
			statement = connection.prepareStatement("delete from subject where cd=?");
			// 受け取った科目インスタンスの科目番号で削除するテーブルを指定する
			statement.setString(1,subject.getCd());
			//SQL文を実行し、削除件数をカウント
			count = statement.executeUpdate();
		}catch (Exception e) {
			throw e;
		}finally {
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
		// 実行できたかどうかの判定
		if (count > 0) {
			// 実行件数が1件以上ある場合
			return true;
		} else {
			// 実行件数が0件の場合
			return false;
		}
	}
}