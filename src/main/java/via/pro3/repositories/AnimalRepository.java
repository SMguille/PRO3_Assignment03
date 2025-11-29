package via.pro3.repositories;

import via.pro3.model.Animal;
import org.springframework.stereotype.Repository;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnimalRepository {

    public void save(Animal animal) throws SQLException {
        // We do NOT insert registration_number because it is SERIAL (auto-generated)
        String sql = "INSERT INTO animals (date, weight, origin) VALUES (?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(animal.getDate()));
            stmt.setDouble(2, animal.getWeight());
            stmt.setString(3, animal.getOrigin());
            stmt.executeUpdate();
        }
    }

    // Renamed to be clear, but takes an INT
    public Animal getByRegistrationNumber(int registrationNumber) throws SQLException {
        String sql = "SELECT * FROM animals WHERE registration_number = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, registrationNumber); // Setting int
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAnimal(rs);
                }
            }
        }
        return null;
    }

    public List<Animal> getAll() throws SQLException {
        List<Animal> animals = new ArrayList<>();
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM animals");
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                animals.add(mapResultSetToAnimal(rs));
            }
        }
        return animals;
    }

    public List<Animal> getByDate(LocalDate date) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE date = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(date));
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animals.add(mapResultSetToAnimal(rs));
                }
            }
        }
        return animals;
    }

    public List<Animal> getByOrigin(String origin) throws SQLException {
        List<Animal> animals = new ArrayList<>();
        String sql = "SELECT * FROM animals WHERE origin = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, origin);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animals.add(mapResultSetToAnimal(rs));
                }
            }
        }
        return animals;
    }

    private Animal mapResultSetToAnimal(ResultSet rs) throws SQLException {
        return new Animal(
                rs.getInt("registration_number"), // Retrieve as INT
                rs.getDate("date").toLocalDate(),
                rs.getDouble("weight"),
                rs.getString("origin")
        );
    }

    // Helper to get animal registration numbers (INTs) by product
    public List<Integer> getRegistrationNumbersByProductId(int productId) throws SQLException {
        List<Integer> regNumbers = new ArrayList<>();
        String sql = """
            SELECT a.registration_number
            FROM animals a
            JOIN product_animal_link pa ON pa.animal_registration_number = a.registration_number
            WHERE pa.product_id = ?
            """;

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, productId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    regNumbers.add(rs.getInt("registration_number"));
                }
            }
        }
        return regNumbers;
    }
}