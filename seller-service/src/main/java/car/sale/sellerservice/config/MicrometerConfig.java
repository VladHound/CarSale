package car.sale.sellerservice.config;

import io.micrometer.tracing.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Configuration
public class MicrometerConfig {

    @Bean
    public Tracer tracer() {
        // Ваша конфигурация Tracer
        return new Tracer() {
            @Override
            public Span nextSpan() {
                return null;
            }

            @Override
            public Span nextSpan(Span parent) {
                return null;
            }

            @Override
            public SpanInScope withSpan(Span span) {
                return null;
            }

            @Override
            public ScopedSpan startScopedSpan(String name) {
                return null;
            }

            @Override
            public Span.Builder spanBuilder() {
                return null;
            }

            @Override
            public TraceContext.Builder traceContextBuilder() {
                return null;
            }

            @Override
            public CurrentTraceContext currentTraceContext() {
                return null;
            }

            @Override
            public SpanCustomizer currentSpanCustomizer() {
                return null;
            }

            @Override
            public Span currentSpan() {
                return null;
            }

            @Override
            public Map<String, String> getAllBaggage() {
                return null;
            }

            @Override
            public Baggage getBaggage(String name) {
                return null;
            }

            @Override
            public Baggage getBaggage(TraceContext traceContext, String name) {
                return null;
            }

            @Override
            public Baggage createBaggage(String name) {
                return null;
            }

            @Override
            public Baggage createBaggage(String name, String value) {
                return null;
            }
        };
    }
}