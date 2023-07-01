package generics;


import java.util.function.Consumer;

public class InterSectionImpl {

    interface Pair<T>{
        T getFirst();
        T getSecond();

        void setFirst(T first);
        void setSecond(T second);
    }

    static class Name implements Pair<String>{

        public Name(String firstName, String lastName) {
            this.firstName = firstName;
            this.lastName = lastName;
        }

        String firstName;
        String lastName;


        @Override
        public String getFirst() {
            return this.firstName;
        }

        @Override
        public String getSecond() {
            return this.lastName;
        }

        @Override
        public void setFirst(String first) {
            this.firstName = first;
        }

        @Override
        public void setSecond(String second) {
            this.lastName = second;
        }
    }

    interface ForwardingPair<T> extends IntersectionType2.DelegateTo<Pair<T>>,Pair<T>{
        default T getFirst(){return delegate().getFirst();}
        default T getSecond(){return delegate().getSecond();}
        default void setFirst(T first){delegate().setFirst(first);}
        default void setSecond(T second){delegate().setSecond(second);}
    }

    private static <T extends IntersectionType2.DelegateTo<S>,S> void run(T t, Consumer<T> consumer){
        consumer.accept(t);
    }

    public static void main(String[] args) {
        Pair<String> name = new Name("Kim","YeongUng");

        run((ForwardingPair<String>)()->name,o->{
            System.out.println(o.getFirst());
            System.out.println(o.getSecond());
        });
    }
}
