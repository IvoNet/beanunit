package immutable;

import java.math.BigDecimal;

/**
 * Holds information about a Stock
 */
public final class StockDto {

    private final BigDecimal insuredAmount;

    /**
     * @return the insuredAmount
     */
    public BigDecimal getInsuredAmount() {
        return this.insuredAmount;
    }

    /**
     * @param insuredAmount {@link java.math.BigDecimal}
     */
    public StockDto(final BigDecimal insuredAmount) {
        this.insuredAmount = insuredAmount;
    }

}
