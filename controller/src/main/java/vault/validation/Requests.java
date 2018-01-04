package vault.validation;

import vault.exception.NullBodyException;

public final class Requests {

    private Requests() {
        throw new AssertionError("The class should not be instantiated");
    }

    public static void requireNonNullBody(Object object) {
        if (object == null) {
            throw new NullBodyException();
        }
    }

}
