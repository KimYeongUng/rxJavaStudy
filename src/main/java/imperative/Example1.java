package imperative;
import java.util.Arrays;
import java.util.List;
public class Example1 {
    public static void main(String[] args) {
        List<Integer> nums = Arrays.asList(1,3,21,10,11);
        int sum = 0;
        for (int num:nums){
            if(num >6 && (num%2!=0))
                sum+=num;
        }
        System.out.println(sum);
    }
}
