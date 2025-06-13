package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;

public class ClassNumDao extends Dao {

	// 学校ごとのクラス一覧をリストで取得
	public List<String> filter(School school) throws Exception {
		List<String> list = new ArrayList<>();
	// DBに接続
	Connection connection = getConnection();
	// SQLを準備する変数
	PreparedStatement statement = null;
	ResultSet rSet = null;
	// SQL文のソート
	String order = "order by class_num asc";

	try {
		// SQL文を準備 学校コードによる絞り込み
		statement = connection.prepareStatement("select * from class_num where school_cd=?" + order);
		// SQL文に学校コードを入れる
		statement.setString(1, school.getCd());
		// SQL文の実行
		rSet = statement.executeQuery();

		// 実行結果をリストに格納
		while (rSet.next()) {
			list.add(rSet.getString("class_num"));
		}
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
}
