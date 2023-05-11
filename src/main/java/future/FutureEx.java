package future;

import lombok.extern.slf4j.Slf4j;

import java.util.Objects;
import java.util.concurrent.*;

@Slf4j
public class FutureEx {
    public static void main(String[] args) throws InterruptedException, ExecutionException {
        ExecutorService es = Executors.newCachedThreadPool();

        CallBackFutureTask cf = new CallBackFutureTask(()->{
            Thread.sleep(1000L);
            log.info("Async");
            return "Hello";
        }
        ,log::info
        ,e->log.info("err :"+e.getMessage())
        );

        es.execute(cf);
        es.shutdown();
    }


    interface SuccessCallback {
        void onSuccess(String result);
    }

    interface ExceptionCallabck{
        void onError(Throwable throwable);
    }

    interface ExceptionCallback{
        void onError(Throwable t);
    }
    static class CallBackFutureTask extends FutureTask{
        SuccessCallback sc;
        ExceptionCallback ec;
        public CallBackFutureTask(Callable<String> callable, SuccessCallback sc, ExceptionCallback ec){
            super(callable);
            this.sc = Objects.requireNonNull(sc);
            this.ec = Objects.requireNonNull(ec);
        }
        @Override
        public void done(){
            try {
                sc.onSuccess((String) get());
            }catch (InterruptedException e){
                Thread.currentThread().interrupt();
            }catch (ExecutionException e){
                ec.onError(e.getCause());
            }
        }

    }
}