package immutable;

/**
 * Holds information about the member of field
 */
public final class UpdateMemberOfDto {

    private static final short TWENTY_THREE = 23;
    private static final short NINETEEN = 19;
    private static final short SEVENTEEN = 17;
    private final Boolean isMemberOfAbuoOrInbbu;
    private final Boolean isMemberOfNVPB;
    private final Boolean isMemberOfVPB;

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

    public UpdateMemberOfDto(final Boolean isMemberOfAbuoOrInbbu, final Boolean isMemberOfNVPB,
                             final Boolean isMemberOfVPB) {
        this.isMemberOfAbuoOrInbbu = isMemberOfAbuoOrInbbu;
        this.isMemberOfNVPB = isMemberOfNVPB;
        this.isMemberOfVPB = isMemberOfVPB;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (!(o instanceof UpdateMemberOfDto)) { return false; }

        final UpdateMemberOfDto that = (UpdateMemberOfDto) o;

        if (isMemberOfAbuoOrInbbu != null
            ? !isMemberOfAbuoOrInbbu.equals(that.isMemberOfAbuoOrInbbu)
            : that.isMemberOfAbuoOrInbbu != null) { return false; }
        if (isMemberOfNVPB != null ? !isMemberOfNVPB.equals(that.isMemberOfNVPB) : that.isMemberOfNVPB != null) {
            return false;
        }
        if (isMemberOfVPB != null ? !isMemberOfVPB.equals(that.isMemberOfVPB) : that.isMemberOfVPB != null) {
            return false;
        }

        return true;
    }

    @Override
    public int hashCode() {
        int result = isMemberOfAbuoOrInbbu != null ? isMemberOfAbuoOrInbbu.hashCode() : 0;
        result = 31 * result + (isMemberOfNVPB != null ? isMemberOfNVPB.hashCode() : 0);
        result = 31 * result + (isMemberOfVPB != null ? isMemberOfVPB.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("UpdateMemberOfDto");
        sb.append("{isMemberOfAbuoOrInbbu=").append(isMemberOfAbuoOrInbbu);
        sb.append(", isMemberOfNVPB=").append(isMemberOfNVPB);
        sb.append(", isMemberOfVPB=").append(isMemberOfVPB);
        sb.append('}');
        return sb.toString();
    }
}
