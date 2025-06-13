package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import bean.Teacher;

public class TeacherDao extends Dao {

	// idで指定した先生インスタンスを返す
	public Teacher get(String id) throws Exception {
		Teacher teacher = new Teacher();
		// DBに接続
		Connection connection = getConnection();
		// SQLの準備をする変数
		PreparedStatement statement = null;

		try {
			// SQL文をセット 先生番号で絞り込み
			statement = connection.prepareStatement("select * from teacher where id=?");
			// SQL文に先生番号を入れる
			statement.setString(1, id);
			// SQL文を実行
			ResultSet rSet = statement.executeQuery();
			// 学校Daoを初期化
			SchoolDao schoolDao = new SchoolDao();

			if (rSet.next()) {
				// 検索に引っかかる先生がいた場合
				// 先生インスタンスに検索結果をセット
				teacher.setId(rSet.getString("id"));
				teacher.setName(rSet.getString("name"));
				// 学校フィールドには学校コードで検索した学校インスタンスをセット
				teacher.setSchool(schoolDao.get(rSet.getString("school_cd")));
			} else {
				// 検索に一件も引っかからなかった場合
				// 先生インスタンスにnullをセット
				teacher = null;
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
		return teacher;
	}



	public Teacher login(String id, String password) throws Exception {
		// idに対応する先生インスタンスを取得
		 Teacher teacher = new Teacher();
		 TeacherDao teacherDao = new TeacherDao();
		 teacher = teacherDao.get(id);
		if (teacher == null) {
			return null; // ユーザーが存在しない場合
		}

		// idでハッシュを生成
		String hashedInput = id;

		// ハッシュが一致する場合はログイン成功
		if (hashedInput==id) {
			return teacher;
		} else {
			return null;
		}
	}
}
