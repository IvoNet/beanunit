package builder;

/**
 * DTO which holds informations about the MemberShips
 */
public final class MemberOfDto {
    private final Boolean isMemberOfAbuoOrInbbu;
    private final Boolean isMemberOfNVPB;
    private final Boolean isMemberOfVPB;

    private final String association;
    private final String associationCode;

    private final boolean memberOfAbuoOrInbbuFieldMandatory;
    private final boolean memberOfNVPBFieldMandatory;
    private final boolean memberOfVPBFieldMandatory;

    /**
     * @return the isMemberOfAbuoOrInbbu
     */
    public Boolean getIsMemberOfAbuoOrInbbu() {
        return this.isMemberOfAbuoOrInbbu;
    }

    /**
     * @return the isMemberOfNVPB
     */
    public Boolean getIsMemberOfNVPB() {
        return this.isMemberOfNVPB;
    }

    /**
     * @return the isMemberOfVPB
     */
    public Boolean getIsMemberOfVPB() {
        return this.isMemberOfVPB;
    }

    /**
     * @return the association
     */
    public String getAssociation() {
        return this.association;
    }

    private MemberOfDto(final Builder b) {
        this.isMemberOfAbuoOrInbbu = b.getIsMemberOfAbuoOrInbbu();
        this.isMemberOfNVPB = b.getIsMemberOfNVPB();
        this.isMemberOfVPB = b.getIsMemberOfVPB();
        this.association = b.getAssociation();
        this.memberOfAbuoOrInbbuFieldMandatory = b.isMemberOfAbuoOrInbbuFieldMandatory();
        this.memberOfNVPBFieldMandatory = b.isMemberOfNVPBFieldMandatory();
        this.memberOfVPBFieldMandatory = b.isMemberOfVPBFieldMandatory();
        this.associationCode = b.getAssociationCode();
    }

    /**
     * Builder for the MemberOfDto
     */
    public static final class Builder {

        private Boolean isMemberOfAbuoOrInbbu;
        private Boolean isMemberOfNVPB;
        private Boolean isMemberOfVPB;
        private String association;
        private boolean memberOfAbuoOrInbbuFieldMandatory;
        private boolean memberOfNVPBFieldMandatory;
        private boolean memberOfVPBFieldMandatory;
        private String associationCode;

        /**
         * @param associationCode the associationCode to set
         */
        public void setAssociationCode(final String associationCode) {
            this.associationCode = associationCode;
        }

        /**
         * @return the isMemberOfAbuoOrInbbu
         */
        public Boolean getIsMemberOfAbuoOrInbbu() {
            return this.isMemberOfAbuoOrInbbu;
        }

        /**
         * @return the code of the association
         */
        public String getAssociationCode() {
            return this.associationCode;
        }

        /**
         * @return the isMemberOfNVPB
         */
        public Boolean getIsMemberOfNVPB() {
            return this.isMemberOfNVPB;
        }

        /**
         * @return the isMemberOfVPB
         */
        public Boolean getIsMemberOfVPB() {
            return this.isMemberOfVPB;
        }

        /**
         * @return the association
         */
        public String getAssociation() {
            return this.association;
        }

        /**
         * @return the memberOfAbuoOrInbbuFieldMandatory
         */
        public boolean isMemberOfAbuoOrInbbuFieldMandatory() {
            return this.memberOfAbuoOrInbbuFieldMandatory;
        }

        /**
         * @return the memberOfNVPBFieldMandatory
         */
        public boolean isMemberOfNVPBFieldMandatory() {
            return this.memberOfNVPBFieldMandatory;
        }

        /**
         * @return the memberOfVPBFieldMandatory
         */
        public boolean isMemberOfVPBFieldMandatory() {
            return this.memberOfVPBFieldMandatory;
        }

        /**
         * @param memberOfAbuoOrInbbuFieldMandatory
         *         the memberOfAbuoOrInbbuFieldMandatory to set
         */
        public void setMemberOfAbuoOrInbbuFieldMandatory(final boolean memberOfAbuoOrInbbuFieldMandatory) {
            this.memberOfAbuoOrInbbuFieldMandatory = memberOfAbuoOrInbbuFieldMandatory;
        }

        /**
         * @param memberOfNVPBFieldMandatory the memberOfNVPBFieldMandatory to set
         */
        public void setMemberOfNVPBFieldMandatory(final boolean memberOfNVPBFieldMandatory) {
            this.memberOfNVPBFieldMandatory = memberOfNVPBFieldMandatory;
        }

        /**
         * @param memberOfVPBFieldMandatory the memberOfVPBFieldMandatory to set
         */
        public void setMemberOfVPBFieldMandatory(final boolean memberOfVPBFieldMandatory) {
            this.memberOfVPBFieldMandatory = memberOfVPBFieldMandatory;
        }

        /**
         * @param isMemberOfAbuoOrInbbu the isMemberOfAbuoOrInbbu to set
         */
        public void setIsMemberOfAbuoOrInbbu(final Boolean isMemberOfAbuoOrInbbu) {
            this.isMemberOfAbuoOrInbbu = isMemberOfAbuoOrInbbu;
        }

        /**
         * @param isMemberOfNVPB the isMemberOfNVPB to set
         */
        public void setIsMemberOfNVPB(final Boolean isMemberOfNVPB) {
            this.isMemberOfNVPB = isMemberOfNVPB;
        }

        /**
         * @param isMemberOfVPB the isMemberOfVPB to set
         */
        public void setIsMemberOfVPB(final Boolean isMemberOfVPB) {
            this.isMemberOfVPB = isMemberOfVPB;
        }

        /**
         * @param association the association to set
         */
        public void setAssociation(final String association) {
            this.association = association;
        }

        public MemberOfDto build() {
            return new MemberOfDto(this);
        }
    }

    /**
     * @return true if the field is mandatory, false otherwise
     */
    public boolean isMemberOfAbuoOrInbbuFieldMandatory() {
        return this.memberOfAbuoOrInbbuFieldMandatory;
    }

    /**
     * @return true if the field is mandatory, false otherwise
     */
    public boolean isMemberOfNVPBFieldMandatory() {
        return this.memberOfNVPBFieldMandatory;

    }

    /**
     * @return true if the field is mandatory, false otherwise
     */
    public boolean isMemberOfVPBFieldMandatory() {
        return this.memberOfVPBFieldMandatory;
    }

    /**
     * @return the code of the assiocation, if present
     */
    public String getAssociationCode() {
        return this.associationCode;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("MemberOfDto");
        sb.append("{isMemberOfAbuoOrInbbu=").append(isMemberOfAbuoOrInbbu);
        sb.append(", isMemberOfNVPB=").append(isMemberOfNVPB);
        sb.append(", isMemberOfVPB=").append(isMemberOfVPB);
        sb.append(", association='").append(association).append('\'');
        sb.append(", associationCode='").append(associationCode).append('\'');
        sb.append(", memberOfAbuoOrInbbuFieldMandatory=").append(memberOfAbuoOrInbbuFieldMandatory);
        sb.append(", memberOfNVPBFieldMandatory=").append(memberOfNVPBFieldMandatory);
        sb.append(", memberOfVPBFieldMandatory=").append(memberOfVPBFieldMandatory);
        sb.append('}');
        return sb.toString();
    }
}
