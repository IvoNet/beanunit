package builder;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import immutable.PolicyCostDto;

/**
 * Proposition costs
 */
public final class CostDto {

    private final List<PolicyCostDto> policyCosts;

    private final BigDecimal premiumTax;

    private final BigDecimal currentSubTotal;
    private final BigDecimal currentTax;
    private final BigDecimal currentTotal;

    private final BigDecimal newSubTotal;
    private final BigDecimal newTax;
    private final BigDecimal newTotal;

    /**
     * Builder for {@link CostDto}
     */
    public static final class Builder {

        private List<PolicyCostDto> policyCosts;
        private BigDecimal premiumTax;
        private BigDecimal currentSubTotal;
        private BigDecimal currentTax;
        private BigDecimal currentTotal;
        private BigDecimal newSubTotal;
        private BigDecimal newTax;
        private BigDecimal newTotal;

        /**
         * @param policyCosts {@link List} of {@link PolicyCostDto}s
         */
        public void setPolicyCosts(final List<PolicyCostDto> policyCosts) {
            this.policyCosts = policyCosts;
        }

        /**
         * @param premiumTax {@link BigDecimal}
         */
        public void setPremiumTax(final BigDecimal premiumTax) {
            this.premiumTax = premiumTax;
        }

        /**
         * @param currentSubTotal {@link BigDecimal}
         */
        public void setCurrentSubTotal(final BigDecimal currentSubTotal) {
            this.currentSubTotal = currentSubTotal;
        }

        /**
         * @param currentTax {@link BigDecimal}
         */
        public void setCurrentTax(final BigDecimal currentTax) {
            this.currentTax = currentTax;
        }

        /**
         * @param currentTotal {@link BigDecimal}
         */
        public void setCurrentTotal(final BigDecimal currentTotal) {
            this.currentTotal = currentTotal;
        }

        /**
         * @param newSubTotal {@link BigDecimal}
         */
        public void setNewSubTotal(final BigDecimal newSubTotal) {
            this.newSubTotal = newSubTotal;
        }

        /**
         * @param newTax {@link BigDecimal}
         */
        public void setNewTax(final BigDecimal newTax) {
            this.newTax = newTax;
        }

        /**
         * @param newTotal {@link BigDecimal}
         */
        public void setNewTotal(final BigDecimal newTotal) {
            this.newTotal = newTotal;
        }

        /**
         * @return {@link CostDto}
         */
        public CostDto build() {
            return new CostDto(this);
        }
    }

    /*
     * Constructor
     */
    private CostDto(final Builder b) {
        this.policyCosts = buildPolicyCostsList(b.policyCosts);
        this.premiumTax = b.premiumTax;
        this.currentSubTotal = b.currentSubTotal;
        this.currentTax = b.currentTax;
        this.currentTotal = b.currentTotal;
        this.newSubTotal = b.newSubTotal;
        this.newTax = b.newTax;
        this.newTotal = b.newTotal;
    }

    private List<PolicyCostDto> buildPolicyCostsList(final List<PolicyCostDto> thePolicyCosts) {
        if (thePolicyCosts != null && !thePolicyCosts.isEmpty()) {
            final List<PolicyCostDto> tempPolicyCosts = new ArrayList<PolicyCostDto>();
            for (final PolicyCostDto costDto : thePolicyCosts) {
                tempPolicyCosts.add(costDto);
            }
            return Collections.unmodifiableList(tempPolicyCosts);
        }
        return Collections.emptyList();
    }

    /**
     * @return the policyCosts
     */
    public List<PolicyCostDto> getPolicyCosts() {
        return this.policyCosts;
    }

    /**
     * @return the premiumTax
     */
    public BigDecimal getPremiumTax() {
        return this.premiumTax;
    }

    /**
     * @return the currentSubTotal
     */
    public BigDecimal getCurrentSubTotal() {
        return this.currentSubTotal;
    }

    /**
     * @return the currentTax
     */
    public BigDecimal getCurrentTax() {
        return this.currentTax;
    }

    /**
     * @return the currentTotal
     */
    public BigDecimal getCurrentTotal() {
        return this.currentTotal;
    }

    /**
     * @return the newSubTotal
     */
    public BigDecimal getNewSubTotal() {
        return this.newSubTotal;
    }

    /**
     * @return the newTax
     */
    public BigDecimal getNewTax() {
        return this.newTax;
    }

    /**
     * @return the newTotal
     */
    public BigDecimal getNewTotal() {
        return this.newTotal;
    }

}
