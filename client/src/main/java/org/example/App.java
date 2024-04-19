package org.example;

public class App {
    public static void main(String[] args) {
        final var result = new GrpcClient().get().block();
        System.out.println(result.getGreeting());
    }
}
