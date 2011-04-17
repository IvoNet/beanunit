package immutable;

import java.math.BigDecimal;

/**
 * Holds information about the inventory
 */
public final class InventoryDto {
    private final BigDecimal insuredAmount;
    private final BigDecimal newWorth;

    /**
     * @param insuredAmount {@link java.math.BigDecimal} the current insured amount
     * @param newWorth      {@link java.math.BigDecimal} the current amount of new worth
     */
    public InventoryDto(final BigDecimal insuredAmount, final BigDecimal newWorth) {
        this.insuredAmount = insuredAmount;
        this.newWorth = newWorth;
    }

    public InventoryDto(final BigDecimal newWorth) {
        this(new BigDecimal(42), newWorth);
    }

    /**
     * @return {@link java.math.BigDecimal} the current insured amount
     */
    public BigDecimal getInsuredAmount() {
        return this.insuredAmount;
    }

    /**
     * @return {@link java.math.BigDecimal} the current amount of new worth
     */
    public BigDecimal getNewWorth() {
        return this.newWorth;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InventoryDto ");
        sb.append("insuredAmount [");
        sb.append(this.insuredAmount);
        sb.append("] newWorth [");
        sb.append(this.newWorth);
        sb.append("].");
        return sb.toString();
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public boolean equals(final Object obj) {
        if (obj == null) {
            return false;
        }

        if (!(obj instanceof InventoryDto)) {
            return false;
        }

        final InventoryDto other = (InventoryDto) obj;

        return isEqual(this.insuredAmount, other.insuredAmount) && isEqual(this.newWorth, other.newWorth);
    }

    private boolean isEqual(final Object thisParam, final Object otherParam) {
        return (thisParam == null && otherParam == null) || (thisParam != null && thisParam.equals(otherParam));
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public int hashCode() {
        final int prime = 19;
        int result = prime;
        if (this.insuredAmount != null) {
            result *= prime + this.insuredAmount.hashCode();
        }
        if (this.newWorth != null) {
            result *= prime + this.newWorth.hashCode();
        }
        return result;
    }

}
