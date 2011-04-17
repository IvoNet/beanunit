package builder;

import java.math.BigDecimal;

/**
 * Dto which holds information of a disability policy for one insured party.
 */
public final class DisabilityDto {

    private final String personName;
    private final String mainOccupation;
    private final String secondOccupation;
    private final BigDecimal insuredAmmount;
    private final String initials;
    private final String infix;

    private DisabilityDto(final Builder b) {
        this.personName = b.personName;
        this.mainOccupation = b.mainOccupation;
        this.secondOccupation = b.secondOccupation;
        this.insuredAmmount = b.insuredAmmount;
        this.initials = b.initials;
        this.infix = b.infix;

    }

    /**
     * @return the insuredAmmount
     */
    public BigDecimal getInsuredAmmount() {
        return this.insuredAmmount;
    }

    /**
     * @return the persone name
     */
    public String getPersonName() {
        return this.personName;
    }

    /**
     * @return the main occupation
     */
    public String getMainOccupation() {
        return this.mainOccupation;
    }

    /**
     * @return the second occupation
     */
    public String getSecondOccupation() {
        return this.secondOccupation;
    }

    /**
     * @return the initials
     */
    public String getInitials() {
        return this.initials;
    }

    /**
     * @return the infix
     */
    public String getInfix() {
        return this.infix;
    }

    /**
     * @return true if this person has an infix, false otherwise
     */
    public boolean hasInfix() {
        return hasText(this.infix);
    }

    private boolean hasText(final String text) {
        return text != null && text.length() > 0;
    }

    /**
     * @return true if this person has an person name, false otherwise
     */
    public boolean hasPersonName() {
        return hasText(this.personName);
    }

    /**
     * @return true if this person has initials, false otherwise
     */
    public boolean hasInitials() {
        return hasText(this.initials);
    }

    /**
     * Builds a {@link DisabilityDto}
     */
    public static final class Builder {

        private String infix;
        private String initials;
        private BigDecimal insuredAmmount;
        private String secondOccupation;
        private String mainOccupation;
        private String personName;

        /**
         * @param infix the infix to set
         */
        public void setInfix(final String infix) {
            this.infix = infix;
        }

        /**
         * @param initials the initials to set
         */
        public void setInitials(final String initials) {
            this.initials = initials;
        }

        /**
         * @param insuredAmmount the insuredAmmount to set
         */
        public void setInsuredAmmount(final BigDecimal insuredAmmount) {
            this.insuredAmmount = insuredAmmount;
        }

        /**
         * @param secondOccupation the secondOccupation to set
         */
        public void setSecondOccupation(final String secondOccupation) {
            this.secondOccupation = secondOccupation;
        }

        /**
         * @param mainOccupation the mainOccupation to set
         */
        public void setMainOccupation(final String mainOccupation) {
            this.mainOccupation = mainOccupation;
        }

        /**
         * @param personName the personName to set
         */
        public void setPersonName(final String personName) {
            this.personName = personName;
        }

        /**
         * @return {@link DisabilityDto}
         */
        public DisabilityDto build() {
            return new DisabilityDto(this);
        }
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("DisabilityDto");
        sb.append("{personName='").append(personName).append('\'');
        sb.append(", mainOccupation='").append(mainOccupation).append('\'');
        sb.append(", secondOccupation='").append(secondOccupation).append('\'');
        sb.append(", insuredAmmount=").append(insuredAmmount);
        sb.append(", initials='").append(initials).append('\'');
        sb.append(", infix='").append(infix).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
