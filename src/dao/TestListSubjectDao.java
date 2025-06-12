package dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	// 学校ごとに表示するために必要
	private String baseSql = "select * from test where school_cd=?";

	private List<TestListSubject> postFilter(ResultSet rSet) throws Exception {
		List<TestListSubject> list = new ArrayList<>();
		try {
			// 科目Daoを初期化
			SubjectDao subjectDao = new SubjectDao();
			// 学生Daoを初期化
			StudentDao studentDao = new StudentDao();
			while (rSet.next()) {
				TestListSubject testListSubject = new TestListSubject();
				// 入学年度をセット
				testListSubject.setEntYear();
				// テストリストインスタンスに検索結果をセット
				testListSubject.setSubjectN(subjectDao.get(rSet.getString("subject_cd")).getName());


				// リストに追加
				list.add(testListSubject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}

	public List<TestListSubject> filter(int entYear,String classNum,Subject subject,School school) {

	}
}