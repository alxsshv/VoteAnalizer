import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DBConnection {

    private static Connection connection;

    private static final String dbName = "learn";
    private static final String dbUser = "root";
    private static final String dbPass = "skillbox";

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(
                    "jdbc:mysql://localhost:3306/" + dbName +
                        "?user=" + dbUser + "&password=" + dbPass);
                connection.createStatement().execute("DROP TABLE IF EXISTS voter_count");
                connection.createStatement().execute("DROP TABLE IF EXISTS station_visit");
                connection.createStatement().execute("CREATE TABLE voter_count(" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "name TINYTEXT NOT NULL, " +
                    "birthDate DATE NOT NULL, " +
                    "`count` INT NOT NULL, " +
                    "PRIMARY KEY(id), " +
                    "UNIQUE KEY name_date(name(50), birthDate))");
                connection.createStatement().execute("CREATE TABLE station_visit(" +
                    "id INT NOT NULL AUTO_INCREMENT, " +
                    "station INT NOT NULL, " +
                    "visitDate DATE NOT NULL, " +
                    "visitTime TIME NOT NULL, " +
                    "PRIMARY KEY(id))");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return connection;
    }

    public static void executeMultiInsertVoters(String votersRecords) throws SQLException{
        StringBuilder sql = new StringBuilder();
                sql.append("INSERT INTO voter_count(name, birthDate, `count`) VALUES");
                sql.append(votersRecords);
                sql.append("ON DUPLICATE KEY UPDATE `count` = `count` + 1");
        DBConnection.getConnection().createStatement().execute(sql.toString());
    }

    public static void printVoterCounts() throws SQLException {
        String sql = "SELECT name, birthDate, `count` FROM voter_count WHERE `count` > 1";
        ResultSet rs = DBConnection.getConnection().createStatement().executeQuery(sql);
        while (rs.next()) {
            System.out.println("\t" +
                rs.getString("name") + " (" +
                rs.getString("birthDate").replaceAll("-", ".") + ") - " +
                rs.getInt("count"));
        }
        rs.close();
    }
}
