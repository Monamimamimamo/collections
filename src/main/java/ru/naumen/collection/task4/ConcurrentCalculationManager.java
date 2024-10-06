package ru.naumen.collection.task4;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.function.Supplier;

public class ConcurrentCalculationManager<T> {

    private final ConcurrentLinkedQueue<CompletableFuture<T>> futures = new ConcurrentLinkedQueue<>();

    public void addTask(Supplier<T> task) {
        CompletableFuture<T> future = CompletableFuture.supplyAsync(task);
        futures.offer(future);
    }

    public T getResult() {
        while (!futures.isEmpty()) {
            CompletableFuture<T> future = futures.poll();
            try {
                return future.get();
            } catch (InterruptedException | ExecutionException e) {
                throw new RuntimeException(e);
            }
        }
        return null;
    }
}
