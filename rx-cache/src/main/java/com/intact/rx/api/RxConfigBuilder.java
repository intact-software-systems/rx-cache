package com.intact.rx.api;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

import com.intact.rx.api.cache.CachePolicy;
import com.intact.rx.core.cache.data.id.DomainCacheId;
import com.intact.rx.core.cache.factory.DataCachePolicyBuilder;
import com.intact.rx.core.machine.RxThreadPoolConfig;
import com.intact.rx.core.machine.RxThreadPoolId;
import com.intact.rx.policy.*;

import static com.intact.rx.templates.Validate.assertTrue;

public abstract class RxConfigBuilder<Builder> {

    @SuppressWarnings({"PublicField", "WeakerAccess"})
    protected class State {
        public CachePolicy cachePolicy = RxDefault.getDefaultCachePolicy();
        public RxThreadPoolConfig rxThreadPoolConfig = RxDefault.getDefaultCommandPoolConfig();
        public DomainCacheId domainCacheId = RxDefault.getDefaultDomainCacheId();
        public Duration clientTimeout = RxDefault.getDefaultClientTimeout();

        public DataCachePolicyBuilder dataCachePolicyBuilder = DataCachePolicyBuilder.from(RxDefault.getDefaultCachePolicy().getDataCachePolicy());

        public final Builder builder;

        public Builder update(RxConfig rxConfig) {
            cachePolicy = rxConfig.cachePolicy;
            rxThreadPoolConfig = rxConfig.rxThreadPoolConfig;
            domainCacheId = rxConfig.domainCacheId;
            clientTimeout = rxConfig.clientTimeout;
            dataCachePolicyBuilder = DataCachePolicyBuilder.from(rxConfig.cachePolicy.getDataCachePolicy());
            return builder;
        }

        public State(Builder builder) {
            this.builder = builder;
        }
    }

    protected abstract State state();

    protected RxConfig buildRxConfig() {
        return new RxConfig(
                CachePolicy.create(state().cachePolicy.getCacheMasterPolicy(), state().dataCachePolicyBuilder.build()),
                state().rxThreadPoolConfig,
                state().clientTimeout,
                state().domainCacheId);
    }

    public Builder withRxConfig(RxConfig rxConfig) {
        return state().update(rxConfig);
    }

    public Builder withThreadPoolConfig(String threadPoolKey, int threadPoolSize) {
        assertTrue(threadPoolSize > 0);

        state().rxThreadPoolConfig = RxThreadPoolConfig.create(MaxLimit.withLimit(threadPoolSize), RxThreadPoolId.create(threadPoolKey));
        return state().builder;
    }

    public Builder withThreadPoolConfig(RxThreadPoolConfig commandThreadPoolPolicy) {
        state().rxThreadPoolConfig = requireNonNull(commandThreadPoolPolicy);
        return state().builder;
    }

    public Builder withClientTimeout(Duration clientTimeout) {
        state().clientTimeout = requireNonNull(clientTimeout);
        return state().builder;
    }

    public Builder withCacheResourceLimits(ResourceLimits resourceLimits) {
        state().dataCachePolicyBuilder.withResourceLimits(resourceLimits);
        return state().builder;
    }

    public Builder withCacheMementoPolicy(MementoPolicy mementoPolicy) {
        state().dataCachePolicyBuilder.withDataCacheMementoPolicy(mementoPolicy);
        return state().builder;
    }

    public Builder withCachedObjectLifetime(Lifetime lifetime) {
        state().dataCachePolicyBuilder.withObjectLifetime(lifetime);
        return state().builder;
    }

    public Builder withCachedObjectMementoPolicy(MementoPolicy mementoPolicy) {
        state().dataCachePolicyBuilder.withObjectMementoPolicy(mementoPolicy);
        return state().builder;
    }

    public Builder withCachedObjectExtension(Extension extension) {
        state().dataCachePolicyBuilder.withObjectExtension(extension);
        return state().builder;
    }

    public Builder withDomainCacheId(DomainCacheId domainCacheId) {
        state().domainCacheId = requireNonNull(domainCacheId);
        return state().builder;
    }
}
