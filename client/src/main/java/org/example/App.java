package org.example;

public class App {
    public static void main(String[] args) {
        final var client = new GrpcClient();
        for (int i = 0; i < 10; i++) {
            final var result = client.get().block();
            System.out.println(result.getGreeting());
        }
    }
}
