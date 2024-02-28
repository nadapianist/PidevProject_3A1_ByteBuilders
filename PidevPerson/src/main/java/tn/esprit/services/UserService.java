package tn.esprit.services;
import tn.esprit.entities.User;
import tn.esprit.utils.MyDatabase;
import java.sql.*;
import java.util.*;

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
                } else if ("localCom".equals(role)) {
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
                stmt.setString(3, localCom.getFname());
                stmt.setString(4, localCom.getLname());
                stmt.setInt(5, localCom.getPhone());
                stmt.setString(6, localCom.getAvailability());
                stmt.setInt(7, localCom.getUserID());
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

                        case "localCom" -> new LocalCom(id, email, password, rs.getString("Fname"), rs.getString("Lname"), rs.getInt("phone"),
                                rs.getString("availability"));

                        default -> throw new IllegalArgumentException("Invalid user type: " + role);
                    };

                }

            }
            return null;
        }

    }

    public boolean doesEmailExist(String email) throws SQLException {
        String query = "SELECT COUNT(*) FROM User WHERE Email = ?";
        try (PreparedStatement ps = conn.prepareStatement(query)) {
            ps.setString(1, email);
            try (ResultSet resultSet = ps.executeQuery()) {
                if (resultSet.next()) {
                    int count = resultSet.getInt(1);
                    return count > 0; // Return true if count is greater than 0, indicating the email exists
                }
            }
        }
        return false; // Return false if an exception occurs or no rows are found
    }

    public int getUidByEmail(String email) throws SQLException {
        String query = "SELECT UserID FROM User WHERE Email = ?";
        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setString(1, email);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    return resultSet.getInt("UserID");
                } else {
                    // Email not found
                    return -1;
                }
            }
        }
    }

    public User searchByUid(int UserID) throws SQLException {
        String query = "SELECT * FROM User WHERE UserID = ?";
        User ur = null;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, UserID);

            try (ResultSet res = statement.executeQuery()) {
                if (res.next()) {
                    String Fname = res.getString(2);
                    String Lname = res.getString(3);
                    String Email = res.getString(4);
                    String pwd = res.getString(5);
                    int phone = res.getInt(6);
                    String role = res.getString(7);
                    String Bio = res.getString(8);
                    String Preferences = res.getString(9);
                    String Availability = res.getString(10);
                    String verifcode = res.getString(11);

                    if ("tourist".equals(role)) {
                        ur = new Tourist(UserID, Email, pwd, Fname, Lname, phone, Bio, Preferences);
                    } else if ("admin".equals(role)) {
                        ur = new Admin(UserID, Email, pwd, role);
                    } else if ("localCom".equals(role)) {
                        ur = new LocalCom(UserID, Email, pwd, Fname, Lname, phone, Availability);
                    } else {
                        throw new IllegalArgumentException("Invalid user type: " + role);
                    }

                    // Check for null and assign an empty string if needed
                    if (verifcode != null) {
                        ur.setVerification_code(verifcode);
                    }
                }
            }
        }

        return ur;
    }


    public List<User> searchByName(String nom) throws SQLException {
        List<User> resultats = new ArrayList<>();

        final String query = "SELECT * FROM user WHERE Email LIKE ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, "%" + nom + "%");  // Utilisation de LIKE pour rechercher partiellement

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("UserID");
                    final String email = rs.getString("Email");
                    final String password = rs.getString("pwd");
                    final String role = rs.getString("role");

                    final User utilisateur = switch (role) {
                        case "admin" -> new Admin(id, email, password);
                        case "tourist" -> new Tourist(id,rs.getString("Fname"),  rs.getString("Lname"),email, password, rs.getInt("phone"),role,rs.getString("Bio"),rs.getString("Preferences"));
                        case "localCom" -> new Tourist(id,rs.getString("Fname"),  rs.getString("Lname"),email, password, rs.getInt("phone"),
                                role,rs.getString("Availability"));
                        default -> throw new IllegalArgumentException("Invalid user type: " + role);
                    };

                    resultats.add(utilisateur);
                }
            }
        }

        return resultats;
    }

    public List<User> diplayListsortedbymail() throws SQLException {
        List<User> sortedUsers = this.listAll();
        Collections.sort(sortedUsers, Comparator.comparing(User::getEmail));
        return sortedUsers;
    }

    public void updatePassword(int userId, String newPassword) throws SQLException {
        String query = "UPDATE user SET pwd = ? WHERE UserID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, newPassword);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }

    public void updateVerificationCode(int userId, String verificationCode) throws SQLException {
        String query = "UPDATE user SET verifcode = ? WHERE UserID = ?";
        try (PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, verificationCode);
            stmt.setInt(2, userId);
            stmt.executeUpdate();
        }
    }
    public String getVerificationCodeByUserId(int userId) throws SQLException {
        String query = "SELECT verifcode FROM User WHERE UserID = ?";
        String verifcode = null;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    verifcode = resultSet.getString("verifcode");
                }
            }
        }

        return verifcode;
    }

    public String getEmailByUserId(int userId) throws SQLException {
        String query = "SELECT Email FROM User WHERE UserID = ?";
        String email = null;

        try (PreparedStatement statement = conn.prepareStatement(query)) {
            statement.setInt(1, userId);

            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    email = resultSet.getString("Email");
                }
            }
        }

        return email;
    }

}