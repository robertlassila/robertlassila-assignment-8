package com.coderscampus.assignment;

import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;

public class AsyncNumberFetchingService {
    private final Assignment8 assignment8;
    private final ExecutorService executorService;
    private final List<List<Integer>> results;

    public AsyncNumberFetchingService() {
        this.assignment8 = new Assignment8();
        this.executorService = Executors.newCachedThreadPool();
        this.results = Collections.synchronizedList(new ArrayList<>());
    }

    public void fetchNumbers() {

        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            CompletableFuture<Void> task = CompletableFuture
                    .supplyAsync(assignment8::getNumbers, executorService)
                    .thenAccept(results::add);
            tasks.add(task);
        }
        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allTasks.join();
    }

    public void countAndPrintNumbers() {

        Map<Integer, Long> numberCounts = results
                .parallelStream()
                .flatMap(Collection::stream)
                .collect(Collectors.groupingByConcurrent(number -> number, Collectors.counting()));

        numberCounts.forEach((number, count) -> System.out.print(number + "=" + count + ", "));

    }

    public void shutdown() {
        executorService.shutdown();
    }
}
