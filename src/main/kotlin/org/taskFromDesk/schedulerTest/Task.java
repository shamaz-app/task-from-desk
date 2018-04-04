package org.taskFromDesk.schedulerTest;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.CancellationException;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;

@Slf4j
public class Task implements Runnable {

    private  ExecutorService executor;
    private boolean done;
    List<CompletableFuture<Boolean>> futures;

    public Task(ExecutorService executor) {
        this.executor = executor;
    }

    public Task(List<CompletableFuture<Boolean>> futures) {
        this.futures = futures;

    }

    @Override
    public void run() {

            futures.forEach(it -> {
                try {
                    it.complete(false);
                } catch (Exception e) {
                    log.warn("task [{}] cancelled with error ", it, e);
                }
            });


//        if (!executor.isTerminated()) {
//            executor.shutdownNow();
//
//        } else {
//            System.out.println("Executor already terminated!");
//        }
//        System.out.println("Executor was shutdown!");
    }

    public void cancel(){
        futures.forEach(it -> it.cancel(true));
    }
}
