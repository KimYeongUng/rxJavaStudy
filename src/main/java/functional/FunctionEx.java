package functional;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Slf4j
public class FunctionEx {
    public static void main(String[] args) {
        List<String> data = Arrays.asList("e","c","b","d","a");

        // functional interface
        Collections.sort(data, (s1,s2)->s1.compareTo(s2));

        for (String s:data)
            log.info(s);
    }
}
