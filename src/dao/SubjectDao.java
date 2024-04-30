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

	public Subject get(String cd) throws Exception {
		// 科目インスタンスを初期化
		Subject subject = new Subject();
		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from school where cd=?");
			// プリペアードステートメントにをバインド
			statement.setString(1, cd);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
		    SchoolDao schoolDao = new SchoolDao ();


			if (rSet.next()) {
				// リザルトセットが存在する場合

				// 学校フィールドには学校コードで検索した学校インスタンスをセット
			    subject.setSchool(schoolDao.get(rSet.getString("school_cd")));

				// 科目インスタンスに検索結果をセット
				subject.setSubjectCd(rSet.getString("cd"));
				subject.setName(rSet.getString("name"));

			} else {
				// 存在しない場合
				// 科目インスタンスにnullをセット
				subject = null;
			}

		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
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
		return subject;
	}

	private String baseSql = "select * from subject where cd=? ";

	private List<Subject> postFilter(ResultSet rSet, School school) throws Exception {
//		//まずはここに処理追加
		//戻り値用のリスト
		List<Subject> list = new ArrayList<>();
		try{
			while(rSet.next()) {
				//科目インスタンスを初期化
				Subject subject = new Subject();
				//科目インスタンスに検索結果をセット
				subject.setSchool (school) ;
				subject.setSubjectCd(rSet. getString("cd"));
				subject.setName(rSet. getString("name"));
				//リストに追加
				list.add(subject);
			}
		} catch (SQLException | NullPointerException e) {
			e.printStackTrace();
		}
		return list;
	}




	/**
	 *
	 * @param school  //学校
	 * @param cd  //科目ID
	 * @return
	 *      科目のリスト List<Subject> ない場合は空(0件)のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school, String cd) throws Exception {
		//リストを初期化
	    List<Subject> list = new ArrayList<>();
	    //コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //リザルトセット
	    ResultSet rSet = null;
	    //SQL文の条件
	    String condition = "and cd =? ";
	    //SQL文のソートー
	    String order = " order by cd asc";


	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement (baseSql + condition + order);
		    //プリペアードステートメントに学校コードをバインド
		    statement. setString(1, school. getCd ());
		    // プリペアードステートメントに科目IDをバインド
		    statement. setString(2, cd) ;

		    // プライベートステートメントを実行
		    rSet = statement.executeQuery ();
		    //帰ってきたResultSet型をStudent型に変えて結果をセットする
		    list = postFilter(rSet,school);
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			// コネクションを閉じる
			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//listを返す
		return list;
	}


	/**
	 *
	 * @param school  //学校
	 * @return
	 *      科目のリスト List<Subject> ない場合は空(0件)のリスト
	 * @throws Exception
	 */
	public List<Subject> filter(School school) throws Exception {
		//リストを初期化
	    List<Subject> list = new ArrayList<>();
	    //コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //リザルトセット
	    ResultSet rSet = null;
	    //SQL文のソート
	    String order = " order by cd asc";


	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement (baseSql + order);
		    //プリペアードステートメントに学校コードをバインド
		    statement. setString(1, school. getCd ());
		    // プライベートステートメントを実行
		    rSet = statement.executeQuery ();
		    list = postFilter(rSet,school);
		} catch (Exception e) {
			throw e;
		} finally {
			//
			if(statement != null) {
				try {
					statement.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}

			if(connection != null) {
				try {
					connection.close();
				} catch (SQLException sqle) {
					throw sqle;
				}
			}
		}
		//listを返す
		return list;
	}


	public List<String> filter2(School school) throws Exception {
		// 戻り値用のリストを作成
		// new演算子とArrayListで空のListを用意
		List<String> list = new ArrayList<>();

		// データベースへのコネクションを確立
		// データベースに接続された！！
		Connection connection = getConnection();

		// プリペアードステートメント
		PreparedStatement statement = null;

		try {

			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement("select cd from subject where school_cd = ?");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1,school.getCd());

			// プリペアードステートメントを実行
			// SQL文を実行する
			// 結果はリザルトセット型となる
			ResultSet rSet = statement.executeQuery();

			// リザルトセット（結果）を全件走査
			while (rSet.next()){
				// 戻り値用のlistに科目IDを追加
				list.add(rSet.getString("cd"));
			}
		} catch (Exception e) {
			throw e;
		} finally {
			// プリペアードステートメントを閉じる
			if (statement != null) {
				try {
					statement.close();
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

		return list;

	}

}