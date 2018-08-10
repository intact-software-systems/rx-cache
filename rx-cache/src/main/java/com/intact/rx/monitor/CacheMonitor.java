package com.intact.rx.monitor;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.intact.rx.core.machine.RxThreadPoolFactory;
import com.intact.rx.templates.DoubleCheckedLocking;
import com.intact.rx.templates.api.InitializeMethods;

public class CacheMonitor implements InitializeMethods {
    private static final Logger log = LoggerFactory.getLogger(CacheMonitor.class);
    private static final CacheMonitor instance = new CacheMonitor();

    private final AtomicBoolean isInitialized;

    private CacheMonitor() {
        this.isInitialized = new AtomicBoolean(false);
    }

    public static CacheMonitor instance() {
        DoubleCheckedLocking.initialize(instance);
        return instance;
    }

    @Override
    public boolean isInitialized() {
        return isInitialized.get();
    }

    @Override
    public boolean initialize() {
        RxThreadPoolFactory.monitorPool().scheduleWithFixedDelay(CacheMonitorAlgorithms::monitorAllCaches, 10, 5, TimeUnit.SECONDS);
        log.info("rx-command cache monitor initialized");

        isInitialized.set(true);
        return true;
    }
}
