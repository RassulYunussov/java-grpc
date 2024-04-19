package org.example;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;
import io.grpc.stub.CallStreamObserver;
import org.example.grpc.HelloRequest;
import org.example.grpc.HelloResponse;
import org.example.grpc.HelloServiceGrpc;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Sinks;

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
        Sinks.One<HelloResponse> sink = Sinks.one();
        final var observer = new CallStreamObserver<HelloResponse>() {
            @Override
            public boolean isReady() {
                return false;
            }

            @Override
            public void setOnReadyHandler(Runnable runnable) {

            }

            @Override
            public void disableAutoInboundFlowControl() {
                System.out.println("disableAutoInboundFlowControl");
            }

            @Override
            public void request(int i) {
                System.out.println(i);
            }

            @Override
            public void setMessageCompression(boolean b) {

            }

            @Override
            public void onNext(HelloResponse helloResponse) {
                sink.tryEmitValue(helloResponse);
            }

            @Override
            public void onError(Throwable throwable) {
                sink.tryEmitError(throwable);
            }

            @Override
            public void onCompleted() {
                System.out.println("complete");
            }
        };

        stub.hello(HelloRequest.newBuilder()
                .setFirstName("John")
                .setLastName("Smith")
                .build(), observer);

        return sink.asMono();
    }
}
