package kadai_007;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Posts_Chapter07 {

	    public static void main(String[] args) {
	        String url = "jdbc:mysql://localhost:3306/challenge_java";
	        String user = "root"; 
	        String password = "Ogihara0833@"; 

	        // データ追加用SQL
	        String insertSql = "INSERT INTO posts (user_id, posted_at, post_content, likes) VALUES "
	                         + "(1003, '2023-02-08', '昨日の夜は徹夜でした・・', 13),"
	                         + "(1002, '2023-02-08', 'お疲れ様です！', 12),"
	                         + "(1003, '2023-02-09', '今日も頑張ります！', 18),"
	                         + "(1001, '2023-02-09', '無理は禁物ですよ！', 17),"
	                         + "(1002, '2023-02-10', '明日から連休ですね！', 20);";

	        try (Connection conn = DriverManager.getConnection(url, user, password);
	             PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {

	            
	            System.out.println("データベース接続成功：" + conn);

	            // レコード追加
	            System.out.println("レコード追加を実行します");
	            int rowsInserted = insertStmt.executeUpdate();
	            System.out.println(rowsInserted + "件のレコードが追加されました");

	            // ユーザーID1002のレコード検索
	            searchPosts(conn, 1002);

	        } catch (SQLException e) {
	            e.printStackTrace();
	        }
	    }

	   
	    public static void searchPosts(Connection conn, int userId) throws SQLException {
	        String selectSql = "SELECT posted_at, post_content, likes FROM posts WHERE user_id = ?";
	        try (PreparedStatement pstmt = conn.prepareStatement(selectSql)) {
	            pstmt.setInt(1, userId);
	            ResultSet rs = pstmt.executeQuery();

	            System.out.println("ユーザーIDが" + userId + "のレコードを検索しました");
	            int count = 1;
	            while (rs.next()) {
	                System.out.println(count + "件目：投稿日時=" + rs.getDate("posted_at") +
	                                   "／投稿内容=" + rs.getString("post_content") +
	                                   "／いいね数=" + rs.getInt("likes"));
	                count++;
	            }
	        }
	    }
}
