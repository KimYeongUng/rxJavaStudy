package functional;

public class CalculateEx {
    public static void main(String[] args) {
        Calculator cal = new CalculatorService(new Additional());
        System.out.println(cal.calculate(10,5));
        cal = new CalculatorService(new Substraction());
        System.out.println(cal.calculate(10,5));
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
