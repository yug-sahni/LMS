import java.sql.*;

public class BookDatabase {
    public static void main(String args[]) throws SQLException {

        String url = "jdbc:sqlite:Library.db";
        Connection connection = DriverManager.getConnection(url);
        String insertSQL = "CREATE TABLE IF NOT EXISTS BOOKS(" +
                "BNO INT," +
                "BNAME VARCHAR(50)," +
                "BAUTHOR VARCHAR(50)," +
                "BQTY INT,"+
                "BAQTY INT );";
//        String insertSQL = "drop table BOOKS;";

        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.executeUpdate();
        preparedStatement.close();
        connection.close();

//        try {
//            Connection conn = DriverManager.getConnection(url);
//            if (conn != null) {
//                DatabaseMetaData meta = conn.getMetaData();
//                System.out.println("The driver name is " + meta.getDriverName());
//                System.out.println("A new database has been created.");
//            }
//
//        } catch (SQLException e) {
//            System.out.println(e.getMessage());
//            System.out.println("An error occurred while connecting MySQL database");
//        }
}}
