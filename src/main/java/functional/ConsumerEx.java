package functional;

import java.util.function.BiConsumer;

public class ConsumerEx {
    public static void main(String[] args) {
        BiConsumer<Integer,Integer> sum = (a,b)-> System.out.println(a+"+"+b+"="+(a+b));
        BiConsumer<String,String> combine = (a,b)-> System.out.println(a+" "+b);
        sum.accept(10,11);
        combine.accept("Hello","World");
    }
}
