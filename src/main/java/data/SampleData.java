package data;
import reactor.util.function.Tuple2;
import reactor.util.function.Tuples;

import java.util.Arrays;
import java.util.List;
public class SampleData {
    public static final List<String> stockNames = Arrays.asList("AAPL","GOOGL","LCID","NFLX","DIS");
    public static final List<Double> applPrice = Arrays.asList(174.41,174.40,174.61,174.81,178.41);

    public static final List<Tuple2<String,Double>> stocks = Arrays.asList(
            Tuples.of("AAPL",174.41),
            Tuples.of("GOOGL",104.72),
            Tuples.of("LCID",7.74),
            Tuples.of("NFLX",320.86),
            Tuples.of("DIS",100.06)
    );
}
