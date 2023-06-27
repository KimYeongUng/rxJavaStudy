package generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CaptureEx {
    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5);
        reverse(list);
        System.out.println(list);
    }

    static void reverse(List<?> list) {
        reverseHelper(list);
    }

    private static <T> void reverseHelper(List<T> list) {
        List<T> temp = new ArrayList<>(list);
        for (int i=0;i<list.size();i++)
            list.set(i,temp.get(list.size()-i-1));
    }
}
