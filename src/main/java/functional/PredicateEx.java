package functional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.Predicate;
import java.util.List;

public class PredicateEx {
    public static void main(String[] args) {
        Predicate<Integer> predicate = n->n>0;
        System.out.println(predicate.test(-1));
        System.out.println(predicate.test(0));
        System.out.println(predicate.test(1));

        List<Integer> list = Arrays.asList(1,2,3,4,5,-1,-2);
        Predicate<Integer> isPositive = n->n>0;
        List<Integer> arr = filter(list,isPositive);

        System.out.println(arr);
    }

    private static <T> List<T> filter(List<T> list,Predicate<T> filter){
        List<T> ret = new ArrayList<>();

        for (T input: list){
            if(filter.test(input))
                ret.add(input);
        }

        return ret;
    }
}
