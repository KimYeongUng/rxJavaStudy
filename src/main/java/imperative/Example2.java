package imperative;
import java.util.Arrays;
import java.util.List;
public class Example2 {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,10,11,34,13);
        int sum = nums.stream().filter(num->num>6 && num%2!=0).mapToInt(n->n).sum();
        System.out.println(sum);
    }
}
