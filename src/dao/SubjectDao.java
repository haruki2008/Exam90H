package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import bean.School;
import bean.Subject;
import bean.Teacher;

public class SubjectDao extends Dao {

	public Subject get(String subjectCd) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 科目インスタンスを初期化
		Subject subject = new Subject();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from subject where cd=?");
			// プリペアードステートメントにをバインド
			statement.setString(1, subjectCd);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
		    SchoolDao schoolDao = new SchoolDao ();


			if (rSet.next()) {
				// リザルトセットが存在する場合

				// 学校フィールドには学校コードで検索した学校インスタンスをセット
			    subject.setSchool(schoolDao.get(rSet.getString("school_cd")));

				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
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

	public Subject get(String subjectCd,School school) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 科目インスタンスを初期化
		Subject subject = new Subject();



		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from subject where school_cd = ? and cd=?");
			// プリペアードステートメントにをバインド
			statement.setString(1, school.getCd());
			statement.setString(2, subjectCd);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
		    SchoolDao schoolDao = new SchoolDao ();


			if (rSet.next()) {
				// リザルトセットが存在する場合

				// 学校フィールドには学校コードで検索した学校インスタンスをセット
			    subject.setSchool(schoolDao.get(rSet.getString("school_cd")));

				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
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


	public Subject get2(String subjectName) throws Exception {

		// データベースへのコネクションを確立
		Connection connection = getConnection();
		// プリペアードステートメント
		PreparedStatement statement = null;
		// 科目インスタンスを初期化
		Subject subject = new Subject();

		try {
			// プリペアードステートメントにSQL文をセット
			statement = connection.prepareStatement(
					"select * from subject where name=?");
			// プリペアードステートメントにをバインド
			statement.setString(1, subjectName);
			// プリペアードステートメントを実行
			ResultSet rSet = statement.executeQuery();

			// 学校Daoを初期化
		    SchoolDao schoolDao = new SchoolDao ();


			if (rSet.next()) {
				// リザルトセットが存在する場合

				// 学校フィールドには学校コードで検索した学校インスタンスをセット
			    subject.setSchool(schoolDao.get(rSet.getString("school_cd")));

				// 科目インスタンスに検索結果をセット
				subject.setCd(rSet.getString("cd"));
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




	private String baseSql = "select * from subject where school_cd=? ";

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
				subject.setCd(rSet. getString("cd"));
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
	    String order = " order by length (cd) asc";


	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement (baseSql + condition + order);
		    //プリペアードステートメントに学校コードをバインド
		    statement. setString(1, school.getCd ());
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
	 *      科目cdのリスト List<Subject> ない場合は空(0件)のリスト
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
	    String order = " order by length(cd),cd asc";


	    try {
		    //プリペアードステートメントにSQL文をセット
		    statement = connection. prepareStatement (baseSql + order);

					    //プリペアードステートメントに学校コードをバインド
		    statement. setString(1, school.getCd());
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


	/**
	 *
	 * @param school  //学校
	 * @return
	 * 			//科目のリスト List<Subject> ない場合は空(0件)のリスト
	 * @throws Exception
	 */
	public List<String> filter3(School school) throws Exception {
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
			statement = connection.prepareStatement(
					"select name from subject where school_cd = ? ");

			//プレースホルダー（？の部分）に値を設定
			statement.setString(1,school.getCd());

			// プリペアードステートメントを実行
			// SQL文を実行する
			// 結果はリザルトセット型となる
			ResultSet rSet = statement.executeQuery();

			// リザルトセット（結果）を全件走査
			while (rSet.next()){
				// 戻り値用のlistに科目名を追加
				list.add(rSet.getString("name"));
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


	/**
	 *
	 * @param school
	 * @return
	 * @throws Exception
	 */
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
			statement = connection.prepareStatement(
					"select cd from subject where school_cd = ? order by length(cd),cd asc");

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


	public boolean save(Subject subject,HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ


		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

        Subject data = get(subject.getCd(),teacher.getSchool());

		//コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //実行件数
	    int count = 0;

	    try{

	        if (data == null) {


	        	System.out.println(teacher.getSchool().getCd());
	            //科目IDが存在しなかった場合
	            //プリペアードステートメンにINSERT文をセット
	            statement = connection. prepareStatement (
	            "insert into subject (school_cd, cd, name) values(?, ?, ?)");
	            //プリペアードステートメントに値をバインド
	            statement. setString(1, teacher.getSchool().getCd());
	            statement. setString (2, subject. getCd ());
	            statement. setString (3, subject. getName ());


	        } else {

	            //科目が存在した場合
	            //プリペアードステートメントにUPDATE文をセット
	            statement = connection
	            		.prepareStatement (
	                "update subject set name =? where school_cd = ? and cd = ? ");
	            //プリペアードステートメントに値をバインド
	            statement. setString(1, subject. getName());
	            statement. setString(2, teacher.getSchool().getCd());
	            statement. setString (3, subject. getCd());

	        }
	        //プリペアードステートメントを実行
	        count = statement.executeUpdate();

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

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	        } else {
	        //実行件数が0件の場合
	        return false;
	        }
	}


	public boolean delete(Subject subject,HttpServletRequest req, HttpServletResponse res) throws Exception {
		// TODO 自動生成されたメソッド・スタブ

		HttpSession session = req.getSession();//セッション
		Teacher teacher = (Teacher) session.getAttribute("user");// ログインユーザーを取得

		//コネクションを確立
	    Connection connection = getConnection();
	    //プリペアードステートメント
	    PreparedStatement statement = null;
	    //実行件数
	    int count = 0;

	    try{
	        //データベースから科目データを取得
	        Subject data = get(subject.getCd(), teacher.getSchool());

            //プリペアードステートメントにdelete文をセット
	        if(data != null){
            statement = connection
            		.prepareStatement (
                "delete from subject where name = ? and school_cd = ? and cd = ? ");

            //プリペアードステートメントに値をバインド
            statement. setString(1, subject. getName ());
            statement. setString(2, teacher.getSchool (). getCd ());
            statement. setString (3, subject. getCd ());

	        }

	        //プリペアードステートメントを実行
	        count = statement.executeUpdate();

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

	    if (count > 0) {
	        // 実行件数が1件以上ある場合
	        return true;
	        } else {
	        //実行件数が0件の場合
	        return false;
	        }
	}

}
