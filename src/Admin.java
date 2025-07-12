import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
///jvs
class realAdmin extends Admin{
    realAdmin(String name,String password){
        super(name,password);
    }

    @Override
    public boolean add() {
        return super.add();
    }

    @Override
    public void edit(int id) {

    }

    @Override
    public void delete(int id) {

    }

    @Override
    public void search(String Name) {

    }

    @Override
    public boolean login() {
        return super.login();
    }

    @Override
    public void print() {

    }
}

abstract class Admin implements Users {
    private final String adminName,adminPassword;

    Admin(String adminName, String adminPassword) {
        this.adminName = adminName;
        this.adminPassword = adminPassword;
    }

    @Override
    public boolean login() {
        String login = "Select * from admins where userName = ? and Password = ? ";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(login)) {
            pstmt.setString(1, this.adminName);
            pstmt.setString(2, this.adminPassword);
            ResultSet rs = pstmt.executeQuery();
            // pstmt.executeUpdate(); only use for non select querires
            if (rs.next()) {
                System.out.println("Login Successfully");
                return false;

            } else {
                System.out.println("Login Fail");
                return true;
            }
        } catch (Exception e) {
            System.out.println("Error : " + e);
        }
        return false;
    }

    @Override
    public boolean checkExistence() {
        String checkIfExit = "Select * from admins where userName = ? ";
        try (Connection conn = DataBaseManager.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(checkIfExit)) {
            pstmt.setString(1, this.adminName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public boolean add() {
        String query = "Insert into Admins (UserName,Password) Values (?, ?)";
        String checkTable = "Select * from sys.tables where name = ? ";
        String createTable = "CREATE TABLE Admins (" +
                "id INT PRIMARY KEY IDENTITY(1,1), " +
                "username VARCHAR(255), " +
                "password VARCHAR(255))";
        try (Connection conn = DataBaseManager.getConnection()) {

            PreparedStatement pstmt = conn.prepareStatement(checkTable);
            pstmt.setString(1, "Admins");
            ResultSet rs = pstmt.executeQuery();

            if (!rs.next()) { // check that Table exit or not

                PreparedStatement pstmt1 = conn.prepareStatement(createTable);
                pstmt1.executeUpdate();

            }
            try (PreparedStatement pstmt2 = conn.prepareStatement(query)) {
                if (!checkExistence()) {
                    pstmt2.setString(1, this.adminName);
                    pstmt2.setString(2, this.adminPassword);
                    int rowEffected = pstmt2.executeUpdate();
                    if (rowEffected > 0) {
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        return false;
    }
}

