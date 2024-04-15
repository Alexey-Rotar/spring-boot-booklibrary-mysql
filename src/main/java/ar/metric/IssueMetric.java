package ar.metric;

import io.micrometer.core.instrument.Counter;
import io.micrometer.core.instrument.MeterRegistry;
import lombok.Data;
import org.springframework.stereotype.Component;

@Component
@Data
public class IssueMetric {
    public final Counter numberOfIssuedBooks;
    public final Counter numberOfFailures;

    public IssueMetric(MeterRegistry meterRegistry) {
        numberOfIssuedBooks = meterRegistry.counter("number_of_issued_books");
        numberOfFailures = meterRegistry.counter("number_of_failures");
    }
}
