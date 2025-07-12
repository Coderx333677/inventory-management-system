
import java.util.Scanner;
public class InputHandler {
    Scanner sc = new Scanner(System.in);


    Admin adminInput (){
            System.out.print("Enter Admin Name : ");
            String adminName = sc.nextLine();
            System.out.print("Enter Password : ");
            String adminPassword = sc.nextLine();
            return new realAdmin(adminName,adminPassword);
    }

    Employee EmployeeInput (){
        System.out.print("Enter Employee Name : ");
        String empName = sc.nextLine();
        System.out.print("Enter Employee Password : ");
        String empPassword = sc.nextLine();
        System.out.print("Enter Email : ");
        String empEmail = sc.nextLine();
        System.out.print("Enter Phone Num : ");
        String empPhoneNum = sc.nextLine();
        System.out.print("City : ");
        String empCity = sc.nextLine();
        System.out.print("Address : ");
        String empAddress = sc.nextLine();
        return new Employee(empName,empPassword,empPhoneNum,empEmail,empCity,empAddress);
    }
    Products productsInput (){
        System.out.print("Enter Product Name : ");
        String name = sc.nextLine();
        System.out.print("Enter Product Price : ");
        int price = sc.nextInt();
        sc.nextLine();
        System.out.print("Enter No of Units in stock : ");
        int stock = sc.nextInt();
        sc.nextLine();
        System.out.println("═".repeat(73));
        System.out.println( " ".repeat(18) + " ✅ Available Categories ✅ " );

        while (Category.checkEmpty() <=  0){
            System.out.println("Category Table is Empty First Add Category");
            InputHandler input = new InputHandler();
            Category category = input.categoryInput();
            category.categoryAdd();
        }

        Category.printCategory();
        System.out.println("═".repeat(73));
        System.out.print("Enter Category ID : ");
        int cid = sc.nextInt();
        sc.nextLine();
        System.out.println("═".repeat(73));
        System.out.println( " ".repeat(18) + " ✅ Available Suppliers ✅ " );

        while (Supplier.checkEmpty() <=  0){
            System.out.println("Supplier Table is Empty First Add Supplier");
            InputHandler input = new InputHandler();
            Supplier supplier = input.supplierInput();
            supplier.addSupplier();
        }
        Supplier.printSuppliers();
        System.out.println("═".repeat(73));
        System.out.print("Enter Supplier ID : ");
        int sid = sc.nextInt();
        sc.nextLine();
        return new Products(name,price,stock,cid,sid);
    }
    Category categoryInput(){
        System.out.print("Enter Category Name : ");
        String name = sc.nextLine();

        return new Category(name);
    }
    Supplier supplierInput(){
        System.out.print("Enter Supplier Name : ");
        String name = sc.nextLine();
        System.out.print("Enter Supplier Contact : ");
        String contact = sc.nextLine();
        System.out.print("Enter Supplier Address : ");
        String address = sc.nextLine().trim();;

        return new Supplier(name,contact,address);
    }

}

