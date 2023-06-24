package generics;

import java.util.Arrays;
import java.util.List;

// wildcard
public class Generics3 {
    static Boolean isEmpty(List<?> list){
        return list.size() == 0;
    }

    static long frequency(List<?> list,Object elem){
        return list.stream().filter(s->s.equals(elem)).count();
    }

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(2,3,3,2,3,2,2,1,5,6,7);
        System.out.println(isEmpty(List.of()));
        System.out.println(frequency(list,2));
    }
}
