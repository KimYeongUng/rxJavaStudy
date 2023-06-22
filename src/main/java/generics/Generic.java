package generics;

import java.util.Arrays;

public class Generic {
    public <T extends Comparable<T>> Long compareGreaterThan(T[] t,T value){
        return Arrays.stream(t).filter(s->s.compareTo(value)>0).count();
    }
    public static void main(String[] args) {
        Integer[] ints = new Integer[]{1,2,3,4,5};
        System.out.println(new Generic().compareGreaterThan(ints,2));
        String[] str = {"a","b","c","d","e","f"};
        System.out.println(new Generic().compareGreaterThan(str,"d"));
    }
}
