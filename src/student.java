import java.util.Scanner;
public class student {
    String sName;
    String sNo;
    int sQty;

    Scanner sc = new Scanner(System.in);
    public student()
    {
        System.out.println("Enter Student Name:");
        this.sName = sc.nextLine();
        System.out.println("Enter Registration Number:");
        this.sNo = sc.nextLine();
        this.sQty = 0;
    }
}

