import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;
import java.sql.*;
import java.util.Scanner;

public class books {
    String url = "jdbc:sqlite:Library.db";
    Connection connection = DriverManager.getConnection(url);

    Scanner sc = new Scanner(System.in);

    public books() throws SQLException {
    }



    public String bookname(int sNo) throws SQLException {
        String sql = "SELECT * FROM BOOKS WHERE BNO =" + sNo + ";";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        String bookName = rs.getString("BNAME");
        preparedStatement.close();
        return bookName;
    }

    public void addBook(book b) throws SQLException {
        Connection connection = DriverManager.getConnection(url);

        String insertSQL = "INSERT INTO  BOOKS (BNO , BNAME , BAUTHOR , BQTY , BAQTY ) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setInt(1, b.sNo);
        preparedStatement.setString(2, b.bookName);
        preparedStatement.setString(3, b.authorName);
        preparedStatement.setInt(4, b.bookQty);
        preparedStatement.setInt(5, b.bookQtyCopy);

        preparedStatement.executeUpdate();

        System.out.println("Book added to the database successfully.");

        preparedStatement.close();
        connection.close();


    }

    public void searchBySno() throws SQLException {
        AsciiTable at = new AsciiTable();
        System.out.println(
                "\t\t\t\tSEARCH BY SERIAL NUMBER\n");

        int sNo;
        System.out.println("Enter Serial No of Book:");
        sNo = sc.nextInt();

        at.getContext().setWidth(80);
        at.addRule();
        at.addRow("Book ID", "Book Name", "Author Name", "Available Qty", "Total Qty");

        String sql = "SELECT * FROM BOOKS WHERE BNO =" + sNo + ";";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        at.getContext().setWidth(80);
        at.addRule();
        at.addRow(rs.getInt("BNO"), rs.getString("BNAME"),
                rs.getString("BAUTHOR"), rs.getInt("BAQTY"), rs.getInt("BQTY"));
        at.addRule();
        System.out.println(at.render());

        rs.close();
        preparedStatement.close();

    }

    public void searchByAuthorName() throws SQLException {
        AsciiTable at = new AsciiTable();

        System.out.println(
                "\t\t\t\tSEARCH BY AUTHOR'S NAME");


        System.out.println("Enter Author Name:");
        String authorName = sc.nextLine();


        at.getContext().setWidth(80);
        at.addRule();
        at.addRow("Book ID", "Book Name", "Author Name", "Available Qty", "Total Qty");
        String sql = "SELECT * FROM BOOKS WHERE BAUTHOR = '" + authorName + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        at.getContext().setWidth(80);
        at.addRule();
        at.addRow(rs.getInt("BNO"), rs.getString("BNAME"),
                rs.getString("BAUTHOR"), rs.getInt("BAQTY"), rs.getInt("BQTY"));
        at.addRule();
        System.out.println(at.render());

        rs.close();
        preparedStatement.close();

    }

    public void showAllBooks() throws SQLException {
        String sql = "SELECT * FROM BOOKS";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        AsciiTable at = new AsciiTable();
        at.getContext().setWidth(80);
        at.addRule();
        at.addRow(null, null, null, null, "BOOKS").setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow("Book ID", "Book Name", "Author Name", "Available Qty", "Total Qty");
        while (rs.next()) {
            at.addRule();
            at.addRow(rs.getInt("BNO"), rs.getString("BNAME"),
                    rs.getString("BAUTHOR"), rs.getInt("BAQTY"), rs.getInt("BQTY"));

        }

        at.addRule();
        System.out.println(at.render());

        rs.close();
        preparedStatement.close();
    }

    public void upgradeBookQty() throws SQLException {


        System.out.println(
                "\t\t\t\tUPDATE QUANTITY OF A BOOK\n");
        System.out.print("Enter Serial No of Book: ");
        int sNo = sc.nextInt();

        String sql = "SELECT BQTY,BAQTY FROM BOOKS WHERE BNO = " + sNo + ";";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();


        int bookQty = rs.getInt("BQTY");
        int bookQtyCopy = rs.getInt("BAQTY");
        rs.close();
        preparedStatement.close();

        System.out.println("Enter books to be added : ");
        int n = sc.nextInt();
        bookQty += n;
        bookQtyCopy += n;

        String sql1 = "UPDATE BOOKS SET BQTY = ? ,BAQTY = ? WHERE BNO = ? ;";
        PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
        preparedStatement1.setInt(1, bookQty);
        preparedStatement1.setInt(2, bookQtyCopy);
        preparedStatement1.setInt(3, sNo);
        preparedStatement1.executeUpdate();

        preparedStatement1.close();

    }


    public void dispMenu() {


        System.out.println(
                "----------------------------------------------------------------------------------------------------------");
        System.out.println("0. Exit.");
        System.out.println("1. Add new Book.");
        System.out.println("2. Update Quantity of a Book.");
        System.out.println("3. Search a Book.");
        System.out.println("4. Show All Books.");
        System.out.println("5. Register Student.");
        System.out.println("6. Show All Registered Students.");
        System.out.println("7. Issue Book. ");
        System.out.println("8. Return Book");
        System.out.println(
                "-------------------------------------------------------------------------------------------------------");
    }

    public int isAvailable(int sNo) throws SQLException {
        String sql = "SELECT BAQTY FROM BOOKS WHERE BNO =" + sNo + ";";
        PreparedStatement preparedStatement2 = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement2.executeQuery();

        if (rs.getInt("BAQTY") > 0) {

            System.out.println("Book is Issued.");    //book is available
            int baqty = rs.getInt("BAQTY");
            preparedStatement2.close();
            return baqty;
        }
        System.out.println("Book is Unavailable");
        return -1;

    }

    public String checkOutBook() throws SQLException {

        System.out.print("Enter Serial No of Book to be Checked Out : ");
        int sNo = sc.nextInt();

        int bookIndex = isAvailable(sNo);
//        System.out.println(bookIndex+" is the working of isAvailable!");

        if (bookIndex != -1) {
            String bnm = bookname(sNo);
            String sql = "UPDATE BOOKS SET BAQTY = ? WHERE BNO = ? ;";
            PreparedStatement preparedStatement1 = connection.prepareStatement(sql);
            preparedStatement1.setInt(1, --bookIndex);
            preparedStatement1.setInt(2, sNo);
            preparedStatement1.executeUpdate();
            preparedStatement1.close();
            return bnm;
        } else {
            System.out.println("Book not available!");
            return null;
        }
    }

    public void checkInBook(int sNo) throws SQLException {
        int bookQtyCopy = isAvailable(sNo);
        bookQtyCopy++;
        String sql = "UPDATE BOOKS SET BAQTY = ? WHERE BNO = ? ;";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bookQtyCopy);
        preparedStatement.setInt(2, sNo);
        preparedStatement.executeUpdate();
        preparedStatement.close();

    }
}
