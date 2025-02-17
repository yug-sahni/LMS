import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;



public class Library{

    public static void main(String[] args) throws IOException, SQLException {

        Scanner sc = new Scanner(System.in);
        System.out.println("_ _ _                          \n" +
                "| (_) |                         \n" +
                "| |_| |__  _ __ __ _ _ __ _   _ \n" +
                "| | | '_ \\| '__/ _` | '__| | | |\n" +
                "| | | |_) | | | (_| | |  | |_| |\n" +
                "|_|_|_.__/|_|  \\__,_|_|   \\__, |\n" +
                "                           __/ |\n" +
                "                          |___/ ");

        books b_obj = new books();

        students s_obj = new students();

        int ch;
        int searchChoice;

        do {

            b_obj.dispMenu();
            ch = sc.nextInt();


            switch (ch) {

                case 1:
                    book b = new book();
                    b_obj.addBook(b);
                    break;

                case 2:
                    b_obj.upgradeBookQty();
                    break;

                case 3:

                    System.out.println(
                            " press 1 to Search with Book Serial No.");
                    System.out.println(
                            " Press 2 to Search with Book's Author Name.");
                    searchChoice = sc.nextInt();

                    switch (searchChoice) {

                        case 1:
                            b_obj.searchBySno();
                            break;

                        case 2:
                            b_obj.searchByAuthorName();
                    }
                    break;

                case 4:
                    b_obj.showAllBooks();
                    break;

                case 5:
                    student s = new student();
                    s_obj.addStudent(s);
                    break;

                case 6:
                    s_obj.showAllStudents();
                    break;

                case 7:
                    s_obj.checkOutBook(b_obj);
                    break;

                case 8:
                    s_obj.checkInBook(b_obj);
                    break;

                default:

                    System.out.println("ENTER BETWEEN 0 TO 8.");
            }

        }
        while (ch != 0);
    }
}

