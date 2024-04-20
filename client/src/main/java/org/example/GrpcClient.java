package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;
import org.example.grpc.HelloServiceGrpc;
import reactor.core.publisher.Mono;

public class GrpcClient {
    final ManagedChannel channel;
    final HelloServiceGrpc.HelloServiceStub stub;

    public GrpcClient() {
        this.channel = ManagedChannelBuilder.forAddress("localhost", 8080)
                .usePlaintext()
                .build();
        this.stub = HelloServiceGrpc.newStub(channel);
    }

    public Mono<HelloResponse> get() {
        return Mono.create(s -> stub.hello(HelloRequest.newBuilder()
                .setFirstName("John")
                .setLastName("Smith")
                .build(), new SimpleStreamObserverImpl<>(s))
        );
    }
}
