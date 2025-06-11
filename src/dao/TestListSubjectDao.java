package dao;

import java.sql.ResultSet;
import java.util.List;

import bean.School;
import bean.Subject;
import bean.TestListSubject;

public class TestListSubjectDao extends Dao {
	private String baseSql;

	private List<TestListSubject> postFilter(ResultSet rSet) {

	}

	public List<TestListSubject> filter(int entYear,String classNum,Subject subject,School school) {

	}
}