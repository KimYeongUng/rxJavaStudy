package data;

public class StockCurrencyPriceEmitter {
    StockCurrencyPriceListener listener;
    public void setStockListener(StockCurrencyPriceListener listener){
        this.listener = listener;
    }

    public void flowInfo(){
        listener.onPrice(SampleData.applPrice);
    }

    public void complete(){
        listener.onComplete();
    }
}
