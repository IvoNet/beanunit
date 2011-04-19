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

import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * @author Ivo Woltring
 */
public class Person {
    private String name;
    private Date birthDate;
    private List<Address> address;

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(final Date birthDate) {
        this.birthDate = birthDate;
    }

    @SuppressWarnings({"unchecked"})
    public List<Address> getAddress() {
        return address == null ? Collections.EMPTY_LIST : address;
    }

    public void setAddress(final List<Address> address) {
        this.address = address;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }

        final Person person = (Person) o;

        if (address != null ? !address.equals(person.address) : person.address != null) { return false; }
        if (birthDate != null ? !birthDate.equals(person.birthDate) : person.birthDate != null) { return false; }
        if (name != null ? !name.equals(person.name) : person.name != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = name != null ? name.hashCode() : 0;
        result = 31 * result + (birthDate != null ? birthDate.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        return result;
    }
}
