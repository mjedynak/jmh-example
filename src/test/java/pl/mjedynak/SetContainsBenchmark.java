package pl.mjedynak;

import com.gs.collections.api.block.HashingStrategy;
import com.gs.collections.impl.block.factory.HashingStrategies;
import com.gs.collections.impl.set.mutable.UnifiedSet;
import com.gs.collections.impl.set.strategy.mutable.UnifiedSetWithHashingStrategy;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.results.format.ResultFormatType;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.lang3.RandomStringUtils.randomNumeric;

public class SetContainsBenchmark {

    private static final String PREFIX = "abc_XYZXYZ";
    private static final int LENGTH = 9;

        static Set<String> data = new HashSet<>();
//    static Set<String> data = new UnifiedSet<>();
//    static Set<String> data = new UnifiedSetWithHashingStrategy(HashingStrategies.defaultStrategy());

    static {
        for (int i = 0; i < 2_000_000; i++) {
            data.add(PREFIX + randomNumeric(LENGTH));
        }
        System.out.println("Finished data population");
    }

    @Benchmark
    @BenchmarkMode(Mode.AverageTime)
    @OutputTimeUnit(TimeUnit.MICROSECONDS)
    public void measureAvgTime() throws InterruptedException {
        data.contains(PREFIX + randomNumeric(LENGTH));
    }

    public static void main(String[] args) throws RunnerException {
        Options opts = new OptionsBuilder()
                .include(SetContainsBenchmark.class.getSimpleName())
                .warmupIterations(5)
                .measurementIterations(50)
                .detectJvmArgs()
                .resultFormat(ResultFormatType.TEXT)
                .jvmArgs("-server")
                .forks(1)
                .build();

        new Runner(opts).run();
    }
}
