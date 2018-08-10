package com.intact.rx.api;

import java.time.Duration;

import static java.util.Objects.requireNonNull;

import com.intact.rx.api.cache.CachePolicy;
import com.intact.rx.core.cache.data.id.DomainCacheId;
import com.intact.rx.core.machine.RxThreadPoolConfig;

@SuppressWarnings({"WeakerAccess", "PublicField"})
public class RxConfig {
    public final CachePolicy cachePolicy;
    public final RxThreadPoolConfig rxThreadPoolConfig;
    public final DomainCacheId domainCacheId;
    public final Duration clientTimeout;

    public RxConfig(CachePolicy defaultCachePolicy,
                    RxThreadPoolConfig rxThreadPoolConfig,
                    Duration clientTimeout,
                    DomainCacheId domainCacheId) {
        this.cachePolicy = requireNonNull(defaultCachePolicy);
        this.rxThreadPoolConfig = requireNonNull(rxThreadPoolConfig);
        this.clientTimeout = requireNonNull(clientTimeout);
        this.domainCacheId = requireNonNull(domainCacheId);
    }

    public static RxConfig fromRxDefault() {
        return new RxConfig(
                RxDefault.getDefaultCachePolicy(),
                RxDefault.getDefaultCommandPoolConfig(),
                RxDefault.getDefaultClientTimeout(),
                RxDefault.getDefaultDomainCacheId());
    }

    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends RxConfigBuilder<Builder> {
        private final State state;

        public Builder() {
            //noinspection ThisEscapedInObjectConstruction
            state = new State(this);
        }

        public RxConfig build() {
            return buildRxConfig();
        }

        @Override
        protected State state() {
            return state;
        }
    }

    @Override
    public String toString() {
        return "RxConfig{" +
                ", rxThreadPoolConfig=" + rxThreadPoolConfig +
                ", domainCacheId=" + domainCacheId +
                ", clientTimeout=" + clientTimeout +
                '}';
    }
}
