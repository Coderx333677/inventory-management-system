import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Category {
    String categoryName;
    Category(){};
    Category (String name){
        categoryName = name;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    void editCategory (int id){
        String query = "UPDATE Categories SET categoryName = ? WHERE categoryID = ?";
        try (Connection conn = DataBaseManager.getConnection();){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setString(1,this.categoryName);
            pstmt.setInt(2,id);
            int rows = pstmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Updated Successfully.");
            } else {
                System.out.println("Category Not Found.");
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    static void printCategory (){
        String print = "Select * from Categories ";
        try (Connection conn = DataBaseManager.getConnection();){

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(print);

            System.out.println("-".repeat(73));
            System.out.printf("%-10s  %-10s%n", "ID" , "Name");
            System.out.println("-".repeat(73));

            while (rs.next()){
                System.out.printf("%-10s  %-10s%n" ,
                        rs.getInt("CategoryID"),
                        rs.getString("CategoryName"));
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    static void createTable (){
        String checkTable = "Select * from sys.tables where name = ? ";
        String createTable = "Create Table Categories (" +
                "CategoryId int Primary Key identity(1,1), " +
                "CategoryName varchar (255)," +
                ")";
        try (Connection conn = DataBaseManager.getConnection()) {

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1, "Categories");
            ResultSet rs = pstmt2.executeQuery();

            if (!rs.next()) {
                PreparedStatement pstmt3 = conn.prepareStatement(createTable);
                pstmt3.executeUpdate();
            }
        }
        catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }

    }
    boolean categoryAdd (){
        String checkTable = "Select * from sys.tables where name = ? ";
        String createTable = "Create Table Categories (" +
                "CategoryId int Primary Key identity(1,1), " +
                "CategoryName varchar (255)," +
                ")";
        String insert = "Insert into Categories (CategoryName) values ( ? )";

        try (Connection conn = DataBaseManager.getConnection()){

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1,"Categories");
            ResultSet rs = pstmt2.executeQuery();

            if (!rs.next()){
                PreparedStatement pstmt3 = conn.prepareStatement(createTable);
                pstmt3.executeUpdate();
            }

            try (PreparedStatement pstmt3 = conn.prepareStatement(insert);){
                pstmt3.setString(1,this.categoryName);
                int roweffected = pstmt3.executeUpdate();
                if (roweffected > 0){
                    System.out.println("ADDED");
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
    //////////////////////////////////////////////////////////////////////////////////////////////////
    boolean checkCategoryExsistance (){
        String checkEmployee = "Select * from Categories where categoryName = ? ";
        try (Connection conn = DataBaseManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(checkEmployee);
            pstmt.setString(1,this.categoryName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()){
                return true;
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
        return false;
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    void deleteCategory(int id){
        String query = "DELETE FROM categories WHERE categoryId = ?";
        try (Connection conn = DataBaseManager.getConnection();){
            PreparedStatement pstmt = conn.prepareStatement(query);
            pstmt.setInt(1,id);
            int rowsAffected = pstmt.executeUpdate();

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
    //////////////////////////////////////////////////////////////////////////////////////////////////
    void search (String Name){
        String search = "SELECT * FROM categories WHERE categoryName LIKE ?";
        try (Connection conn = DataBaseManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(search);
            pstmt.setString(1, "%" + Name + "%");
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()){
                System.out.println("Found !!");
                System.out.println("-".repeat(73));
                System.out.printf("%-10s  %-10s%n", "ID" , "Name");
                System.out.println("-".repeat(73));
                while(rs.next()){
                    System.out.printf("%-10s  %-10s%n" ,
                            rs.getInt("categoryID"),
                            rs.getString("categoryName")
                    );
                }
            }
            else {
                System.out.println("NOT Found !!");
            }

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    //////////////////////////////////////////////////////////////////////////////////////////////////
    static int checkEmpty (){
        String checkEmpty = "SELECT COUNT(*) FROM Categories;\n";
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
}

