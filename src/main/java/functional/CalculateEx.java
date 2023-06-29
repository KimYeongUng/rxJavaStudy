package functional;

import io.reactivex.rxjava3.functions.BiFunction;
import io.reactivex.rxjava3.functions.Function;

public class CalculateEx {
    static BiFunction<Integer,Integer,Integer> add = Integer::sum;
    static BiFunction<Integer,Integer,Integer> substract = (value1,value2) -> value1-value2;
    static BiFunction<Integer,Integer,Integer> multiply = (val1,val2) -> val1*val2;
    static BiFunction<Integer,Integer,Integer> divide = (val1,val2) -> val1/val2;

    public static void main(String[] args) throws Throwable {
        // ordinary
        Calculator cal = new CalculatorService(new Additional());
        System.out.println(cal.calculate(10,5));

        // functional
        System.out.println(add.apply(1,2));
        System.out.println(substract.apply(10,3));
        System.out.println(multiply.apply(3,1));
        System.out.println(divide.apply(3,3));

    }

}

interface Calculator{
    int calculate(int num1,int num2);
}

class Additional implements Calculator{

    @Override
    public int calculate( int num1, int num2) {
        return num1+num2;
    }
}

class Substraction implements Calculator{

    @Override
    public int calculate(int num1, int num2) {
        return num1-num2;
    }
}

class Mulitple implements Calculator{

    @Override
    public int calculate(int num1, int num2) {
        return num1 * num2;
    }
}

class Divide implements Calculator{

    @Override
    public int calculate(int num1, int num2) {
        return num1/num2;
    }
}

// functional
class FunctionalCaculatorService{
    public int calculate(Calculator calculator,int num1,int num2){
        if(num1>=10 && num2<num1)
            return calculator.calculate(num1,num2);
        else
            throw new IllegalArgumentException();
    }
}

class CalculatorService implements Calculator{

    private final Calculator calculator;

    CalculatorService(Calculator calculator){
        this.calculator = calculator;
    }

    public int calculate(int num1,int num2){
        if(num1>=10 && num2 <num1)
            return this.calculator.calculate(num1,num2);
        else
            throw new IllegalArgumentException("invalid arguments");
    }
}
