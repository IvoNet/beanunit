package builder;

/**
 * @author Ivo Woltring
 */
public final class BuilderHasOtherNameBean {

    private final String foo;

    /**
     * The Builder for {@link builder.BuilderHasOtherNameBean}.
     */
    public static class Creator {
        private String foo;

        /**
         * Constructor of the {@link builder.BuilderHasOtherNameBean} class.
         */
        public Creator() {
            //code here
        }

        /**
         * @return this the {@link builder.BuilderHasOtherNameBean.Creator}
         */
        public final Creator setFoo(final String foo) {
            this.foo = foo;
            return this;
        }

        /**
         * Builds the {@link builder.BuilderHasOtherNameBean} class.
         * <p/>
         * Should be called when finished building and the final product may be created. After calling this method the
         * resulting object will be immutable.
         *
         * @return {@link builder.BuilderHasOtherNameBean} instance.
         */
        public final BuilderHasOtherNameBean build() {
            return new BuilderHasOtherNameBean(this);
        }
    }

    /**
     * Private constructor for the Builder.
     *
     * @param creator the {@link builder.BuilderHasOtherNameBean.Creator} creating this object.
     */
    private BuilderHasOtherNameBean(final Creator creator) {
        this.foo = creator.foo;
    }

    public String getFoo() {
        return foo;
    }
}
