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
        String sql = "INSERT INTO animals (registration_number, date, weight, origin) VALUES (?, ?, ?, ?)";

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, animal.getRegistrationNumber());
            stmt.setDate(2, Date.valueOf(animal.getDate()));
            stmt.setDouble(3, animal.getWeight());
            stmt.setString(4, animal.getOrigin());
            stmt.executeUpdate();
        }
    }

    public Animal getByRegistrationNumber(String regNumber) throws SQLException {
        String sql = "SELECT * FROM animals WHERE registration_number = ?";
        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, regNumber);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToAnimal(rs);
                }
            }
        }
        return null;
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
                rs.getString("registration_number"),
                rs.getDate("date").toLocalDate(),
                rs.getDouble("weight"),
                rs.getString("origin")
        );
    }

    public List<Integer> getAnimalsByProductId(int productId) throws SQLException {
        List<Integer> animalIds = new ArrayList<>();
        String sql = """
            SELECT a.id
            FROM animals a
            JOIN product_animal_link pa ON pa.animal_id = a.id
            WHERE pa.product_id = ?
            """;

        try (Connection conn = DBConnection.openConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, productId);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    animalIds.add(rs.getInt("id"));
                }
            }
        }
        return animalIds;
    }
}