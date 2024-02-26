
package tn.esprit.controller;
import tn.esprit.utils.MyDataBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/*public class ForumDao {
    public static List<String> getUniqueCategories() {
        List<String> categories = new ArrayList<>();
        try {
            String query = "SELECT DISTINCT Category FROM forum";
            Connection con;
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/esprit", "root", "");
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }

}*/


/*public class ForumDao {
    public static List<String> getUniqueCategories() {
       /* List<String> categories = new ArrayList<>();
        try (Connection con = MyDataBase.instance.reconnect();
             PreparedStatement ps = con.prepareStatement("SELECT DISTINCT Category FROM forum");
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                categories.add(rs.getString("Category"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return categories;
    }*/

