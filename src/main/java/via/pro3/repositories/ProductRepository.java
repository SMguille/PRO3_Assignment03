package via.pro3.repositories;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductRepository {

    public List<Integer> getProductsByAnimalId(int animalId) throws SQLException {
        List<Integer> products = new ArrayList<>();
        DBConnection db = new DBConnection();

        String query = "SELECT product_id FROM product_animal_link WHERE animal_id = ?";

        try (Connection connection = db.openConnection();
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, animalId);

            try (ResultSet rs = statement.executeQuery()) {
                while (rs.next()) {
                    products.add(rs.getInt("product_id"));
                }
            }
        }

        return products;
    }
}
