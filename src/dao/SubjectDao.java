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

	// cdとschoolで指定した科目を科目インスタンスにして一件返す
	public Subject get(String cd, School school) throws Exception {
		Subject subject = null;
		Connection connection = getConnection();
		PreparedStatement statement = null;
		ResultSet rSet = null;

		try {
			statement = connection.prepareStatement("select * from subject where cd=? and school_cd=?");
			statement.setString(1, cd);
			statement.setString(2, school.getCd());
			rSet = statement.executeQuery();

			if (rSet.next()) {
				subject = new Subject();
				subject.setCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));
				subject.setSchool(school);
			}
		} catch (Exception e) {
			throw e;
		} finally {
			if (rSet != null) { try { rSet.close(); } catch (SQLException sqle) { throw sqle; } }
			if (statement != null) { try { statement.close(); } catch (SQLException sqle) { throw sqle; } }
			if (connection != null) { try { connection.close(); } catch (SQLException sqle) { throw sqle; } }
		}
		return subject;
	}

	// 学校ごとの科目一覧を検索
	public List<Subject> filter(School school) throws Exception {
		List<Subject> list = new ArrayList<>();
		Connection connection = getConnection();
		PreparedStatement statement = null;
        ResultSet rSet = null;
		String order = " order by cd asc";

		try {
			statement = connection.prepareStatement("select * from subject where school_cd=?" + order);
			statement.setString(1, school.getCd());
			rSet = statement.executeQuery();

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
            if (rSet != null) { try { rSet.close(); } catch (SQLException sqle) { throw sqle; } }
			if (statement != null) { try { statement.close(); } catch (SQLException sqle) { throw sqle; } }
			if (connection != null) { try { connection.close(); } catch (SQLException sqle) { throw sqle; } }
		}
		return list;
	}

	// 科目インスタンスをDBに保存するメソッド
	public boolean save(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			// getメソッドで存在確認してからUPDATE/INSERTを分岐
			Subject old = get(subject.getCd(), subject.getSchool());
			if (old == null) {
				// 新規登録
				statement = connection.prepareStatement("insert into subject(school_cd,cd,name) values(?,?,?)");
				statement.setString(1, subject.getSchool().getCd());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getName());
			} else {
				// 更新
				statement = connection.prepareStatement("update subject set name=? where cd=? and school_cd=?");
				statement.setString(1, subject.getName());
				statement.setString(2, subject.getCd());
				statement.setString(3, subject.getSchool().getCd());
			}
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) { try { statement.close(); } catch (SQLException sqle) { throw sqle; } }
			if (connection != null) { try { connection.close(); } catch (SQLException sqle) { throw sqle; } }
		}
		return count > 0;
	}

	// 指定した科目レコードを削除する
	public boolean delete(Subject subject) throws Exception {
		Connection connection = getConnection();
		PreparedStatement statement = null;
		int count = 0;

		try {
			statement = connection.prepareStatement("delete from subject where cd=? and school_cd=?");
			statement.setString(1, subject.getCd());
			statement.setString(2, subject.getSchool().getCd());
			count = statement.executeUpdate();
		} catch (Exception e) {
			throw e;
		} finally {
			if (statement != null) { try { statement.close(); } catch (SQLException sqle) { throw sqle; } }
			if (connection != null) { try { connection.close(); } catch (SQLException sqle) { throw sqle; } }
		}
		return count > 0;
	}
}