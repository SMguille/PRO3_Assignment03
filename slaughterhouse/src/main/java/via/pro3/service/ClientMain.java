package via.pro3.service;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import via.pro3.generated.*;

public class ClientMain {
    private static final String HOST = "localhost";
    private static final int PORT = 50051;

    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder
                .forAddress(HOST, PORT)
                .usePlaintext()
                .build();

        SlaughterhouseGrpc.SlaughterhouseBlockingStub stub =
                SlaughterhouseGrpc.newBlockingStub(channel);

        int testProductId = 1;

        ProductRequest request = ProductRequest.newBuilder()
                .setProductId(testProductId)
                .build();

            AnimalsResponse response = stub.getAnimalsByProductId(request);

            if (response.getAnimalIdsCount() > 0) {
                System.out.println("Animals in Product ID " + testProductId + ":");

                for (Integer animalId : response.getAnimalIdsList()) {
                    System.out.println("- Animal ID: " + animalId);
                }
            } else {
                System.out.println("No animals found in Product ID " + testProductId);
            }

            int testAnimalId = 3;

            AnimalRequest request2 = AnimalRequest.newBuilder()
                    .setAnimalId(testAnimalId)
                    .build();

            ProductsResponse response2 = stub.getProductsByAnimalId(request2);

        if (response2.getProductIdsCount() > 0) {
            System.out.println("Products in Animal ID " + 3 + ":");

            for (Integer productId : response2.getProductIdsList()) {
                System.out.println("- Product ID: " + productId);
            }
        } else {
            System.out.println("No products found in Animal ID " + 3);
        }

            channel.shutdown();
    }
}