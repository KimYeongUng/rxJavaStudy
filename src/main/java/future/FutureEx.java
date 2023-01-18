package future;


import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class FutureEx {

    public interface SuccessCallback{
        void onSuccess(String result);
    }

    public interface ExceptionCallback{
        void onError(Throwable t);
    }

    public static class CallbackFutureTask extends FutureTask<String>{
        SuccessCallback sc;
        ExceptionCallback ec;

        public CallbackFutureTask(Callable<String> callable,SuccessCallback sc,ExceptionCallback ec) {
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }

        @Override
        protected void done(){
            try {
                sc.onSuccess(get());
            }catch (ExecutionException e){
                ec.onError(e.getCause());
            }
            catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }

        }
    }

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService es = Executors.newCachedThreadPool();

        CallbackFutureTask cft = new CallbackFutureTask(()->{
            Thread.sleep(2000);
            if(true)
                throw new RuntimeException("GET Async ERROR");

            log.info("Async");
            return "Hello";
        }, System.out::println,e->System.out.println("Error GET ; "+e));

        es.execute(cft);

        System.out.println("EXIT");
        es.shutdown();

    }
}
