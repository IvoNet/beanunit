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
public final class EqualsMethodButNoHasCode {

    private final String foo;

    /**
     * The Builder for {@link builder.EqualsMethodButNoHasCode}.
     */
    public static class Builder {
        private String foo;

        public final Builder setFoo(final String foo) {
            this.foo = foo;
            return this;
        }

        public final EqualsMethodButNoHasCode build() {
            return new EqualsMethodButNoHasCode(this);
        }
    }

    private EqualsMethodButNoHasCode(final Builder builder) {
        this.foo = builder.foo;
    }

    public String getFoo() {
        return foo;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof EqualsMethodButNoHasCode)) { return false; }

        final EqualsMethodButNoHasCode that = (EqualsMethodButNoHasCode) o;

        if (foo != null ? !foo.equals(that.foo) : that.foo != null) { return false; }

        return true;
    }

}
