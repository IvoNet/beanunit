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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author Ivo Woltring
 */
public final class BuilderWithMultipleConstructorBean {
    private final String foo;
    private final List<String> list;

    /**
     * The Builder for {@link builder.BuilderWithMultipleConstructorBean}.
     */
    public static class Builder {
        private final List<String> list;
        private final String foo;

        public Builder() {
            this.list = new ArrayList<String>();
            this.foo = "foo";
        }

        public Builder(final String foo) {
            this.foo = foo;
            this.list = new ArrayList<String>();
        }

        public final Builder add(final String item) {
            this.list.add(item);
            return this;
        }

        public final BuilderWithMultipleConstructorBean build() {
            return new BuilderWithMultipleConstructorBean(this);
        }
    }

    /**
     * Private constructor for the builder.
     *
     * @param builder the {@link builder.BuilderWithMultipleConstructorBean.Builder} creating this object.
     */
    private BuilderWithMultipleConstructorBean(final Builder builder) {
        //code here
        this.foo = builder.foo;
        this.list = builder.list;
    }

    public String getFoo() {
        return foo;
    }

    public List<String> getList() {
        return Collections.unmodifiableList(list);
    }
}
