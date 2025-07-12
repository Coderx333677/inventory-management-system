import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class Products {

    String prodId,prodName; int prodPrice,cid,sid,prodStock;
    Products (){};
    Products (String n, int p ,int st, int c, int s){
        prodName = n;
        prodPrice = p;
        prodStock = st;
        cid = c;
        sid = s;
    }
    static void createProdTable (){
        String checkTable = "Select * from sys.tables where name = ? ";
        String createTable = "CREATE TABLE Products (" +
                "    ProdID INT PRIMARY KEY IDENTITY(1,1)," +
                "    ProdName VARCHAR(255)," +
                "    ProdPrice DECIMAL(10,2)," +
                "    ProdStock INT," +
                "    CategoryID INT," +
                "    SupplierID INT," +
                "    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)," +
                "    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID)" +
                ")";
        try (Connection conn = DataBaseManager.getConnection()) {

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1, "Products");
            ResultSet rs = pstmt2.executeQuery();

            if (!rs.next()) {
                PreparedStatement pstmt3 = conn.prepareStatement(createTable);
                pstmt3.executeUpdate();
            }
        }catch (Exception e){
            System.out.println("Error : " + e.getMessage());
        }

    }
    boolean addProduct (){
        String insert = "Insert into products (prodName , prodPrice,prodStock,CategoryID,SupplierID) values ( ? , ? , ? , ? , ?)";
        String checkTable = "Select * from sys.tables where name = ? ";
        String createTable = "CREATE TABLE Products (" +
                "    ProdID INT PRIMARY KEY IDENTITY(1,1)," +
                "    ProdName VARCHAR(255)," +
                "    ProdPrice INT," +
                "    ProdStock INT," +
                "    CategoryID INT," +
                "    SupplierID INT," +
                "    FOREIGN KEY (CategoryID) REFERENCES Categories(CategoryID)," +
                "    FOREIGN KEY (SupplierID) REFERENCES Suppliers(SupplierID)" +
                ")";
        try (Connection conn = DataBaseManager.getConnection()){

            PreparedStatement pstmt2 = conn.prepareStatement(checkTable);//Create if Not Exit
            pstmt2.setString(1,"Products");
            ResultSet rs = pstmt2.executeQuery();

            if (!rs.next()){
                PreparedStatement pstmt3 = conn.prepareStatement(createTable);
                pstmt3.executeUpdate();
            }

            try (PreparedStatement pstmt3 = conn.prepareStatement(insert);){
                pstmt3.setString(1,this.prodName);
                pstmt3.setInt(2,this.prodPrice);
                pstmt3.setInt(3,this.prodStock);
                pstmt3.setInt(4,this.cid);
                pstmt3.setInt(5,this.sid);
                int roweffected = pstmt3.executeUpdate();
                if (roweffected > 0){
                    System.out.println("Product Added");
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
    //Check that one product will not duplicate
    boolean checkProductExsistance() {
        String checkProduct = "Select * from Products where prodName = ? ";
        try (Connection conn = DataBaseManager.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(checkProduct);
            pstmt.setString(1,this.prodName);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                //System.out.println("Found !! ");
                return true;
            }

        } catch (Exception e) {
            System.out.println("ERROR : " + e.getMessage());
        }
        return false;
    }
    //******************************************************************************************************//
    static void printProduct (){
        String print = "Select * from Products ";
        try (Connection conn = DataBaseManager.getConnection();){

            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(print);

            System.out.println("-".repeat(73));
            System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n", "ID" , "Name" , "Price" , "Stock" , "CategoryID" , "SupplierID");
            System.out.println("-".repeat(73));

            while (rs.next()){
                System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n" ,
                        rs.getInt("prodID"),
                        rs.getString("prodName"),
                        rs.getString("prodPrice"),
                        rs.getString("prodStock"),
                        rs.getString("CategoryID"),
                        rs.getString("SupplierID"));
            }
        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    boolean editProduct (int id){
        String query = "UPDATE Products SET prodName = ?, prodPrice = ? , prodStock = ? , CategoryID = ? , SupplierID = ? WHERE prodID = ?";
        try (Connection conn = DataBaseManager.getConnection();){
            PreparedStatement pstmt = conn.prepareStatement(query);

            pstmt.setString(1,this.prodName);
            pstmt.setInt(2,this.prodPrice);
            pstmt.setInt(3,this.prodStock);
            pstmt.setInt(4,this.cid);
            pstmt.setInt(5,this.sid);
            pstmt.setInt(6,id);
            int rowsEffected  = pstmt.executeUpdate();
            if (rowsEffected > 0){
                return true;
            }

        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }return false;
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    static void deleteProduct (int id){
        String query = "DELETE FROM Products WHERE prodId = ?";
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
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
    static void search (String Name){
        String search = "SELECT * FROM Products WHERE prodName LIKE ?";
        try (Connection conn = DataBaseManager.getConnection()){
            PreparedStatement pstmt = conn.prepareStatement(search);
            pstmt.setString(1, "%" + Name + "%");
            ResultSet rs = pstmt.executeQuery();

           if (rs.next()){
               System.out.println("Found !!");
               System.out.println("-".repeat(73));
               System.out.printf("%-10s %-10s %-10s %-10s %-10s  %-10s%n", "ID" , "Name" , "Price" , "Stock" , "CategoryID" , "SupplierID");
               System.out.println("-".repeat(73));
               while(rs.next()){
                   System.out.printf("%-10s %-10s %-10s %-10s %-10s %-10s%n" ,
                           rs.getInt("prodID"),
                           rs.getString("prodName"),
                           rs.getString("prodPrice"),
                           rs.getString("prodStock"),
                           rs.getString("CategoryID"),
                           rs.getString("SupplierId"));
               }

            }
            else{
                System.out.println("NOT Found !!");
            }


        }catch (Exception e){
            System.out.println("ERROR : " + e.getMessage());
        }
    }
    /////////////////////////////////////////////////////////////////////////////////////////////////////////
}
