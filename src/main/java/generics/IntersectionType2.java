package generics;

import java.util.function.Consumer;

public class IntersectionType2 {

    interface DelegateTo<T>{
        T delegate();
    }

    interface Hello extends DelegateTo<String>{
        default void hello(){
            System.out.println("Hello "+delegate());
        }
    }

    interface UpperCase extends DelegateTo<String>{
        default void upper(){
            System.out.println(delegate().toUpperCase());
        }
    }

    public static void main(String[] args) {
        run((DelegateTo<String> & Hello & UpperCase)()->"YeongUng Kim",o->{
            o.hello();
            o.upper();
        });
    }

    private static <T extends DelegateTo<S>,S> void run(T t, Consumer<T> consumer) {
        consumer.accept(t);
    }
}
