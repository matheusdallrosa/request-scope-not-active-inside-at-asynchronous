package bug.reproducer;

import io.quarkus.runtime.ShutdownEvent;
import io.quarkus.runtime.StartupEvent;

import javax.enterprise.event.Observes;
import javax.inject.Inject;

public class AppLifeCycle {
    @Inject
    Consumer consumer;

    void onStart(@Observes StartupEvent event) {
        consumer.start("amqp://guest:guest@localhost:5672/%2F", "my-exchange", "my-queue");
    }

    void onStop(@Observes ShutdownEvent event) {
        consumer.stop();
    }
}
