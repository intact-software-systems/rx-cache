package com.intact.rx.api;

import com.intact.rx.monitor.CacheMonitor;

public final class RxRuntime {

    public static boolean initialize() {
        //start rx monitors
        CacheMonitor cacheMonitor = CacheMonitor.instance();
        return cacheMonitor.isInitialized();
    }

    private RxRuntime() {
    }
}
