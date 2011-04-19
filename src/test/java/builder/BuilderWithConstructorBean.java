package builder;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Ivo Woltring
 */
public final class BuilderWithConstructorBean {
    private final String foo;
    private final List<String> list;

    /**
     * The Builder for {@link BuilderWithConstructorBean}.
     */
    public static class Builder {
        private final List<String> list;
        private final String foo;

        public Builder(final String foo) {
            this.foo = foo;
            this.list = new ArrayList<String>();
        }

        public final Builder add(final String item) {
            this.list.add(item);
            return this;
        }

        public final BuilderWithConstructorBean build() {
            return new BuilderWithConstructorBean(this);
        }
    }

    /**
     * Private constructor for the builder.
     *
     * @param builder the {@link builder.BuilderWithConstructorBean.Builder} creating this object.
     */
    private BuilderWithConstructorBean(final Builder builder) {
        //code here
        this.foo = builder.foo;
        this.list = builder.list;
    }

    public String getFoo() {
        System.out.println("getFoo called");
        return foo;
    }

    public List<String> getList() {
        System.out.println("getList called");
        return list;
    }
}
