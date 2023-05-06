package operator;

import data.StockCurrencyPriceEmitter;
import data.StockCurrencyPriceListener;
import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.scheduler.Schedulers;

import java.util.List;

@Slf4j
public class CreateEx2 {
    public static void main(String[] args) throws InterruptedException {
        StockCurrencyPriceEmitter emitter = new StockCurrencyPriceEmitter();

        Flux.create((FluxSink<Double> sink)->emitter.setStockListener(new StockCurrencyPriceListener() {
            @Override
            public void onPrice(List<Double> priceList) {
                priceList.forEach(sink::next);
            }

            @Override
            public void onComplete() {
                sink.complete();
            }
        }))
                .publishOn(Schedulers.parallel())
                .subscribe(data->log.info("# onNext: {}",data)
                ,err->log.error("#err: {}",err.getMessage())
                ,()->log.info("#onComplete"));
        Thread.sleep(3000L);
        emitter.flowInfo();
        Thread.sleep(2000L);
        emitter.complete();
    }
}
