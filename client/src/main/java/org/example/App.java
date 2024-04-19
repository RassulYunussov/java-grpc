package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;
import org.example.grpc.HelloServiceGrpc;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) {

        ManagedChannel channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();

        HelloServiceGrpc.HelloServiceBlockingStub stub
                = HelloServiceGrpc.newBlockingStub(channel);

        HelloResponse helloResponse = stub.hello(HelloRequest.newBuilder()
                .setFirstName("John")
                .setLastName("Smith")
                .build());

        System.out.printf(helloResponse.getGreeting());

        channel.shutdown();
    }
}
