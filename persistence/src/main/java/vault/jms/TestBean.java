package vault.jms;

import org.apache.camel.Consume;

public class TestBean {

    @Consume(uri = "activemq:foo.bar")
    public String report(String stuff) {
        System.err.println("Consuming: " + stuff);
        return "tested";
    }

    public String placeholder() {
        System.err.println("Placeholder");
        return "placeholder";
    }
}
