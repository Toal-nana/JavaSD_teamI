package tool;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public abstract class CommonServlet {

//	ページごとのGETの処理
	protected abstract String get(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのPOSTの処理
	protected abstract String post(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのlspNameの処理
	protected abstract String getlspName(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのgetSuccessUrlの処理
	protected abstract String getSuccessUrl(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのsetRequestDateの処理
	protected abstract void setRequestDate(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのexecuteGetの処理
	protected abstract String executeGet(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのexecutePostの処理
	protected abstract String executePost(HttpServletRequest req, HttpServletResponse resp) throws Exception;

//	ページごとのexecuteの処理
	protected abstract void execute(HttpServletRequest req, HttpServletResponse resp) throws Exception;
}
