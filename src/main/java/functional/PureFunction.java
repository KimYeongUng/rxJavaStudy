package functional;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;

public class PureFunction {
    final static String value = "name";

    // not pure
    public static void func1(){
        System.out.println("Hello "+value);
    }

    // pure function
    public static void pureFunc(String value){
        System.out.println("Hello "+value);
    }

    public static void main(String[] args) {
        func1();
        pureFunc("hero");

        List<Integer> list = Arrays.asList(1,2,3,4,5);

        // iterate
        for (Integer i: list){
            System.out.print(i);
        }

        System.out.println();

        // no iterate
        list.forEach(System.out::print);
        System.out.println();

        // high order function
        Function<String,Function<String,String>> func = value-> (name) -> value+" "+name;

        Function<String,String> hi = func.apply("Hi");
        Function<String,String> hello = func.apply("Hello");

        System.out.println(hi.apply("hero"));
        System.out.println(hello.apply("hero"));

    }
}
