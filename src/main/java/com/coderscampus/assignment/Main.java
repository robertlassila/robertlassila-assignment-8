package com.coderscampus.assignment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        Assignment8 assignment8 = new Assignment8();

        ExecutorService executor = Executors.newFixedThreadPool(6);
        List<List<Integer>> results = Collections.synchronizedList(new ArrayList<>());
        List<CompletableFuture<Void>> tasks = new ArrayList<>();

        for (int i = 0; i < 1000; i++) {
            CompletableFuture<Void> task = CompletableFuture
                    .supplyAsync(() -> assignment8.getNumbers(), executor)
                    .thenAccept(numbersList -> results.add(numbersList));
            tasks.add(task);
        }


        CompletableFuture<Void> allTasks = CompletableFuture.allOf(tasks.toArray(new CompletableFuture[0]));
        allTasks.join();


        executor.shutdown();

        for (int i = 0; i <= 14; i++) {
            final int target = i;
            long count = results.stream()
                    .flatMap(Collection::stream)
                    .filter(num -> num == target)
                    .count();

            System.out.print(target + "=" + count);
            if (i < 14) {
                System.out.print(", ");
            }
        }

    }
}
