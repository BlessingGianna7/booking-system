

package models;

import java.sql.*;

import org.mindrot.jbcrypt.BCrypt;

public class UserDao {
    private String dburl = "jdbc:mysql://localhost:3306/library";
    private String username = "root";
    private String password = "kayumba@";
    private String dbdriver = "com.mysql.cj.jdbc.Driver";
    private static final String INSERT_STUDENT_SQL = "INSERT INTO library.users (fname, lname, account , email, password) VALUES (?,?, ?, ?, ?);";
    private static final String CHECK_CREDENTIALS_AND_ROLE_SQL =
            "SELECT * FROM library.users WHERE email = ?;";
    
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(dbdriver);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        
        Connection connection = DriverManager.getConnection(dburl, username, password);
        
        // Create the table if it doesn't exist
        createUsersTableIfNotExists(connection);
        
        return connection;
    }
    
    private void createUsersTableIfNotExists(Connection connection) {
        String createTableSQL = "CREATE TABLE IF NOT EXISTS library.users (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "fname VARCHAR(255) NOT NULL," +
                "lname VARCHAR(255) NOT NULL," +
                "account VARCHAR(255) NOT NULL,"+
                "email VARCHAR(255) NOT NULL," +
                "password VARCHAR(255) NOT NULL)";
        try (Statement statement = connection.createStatement()) {
            statement.executeUpdate(createTableSQL);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    public String insert(User u) {
        try (Connection con = getConnection();
             PreparedStatement stm = con.prepareStatement(INSERT_STUDENT_SQL)) {
            
            stm.setString(1, u.getfName());
            stm.setString(2, u.getlName());
            stm.setString(3, u.getAccount());
            stm.setString(4, u.getEmail());
            String hashedPassword = BCrypt.hashpw(u.getPassword(), BCrypt.gensalt());
            stm.setString(5, hashedPassword);
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return "Data not added!";
        }
        return "successfully added!";
    }
    
    public User checkCredentials(String email, String plainPassword) {
        try (Connection con = getConnection();
             PreparedStatement stm = con.prepareStatement(CHECK_CREDENTIALS_AND_ROLE_SQL)) {
            
            stm.setString(1, email);
            
            try (ResultSet resultSet = stm.executeQuery()) {
                if (resultSet.next()) {
                    String hashedPassword = resultSet.getString("password");
                    if (BCrypt.checkpw(plainPassword, hashedPassword)) {
                        String fName = resultSet.getString("fname");
                        String lName = resultSet.getString("lname");
                        String account = resultSet.getString("account");
                        return new User(fName, lName,account, email, hashedPassword);
                    }
                }
            }
            
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}

