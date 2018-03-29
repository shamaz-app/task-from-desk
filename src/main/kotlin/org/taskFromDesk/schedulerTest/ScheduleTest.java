package org.taskFromDesk.schedulerTest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.function.Function;

public class ScheduleTest {

    public static void main(String agrs[]) {
        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(4);

        try {
            List<String> list = new ArrayList<String>() {{
                add("First task");
                add("Second task");
                add("Third task");
                add("Fourth task");
            }};
            ScheduleTest scheduleTest = new ScheduleTest();
            Function<String, Boolean> func = scheduleTest::run;

            ExecutorService executor = Executors.newFixedThreadPool(4);

            scheduler.scheduleAtFixedRate(new Task(executor), 20, 3, TimeUnit.SECONDS);
            Utils.run(executor, list,func);

            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public boolean run(String task) {
        for (int i = 0; i < 1000; i++) {
            try {
                System.out.println(task);
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return true;
    }
}
