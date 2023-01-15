package duality;

import java.util.Observable;
import java.util.Observer;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Duality Observalbe <--> Iterator
// push
@SuppressWarnings("deprecation")
public class Ob2 {
    static class IterObservable extends Observable implements Runnable{

        @Override
        public void run() {
            for (int i=1;i<=10;i++){
                setChanged();
                notifyObservers(i); // Iterator next() 대응
            }
        }
    }
    public static void main(String[] args) {
        // subscriber
        Observer ob = (o, arg) -> {
            System.out.println(Thread.currentThread().getName()+": "+ arg);
        };

        IterObservable io = new IterObservable();
        io.addObserver(ob);

        ExecutorService es = Executors.newSingleThreadExecutor();
        es.execute(io);
        System.out.println(Thread.currentThread().getName()+": EXIT");
        es.shutdown();
    }
}
