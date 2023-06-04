package functional;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

public class ConsumerEx {
    public static void main(String[] args) {
        // 2 parameters
        BiConsumer<Integer,Integer> multiple = (a,b)-> System.out.println(a+"*"+b+"="+(a*b));
        BiConsumer<String,String> combine = (a,b)-> System.out.println(a+" "+b);

        Consumer<String> print = System.out::println;

        combine.accept("Hello","World");

        print.accept("Hello World");

        for (int i=2;i<=19;i++){
            for (int j=1;j<=19;j++)
                multiple.accept(i,j);
            System.out.println();
        }

    }
}
