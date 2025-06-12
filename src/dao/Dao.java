package dao;

import java.sql.Connection;

import javax.naming.InitialContext;
import javax.sql.DataSource;

public class Dao {
	static DataSource ds;

	public Connection getConnection() throws Exception {
		if (ds==null) {
//			接続設定をcontext.xmlから取得する
			InitialContext ic = new InitialContext();

//			繋ぐDBをきめる
			ds = (DataSource)ic.lookup("java:/comp/env/jdbc/teami");
		}
		//DBへの接続
		return ds.getConnection();
	}
}
