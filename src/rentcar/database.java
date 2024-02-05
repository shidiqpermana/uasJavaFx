package rentcar;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {

    /**
     *
     * @return
     */
    public static Connection connectDb() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection connect = DriverManager.getConnection("Jdbc:mysql://localhost/rentcar", "root", "");
            return connect;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
