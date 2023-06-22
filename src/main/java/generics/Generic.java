package generics;

import java.util.Arrays;

public class Generic {
    // Type extends Comparable : Comparable을 구현한 클래스가 Type으로 올 수 있다능
    public <T extends Comparable<T>> Long compareGreaterThan(T[] t,T value){
        return Arrays.stream(t).filter(s->s.compareTo(value)>0).count();
    }
    public static void main(String[] args) {
        Generic gen = new Generic();
        Integer[] ints = new Integer[]{1,2,3,4,5};
        System.out.println(gen.compareGreaterThan(ints,2));
        String[] str = {"a","b","c","d","e","f"};
        System.out.println(gen.compareGreaterThan(str,"d"));
    }
}
