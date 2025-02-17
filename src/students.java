import de.vandermeer.asciitable.AsciiTable;
import de.vandermeer.skb.interfaces.transformers.textformat.TextAlignment;


import java.sql.*;
import java.util.Scanner;
public class students {
    String url = "jdbc:sqlite:Library.db";
    Connection connection = DriverManager.getConnection(url);
    Scanner sc = new Scanner(System.in);

    public students() throws SQLException {
    }

    public int getBookQTY(String sID) throws SQLException {
        String sql = "SELECT * FROM STUDENTS WHERE SNO ='" + sID + "';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        int bookQTY = rs.getInt("SQTY");
        preparedStatement.close();
        return bookQTY;
    }
    public void addStudent(student s) throws SQLException {
        Connection connection = DriverManager.getConnection(url);

        String insertSQL = "INSERT INTO  STUDENTS (SNO , SNAME , SQTY ) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = connection.prepareStatement(insertSQL);
        preparedStatement.setString(1, s.sNo);
        preparedStatement.setString(2, s.sName);
        preparedStatement.setInt(3, s.sQty);

        preparedStatement.executeUpdate();

        System.out.println("Student added to the database successfully.");

        preparedStatement.close();
//        connection.close();

        String iSQL = "INSERT INTO  ISSUED (SNO , SNAME , B1 , B2, B3 ) VALUES (?, ?, ?, ?, ?)";
        preparedStatement = connection.prepareStatement(iSQL);
        preparedStatement.setString(1, s.sNo);
        preparedStatement.setString(2, s.sName);
        preparedStatement.setString(3,null);
        preparedStatement.setString(4,null);
        preparedStatement.setString(5, null);

        preparedStatement.executeUpdate();
        connection.close();
    }

    public void showAllStudents() throws SQLException {
        String sql = "SELECT * FROM STUDENTS";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();
        AsciiTable at = new AsciiTable();
        at.getContext().setWidth(80);
        at.addRule();
        at.addRow(null, null, "BOOKS").setTextAlignment(TextAlignment.CENTER);
        at.addRule();
        at.addRow("Student ID", "Student Name", "Books Issued");
        while (rs.next()) {
            at.addRule();
            at.addRow(rs.getString("SNO"), rs.getString("SNAME"),
                    rs.getInt("SQTY"));

        }

        at.addRule();
        System.out.println(at.render());

        rs.close();
        preparedStatement.close();

    }

    public String isStudent() throws SQLException {
//        String sid = sc.nextLine();
        System.out.println("Enter Reg Number:");
        String regNum = sc.nextLine();
        String sql = "SELECT SNO FROM STUDENTS WHERE SNO = '"+regNum+"';";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ResultSet rs = preparedStatement.executeQuery();

        String num = rs.getString("SNO");
//        System.out.println(num+ " is the reg no ");
        if (num.equalsIgnoreCase(regNum)) {
            preparedStatement.close();
                return num;

        }
        System.out.println("Student is not Registered.");
        System.out.println("Get Registered First.");

        return null;

    }
//
    public void checkOutBook(books book) throws SQLException {
        String studentIndex = this.isStudent();
        if (studentIndex != null) {
            String sql = "SELECT SNAME,SQTY FROM STUDENTS WHERE SNO = '" + studentIndex + "';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();
            int sqty = rs.getInt("SQTY");
            String stuname = rs.getString("SNAME");
            rs.close();
            preparedStatement.close();
            book.showAllBooks();
            String b = book.checkOutBook();

            if (b != null) {

                if (sqty < 3) {
//                    String sql1 = "UPDATE STUDENTS SET SQTY = ? WHERE SNO = '?' ;";
                    String sql1 = "UPDATE STUDENTS SET SQTY = "+(++sqty)+" WHERE SNO = '"+studentIndex+"' ;";
                    PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
//                    System.out.println(studentIndex+" is student index");
//                    preparedStatement1.setInt(1, --sqty);
//                    preparedStatement1.setString(2, studentIndex);
                    preparedStatement1.executeUpdate();
                    preparedStatement1.close();
                    switch(sqty){
                        case 1:
                            String insertSQL1 = "UPDATE ISSUED SET B1 ='"+b+"' WHERE SNAME = '"+stuname+"';";
                            preparedStatement1 = connection.prepareStatement(insertSQL1);
                            preparedStatement1.executeUpdate();
                            preparedStatement1.close();
                            break;
                        case 2:
                            String insertSQL2 = "UPDATE ISSUED SET B2 ='"+b+"' WHERE SNAME = '"+stuname+"';";
                            preparedStatement1 = connection.prepareStatement(insertSQL2);
                            preparedStatement1.executeUpdate();
                            preparedStatement1.close();
                            break;
                        case 3:
                            String insertSQL3 = "UPDATE ISSUED SET B3 ='"+b+"' WHERE SNAME = '"+stuname+"';";
                            preparedStatement1 = connection.prepareStatement(insertSQL3);
                            preparedStatement1.executeUpdate();
                            preparedStatement1.close();
                            break;
                    }


                } else {
                    System.out.println("Student Can not Borrow more than 3 Books.");
                    return;
                }


            } else {
                System.out.println("Book is not available!");
                return;
            }
        }
    }

