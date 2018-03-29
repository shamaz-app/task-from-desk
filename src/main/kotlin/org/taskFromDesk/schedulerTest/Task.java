package org.taskFromDesk.schedulerTest;

import java.util.concurrent.ExecutorService;

public class Task implements Runnable {

    private final ExecutorService executor;

    public Task(ExecutorService executor) {
        this.executor = executor;
    }

    @Override
    public void run() {
        executor.shutdownNow();
        System.out.println("Executor was shutdown!");
    }
}
