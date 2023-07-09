package functional;

import java.util.function.Supplier;

public class SupplierEx {
    public static void main(String[] args) throws InterruptedException {
        long start = System.currentTimeMillis();
        printIfValidIndex(1, SupplierEx::getVeryExpensiveValue);
        printIfValidIndex(-1, SupplierEx::getVeryExpensiveValue);
        printIfValidIndex(-3, SupplierEx::getVeryExpensiveValue);
        long end = System.currentTimeMillis();
        System.out.println(end-start);
    }

    private static String getVeryExpensiveValue(){
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        return "HERO";
    }

    private static void printIfValidIndex(int index,Supplier<String> value) {
        if(index>=0)
            System.out.println("Value is: "+value.get());
        else
            System.out.println("Invalid");
    }
}
