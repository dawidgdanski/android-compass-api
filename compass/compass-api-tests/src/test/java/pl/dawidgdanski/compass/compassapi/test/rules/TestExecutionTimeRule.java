package pl.dawidgdanski.compass.compassapi.test.rules;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

import java.util.concurrent.TimeUnit;

public class TestExecutionTimeRule implements TestRule {

    @Override
    public Statement apply(Statement base, Description description) {
        return new ExecutionTimeStatement(base, description);
    }

    private static class ExecutionTimeStatement extends Statement {

        private final Statement actual;

        private final Description description;

        private ExecutionTimeStatement(Statement actual, Description description) {
            this.actual = actual;
            this.description = description;
        }

        @Override
        public void evaluate() throws Throwable {
            final long startNanoTime = System.nanoTime();
            try {
                actual.evaluate();
            } finally {

                final long executionTime = System.nanoTime() - startNanoTime;

                final long millis = TimeUnit.NANOSECONDS.toMillis(executionTime);

                final long seconds = millis / 1000;

                final long milliSeconds = seconds == 0 ? millis : millis % (seconds * 1000);

                System.out.println(String.format("%s executed in %s s %s ms", description.getMethodName(), seconds, milliSeconds));
            }
        }
    }
}
