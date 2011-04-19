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
