package immutable;

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

    /**
     * @return the id
     */
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

    /**
     * @return the name of the city
     */
    public String getCity() {
        return this.city;
    }

    /**
     * @return the street
     */
    public String getStreet() {
        return this.street;
    }

    /**
     * @return the street addition
     */
    public String getStreetAddition() {
        return this.streetAddition;
    }

    /**
     * @return the street number
     */
    public String getStreetNumber() {
        return this.streetNumber;
    }

    /**
     * @return the zipcode
     */
    public String getZipCode() {
        return this.zipCode;
    }

    /**
     * {@inheritDoc}
     */
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

    /**
     * {@inheritDoc}
     */
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
     * Builder for the AddressDto
     */
    public static final class Builder {
        private String streetAddition;
        private String city;
        private String street;
        private String streetNumberAddition;
        private String streetNumber;
        private String zipCode;
        private String id;

        /**
         * @param city the city to set
         */
        public Builder setCity(final String city) {
            this.city = city;
            return this;
        }

        /**
         * @param street the street to set
         */
        public Builder setStreet(final String street) {
            this.street = street;
            return this;
        }

        /**
         * @param streetAddition the streetAddition to set
         */
        public Builder setStreetAddition(final String streetAddition) {
            this.streetAddition = streetAddition;
            return this;
        }

        /**
         * @param streetNumber the streetNumber to set
         */
        public Builder setStreetNumber(final String streetNumber) {
            this.streetNumber = streetNumber;
            return this;
        }

        /**
         * @param zipCode the zipCode to set
         */
        public Builder setZipCode(final String zipCode) {
            this.zipCode = zipCode;
            return this;
        }

        /**
         * @return {@link AddressDto}
         */
        public AddressDto build() {
            return new AddressDto(this);
        }

        /**
         * @param id the id
         */
        public void setId(final String id) {
            this.id = id;
        }

        /**
         * @param streetNumberAddition the addition of the number
         */
        public void setStreetNumberAddition(final String streetNumberAddition) {
            this.streetNumberAddition = streetNumberAddition;
        }
    }

    /**
     * @return the street number addition
     */
    public String getStreetNumberAddition() {
        return this.streetNumberAddition;
    }
}
