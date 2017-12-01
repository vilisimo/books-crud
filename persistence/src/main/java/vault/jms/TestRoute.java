package vault.jms;

import org.apache.camel.builder.RouteBuilder;

public class TestRoute extends RouteBuilder {

    private static final String queue = "activemq:foo.bar";

    @Override
    public void configure() throws Exception {
        from(queue)
                .bean(TestBean.class, "report");
    }
}
