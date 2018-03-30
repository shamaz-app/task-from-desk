package org.taskFromDesk.schedulerTest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Slf4j
public class ScheduleTest {

    public static void main(String agrs[]) {
//        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);
        ExecutorService executor = Executors.newFixedThreadPool(4);
        List<Boolean> result = null;

        try {
            List<String> list = new ArrayList<String>() {{
                add("First task");
                add("Second task");
                add("Third task");
                add("Fourth task");
            }};
            ScheduleTest scheduleTest = new ScheduleTest();
            Function<String, Boolean> func = scheduleTest::run;



            List<CompletableFuture<Boolean>> futures = new ArrayList<>();

            for (String item : list) {
                futures.add(CompletableFuture.supplyAsync(() -> func.apply(item), executor));
            }

            ScheduledExecutorService scheduler = Executors.newSingleThreadScheduledExecutor();
            scheduler.schedule(new Task(futures), 5, TimeUnit.SECONDS);
            scheduler.shutdown();

            CompletableFuture<Void> done = CompletableFuture.allOf(futures.toArray(new CompletableFuture[0]));
            CompletableFuture<List<Boolean>> allDone
                    = done.thenApply(element -> futures.stream().peek(it -> log.debug("Peeked 1 value {}", it)).map(CompletableFuture::join).peek(it -> log.debug("Peeked 2 value {}", it)).collect(Collectors.toList()));
            CompletableFuture<List<Boolean>> gtFuture = allDone.thenApply(ArrayList::new);
            log.debug("R 1 [{}] {} {} ", done, allDone, gtFuture);
            result = gtFuture.join();
            log.debug("R 2 [{}] {} {} {}", done, allDone, gtFuture, result);

        } catch (CancellationException e) {
            log.info("Exception last !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!! ......");
            executor.shutdownNow();
        } finally {
            executor.shutdown();
        }

    }

    public boolean run(String task) {
            try {
                log.debug("Task [{}] started", task);
                Thread.sleep(getMillis());
                log.debug("Task [{}] finished", task);
            } catch (InterruptedException e) {
                log.debug("Task [{}] exception", task, e);
                throw new RuntimeException(e);
            }
        return true;
    }

    private int getMillis() {
        return  100000; //ThreadLocalRandom.current().nextInt(10000);
    }
}
