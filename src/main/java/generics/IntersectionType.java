package generics;

import java.util.function.Consumer;
import java.util.function.Function;

public class IntersectionType{

    interface Hello{
        default void hello(){
            System.out.println("Hello");
        }
    }

    interface Hi{
        default void hi(){
            System.out.println("Hi");
        }
    }

    interface Prinnt{
        default void print(String val){
            System.out.println(val);
        }
    }

    public static void main(String[] args) {
        hello((Function & Hello & Hi & Prinnt) s->s);
        System.out.println("=====");
        run((Function & Hello & Hi) s->s,o->{
            o.hello();
            o.hi();
        });
    }

    private static <T extends Function> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    // marker interface
    private static <T extends Function & Hello & Hi & Prinnt> void hello(T t) {
        t.hello();
        t.hi();
        t.print("value");
    }


}
