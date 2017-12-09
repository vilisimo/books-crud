package vault.jms;

import org.apache.camel.builder.RouteBuilder;

public class TestSimpleRoute extends RouteBuilder {

    private static final String queue = "activemq:foo.bar.simple";

    @Override
    public void configure() {
        from(queue)
                .bean(TestBean.class);
    }
}
