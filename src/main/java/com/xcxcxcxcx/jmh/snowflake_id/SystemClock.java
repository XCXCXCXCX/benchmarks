package com.xcxcxcxcx.jmh.snowflake_id;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class SystemClock {

    private final long period;
    /**
     * 适用64位系统
     */
    private volatile long now = System.currentTimeMillis();

    private SystemClock(long period) {
        this.period = period;
        scheduleClockUpdating();
    }

    private static SystemClock instance() {
        return InstanceHolder.INSTANCE;
    }

    public static long now() {
        return instance().currentTimeMillis();
    }

    private void scheduleClockUpdating() {
        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, r -> {
            Thread t = new Thread(r, "system-clock");
            t.setDaemon(true);
            return t;
        });
        executorService.scheduleAtFixedRate(() -> {
                    now = System.currentTimeMillis();
                },
                period, period, TimeUnit.MILLISECONDS);
    }

    private long currentTimeMillis() {
        return now;
    }

    private static class InstanceHolder {
        public static final SystemClock INSTANCE = new SystemClock(1);
    }
}