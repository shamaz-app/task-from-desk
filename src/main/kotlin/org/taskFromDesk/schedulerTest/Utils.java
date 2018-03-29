package org.taskFromDesk.schedulerTest;

import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Utils {

    public static <T, R> List<R> run(ExecutorService executor, List<T> data, Function<T, R> f) {
        List<R> result = null;

        if (!CollectionUtils.isEmpty(data)) {

            List<CompletableFuture<R>> futures = new ArrayList<>();

            for (T item : data) {
                futures.add(CompletableFuture.supplyAsync(() -> f.apply(item), executor));
            }

            CompletableFuture<List<R>> allDone = sequencedFutures(futures);
            CompletableFuture<List<R>> gtFuture = allDone.thenApply(gts -> gts.stream().collect(Collectors.toList()));

            result = gtFuture.join();
        }

        return result;
    }

    private static <T> CompletableFuture<List<T>> sequencedFutures(List<CompletableFuture<T>> futures) {
        CompletableFuture<Void> done =
                CompletableFuture.allOf(futures.toArray(new CompletableFuture[futures.size()]));
        return done.thenApply(element ->
                futures.stream().map(CompletableFuture::join).collect(Collectors.toList()));
    }
}
