package builder;

/**
 * Dto which holds information about the questions for flap 1.
 */
public final class OrganizationDataMandatoryQuestionsDto {
    private final boolean numberEmployees;
    private final boolean revenue;
    private final boolean numberMembers;
    private final boolean operatingCostsVandS;
    private final boolean operatingCostsVve;
    private final boolean operatingCostsOther;
    private final boolean numberStudents;
    private final boolean technicalServiceStation;

    private OrganizationDataMandatoryQuestionsDto(final Builder b) {
        this.numberEmployees = Boolean.TRUE.equals(b.numberEmployees);
        this.revenue = b.revenue;
        this.operatingCostsVandS = b.operatingCostsVandS;
        this.operatingCostsOther = b.operatingCostsOther;
        this.operatingCostsVve = b.operatingCostsVve;
        this.numberMembers = b.numberMembers;
        this.numberStudents = b.numberStudents;
        this.technicalServiceStation = b.technicalServiceStation;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isTechnicalServiceStation() {
        return this.technicalServiceStation;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isNumberEmployees() {
        return this.numberEmployees;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isRevenue() {
        return this.revenue;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isNumberMembers() {
        return this.numberMembers;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isOperatingCostsVandS() {
        return this.operatingCostsVandS;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isOperatingCostsVve() {
        return this.operatingCostsVve;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isOperatingCostsOther() {
        return this.operatingCostsOther;
    }

    /**
     * @return true if mandatory, false otherwise
     */
    public boolean isNumberStudents() {
        return this.numberStudents;
    }

    /**
     * Builder for {@link OrganizationDataMandatoryQuestionsDto}
     */
    public static final class Builder {
        private boolean numberEmployees;
        private boolean revenue;
        private boolean numberMembers;
        private boolean operatingCostsVandS;
        private boolean operatingCostsVve;
        private boolean operatingCostsOther;
        private boolean numberStudents;
        private boolean technicalServiceStation;

        /**
         * @param technicalServiceStation whether or not the field is mandatory
         */
        public void setTechnicalServiceStation(final boolean technicalServiceStation) {
            this.technicalServiceStation = technicalServiceStation;
        }

        /**
         * @param numberEmployees whether or not the field is mandatory
         */
        public void setNumberEmployees(final boolean numberEmployees) {
            this.numberEmployees = numberEmployees;
        }

        /**
         * @param revenue whether or not the field is mandatory
         */
        public void setRevenue(final boolean revenue) {
            this.revenue = revenue;
        }

        /**
         * @param numberMembers whether or not the field is mandatory
         */
        public void setNumberMembers(final boolean numberMembers) {
            this.numberMembers = numberMembers;
        }

        /**
         * @param operatingCostsVandS whether or not the field is mandatory
         */
        public void setOperatingCostsVandS(final boolean operatingCostsVandS) {
            this.operatingCostsVandS = operatingCostsVandS;
        }

        /**
         * @param operatingCostsVve whether or not the field is mandatory
         */
        public void setOperatingCostsVve(final boolean operatingCostsVve) {
            this.operatingCostsVve = operatingCostsVve;
        }

        /**
         * @param operatingCostsOther whether or not the field is mandatory
         */
        public void setOperatingCostsOther(final boolean operatingCostsOther) {
            this.operatingCostsOther = operatingCostsOther;
        }

        /**
         * @param numberStudents whether or not the field is mandatory
         */
        public void setNumberStudents(final boolean numberStudents) {
            this.numberStudents = numberStudents;
        }

        /**
         * @return {@link OrganizationDataMandatoryQuestionsDto}
         */
        public OrganizationDataMandatoryQuestionsDto build() {
            return new OrganizationDataMandatoryQuestionsDto(this);
        }
    }
}
