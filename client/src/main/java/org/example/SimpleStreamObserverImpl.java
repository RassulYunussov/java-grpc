package org.example;

import io.grpc.stub.StreamObserver;
import reactor.core.publisher.MonoSink;

public class SimpleStreamObserverImpl<T> implements StreamObserver<T> {

    private final MonoSink<T> sink;

    public SimpleStreamObserverImpl(final MonoSink<T> sink) {
        this.sink = sink;
    }

    @Override
    public void onNext(T t) {
        this.sink.success(t);
    }

    @Override
    public void onError(Throwable throwable) {
        this.sink.error(throwable);
    }

    @Override
    public void onCompleted() {
        // noop
    }
}
