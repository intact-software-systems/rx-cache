package com.intact.rx.core.machine;

import java.util.Optional;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;

import com.intact.rx.api.RxDefault;
import com.intact.rx.api.cache.CachePolicy;
import com.intact.rx.api.cache.RxCache;
import com.intact.rx.api.cache.RxCacheAccess;
import com.intact.rx.core.cache.CacheReaderWriter;
import com.intact.rx.core.cache.data.context.CacheMasterPolicy;
import com.intact.rx.core.cache.data.context.DataCachePolicy;
import com.intact.rx.core.cache.data.id.DataCacheId;
import com.intact.rx.core.cache.data.id.MasterCacheId;

public final class RxThreadPoolFactory {
    private static final DataCacheId commandControllerCacheId = DataCacheId.create(ScheduledThreadPoolExecutor.class, MasterCacheId.create(RxThreadPoolFactory.class));
    private static final CachePolicy cachePolicy = CachePolicy.create(CacheMasterPolicy.validForever(), DataCachePolicy.unlimitedForever());

    public static ScheduledThreadPoolExecutor computeIfAbsent(RxThreadPoolConfig poolPolicy,
                                                              int limit, ThreadFactory threadFactory) {
        return poolcache().computeIfAbsent(poolPolicy.getThreadPoolId(), id -> new ScheduledThreadPoolExecutor(limit, threadFactory));
    }

    public static Optional<ScheduledThreadPoolExecutor> findThreadPool(RxThreadPoolId id) {
        return poolcache().read(id);
    }

    public static void shutdown(RxThreadPoolId rxThreadPoolId) {
        poolcache().take(rxThreadPoolId).ifPresent(ScheduledThreadPoolExecutor::shutdown);
    }

    public static RxCache<RxThreadPoolId, ScheduledThreadPoolExecutor> poolcache() {
        return new CacheReaderWriter<>(() -> RxCacheAccess.defaultRxCacheFactory().computeDataCacheIfAbsent(commandControllerCacheId, cachePolicy));
    }

    public static ScheduledThreadPoolExecutor monitorPool() {
        return computeIfAbsent(RxDefault.getMonitorPoolPolicy(), RxDefault.getMonitorPoolPolicy().threadPoolSize().getLimit(), RxDefault.getMonitorPoolPolicy().getThreadFactory());
    }

    private RxThreadPoolFactory() {
    }
}
