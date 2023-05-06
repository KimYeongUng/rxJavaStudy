package data;

import java.util.List;
public interface StockCurrencyPriceListener {
    void onPrice(List<Double> priceList);
    void onComplete();
}
