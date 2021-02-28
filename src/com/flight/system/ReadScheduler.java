package com.flight.system;

import com.flight.system.util.ConfigurationLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * The ReadScheduler class provides scheduling for a given task, by creating a
 * new thread and running the task in that thread every refreshPeriod seconds.
 * It also provides method to terminate the given task
 */
public class ReadScheduler {

    ScheduledExecutorService executor;
    private final static long refreshPeriod = Long.parseLong(ConfigurationLoader.getPropertyValue("refreshPeriodInSeconds"));

    public <T extends Runnable> ReadScheduler(T task) {
        executor = Executors.newScheduledThreadPool(1);
        executor.scheduleAtFixedRate(task, 0, refreshPeriod, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
