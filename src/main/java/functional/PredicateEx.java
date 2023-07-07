package functional;

import java.util.function.Predicate;

public class PredicateEx {
    public static void main(String[] args) {
        Predicate<Integer> predicate = n->n>0;
        System.out.println(predicate.test(-1));
        System.out.println(predicate.test(1));
    }
}
