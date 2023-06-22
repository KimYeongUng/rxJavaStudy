package generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Generic2 {
    static <T> void method(T t,List<T> list){
        System.out.println(t.toString());
        list.forEach(System.out::println);
    }

    // wildcard
    static void method2(List<?> list){
        System.out.println("WildCard");
        list.forEach(s-> System.out.println(s.hashCode()));
    }
    public static void main(String[] args) {
        // Type Inference
        Generic2.method(1, Arrays.asList(1,2,3,5));

        Generic2.method2(Arrays.asList("HI","HELLO","BYE"));
    }
}
