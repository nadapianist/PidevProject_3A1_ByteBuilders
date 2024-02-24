package tn.esprit.services;
import tn.esprit.entities.User;
import tn.esprit.utils.MyDatabase;
import java.sql.*;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import tn.esprit.entities.*;
import org.jetbrains.annotations.Nullable;

public  class UserService implements  IUser<User> {


    private final Connection conn;

    public UserService() {
        this.conn = MyDatabase.getInstance().getConn();
    }

    public UserService(Connection conn) {
        this.conn = conn;
    }

    @Override
    public void add(User user) throws SQLException {
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            String query = "INSERT INTO user (Email, pwd, role) VALUES (?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, admin.getEmail());
                stmt.setString(2, admin.getPwd());
                stmt.setString(3, "admin");
                stmt.executeUpdate();
            }
        } else if (user instanceof Tourist) {
            Tourist tourist = (Tourist) user;
            String query = "INSERT INTO user (Email, pwd, role, Fname, Lname, phone, Bio, Preferences) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, tourist.getEmail());
                stmt.setString(2, tourist.getPwd());
                stmt.setString(3, "tourist");
                stmt.setString(4, tourist.getFname());
                stmt.setString(5, tourist.getLname());
                stmt.setInt(6, tourist.getPhone());
                stmt.setString(7, tourist.getBio());
                stmt.setString(8, tourist.getPreferences());
                stmt.executeUpdate();
            }
        } else if (user instanceof LocalCom) {
            LocalCom localCom = (LocalCom) user;
            String query = "INSERT INTO user (Email, pwd, role, Fname, Lname, phone, Availability) VALUES (?, ?, ?, ?, ?, ?, ?)";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, localCom.getEmail());
                stmt.setString(2, localCom.getPwd());
                stmt.setString(3, "localCom");
                stmt.setString(4, localCom.getFname());
                stmt.setString(5, localCom.getLname());
                stmt.setInt(6, localCom.getPhone());
                stmt.setString(7, localCom.getAvailability());
                stmt.executeUpdate();
            }
        }
    }

    @Override
    public void delete(int UserID) throws SQLException {
        final String query = "DELETE FROM user WHERE UserID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, UserID);
            stmt.executeUpdate();
        }
    }

    @Override
    public List<User> listAll() throws SQLException {
        final String query = "SELECT * FROM user";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            final List<User> users = new ArrayList<>();

            while (rs.next()) {
                int UserID = rs.getInt("UserID");
                final String Email = rs.getString("Email");
                final String pwd = rs.getString("pwd");
                final String role = rs.getString("role");


                final User user;
                if ("admin".equals(role)) {
                    user = new Admin(UserID, Email, pwd);
                } else if ("tourist".equals(role)) {
                    user = new Tourist(UserID, Email, pwd, rs.getString("Fname"), rs.getString("Lname"), rs.getInt("phone"), rs.getString("Bio"), rs.getString("Preferences"));
                } else if ("localcom".equals(role)) {
                    user = new LocalCom(UserID, Email, pwd, rs.getString("Fname"), rs.getString("Lname"), rs.getInt("phone"), rs.getString("Availability"));
                } else {
                    throw new IllegalArgumentException("Invalid user type: " + role);
                }

                users.add(user);
            }

            return users;

        }
    }

    @Override
    public void update(User user) throws SQLException {
        if (user instanceof Admin) {
            Admin admin = (Admin) user;
            String query = "UPDATE user SET Email = ?, pwd = ? WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getPwd());
                stmt.setInt(3, user.getUserID());
                stmt.executeUpdate();
                System.out.printf("Admin %d updated\n", user.getUserID());
            }
        } else if (user instanceof Tourist) {
            Tourist tourist = (Tourist) user;
            String query = "UPDATE user SET Email = ?, pwd = ?, Fname = ?, Lname = ?,  phone = ?, Bio = ?,Preferences = ? WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getPwd());
                stmt.setString(3, tourist.getFname());
                stmt.setString(4, tourist.getLname());
                stmt.setInt(5, tourist.getPhone());
                stmt.setString(6, tourist.getBio());
                stmt.setString(7, tourist.getPreferences());
                stmt.setInt(8, tourist.getUserID());
                stmt.executeUpdate();
                System.out.printf("Tourist %d updated\n", user.getUserID());
            }
        } else if (user instanceof LocalCom) {
            LocalCom localCom = (LocalCom) user;
            String query = "UPDATE user SET  Email = ?, pwd = ?, Fname = ?, Lname = ?,  phone = ? ,Availability = ? WHERE UserID = ?";
            try (PreparedStatement stmt = conn.prepareStatement(query)) {
                stmt.setString(1, user.getEmail());
                stmt.setString(2, user.getPwd());
                stmt.setString(3, user.getEmail());
                stmt.setString(4, localCom.getFname());
                stmt.setString(5, localCom.getLname());
                stmt.setInt(6, localCom.getPhone());
                stmt.setString(7, localCom.getAvailability());
                stmt.setInt(8, localCom.getUserID());
                stmt.executeUpdate();
                System.out.printf("LocalCom %d updated\n", user.getUserID());
            }

        }
    }
    public String encrypt(String password) {

        return Base64.getEncoder().encodeToString(password.getBytes());
    }

    public String decrypt(String password) {
        return new String(Base64.getMimeDecoder().decode(password));
    }

    public @Nullable User find(String username, String password) throws SQLException {
        String req = "select * from user where Email = ? AND pwd = ? ";
        try (PreparedStatement stmt = conn.prepareStatement(req)) {
            stmt.setString(1, username);
            stmt.setString(2, password);

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    final int id = rs.getInt("UserId");
                    final String email = rs.getString("Email");
                    final String role = rs.getString("role");

                    return switch (role) {
                        case "admin" -> new Admin(id, email, password);

                        case "tourist" ->
                                new Tourist(id, email, password, rs.getString("Fname"), rs.getString("Lname"), rs.getInt("phone"), rs.getString("Bio"),rs.getString("Preferences"));

                        case "localcom" -> new LocalCom(id, email, password, rs.getString("Fname"), rs.getString("Lname"), rs.getInt("phone"),
                                rs.getString("availability"));

                        default -> throw new IllegalArgumentException("Invalid user type: " + role);
                    };

                }

            }
            return null;
        }

    }
}