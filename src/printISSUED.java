import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;

import java.sql.*;

public class printISSUED {
    public static void main(String[] args) throws SQLException {
        String url = "jdbc:sqlite:Library.db";
        Connection connection = DriverManager.getConnection(url);
        String sql = "SELECT * FROM ISSUED WHERE SNO = '229301386';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();


        AsciiTable at = new AsciiTable();
        at.getContext().setWidth(80);
        at.addRule();
        at.addRow(null, null, null, "BOOKS ISSUED").setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow("Student ID", "Student Name", "Book 1", "Book 2");
        at.addRule();
        at.addRow(rs.getString("SNO"), rs.getString("SNAME"), rs.getString("B1"), rs.getString("B2"));
        at.addRule();
        System.out.println(at.render());
        rs.close();
        preparedStatement.close();
    }
}
