package immutable;

import java.math.BigDecimal;

/**
 * Represents a Dealer Registration Dto (Dutch: keteken)
 */
public final class DealerRegistrationDto {

    private final String registration;
    private final BigDecimal listValue;

    /**
     * @param registration the dealerRegistration
     * @param listValue    the total list value of a Dealer Registration
     */
    public DealerRegistrationDto(final String registration, final BigDecimal listValue) {
        this.registration = registration;
        this.listValue = listValue;
    }

    /**
     * @return the registration
     */
    public String getRegistration() {
        return this.registration;
    }

    /**
     * @return the listValue
     */
    public BigDecimal getListValue() {
        return this.listValue;
    }

}
