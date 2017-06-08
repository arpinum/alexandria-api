package arpinum.ddd.event;

public interface EventBusMiddleware {

    void intercept(Event<?> event, Runnable next);
}
