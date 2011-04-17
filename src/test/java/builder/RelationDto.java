package builder;

import java.math.BigDecimal;

/**
 * Dto which holds information about a relation.
 */
public final class RelationDto {

    private final String industrialClassificationChambersOfCommerceDescription;
    private final String name;
    private final String contactName;
    private final BigDecimal studentCount;
    private final String technicalServiceStation;
    private final BigDecimal employeeCount;
    private final BigDecimal operatingCosts;
    private final BigDecimal turnover;
    private final BigDecimal memberCount;

    /**
     * Builder for a {@link RelationDto}
     */
    public static final class Builder {

        private String industrialClassificationChambersOfCommerceDescription;
        private String name;
        private String contactName;
        private BigDecimal studentCount;
        private String technicalServiceStation;
        private BigDecimal employeeCount;
        private BigDecimal operatingCosts;
        private BigDecimal turnover;
        private BigDecimal memberCount;

        /**
         * Build a RelationDto with the current data
         *
         * @return {@link RelationDto}
         */
        public RelationDto build() {
            return new RelationDto(this);
        }

        /**
         * @param industrialClassificationChambersOfCommerceDescription
         *         the
         *         industrialClassificationChambersOfCommerceDescription to set
         */
        public void setIndustrialClassificationChambersOfCommerceDescription(final String industrialClassificationChambersOfCommerceDescription) {
            this.industrialClassificationChambersOfCommerceDescription = industrialClassificationChambersOfCommerceDescription;
        }

        /**
         * @param name the name to set
         */
        public void setName(final String name) {
            this.name = name;
        }

        /**
         * @param contactName the contactName to set
         */
        public void setContactName(final String contactName) {
            this.contactName = contactName;
        }

        /**
         * @param studentCount the studentCount to set
         */
        public void setStudentCount(final BigDecimal studentCount) {
            this.studentCount = studentCount;
        }

        /**
         * @param technicalServiceStation the technicalServiceStation to set
         */
        public void setTechnicalServiceStation(final String technicalServiceStation) {
            this.technicalServiceStation = technicalServiceStation;
        }

        /**
         * @param employeeCount the employeeCount to set
         */
        public void setEmployeeCount(final BigDecimal employeeCount) {
            this.employeeCount = employeeCount;
        }

        /**
         * @param operatingCosts the operatingCosts to set
         */
        public void setOperatingCosts(final BigDecimal operatingCosts) {
            this.operatingCosts = operatingCosts;
        }

        /**
         * @param turnover the turnover to set
         */
        public void setTurnover(final BigDecimal turnover) {
            this.turnover = turnover;
        }

        /**
         * @param bigDecimal {@link BigDecimal}
         */
        public void setMemberCount(final BigDecimal bigDecimal) {
            this.memberCount = bigDecimal;
        }
    }

    private RelationDto(final Builder b) {
        this.contactName = b.contactName;
        this.employeeCount = b.employeeCount;
        this.industrialClassificationChambersOfCommerceDescription = b.industrialClassificationChambersOfCommerceDescription;
        this.name = b.name;
        this.operatingCosts = b.operatingCosts;
        this.studentCount = b.studentCount;
        this.technicalServiceStation = b.technicalServiceStation;
        this.turnover = b.turnover;
        this.memberCount = b.memberCount;
    }

    /**
     * @return the industrialClassificationChambersOfCommerceDescription
     */
    public String getIndustrialClassificationChambersOfCommerceDescription() {
        return this.industrialClassificationChambersOfCommerceDescription;
    }

    /**
     * @return the name
     */
    public String getName() {
        return this.name;
    }

    /**
     * @return the contact person
     */
    public String getContactName() {
        return this.contactName;
    }

    /**
     * @return {@link BigDecimal}
     */
    public BigDecimal getMemberCount() {
        return this.memberCount;
    }

    /**
     * @return {@link BigDecimal}
     */
    public BigDecimal getStudentCount() {
        return this.studentCount;
    }

    /**
     * @return the technical service station
     */
    public String getTechnicalServiceStation() {
        return this.technicalServiceStation;
    }

    /**
     * @return {@link BigDecimal}
     */
    public BigDecimal getEmployeeCount() {
        return this.employeeCount;
    }

    /**
     * @return {@link BigDecimal}
     */
    public BigDecimal getOperatingCosts() {
        return this.operatingCosts;
    }

    /**
     * @return {@link BigDecimal}
     */
    public BigDecimal getTurnover() {
        return this.turnover;
    }

}
