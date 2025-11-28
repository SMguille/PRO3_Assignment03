package via.pro3.repositories;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;

import java.sql.SQLException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@DisplayName("ProductRepository Tests")
public class ProductRepositoryTest {

    private ProductRepository productRepository;

    @BeforeEach
    void setUp() {
        productRepository = new ProductRepository();
    }

    @Test
    @DisplayName("Z - Zero: Non-existent animal ID returns empty list")
    void getProductsByAnimalId_nonExistentAnimalId_returnsEmptyList() throws SQLException {

        int nonExistentAnimalId = 99999;
        
        List<Integer> result = productRepository.getProductsByAnimalId(nonExistentAnimalId);
        
        assertNotNull(result, "Result should not be null");
        assertTrue(result.isEmpty(), "Result should be empty for non-existent animal");
    }

    @Test
    @DisplayName("O - One: Valid animal ID returns expected products")
    void getProductsByAnimalId_validAnimalId_returnsExpectedProducts() throws SQLException {

        int animalId = 1; //animal 1 -> product 1
        
        List<Integer> result = productRepository.getProductsByAnimalId(animalId);
        
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");
        assertTrue(result.contains(1), "Should contain product ID 1");
    }

    @Test
    @DisplayName("M - Many: Animal with multiple products returns all products")
    void getProductsByAnimalId_animalWithMultipleProducts_returnsAllProducts() throws SQLException {

        int animalId = 3; //animal 3 -> products 2 and 3
        
        List<Integer> result = productRepository.getProductsByAnimalId(animalId);
        
        assertNotNull(result, "Result should not be null");
        assertFalse(result.isEmpty(), "Result should not be empty");
        assertTrue(result.contains(2), "Should contain product ID 2");
        assertTrue(result.contains(3), "Should contain product ID 3");
    }

    @Test
    @DisplayName("B - Boundary: Minimum valid animal ID works")
    void getProductsByAnimalId_minimumValidId_works() throws SQLException {

        int animalId = 1;
        
        List<Integer> result = productRepository.getProductsByAnimalId(animalId);
        
        assertNotNull(result, "Result should not be null");
    }


    @Test
    @DisplayName("I - Interface: Method returns correct List<Integer> type")
    void getProductsByAnimalId_returnsCorrectType() throws SQLException {

        int animalId = 1;
        
        List<Integer> result = productRepository.getProductsByAnimalId(animalId);
        
        assertNotNull(result, "Result should not be null");
        assertTrue(result instanceof List, "Result should be a List");
        if (!result.isEmpty()) {
            assertTrue(result.get(0) instanceof Integer, "Elements should be Integers");
        }
    }

    @Test
    @DisplayName("E - Exception: Method handles database errors properly")
    void getProductsByAnimalId_databaseError_throwsSQLException() {

        assertDoesNotThrow(() -> {
            productRepository.getProductsByAnimalId(1);
        }, "Should not throw when database is working");
    }

    @Test
    @DisplayName("S - State: Method is stateless and consistent")
    void getProductsByAnimalId_isStateless() throws SQLException {

        int animalId = 1;
        
        List<Integer> result1 = productRepository.getProductsByAnimalId(animalId);
        List<Integer> result2 = productRepository.getProductsByAnimalId(animalId);
        
        assertEquals(result1, result2, "Multiple calls should return same result");
        assertNotSame(result1, result2, "Should return new list instances");
    }
}
