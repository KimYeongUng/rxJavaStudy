package functional;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;

@Slf4j
public class FunctionEx {

    // function var
    static final Consumer<String> print = System.out::println;

    public static void main(String[] args) {
        List<String> data = Arrays.asList("e","c","b","d","a");

        // functional interface
        Collections.sort(data, Comparator.naturalOrder());

        for (String s:data)
            print.accept(s);
    }
}
