package immutable;

import java.util.Collections;
import java.util.Set;

/**
 * Immutable dto which holds information about a Question.
 */
public final class QuestionDto {

    private final String questionCode;
    private final Set<String> bikCodes;

    /**
     * @param questionCode the unique code of this question
     * @param bikCodes     the list of bikcodes for which this question is mandatory
     */
    public QuestionDto(final String questionCode, final Set<String> bikCodes) {
        this.questionCode = questionCode;
        if (bikCodes != null) {
            this.bikCodes = bikCodes;
        } else {
            this.bikCodes = Collections.emptySet();
        }
    }

    /**
     * @return the questionCode
     */
    public String getQuestionCode() {
        return this.questionCode;
    }

    /**
     * @return the bikCodes
     */
    public Set<String> getBikCodes() {
        return Collections.unmodifiableSet(this.bikCodes);
    }

    /**
     * @param bikCode the bikCode
     * @return true if the bikCode is in de collection of bikCodes from this question; otherwise false
     */
    public boolean hasBikCode(final String bikCode) {
        return this.bikCodes.contains(bikCode);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("QuestionDto with code [");
        sb.append(this.questionCode);
        sb.append("] and bikCodes size [");
        sb.append(this.bikCodes.size());
        sb.append("].");
        return sb.toString();
    }
}
