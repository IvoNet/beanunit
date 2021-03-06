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
 * @author Ivo Woltring
 */
public class SimpleBean {

    String hello;
    Integer times;
    boolean trueOrFalse;

    public Integer getTimes() {
        return times;
    }

    public void setTimes(final Integer times) {
        this.times = times;
    }

    public boolean isTrueOrFalse() {
        return trueOrFalse;
    }

    public void setTrueOrFalse(final boolean trueOrFalse) {
        this.trueOrFalse = trueOrFalse;
    }

    public String getHello() {
        return hello;
    }

    public void setHello(final String hello) {
        this.hello = hello;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final SimpleBean that = (SimpleBean) o;

        if (trueOrFalse != that.trueOrFalse) { return false; }
        if (hello != null ? !hello.equals(that.hello) : that.hello != null) { return false; }
        if (times != null ? !times.equals(that.times) : that.times != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = hello != null ? hello.hashCode() : 0;
        result = 31 * result + (times != null ? times.hashCode() : 0);
        result = 31 * result + (trueOrFalse ? 1 : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("SimpleBean");
        sb.append("{hello='").append(hello).append('\'');
        sb.append(", times=").append(times);
        sb.append(", trueOrFalse=").append(trueOrFalse);
        sb.append('}');
        return sb.toString();
    }
}
