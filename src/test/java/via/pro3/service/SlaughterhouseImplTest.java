package via.pro3.service;

import io.grpc.stub.StreamObserver;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import via.pro3.generated.*;
import via.pro3.repositories.AnimalRepository;
import via.pro3.repositories.ProductRepository;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("SlaughterhouseImpl Tests")
public class SlaughterhouseImplTest {

    @Mock
    private AnimalRepository animalRepository;
    
    @Mock
    private ProductRepository productRepository;
    
    @Mock
    private StreamObserver<AnimalsResponse> animalsResponseObserver;
    
    @Mock
    private StreamObserver<ProductsResponse> productsResponseObserver;

    private SlaughterhouseImpl slaughterhouseImpl;

    @BeforeEach
    void setUp() throws Exception {
        slaughterhouseImpl = new SlaughterhouseImpl();
        
        java.lang.reflect.Field animalRepoField = SlaughterhouseImpl.class.getDeclaredField("animalRepository");
        animalRepoField.setAccessible(true);
        animalRepoField.set(slaughterhouseImpl, animalRepository);
        
        java.lang.reflect.Field productRepoField = SlaughterhouseImpl.class.getDeclaredField("productRepository");
        productRepoField.setAccessible(true);
        productRepoField.set(slaughterhouseImpl, productRepository);
    }

    // ========== getAnimalsByProductId Tests ==========

