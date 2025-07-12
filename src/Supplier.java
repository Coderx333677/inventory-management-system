import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Supplier {
    int supplierId;
    String supplierName;
    String address;
    String contact;

    // Constructors
    Supplier() {}
    Supplier(String name, String contact  ,String address) {
        this.supplierName = name;
        this.address = address;
        this.contact = contact;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    static void createTable () {
        String createTable = "CREATE TABLE Suppliers (" +
                "    supplierID INT PRIMARY KEY IDENTITY(1,1)," +
                "    supplierName VARCHAR(255)," +
                "    supplierAddress VARCHAR(MAX)," +
                "    supplierContact VARCHAR(255)" +
                ")";
        String checkTable = "Select * from sys.tables where name = ? ";
        try (Connection conn = DataBaseManager.getConnection()) {

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1, "Suppliers");
            ResultSet rs = pstmt2.executeQuery();

            if (!rs.next()) {
                PreparedStatement pstmt3 = conn.prepareStatement(createTable);
                pstmt3.executeUpdate();
            }

        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    boolean addSupplier() {
        String checkTable = "Select * from sys.tables where name = ? ";
        String insert = "INSERT INTO Suppliers (SupplierName, supplierAddress, supplierContact) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseManager.getConnection()) {

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1, "Suppliers");
            ResultSet rs2 = pstmt2.executeQuery();

            if (!rs2.next()) {
                createTable();
            }

            PreparedStatement pstmt = conn.prepareStatement(insert);
            if(!checkSupplierExistence()){
                pstmt.setString(1, this.supplierName);
                pstmt.setString(2, this.address);
                pstmt.setString(3, this.contact);
                int rows = pstmt.executeUpdate();
                if (rows > 0) {
                    System.out.println("Supplier Added Successfully.");
                    return true;
                }
            }else {
                System.out.println(this.supplierName + "is Already Exist");
            }

        }
        catch (Exception e) {
                System.out.println("ERROR : " + e.getMessage());
            }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    void editSupplier(int id) {
        String update = "UPDATE Suppliers SET SupplierName = ?, supplierAddress = ?, supplierContact = ? WHERE SupplierID = ?";
        try (Connection conn = DataBaseManager.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(update);
            pstmt.setString(1, this.supplierName);
            pstmt.setString(2, this.address);
            pstmt.setString(3, this.contact);
            pstmt.setInt(4, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Supplier Updated Successfully.");
            } else {
                System.out.println("Supplier Not Found.");
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    static void printSuppliers() {
        String query = "SELECT * FROM Suppliers";
        try (Connection conn = DataBaseManager.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            System.out.println("-".repeat(73));
            System.out.printf("%-10s %-20s %-25s %-15s%n", "ID", "Name", "Address", "Contact");
            System.out.println("-".repeat(73));

            while (rs.next()) {
                System.out.printf("%-10d %-20s %-25s %-15s%n",
                        rs.getInt("SupplierID"),
                        rs.getString("SupplierName"),
                        rs.getString("SupplierAddress"),
                        rs.getString("SupplierContact"));
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    boolean checkSupplierExistence() {
        String query = "SELECT * FROM Suppliers WHERE SupplierName = ?";
        try (Connection conn = DataBaseManager.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, this.supplierName);
            ResultSet rs = pstmt.executeQuery();
            if(rs.next()){
                return true;
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
        return false;
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////
    void deleteSupplier(int id) {
        String query = "DELETE FROM Suppliers WHERE SupplierID = ?";
        try (Connection conn = DataBaseManager.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1, id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Supplier Deleted with ID: " + id);
            } else {
                System.out.println("Supplier Not Found with ID: " + id);
            }
        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    void searchSupplier(String name) {
        String query = "SELECT * FROM Suppliers WHERE SupplierName LIKE ?";
        try (Connection conn = DataBaseManager.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1, "%" + name + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                System.out.println("Supplier Found:");
                System.out.println("-".repeat(73));
                System.out.printf("%-10s %-20s %-25s %-15s%n", "ID", "Name", "Address", "Contact");
                System.out.println("-".repeat(73));
                do {
                    System.out.printf("%-10d %-20s %-25s %-15s%n",
                            rs.getInt("SupplierID"),
                            rs.getString("SupplierName"),
                            rs.getString("SupplierAddress"),
                            rs.getString("SupplierContact"));
                } while (rs.next());
            } else {
                System.out.println("Supplier Not Found.");
            }

        } catch (Exception e) {
            System.out.println("ERROR: " + e.getMessage());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
    static int checkEmpty (){
        String checkEmpty = "SELECT COUNT(*) FROM Suppliers;\n";
        try (Connection conn = DataBaseManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(checkEmpty);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                int count = rs.getInt(1);
                return count;
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
        return 0;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////////////
}
