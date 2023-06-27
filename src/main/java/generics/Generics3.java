package generics;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

// wildcard
public class Generics3 {
    static Boolean isEmpty(List<?> list){
        return list.size() == 0;
    }

    static long frequency(List<?> list,Object elem){
        return list.stream().filter(s->s.equals(elem)).count();
    }

    static <T extends Comparable<T>> T max(List<T> list) {
        return list.stream().reduce((a,b)->a.compareTo(b) > 0 ? a : b).get();
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2,3,3,2,3,2,2,1,5,6,7);
        System.out.println(isEmpty(List.of()));
        System.out.println(frequency(list,2));
        System.out.println(max(list));
        System.out.println(Collections.max(list,
                (Comparator<Object>) Comparator.comparing(Object::toString)));
    }
}
