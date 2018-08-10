package com.intact.rx.api;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicReference;

import static java.util.Objects.requireNonNull;

import com.intact.rx.api.cache.CachePolicy;
import com.intact.rx.core.cache.data.context.CacheMasterPolicy;
import com.intact.rx.core.cache.data.context.DataCachePolicy;
import com.intact.rx.core.cache.data.id.DomainCacheId;
import com.intact.rx.core.machine.RxThreadFactory;
import com.intact.rx.core.machine.RxThreadPoolConfig;
import com.intact.rx.core.machine.RxThreadPoolId;
import com.intact.rx.policy.*;

/**
 * Main HUB for default configurations in rx. They are used throughout rx when no user-defined input.
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public final class RxDefault {

    // -------------------------------------------------------
    // Default policy and configuration instances
    // -------------------------------------------------------

    private static final AtomicReference<CachePolicy> defaultCachePolicy = new AtomicReference<>(CachePolicy.create(CacheMasterPolicy.validForever(), DataCachePolicy.leastRecentlyUsedAnd(ResourceLimits.unlimited(), Lifetime.ofHours(2), MementoPolicy.none)));

    private static final AtomicReference<CachePolicy> defaultRequestCachePolicy = new AtomicReference<>(CachePolicy.unlimited(Lifetime.ofMinutes(60)));
    private static final AtomicReference<Duration> defaultClientTimeout = new AtomicReference<>(Duration.ofSeconds(60));

    /**
     * RxContext used to decorate (before, after) calls to user provided lambdas/actions/commands.
     */
    private static final AtomicReference<RxContext> defaultRxContext = new AtomicReference<>(RxContextNoOp.instance());

    /**
     * Timeout to decide when to remove inactive caches that are empty.
     */
    private static final AtomicReference<Timeout> cacheInactiveTimeout = new AtomicReference<>(Timeout.ofMinutes(30));

    /**
     * Default thread pool policy/config used by user-defined commands.
     */
    private static final AtomicReference<RxThreadPoolConfig> defaultCommandPoolConfig =
            new AtomicReference<>(RxThreadPoolConfig.create(MaxLimit.withLimit(50), RxThreadFactory.daemonWithName("rx-command "), RxThreadPoolId.create("rx-command")));

    /**
     * Default thread pool policy/config used by controllers.
     */
    private static final AtomicReference<RxThreadPoolConfig> controllerPoolPolicy =
            new AtomicReference<>(RxThreadPoolConfig.create(MaxLimit.withLimit(5), RxThreadFactory.daemonWithName("rx-controller "), RxThreadPoolId.create("rx-controller")));


    /**
     * Default thread pool policy/config used by monitors
     */
    private static final AtomicReference<RxThreadPoolConfig> monitorPoolPolicy =
            new AtomicReference<>(RxThreadPoolConfig.create(MaxLimit.withLimit(5), RxThreadFactory.daemonWithName("rx-monitor "), RxThreadPoolId.create("rx-monitor")));

    /**
     * Default domain cache id
     */
    private static final AtomicReference<DomainCacheId> defaultDomainCacheId = new AtomicReference<>(new DomainCacheId("RxCommand.DefaultDomainCacheId"));

    /**
     * Used for internal RxCommand data
     */
    private static final AtomicReference<DomainCacheId> defaultRxCommandDomainCacheId = new AtomicReference<>(new DomainCacheId("RxCommand.DefaultRxCommandCacheId"));

    // -------------------------------------------------------
    // Get default policies.
    // -------------------------------------------------------

    public static CachePolicy getDefaultCachePolicy() {
        return defaultCachePolicy.get();
    }


    public static CachePolicy getDefaultRequestCachePolicy() {
        return defaultRequestCachePolicy.get();
    }

    public static Duration getDefaultClientTimeout() {
        return defaultClientTimeout.get();
    }

    public static RxContext getDefaultRxContext() {
        return defaultRxContext.get();
    }


    public static Timeout getCacheInactiveTimeout() {
        return cacheInactiveTimeout.get();
    }

    public static RxThreadPoolConfig getDefaultCommandPoolConfig() {
        return defaultCommandPoolConfig.get();
    }

    public static RxThreadPoolConfig getControllerPoolPolicy() {
        return controllerPoolPolicy.get();
    }

    public static RxThreadPoolConfig getMonitorPoolPolicy() {
        return monitorPoolPolicy.get();
    }

    public static DomainCacheId getDefaultDomainCacheId() {
        return defaultDomainCacheId.get();
    }

    public static DomainCacheId getDefaultRxCommandDomainCacheId() {
        return defaultRxCommandDomainCacheId.get();
    }


    // -------------------------------------------------------
    // Setters update default policies.
    // -------------------------------------------------------

    public static void setDefaultRequestCachePolicy(CachePolicy defaultRequestCachePolicy) {
        RxDefault.defaultRequestCachePolicy.set(requireNonNull(defaultRequestCachePolicy));
    }

    public static void setDefaultClientTimeout(Duration defaultClientTimeout) {
        RxDefault.defaultClientTimeout.set(requireNonNull(defaultClientTimeout));
    }

    public static void setDefaultRxContext(RxContext defaultRxContext) {
        RxDefault.defaultRxContext.set(requireNonNull(defaultRxContext));
    }

    public static void setCacheInactiveTimeout(Timeout cacheInactiveTimeout) {
        RxDefault.cacheInactiveTimeout.set(requireNonNull(cacheInactiveTimeout));
    }

    public static void setDefaultCommandPoolConfig(RxThreadPoolConfig defaultCommandPoolConfig) {
        RxDefault.defaultCommandPoolConfig.set(requireNonNull(defaultCommandPoolConfig));
    }

    public static void setControllerPoolPolicy(RxThreadPoolConfig controllerPoolPolicy) {
        RxDefault.controllerPoolPolicy.set(requireNonNull(controllerPoolPolicy));
    }

    public static void setMonitorPoolPolicy(RxThreadPoolConfig monitorPoolPolicy) {
        RxDefault.monitorPoolPolicy.set(requireNonNull(monitorPoolPolicy));
    }

    public static void setDefaultDomainCacheId(DomainCacheId defaultDomainCacheId) {
        RxDefault.defaultDomainCacheId.set(requireNonNull(defaultDomainCacheId));
    }

    public static void setDefaultRxCommandDomainCacheId(DomainCacheId defaultRxCommandDomainCacheId) {
        RxDefault.defaultRxCommandDomainCacheId.set(requireNonNull(defaultRxCommandDomainCacheId));
    }

    private RxDefault() {
    }

}
