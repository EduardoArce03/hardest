package swagger;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import io.opentracing.util.GlobalTracer;

public class Config {
	public static void initTracer() {
        Configuration config = new Configuration("servicio")
            .withSampler(Configuration.SamplerConfiguration.fromEnv().withType("const").withParam(1))
            .withReporter(Configuration.ReporterConfiguration.fromEnv().withLogSpans(true));
        
        Tracer tracer = config.getTracer();
        GlobalTracer.registerIfAbsent(tracer);
    }
}