    public void checkInBook(books book) throws SQLException {
        String studentIndex = this.isStudent();
        if (studentIndex != null) {
            String sql = "SELECT * FROM ISSUED WHERE SNO = '"+studentIndex+"';";
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            ResultSet rs = preparedStatement.executeQuery();


            AsciiTable at = new AsciiTable();
            at.getContext().setWidth(80);
            at.addRule();
            at.addRow(null, null, null, null, "BOOKS ISSUED").setTextAlignment(TextAlignment.CENTER);
            at.addRule();
            at.addRow("Student ID", "Student Name", "Book 1", "Book 2", "Book 3");
            at.addRule();
            String empty1 = rs.getString("B1");
            String empty2 = rs.getString("B2");
            String empty3 = rs.getString("B3");
            if(empty1!=null && empty2!=null && empty3!=null){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), rs.getString("B1"), rs.getString("B2"), rs.getString("B3"));

            }
            else if(empty1!=null && empty2!=null){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), rs.getString("B1"), rs.getString("B2"), "null");

            }
            else if(empty3!=null && empty2!=null){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), "null", rs.getString("B2"), rs.getString("B3"));

            }
            else if(empty3!=null && empty1!=null){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), rs.getString("B1"), "null", rs.getString("B3"));

            }
            else if(empty1!=null ){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), rs.getString("B1"), "null","null");
            }
            else if(empty2!=null){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), "null", rs.getString("B2"), "null");
            }
            else if(empty3!=null ){
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), "null", "null", rs.getString("B3"));
            }
            else {
                at.addRow(rs.getString("SNO"), rs.getString("SNAME"), "null", "null", "null");
            }
            at.addRule();
            System.out.println(at.render());
            rs.close();
            preparedStatement.close();

            book.showAllBooks();

            System.out.print("Enter Serial Number of Book to be Checked In:");

            int bNo = sc.nextInt();
            sql = "SELECT * FROM BOOKS WHERE BNO ="+bNo+";";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs1 = preparedStatement.executeQuery();
            String bookName = rs1.getString("BNAME");
            preparedStatement.close();
            book.checkInBook(bNo);

            sql = "SELECT * FROM ISSUED WHERE B1='"+bookName+"' OR B2='"+bookName+"' OR B3='"+bookName+"'";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs2 = preparedStatement.executeQuery();
            String stuName = rs2.getString("SNAME");
            preparedStatement.close();


            sql = "SELECT * FROM ISSUED WHERE SNO = '"+studentIndex+"';";
            preparedStatement = connection.prepareStatement(sql);
            ResultSet rs3 = preparedStatement.executeQuery();
            String b1 = rs3.getString("B1");
            String b2 = rs3.getString("B2");
            String b3 = rs3.getString("B3");
            preparedStatement.close();
            if(b1.equals(bookName)){
                String sql2 = "UPDATE ISSUED SET B1 = ? WHERE SNO = '"+studentIndex+"';";
                PreparedStatement preparedStatement2 = connection.prepareStatement(sql2);
                preparedStatement2.setString(1,null);
                preparedStatement2.executeUpdate();
                preparedStatement2.close();
            }
            else if(b2.equals(bookName)){
                String sql3 = "UPDATE ISSUED SET B2 = ? WHERE SNO = '"+studentIndex+"';";
                PreparedStatement preparedStatement3 = connection.prepareStatement(sql3);
                preparedStatement3.setString(1,null);
                preparedStatement3.executeUpdate();
                preparedStatement3.close();
            }
            else if(b3.equals(bookName)){
                String sql1 = "UPDATE ISSUED SET B3 = ? WHERE SNO = '"+studentIndex+"';";
                PreparedStatement preparedStatement1 = connection.prepareStatement(sql1);
                preparedStatement1.setString(1,null);
                preparedStatement1.executeUpdate();
                preparedStatement1.close();
            }
            int sqty = getBookQTY(studentIndex);
            sql = "UPDATE STUDENTS SET SQTY = "+(--sqty)+" WHERE SNO = '"+studentIndex+"' ;";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.executeUpdate();
            preparedStatement.close();


        }
    }
}
