package builder;

/**
 * @author Ivo Woltring
 */
public final class SimpleBuilderBean {

    private final String foo;

    /**
     * The Builder for {@link SimpleBuilderBean}.
     */
    public static class Builder {
        private String foo;

        /**
         * Constructor of the {@link builder.SimpleBuilderBean} class.
         */
        public Builder() {
            //code here
        }

        /**
         * @return this the {@link builder.SimpleBuilderBean.Builder}
         */
        public final Builder setFoo(final String foo) {
            this.foo = foo;
            return this;
        }

        /**
         * Builds the {@link builder.SimpleBuilderBean} class.
         * <p/>
         * Should be called when finished building and the final product may be created. After calling this method the
         * resulting object will be immutable.
         *
         * @return {@link builder.SimpleBuilderBean} instance.
         */
        public final SimpleBuilderBean build() {
            return new SimpleBuilderBean(this);
        }
    }

    /**
     * Private constructor for the builder.
     *
     * @param builder the {@link builder.SimpleBuilderBean.Builder} creating this object.
     */
    private SimpleBuilderBean(final Builder builder) {
        this.foo = builder.foo;
    }

    public String getFoo() {
        return foo;
    }
}
