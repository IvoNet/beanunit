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
 * Immutalable class which holds information about an address
 */
public final class AddressDto {

    private final String city;
    private final String street;
    private final String streetNumberAddition;
    private final String streetNumber;
    private final String zipCode;
    private final String id;
    private final String streetAddition;

    public String getId() {
        return this.id;
    }

    private AddressDto(final Builder b) {
        this.city = b.city;
        this.street = b.street;
        this.streetNumberAddition = b.streetNumberAddition;
        this.streetNumber = b.streetNumber;
        this.zipCode = b.zipCode;
        this.id = b.id;
        this.streetAddition = b.streetAddition;
    }

    public String getCity() {
        return this.city;
    }

    public String getStreet() {
        return this.street;
    }

    public String getStreetAddition() {
        return this.streetAddition;
    }

    public String getStreetNumber() {
        return this.streetNumber;
    }

    public String getZipCode() {
        return this.zipCode;
    }

    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof AddressDto)) {
            return false;
        }

        final AddressDto other = (AddressDto) obj;

        if (isEqual(this.city, other.city) && isEqual(this.street, other.street)
            && isEqual(this.streetNumberAddition, other.streetNumberAddition)
            && isEqual(this.streetNumber, other.streetNumber)
            && isEqual(this.streetNumberAddition, other.streetNumberAddition) && isEqual(this.zipCode, other.zipCode)
            && isEqual(this.id, other.id)) {
            return true;
        }

        return true;
    }

    @Override
    public int hashCode() {
        final int prime = 17;
        int result = prime;
        if (this.city != null) {
            result *= prime + this.city.hashCode();
        }
        if (this.id != null) {
            result *= prime + this.id.hashCode();
        }
        if (this.street != null) {
            result *= prime + this.street.hashCode();
        }
        if (this.streetNumber != null) {
            result *= prime + this.streetNumber.hashCode();
        }
        if (this.streetNumberAddition != null) {
            result *= prime + this.streetNumberAddition.hashCode();
        }
        if (this.zipCode != null) {
            result *= prime + this.zipCode.hashCode();
        }
        return result;
    }

    private boolean isEqual(final String thisParam, final String otherParam) {
        return (thisParam == null && otherParam == null) || (thisParam != null && thisParam.equals(otherParam));
    }

    /**
     * Creator for the AddressDto
     */
    public static final class Builder {
        private String streetAddition;
        private String city;
        private String street;
        private String streetNumberAddition;
        private String streetNumber;
        private String zipCode;
        private String id;

        public Builder setCity(final String city) {
            this.city = city;
            return this;
        }

        public Builder setStreet(final String street) {
            this.street = street;
            return this;
        }

        public Builder setStreetAddition(final String streetAddition) {
            this.streetAddition = streetAddition;
            return this;
        }

        public Builder setStreetNumber(final String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        public Builder setZipCode(final String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        public Builder setId(final String id) {
            this.id = id;
            return this;
        }

        public Builder setStreetNumberAddition(final String streetNumberAddition) {
            this.streetNumberAddition = streetNumberAddition;
            return this;
        }

        public AddressDto build() {
            return new AddressDto(this);
        }
    }

    /**
     * @return the street number addition
     */
    public String getStreetNumberAddition() {
        return this.streetNumberAddition;
    }
}
