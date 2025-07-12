import java.sql.Connection;
import java.sql.DriverManager;
public class DataBaseManager {

    private static final String url = "jdbc:sqlserver://localhost:1433;databaseName=Inventory;encrypt=true;trustServerCertificate=true;";
    private static final String user = "my";
    private static final String password = "3336";

    // We just try here to Connect Over project with Data Base
    public static Connection getConnection  () {
        try {
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            // System.out.println("JDBC Driver loaded successfully!");1
            return DriverManager.getConnection(url, user, password);
        }catch (Exception e){
            System.out.println(e.getMessage());
            return null;
        }
    }



}
