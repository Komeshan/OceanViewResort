import util.DatabaseConnection;
import java.sql.Connection;

public class Main {
    public static void main(String[] args) {
        Connection conn = DatabaseConnection.getInstance();
        if (conn != null) {
            System.out.println("Test successful: Database is connected!");
        }
    }
}
