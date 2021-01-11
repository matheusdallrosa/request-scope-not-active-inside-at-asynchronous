package bug.reproducer;

import org.eclipse.microprofile.faulttolerance.Asynchronous;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.context.ContextNotActiveException;
import javax.enterprise.context.RequestScoped;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;

@ApplicationScoped
public class AsynchronousSevice {

    @Inject
    BeanManager beanManager;

    public boolean isRequestScopeActive() {
        try {
            if (beanManager.getContext(RequestScoped.class).isActive()) {
                return true;
            } else {
                return false;
            }
        } catch (final ContextNotActiveException e) {
            return false;
        }
    }

    @Asynchronous
    public CompletionStage<String> asynchronousMethod(){
        System.out.println("--------------------------------- is request scoped active?");
        System.out.println("--------------------------------- " + isRequestScopeActive());
        return CompletableFuture.completedFuture("done!");
    }

}
