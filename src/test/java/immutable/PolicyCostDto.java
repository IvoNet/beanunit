package immutable;

import java.math.BigDecimal;

/**
 * PolicyCostDto
 */
public final class PolicyCostDto {

    private final BigDecimal currentPremium;
    private final BigDecimal newPremium;

    public PolicyCostDto(final BigDecimal currentPremium, final BigDecimal newPremium) {
        this.currentPremium = currentPremium;
        this.newPremium = newPremium;
    }

    /**
     * @return the currentPremium
     */
    public BigDecimal getCurrentPremium() {
        return this.currentPremium;
    }

    /**
     * @return the newPremium
     */
    public BigDecimal getNewPremium() {
        return this.newPremium;
    }

}
