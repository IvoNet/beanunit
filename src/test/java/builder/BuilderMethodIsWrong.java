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
public final class BuilderMethodIsWrong {
    private final String foo;
    private final boolean bar;

    public static class Builder {
        private boolean bar;
        private String foo;

        public final Builder bar(boolean bar) {
            this.bar = bar;
            return this;
        }

        /*
         * This method is not valid because the return type is not Builder.
         */
        public final void foo(final String foo) {
            this.foo = foo;
        }

        public final BuilderMethodIsWrong build() {
            return new BuilderMethodIsWrong(this);
        }
    }

    private BuilderMethodIsWrong(final Builder builder) {
        //code here
        this.foo = builder.foo;
        this.bar = builder.bar;
    }

    public String getFoo() {
        return foo;
    }
}
