package generics;

import java.io.Serializable;
import java.util.Arrays;
import java.util.function.Function;
import java.util.List;
import java.util.stream.Collectors;

public class IntersectionType {
    public static void main(String[] args) {
        hello((Function & Serializable) s->s);
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        String s = list.stream().map(String::valueOf).collect(Collectors.joining(":"));
        System.out.println(s);
    }

    // marker interface

    private static void hello(Function o) {
    }
}
