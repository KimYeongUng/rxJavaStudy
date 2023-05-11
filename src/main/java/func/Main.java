package func;
import javax.servlet.http.HttpServlet;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
public class Main {
    public static void main(String[] args) throws IOException {
        String greeting = FunctionEx.greeting("hero");
        System.out.println(greeting);

        List<Integer> list = Arrays.asList(1,2,3,4,5);
        FunctionEx.iterate(Collections.singletonList(list));
        HttpServlet servlet;
    }
}
