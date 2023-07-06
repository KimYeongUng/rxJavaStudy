package functional;

import java.util.function.Consumer;
import java.util.function.Function;

public class FunctionalInterfaceEx {

    public static void main(String[] args) {
        String value = "100";

        // method call
        System.out.println(toInt(value));

        // lambda
        Function<String,Integer> toInt = Integer::parseInt;
        System.out.println(toInt.apply(value));

        // identity
        Function<Integer,Integer> identity = n->n;
        System.out.println(identity.apply(100));

        System.out.println("======");

        // Consumer
        final Consumer<String> print = System.out::println;
        // or
        final Consumer<String> print2 = s -> System.out.println("Greetings! "+s);

        // return 값 없음 -> compile error
        // final Function<String,Void> print2  = System.out::println;

        // consume
        print.accept("hello");
        print2.accept("Hello World");
    }

    // return own value
    public static Integer toInt(String value){
        return Integer.parseInt(value);
    }
}
