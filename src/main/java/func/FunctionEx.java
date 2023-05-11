package func;

import java.util.List;
import java.util.function.Function;

public class FunctionEx {
    // pure function
    public static String greeting(String name){
        return "Hello "+name;
    }

    // foreach , no iterate
    public static void iterate(List<Object> list){
        list.forEach(System.out::println);
    }

    Function<String, Function<String,String>> func = (text)->{
      return (name)->{
          return text+" "+name;
      };
    };
}
