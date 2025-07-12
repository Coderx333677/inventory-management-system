import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Employee implements Users {

    private String empName, empPassword, empEmail, empCity, empAddress, empPhoneNum;
    Employee(){};
    Employee(String n, String pass){
        empName = n;
        empPassword = pass;
    };
    //******************************************************************************************************//
    Employee(String empName, String empPassword,String empPhoneNum, String empEmail, String empCity, String empAddress) {
        this.empName = empName;
        this.empPassword = empPassword;
        this.empPhoneNum = empPhoneNum;
        this.empEmail = empEmail;
        this.empCity = empCity;
        this.empAddress = empAddress;
    }
    //******************************************************************************************************//
    @Override
    public boolean add() {
        String checkTable = "Select * from sys.tables where name = ? ";
        String createTable = "Create Table employees (" +
                "empId int Primary Key identity(1,1), " +
                "empName varchar (255)," +
                "empPassword varchar (255) ," +
                "empPhoneNum varchar (255) ," +
                "empEmail varchar (255) ," +
                "empCity varchar(255) ," +
                "empAddress varchar (255) " +
                ")";
        String insert = "Insert into employees (empName , empPassword ,empPhoneNum, empEmail , empCity , empAddress) values ( ? , ? , ? , ? , ? , ?)";

        try (Connection conn = DataBaseManager.getConnection()){

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1,"employees");
            ResultSet rs = pstmt2.executeQuery();

            if (!rs.next()){
                PreparedStatement pstmt3 = conn.prepareStatement(createTable);
                pstmt3.executeUpdate();
            }

            try (PreparedStatement pstmt3 = conn.prepareStatement(insert);){
                pstmt3.setString(1,empName);
                pstmt3.setString(2,empPassword);
                pstmt3.setString(3,empPhoneNum);
                pstmt3.setString(4,empEmail);
                pstmt3.setString(5,empCity);
                pstmt3.setString(6,empAddress);
                int roweffected = pstmt3.executeUpdate();
                if (roweffected > 0){
                    return true;
                }

            }catch (Exception e){
                System.out.println("Error : " + e.getMessage() );
            }

        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    //******************************************************************************************************//
    @Override
    public boolean checkExistence() {
        String checkEmployee = "Select * from employees where empName = ? ";
        try (Connection conn = DataBaseManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(checkEmployee);
            pstmt.setString(1,this.empName);
            ResultSet rs = pstmt.executeQuery(); //
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
        return false;
    }
    //******************************************************************************************************//
    @Override
    public void search(String Name) {
        String search = "SELECT * FROM employees WHERE empName LIKE ?";
        try (Connection conn = DataBaseManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(search);
            pstmt.setString(1, "%" + Name + "%");
            ResultSet rs = pstmt.executeQuery();
            System.out.println("Found !!");
            System.out.println("-".repeat(73));
            System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s%n", "ID" , "Name" , "Password" , "Phone No" , "Email" , "City" , "Address" );
            System.out.println("-".repeat(73));
            boolean Found = false;

            while (rs.next()){
                Found = true;
                System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s%n" ,
                        rs.getInt("empID"),
                        rs.getString("empName"),
                        rs.getString("empPassword"),
                        rs.getString("empPhoneNum"),
                        rs.getString("empEmail"),
                        rs.getString("empCity"),
                        rs.getString("empAddress"));
            }
            if(!Found){
                System.out.println("NOT Found !!");
            }

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }

    //******************************************************************************************************//
    @Override
    public void print() {
        String print = "Select * from employees ";
        try (Connection conn = DataBaseManager.getConnection();){

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(print);

            System.out.println("-".repeat(73));
            System.out.printf("%-10s %-10s %-10s %-10s %-18s %-10s %-10s%n", "ID" , "Name" , "Password" , "Phone No" , "Email" , "City" , "Address" );
            System.out.println("-".repeat(73));

            while (rs.next()){
                System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s %-10s%n" ,
                        rs.getInt("empID"),
                        rs.getString("empName"),
                        rs.getString("empPassword"),
                        rs.getString("empPhoneNum"),
                        rs.getString("empEmail"),
                        rs.getString("empCity"),
                        rs.getString("empAddress"));
            }
        }catch (Exception e){
            System.out.println("ERROR 11 : " + e.getMessage());
        }
    }
    //******************************************************************************************************//

    @Override
    public void delete(int id) {
        String query = "DELETE FROM employees WHERE empId = ?";
        try (Connection conn = DataBaseManager.getConnection();){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,id);
            int rowsAffected = pstmt.executeUpdate();//Return int

            if (rowsAffected > 0){
                System.out.println("Record Deleted having ID : " + id);
            }
            else {
                System.out.println("Record Not Deleted having ID : " + id);
            }

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    //******************************************************************************************************//

    @Override
    public void edit(int id) {
        String query = "UPDATE employees SET empName = ?, empPassword = ?, empPhoneNum = ?, empEmail = ?, empCity = ?, empAddress = ? WHERE empID = ?";
        try (Connection conn = DataBaseManager.getConnection();){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,this.empName);
            pstmt.setString(2,this.empPassword);
            pstmt.setString(3,this.empPhoneNum);
            pstmt.setString(4,this.empEmail);
            pstmt.setString(5,this.empCity);
            pstmt.setString(6,this.empAddress);
            pstmt.setInt(7,id);
            int rows = pstmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Employee Updated Successfully.");
            } else {
                System.out.println("Employee Not Found or No Changes Made.");
            }

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    //******************************************************************************************************//

    @Override
    public boolean login() {
        String login = "Select * from employees where empName = ? and empPassword = ? " ;
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement pstmt  = conn.prepareStatement(login)){
            pstmt.setString(1,this.empName);
            pstmt.setString(2,this.empPassword);
            ResultSet rs3 = pstmt.executeQuery();
            // pstmt.executeUpdate(); only use for non select querires
            if (rs3.next()){
                System.out.println("Login Successfully");
                return false;

            }
            else {
                System.out.println("Login Fail");
                return true;
            }
        }catch (Exception e){
            System.out.println("Error : " + e);
        }
        return false;
    }
}
