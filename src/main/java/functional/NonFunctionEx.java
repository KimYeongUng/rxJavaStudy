package functional;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

@Slf4j
public class NonFunctionEx {
    public static void main(String[] args) {
        List<String> data = Arrays.asList("e","c","b","d","a");
        Collections.sort(data, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });

        for (String s: data)
            log.info(s);
    }
}
