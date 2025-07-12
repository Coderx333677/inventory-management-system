import java.io.FileWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;
public class MenuDisplay {

    InputHandler input = new InputHandler();
    Scanner sc = new Scanner(System.in);

    Employee employee = new Employee();
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void adminFeatures (){
            while (true){
                System.out.println(".".repeat(73));
                System.out.println( " ".repeat(28) + " Admin View");
                System.out.println(".".repeat(73));
                System.out.println("Press 1 for Employee Management");
                System.out.println("Press 2 for product Report");
                System.out.println("Press 3 to Move Back");
                System.out.println("Press 4 to Exit");

                int choice = sc.nextInt();
                switch (choice){
                    case 1:
                        while (true) {
                            System.out.println("═".repeat(73));
                            System.out.println(" ".repeat(25) + " Employee Management ");
                            System.out.println("═".repeat(73));

                            System.out.println("Press 1 to Add Employee ");
                            System.out.println("Press 2 to Delete Employee");
                            System.out.println("Press 3 to Edit Employee"); // Start Here
                            System.out.println("Press 4 to Search Employee");
                            System.out.println("Press 5 to Move Back");
                            System.out.println("Press 6 to Exit");
                            int ch = sc.nextInt();
                            sc.nextLine();
                            switch (ch) {
                                case 1:
                                    employee = input.EmployeeInput();
                                    while (employee.checkExistence()) {
                                        System.out.println("ERROR : Employee Already Exit");
                                        employee = input.EmployeeInput();
                                    }
                                    if(employee.add()){
                                        System.out.println("Employee Added Successfully");
                                    }
                                    else {
                                        System.out.println("Employee NOT Added");
                                    }
                                    break;
                                case 2:
                                    employee.print();
                                    System.out.print("Enter ID to Delete User : ");
                                    int id = sc.nextInt();
                                    sc.nextLine();
                                    employee.delete(id);
                                    break;
                                case 3:
                                    employee.print();
                                    System.out.println(".".repeat(73));
                                    System.out.print("Enter ID to Edit : ");
                                    int id1 = sc.nextInt();
                                    sc.nextLine();
                                    employee = input.EmployeeInput();
                                    employee.edit(id1);
                                    break;
                                case 4:
                                    System.out.print("Enter Name to Search : ");
                                    String name = sc.nextLine();
                                    employee.search(name);
                                    break;
                                case 5:
                                    adminFeatures ();
                                    break;
                                case 6:
                                    System.exit(0);
                                    break;
                            }
                        }
                    case 2:
                        while(true){
                        System.out.println("═".repeat(73));
                        System.out.println(" ".repeat(25) + " Product Management ");
                        System.out.println("═".repeat(73));
                        System.out.println("Press 1 for Product Report ");
                        System.out.println("Press 2 for Supplier Report");
                        System.out.println("Press 3 for Low Stock Report");
                        System.out.println("Press 4 to Move Back");
                        System.out.println("Press 5 to Exit");
                        int choice3 = sc.nextInt();
                        switch (choice3) {
                            case 1:
                                String filePath = "C:\\Users\\Maaz\\Desktop\\products.csv";
                                Category.createTable();
                                Supplier.createTable();
                                Products.createProdTable();

                                String query = "SELECT * FROM Products"; // adjust table name/columns
                                try (Connection conn = DataBaseManager.getConnection();
                                     Statement stmt = conn.createStatement();
                                     ResultSet rs = stmt.executeQuery(query);
                                     FileWriter writer = new FileWriter(filePath)) {

                                    // Write header
                                    writer.append("ProductID,ProductName,Quantity,Price,CategoryId,SupplierID\n");

                                    // Write data
                                    while (rs.next()) {
                                        writer.append(rs.getInt("prodID") + ",");
                                        writer.append(rs.getString("prodName") + ",");
                                        writer.append(rs.getDouble("prodPrice") + ",");
                                        writer.append(rs.getInt("prodStock") + ",");
                                        writer.append(rs.getInt("CategoryId") + ",");
                                        writer.append(rs.getInt("SupplierID") + "\n");

                                    }

                                    System.out.println("Exported to Excel (CSV) at: " + filePath);

                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }
                                break;
                            case 2:
                                String filePath1 = "C:\\Users\\Maaz\\Desktop\\Supplier.csv";
                                Supplier.createTable();

                                String query1 = "SELECT * FROM Suppliers"; // adjust table name/columns
                                try (Connection conn = DataBaseManager.getConnection();
                                     Statement stmt = conn.createStatement();
                                     ResultSet rs = stmt.executeQuery(query1);
                                     FileWriter writer = new FileWriter(filePath1)) {

                                    // Write header
                                    writer.append("SupplierID,SupplierName,SupplierAddress,SupplierContact\n");

                                    // Write data
                                    while (rs.next()) {
                                        writer.append(rs.getInt("supplierID") + ",");
                                        writer.append(rs.getString("supplierName") + ",");
                                        writer.append(rs.getString("supplierAddress") + ",");
                                        writer.append(rs.getString("supplierContact") + "\n");
                                    }

                                    System.out.println("Exported to Excel (CSV) at: " + filePath1);

                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }
                                break;
                            case 3:
                                String filePath4 = "C:\\Users\\Maaz\\Desktop\\stock.csv";

                                String query4 = "SELECT * FROM Products ORDER BY prodstock ASC"; // adjust table name/columns
                                try (Connection conn = DataBaseManager.getConnection();
                                     Statement stmt = conn.createStatement();
                                     ResultSet rs = stmt.executeQuery(query4);
                                     FileWriter writer = new FileWriter(filePath4)) {

                                    // Write header
                                    writer.append("ProductID,ProductName,Price,Stock,CategoryId,SupplierID\n");

                                    // Write data
                                    while (rs.next()) {
                                        writer.append(rs.getInt("prodID") + ",");
                                        writer.append(rs.getString("prodName") + ",");
                                        writer.append(rs.getDouble("prodPrice") + ",");
                                        writer.append(rs.getInt("prodStock") + ",");
                                        writer.append(rs.getInt("CategoryID") + ",");
                                        writer.append(rs.getInt("SupplierID") + "\n");

                                    }

                                    System.out.println("Exported to Excel (CSV) at: " + filePath4);

                                } catch (Exception e) {
                                    System.out.println("ERROR: " + e.getMessage());
                                }
                                break;
                            case 4:
                                adminFeatures();
                                break;
                            case 5:
                                System.exit(0);
                                break;

                        }
                        }
                       case 3:
                        AdminAccessView();
                        break;
                    case 4:
                        System.exit(0);
                        break;
                }
            }

    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    public void AdminAccessView (){

        System.out.println(".".repeat(73));
        System.out.println( " ".repeat(28) + " Admin Access ");
        System.out.println(".".repeat(73));

        System.out.println("Press 1 for Signup ");
        System.out.println("Press 2 for Login");
        System.out.println("Press 4 to Move Back");
        System.out.println("Press 3 to Exit");
        //reports
        int ch = sc.nextInt();

        switch (ch){
            case 1:
                // *************************** SignUp of Admin *************************** //
                System.out.println(".".repeat(73));
                System.out.println( " ".repeat(30) + " Signup ");
                System.out.println(".".repeat(73));

                Admin admin = input.adminInput();//First input and then store thats why we created seprate function
                while (admin.checkExistence()){

                    System.out.println(".".repeat(73));
                    System.out.println( " ".repeat(30) + " Again ");
                    System.out.println(".".repeat(73));

                    System.out.println("ERROR : User Name Already Exit");
                    admin = input.adminInput();//First input and then store
                }

                admin.add();
                System.out.println("Admin added SuccessFully");

                adminFeatures();
                break;
            case 2:
                // *************************** Login of Admin *************************** //
                System.out.println(".".repeat(73));
                System.out.println( " ".repeat(30) + " Login ");
                System.out.println(".".repeat(73));
                //input.adminInput();//in this way we cannot integrate with obj
                Admin admin3 = input.adminInput();
                //admin3.login();
                while (admin3.login()){
                    System.out.println("ERROR : Admin name or password is wrong !!");
                    System.out.println(".".repeat(73));
                    System.out.println( " ".repeat(30) + " Again ");
                    System.out.println(".".repeat(73));

                    admin3 = input.adminInput();
                }
                adminFeatures();
                break;
            case 4:
                Main.main(new String [] {});
                break;
            case 3:
                System.exit(0);
                break;

        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void productManagement () {//while
        while (true) {
            System.out.println(".".repeat(73));
            System.out.println(" ".repeat(30) + " Product Management ");
            System.out.println(".".repeat(73));
            System.out.println("Press 1 to Add Product ");
            System.out.println("Press 2 to Edit Product");
            System.out.println("Press 3 to Delete Product");
            System.out.println("Press 4 to Search Product");
            System.out.println("Press 5 to Move Back");
            System.out.println("Press 6 to Exit");
            int ch1 = sc.nextInt();
            sc.nextLine();
            switch (ch1) {
                case 1:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Add Product ");
                    System.out.println(".".repeat(73));
                    Products prod = input.productsInput();
                    if (!prod.checkProductExsistance()) {
                        prod.addProduct();
                    }else
                    {
                        System.out.println("Not Added.Please Enter unique Product");
                    }

                    break;
                case 2:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Edit Product ");
                    System.out.println(".".repeat(73));
                    Products.printProduct();
                    System.out.println("Enter Product ID to Edit : ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Products products = input.productsInput();
                    if(products.editProduct(id)){
                        System.out.println("Updated Successfully");
                    }
                    break;
                case 3:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Delete Product ");
                    System.out.println(".".repeat(73));
                    Products.printProduct();
                    System.out.println("Enter ID to Delete : ");
                    int id4 = sc.nextInt();
                    sc.nextLine();
                    Products.deleteProduct(id4);
                    break;
                case 4:
                    System.out.println("Enter Product name to search : ");
                    String n = sc.nextLine();
                    Products.search(n);
                    break;
                case 5:
                    employeeFeatures();
                    break;
                case 6:
                    System.exit(0);
                    break;
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void supplierManagement() {
        //Supplier sup = new Supplier();
        //sup.addSupplier();
        while(true) {
            System.out.println(".".repeat(73));
            System.out.println(" ".repeat(30) + " Supplier Management ");
            System.out.println(".".repeat(73));
            System.out.println("Press 1 to Add Supplier");
            System.out.println("Press 2 to Edit Supplier");
            System.out.println("Press 3 to Delete Supplier");
            System.out.println("Press 4 to Search Supplier");
            System.out.println("Press 5 to Move Back");
            System.out.println("Press 6 to Exit");
            int ch2 = sc.nextInt();
            sc.nextLine(); // Clear newline

            switch (ch2) {
                case 1:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Add Supplier ");
                    System.out.println(".".repeat(73));
                    Supplier supplier = input.supplierInput();
                    while (!supplier.addSupplier()) {
                        supplier = input.supplierInput();
                    }
                    break;
                case 2:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Edit Supplier ");
                    System.out.println(".".repeat(73));
                    Supplier supplier1 = new Supplier();
                    supplier1.printSuppliers();
                    System.out.println("Enter ID to Edit: ");
                    int id = sc.nextInt();
                    sc.nextLine(); // Clear newline
                    Supplier supplier4 = input.supplierInput();
                    supplier4.editSupplier(id);
                    break;
                case 3:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Delete Supplier ");
                    System.out.println(".".repeat(73));
                    Supplier supplier2 = new Supplier();
                    supplier2.printSuppliers();
                    System.out.println("Enter ID to Delete: ");
                    int id1 = sc.nextInt();
                    sc.nextLine(); // Clear newline
                    supplier2.deleteSupplier(id1);
                    break;
                case 4:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Search Supplier ");
                    System.out.println(".".repeat(73));
                    Supplier supplier3 = new Supplier();
                    System.out.println("Enter Name to Search: ");
                    String name = sc.nextLine();
                    supplier3.searchSupplier(name);
                    break;
                case 5:
                    employeeFeatures(); // Back to main menu
                    break;
                case 6:
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid Choice!");
            }
        }
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void categoryManagement () {
        while(true) {
            System.out.println(".".repeat(73));
            System.out.println(" ".repeat(30) + " Category Management ");
            System.out.println(".".repeat(73));
            System.out.println("Press 1 to Add Category ");
            System.out.println("Press 2 to Edit Category");
            System.out.println("Press 3 to Delete Category");
            System.out.println("Press 4 to Search Category");
            System.out.println("Press 5 to Move Back");
            System.out.println("Press 6 to Exit");
            int ch2 = sc.nextInt();
            sc.nextLine();
            switch (ch2) {
                case 1:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Add Category ");
                    System.out.println(".".repeat(73));
                    Category category = input.categoryInput();
                    while (!category.categoryAdd()) {
                        category = input.categoryInput();
                    }
                    break;
                case 2:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Edit Category ");
                    System.out.println(".".repeat(73));
                    Category category1 = new Category();
                    category1.printCategory();
                    System.out.println("Enter Id to Edit : ");
                    int id = sc.nextInt();
                    sc.nextLine();
                    Category category5 = input.categoryInput();
                    category5.editCategory(id);
                    break;
                case 3:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Delete Category ");
                    System.out.println(".".repeat(73));
                    Category category2 = new Category();
                    category2.printCategory();
                    System.out.println("Enter Id to Delete : ");
                    int id1 = sc.nextInt();
                    sc.nextLine();
                    category2.deleteCategory(id1);
                    break;
                case 4:
                    System.out.println(".".repeat(73));
                    System.out.println(" ".repeat(30) + " Search Category ");
                    System.out.println(".".repeat(73));
                    Category category3 = new Category();
                    System.out.println("Enter Name to Search : ");
                    String name = sc.nextLine();
                    category3.search(name);
                    break;
                case 5:
                    employeeFeatures();
                    break;
                case 6:
                    System.exit(0);
                    break;
            }
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void EmployeeAccessView (){
        System.out.println("═".repeat(73));
        System.out.println( " ".repeat(28) + " Employee Access ");
        System.out.println("═".repeat(73));

        System.out.println("=> Enter User name and Password to Login");

        System.out.println(".".repeat(73));
        System.out.println( " ".repeat(30) + " Login ");
        System.out.println(".".repeat(73));


        System.out.print("Enter User name : ");
        String name = sc.nextLine();
        System.out.print("Enter Password : ");
        String password = sc.nextLine();

        Employee emp1 = new Employee(name,password);
        while (emp1.login()){
            System.out.println("ERROR : Admin name or password is wrong !!");
            System.out.println(".".repeat(73));
            System.out.println( " ".repeat(30) + " Again ");
            System.out.println(".".repeat(73));

            System.out.print("Enter User name : ");
            name = sc.nextLine();
            System.out.print("Enter Password : ");
            password = sc.nextLine();

            emp1 = new Employee(name,password);

        }
        employeeFeatures();
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    void employeeFeatures() {
        System.out.println("Press 1 for Supplier Management");
        System.out.println("Press 2 for Category Management");
        System.out.println("Press 3 for Product Management");
        System.out.println("Press 4 to Move Back");
        System.out.println("Press 5 to EXIT");
        int ch = sc.nextInt();
        switch (ch){
            case 1:
                supplierManagement();
                break;
            case 2:
                categoryManagement();
                break;
            case 3:
                productManagement();
                break;
            case 4:
                Main.main(new String []{});
                break;
            case 5:
                System.exit(0);
                break;
        }

    }


}
