package functional;

public class CalculateEx {
    public static void main(String[] args) {
        Calculator cal = new CalculatorService(new Additional());
        System.out.println(cal.calculate(1,1));
        cal = new CalculatorService(new Substraction());
        System.out.println(cal.calculate(1,1));
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

class CalculatorService implements Calculator{

    private Calculator calculator;

    CalculatorService(Calculator calculator){
        this.calculator = calculator;
    }

    public int calculate(int num1,int num2){
        return this.calculator.calculate(num1,num2);
    }
}
