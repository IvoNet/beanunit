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
public class WrongSinpleBean extends SimpleBean {

    String wrong;

    public String getWrong() {
        return wrong + "wrong";
    }

    public void setWrong(final String wrong) {
        this.wrong = wrong;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }

        final WrongSinpleBean that = (WrongSinpleBean) o;

        if (wrong != null ? !wrong.equals(that.getWrong()) : that.wrong != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (wrong != null ? wrong.hashCode() : 0);
        return result;
    }
}
