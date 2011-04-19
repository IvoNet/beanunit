/*
 * Copyright 2011 Ivo Woltring
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package builder;

/**
 * @author Ivo Woltring
 */
public final class NotImmutableBuilderBean {
    private final String foo;
    private String bar;

    /**
     * The Builder for {@link NotImmutableBuilderBean}.
     */
    public static class Builder {
        private String foo;
        private String bar;

        /**
         * Constructor of the {@link builder.NotImmutableBuilderBean} class.
         */
        public Builder() {
            //code here
        }

        /**
         * @return this the {@link builder.NotImmutableBuilderBean.Builder}
         */
        public final Builder setFoo(final String foo) {
            this.foo = foo;
            return this;
        }

        /**
         * @return this the {@link builder.NotImmutableBuilderBean.Builder}
         */
        public final Builder setBar(final String bar) {
            this.bar = bar;
            return this;
        }

        /**
         * Builds the {@link builder.NotImmutableBuilderBean} class.
         * <p/>
         * Should be called when finished building and the final product may be created. After calling this method the
         * resulting object will be immutable.
         *
         * @return {@link builder.NotImmutableBuilderBean} instance.
         */
        public final NotImmutableBuilderBean build() {
            return new NotImmutableBuilderBean(this);
        }
    }

    /**
     * Private constructor for the builder.
     *
     * @param builder the {@link builder.NotImmutableBuilderBean.Builder} creating this object.
     */
    private NotImmutableBuilderBean(final Builder builder) {
        this.foo = builder.foo;
        this.bar = builder.bar;
    }

    /**
     * Makes it not immutable!!!
     *
     * @param bar the object to set
     */
    public void setBar(final String bar) {
        this.bar = bar;
    }

    //Object getters here

}
