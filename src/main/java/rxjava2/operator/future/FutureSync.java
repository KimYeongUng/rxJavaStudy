package rxjava2.operator.future;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class FutureSync {
    public static void main(String[] args) throws InterruptedException {
        long startTime = System.currentTimeMillis();
        int n = doTask(1,3);
        log.info("Task Finished : task"+n);

        n = doTask(2,1);
        log.info("Task Finished : task"+n);

        n = doTask(3,1);
        log.info("Task Finished : task"+n);

        long endTime = System.currentTimeMillis();

        double execute = (endTime-startTime)/1000.0;
        log.info("Process Time: "+execute+"sec");
    }

    static int doTask(int taskNum,int sec) throws InterruptedException {
        Thread.sleep(sec*1000L);
        return taskNum;
    }
}
