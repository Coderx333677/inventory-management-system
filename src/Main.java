//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.Scanner;
public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("═".repeat(73));
        System.out.println( " ".repeat(18) + " ✅ Inventory Management System ✅ " );
        System.out.println("═".repeat(73));
        MenuDisplay display = new MenuDisplay();
        while (true){
            System.out.println("Press 1 for Admin access");// Only admin can add User
            System.out.println("Press 2 for User access");
            System.out.println("Press 3 to Exit");
            int ch = sc.nextInt();
            sc.nextLine();
            switch (ch){
                case 1:
                    display.AdminAccessView();
                    break;
                case 2:
                    display.EmployeeAccessView();
                    break;
                case 3:
                    System.exit(0);
                    break;
            }
        }
    }
}