package duality;

import java.util.Iterator;

// Implements Iterable
// pull
public class Ob1 {
    public static void main(String[] args) {
        Iterable<Integer> iter = () ->
           new Iterator<>() {
              int i = 0;
              final static int MAX = 10;
              @Override
              public boolean hasNext() {
                  return i < MAX;
              }

              @Override
              public Integer next() {
                  return ++i;
              }
          };

        for (Integer i:iter)
            System.out.println(i);
    }
}
