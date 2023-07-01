package generics;

import java.util.function.Consumer;
import java.util.function.Function;

public class IntersectionType{

    interface Hello extends Function{
        default void hello(){
            System.out.println("Hello");
        }
    }

    interface Hi extends Function{
        default void hi(){
            System.out.println("Hi");
        }
    }

    interface Print {
        default void print(String val){
            System.out.println(val);
        }
    }

    public static void main(String[] args) {
        //hello((Function & Hello & Hi & Print) s->s);
        run((Function & Hello & Hi & Print) s->s,o->{
            o.hello();
            o.hi();
            o.print("lambda");
        });
    }

    private static <T extends Function> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }

    // marker interface
    private static <T extends Function & Hello & Hi & Print> void hello(T t) {
        t.hello();
        t.hi();
        t.print("value");
    }


}
