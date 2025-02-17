import java.sql.*;

public class BooksBorrowed {
    public static void main(String args[]) throws SQLException {

        String url = "jdbc:sqlite:Library.db";
        Connection connection = DriverManager.getConnection(url);
        String insertSQL = "CREATE TABLE IF NOT EXISTS ISSUED(" +
                "SNO  VARCHAR(20)," +
                "SNAME VARCHAR(50)," +
                "B1 VARCHAR(20)," +
                "B2  VARCHAR(20),"+
                "B3  VARCHAR(20) );";
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
