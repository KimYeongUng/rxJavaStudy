package functional;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class CalculateExTest {

    @Test
    public void calculateAdditional(){
        Calculator calculator = new CalculatorService(new Additional());
        int res = calculator.calculate(1,1);
        assertEquals(res,2);
    }

    @Test
    public void calculateSubstraction(){
        Calculator calculator = new CalculatorService(new Substraction());
        int res = calculator.calculate(1,1);
        assertEquals(res,0);
    }

}