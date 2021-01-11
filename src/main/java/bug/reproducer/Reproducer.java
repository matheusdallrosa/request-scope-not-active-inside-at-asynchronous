package bug.reproducer;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import java.util.concurrent.ExecutionException;

@ApplicationScoped
@Path("/reproducer")
public class Reproducer {

    @Inject
    AsynchronousSevice asynchronousSevice;

    @GET
    public String get() throws ExecutionException, InterruptedException {
        return asynchronousSevice.asynchronousMethod().toCompletableFuture().get();
    }
}
