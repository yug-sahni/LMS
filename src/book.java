import java.util.*;

class book{

    int sNo;
    String bookName;
    String authorName;
    int bookQty;
    int bookQtyCopy;

    Scanner sc = new Scanner(System.in);

    public book()  {
        System.out.println("Enter Serial No of Book:");
        this.sNo = sc.nextInt();
        sc.nextLine();

        System.out.println("Enter Book Name:");
        this.bookName = sc.nextLine();

        System.out.println("Enter Author Name:");
        this.authorName = sc.nextLine();

        System.out.println("Enter Quantity of Books:");
        this.bookQty = sc.nextInt();
        bookQtyCopy = this.bookQty;


    }
}
