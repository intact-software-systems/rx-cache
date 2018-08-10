module rx.cache.main {
    requires slf4j.api;

    exports com.intact.rx.api;
    exports com.intact.rx.api.cache;
    exports com.intact.rx.api.cache.observer;
    exports com.intact.rx.api.command;
    exports com.intact.rx.api.subject;

    exports com.intact.rx.core.cache.data.id;
    exports com.intact.rx.core.cache;
    exports com.intact.rx.core.cache.data.context;
    exports com.intact.rx.core.cache.factory;
    exports com.intact.rx.core.cache.data;

    exports com.intact.rx.exception;
    exports com.intact.rx.policy;
    exports com.intact.rx.templates;
    exports com.intact.rx.templates.annotations;
    exports com.intact.rx.templates.api;
    exports com.intact.rx.templates.key;

    exports com.intact.rx.core.machine.context;
    exports com.intact.rx.core.cache.status;
    exports com.intact.rx.core.cache.subject;
    exports com.intact.rx.core.machine;
    exports com.intact.rx.core.machine.api;
}