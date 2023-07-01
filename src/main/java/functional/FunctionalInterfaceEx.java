package functional;

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
    }

    // return own value
    public static Integer toInt(String value){
        return Integer.parseInt(value);
    }
}
