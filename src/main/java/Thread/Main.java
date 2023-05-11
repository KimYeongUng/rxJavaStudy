package Thread;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

@Slf4j
public class Main {
    public static boolean stopRequest;
    public static void main(String[] args) throws InterruptedException {
        Thread background = new Thread(()->{
            for (int i=0;!stopRequest;i++){
                System.out.println("Terminate BackGround Thread:"+i);
            }
        });

        background.start();

        TimeUnit.SECONDS.sleep(1);
        stopRequest = true;
        System.out.println("Terminate main Thread");
    }
}
