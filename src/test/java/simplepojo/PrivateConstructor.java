package simplepojo;

/**
 * Simple class with a private constructor to make instantiation very difficult :-)
 * The private constructor also needs a parameter to make it even more difficult.
 *
 * @author Ivo Woltring
 */
public class PrivateConstructor {

    private String bar;

    /*
    private constructor
     */
    private PrivateConstructor(final String foo) {
        this.bar = foo;
    }

    public String getBar() {
        return bar;
    }

    public void setBar(final String bar) {
        this.bar = bar;
    }
}
