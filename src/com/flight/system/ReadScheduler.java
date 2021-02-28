package com.flight.system;

import com.flight.system.util.ConfigurationLoader;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ReadScheduler {

    ScheduledExecutorService executor;
    private final static long refreshPeriod = Long.parseLong(ConfigurationLoader.getPropertyValue("refreshPeriodInSeconds"));

    public ReadScheduler(Flights flights) {
        executor = Executors.newScheduledThreadPool(1);
        DataReader readTask = new DataReader(flights);
        executor.scheduleAtFixedRate(readTask, 0, refreshPeriod, TimeUnit.SECONDS);
    }

    public void shutdown() {
        executor.shutdown();
    }
}
