package functional.functional_interface;

public class FunctionEx {
    public static void main(String[] args) {
        println(1,2,3,(t1,t2,t3)->String.valueOf(t1+t2+t3));
    }

    private static  <T1,T2,T3> void println(T1 t1,T2 t2,T3 t3,TripleFunction<T1,T2,T3,String> function){
        System.out.println(function.apply(t1,t2,t3));
    }
}