    @Test
    @DisplayName("Z - Zero: Empty animal list returns empty response")
    void getAnimalsByProductId_emptyResult_returnsEmptyResponse() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        when(animalRepository.getAnimalsByProductId(1)).thenReturn(Collections.emptyList());
        
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        
        verify(animalRepository).getAnimalsByProductId(1);
        verify(animalsResponseObserver).onNext(argThat(response -> 
            response.getAnimalIdsList().isEmpty()));
        verify(animalsResponseObserver).onCompleted();
        verify(animalsResponseObserver, never()).onError(any());
    }

    @Test
    @DisplayName("O - One: Single animal returns correct response")
    void getAnimalsByProductId_singleAnimal_returnsCorrectResponse() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        List<Integer> animals = Arrays.asList(1);
        when(animalRepository.getAnimalsByProductId(1)).thenReturn(animals);
        
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        
        verify(animalRepository).getAnimalsByProductId(1);
        verify(animalsResponseObserver).onNext(argThat(response -> 
            response.getAnimalIdsList().size() == 1 && 
            response.getAnimalIdsList().contains(1)));
        verify(animalsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("M - Many: Multiple animals returns all in response")
    void getAnimalsByProductId_multipleAnimals_returnsAllAnimals() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        List<Integer> animals = Arrays.asList(1, 2, 3);
        when(animalRepository.getAnimalsByProductId(1)).thenReturn(animals);
        
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        
        verify(animalRepository).getAnimalsByProductId(1);
        verify(animalsResponseObserver).onNext(argThat(response -> 
            response.getAnimalIdsList().size() == 3 &&
            response.getAnimalIdsList().containsAll(Arrays.asList(1, 2, 3))));
        verify(animalsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("B - Boundary: Minimum product ID works")
    void getAnimalsByProductId_minimumProductId_works() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        when(animalRepository.getAnimalsByProductId(1)).thenReturn(Arrays.asList(1));
        
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        
        verify(animalRepository).getAnimalsByProductId(1);
        verify(animalsResponseObserver).onNext(any(AnimalsResponse.class));
        verify(animalsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("I - Interface: Correct AnimalsResponse type is sent")
    void getAnimalsByProductId_sendsCorrectResponseType() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        when(animalRepository.getAnimalsByProductId(1)).thenReturn(Arrays.asList(1));
        
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        
        verify(animalsResponseObserver).onNext(any(AnimalsResponse.class));
        verify(animalsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("E - Exception: SQLException is handled and RuntimeException thrown")
    void getAnimalsByProductId_sqlException_throwsRuntimeException() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        SQLException sqlException = new SQLException("Database connection failed");
        when(animalRepository.getAnimalsByProductId(1)).thenThrow(sqlException);
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        });
        
        assertEquals(sqlException, exception.getCause(), "RuntimeException should wrap the SQLException");
        verify(animalRepository).getAnimalsByProductId(1);
        verify(animalsResponseObserver, never()).onNext(any());
        verify(animalsResponseObserver, never()).onCompleted();
        verify(animalsResponseObserver, never()).onError(any());
    }

    @Test
    @DisplayName("S - State: Method doesn't modify service state")
    void getAnimalsByProductId_doesNotModifyState() throws SQLException {

        ProductRequest request = ProductRequest.newBuilder().setProductId(1).build();
        when(animalRepository.getAnimalsByProductId(1)).thenReturn(Arrays.asList(1));
        
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        slaughterhouseImpl.getAnimalsByProductId(request, animalsResponseObserver);
        
        verify(animalRepository, times(2)).getAnimalsByProductId(1);
    }

    // ========== getProductsByAnimalId Tests ==========

    @Test
    @DisplayName("Z - Zero: Empty product list returns empty response")
    void getProductsByAnimalId_emptyResult_returnsEmptyResponse() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        when(productRepository.getProductsByAnimalId(1)).thenReturn(Collections.emptyList());
        
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        
        verify(productRepository).getProductsByAnimalId(1);
        verify(productsResponseObserver).onNext(argThat(response -> 
            response.getProductIdsList().isEmpty()));
        verify(productsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("O - One: Single product returns correct response")
    void getProductsByAnimalId_singleProduct_returnsCorrectResponse() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        List<Integer> products = Arrays.asList(1);
        when(productRepository.getProductsByAnimalId(1)).thenReturn(products);
        
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        
        verify(productRepository).getProductsByAnimalId(1);
        verify(productsResponseObserver).onNext(argThat(response -> 
            response.getProductIdsList().size() == 1 && 
            response.getProductIdsList().contains(1)));
        verify(productsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("M - Many: Multiple products returns all in response")
    void getProductsByAnimalId_multipleProducts_returnsAllProducts() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        List<Integer> products = Arrays.asList(1, 2, 3);
        when(productRepository.getProductsByAnimalId(1)).thenReturn(products);
        
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        
        verify(productRepository).getProductsByAnimalId(1);
        verify(productsResponseObserver).onNext(argThat(response -> 
            response.getProductIdsList().size() == 3 &&
            response.getProductIdsList().containsAll(Arrays.asList(1, 2, 3))));
        verify(productsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("B - Boundary: Minimum animal ID works")
    void getProductsByAnimalId_minimumAnimalId_works() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        when(productRepository.getProductsByAnimalId(1)).thenReturn(Arrays.asList(1));
        
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        
        verify(productRepository).getProductsByAnimalId(1);
        verify(productsResponseObserver).onNext(any(ProductsResponse.class));
        verify(productsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("I - Interface: Correct ProductsResponse type is sent")
    void getProductsByAnimalId_sendsCorrectResponseType() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        when(productRepository.getProductsByAnimalId(1)).thenReturn(Arrays.asList(1));
        
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        
        verify(productsResponseObserver).onNext(any(ProductsResponse.class));
        verify(productsResponseObserver).onCompleted();
    }

    @Test
    @DisplayName("E - Exception: SQLException in getProductsByAnimalId throws RuntimeException")
    void getProductsByAnimalId_sqlException_throwsRuntimeException() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        SQLException sqlException = new SQLException("Database error");
        when(productRepository.getProductsByAnimalId(1)).thenThrow(sqlException);
        
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        });
        
        assertEquals(sqlException, exception.getCause(), "RuntimeException should wrap the SQLException");
        verify(productRepository).getProductsByAnimalId(1);
        verify(productsResponseObserver, never()).onNext(any());
        verify(productsResponseObserver, never()).onCompleted();
        verify(productsResponseObserver, never()).onError(any());
    }

    @Test
    @DisplayName("S - State: Method doesn't modify service state")
    void getProductsByAnimalId_doesNotModifyState() throws SQLException {

        AnimalRequest request = AnimalRequest.newBuilder().setAnimalId(1).build();
        when(productRepository.getProductsByAnimalId(1)).thenReturn(Arrays.asList(1));
        
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        slaughterhouseImpl.getProductsByAnimalId(request, productsResponseObserver);
        
        verify(productRepository, times(2)).getProductsByAnimalId(1);
    }

}
