package via.pro3.repositories;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    // Parameter is INT registrationNumber
    public List<Integer> getProductsByAnimalRegistrationNumber(int registrationNumber) throws SQLException {
        List<Integer> products = new ArrayList<>();
        DBConnection db = new DBConnection();

        String query = "SELECT product_id FROM product_animal_link WHERE animal_registration_number = ?";

        try (Connection connection = db.openConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, registrationNumber); // Set as INT

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(rs.getInt("product_id"));
                }
            }
        }
        return products;
    }
}