package immutable;

/**
 * Representation of an Option from a Question
 */
public final class OptionDto {
    private final String code;
    private final String description;

    /**
     * @param code        the unique code of the Question
     * @param description the description of the value
     */
    public OptionDto(final String code, final String description) {
        this.code = code;
        this.description = description;
    }

    /**
     * @return the code
     */
    public String getCode() {
        return this.code;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return this.description;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OptionDto with code [");
        sb.append(this.code);
        sb.append("] and description [");
        sb.append(this.description);
        sb.append("].");
        return sb.toString();
    }
}
