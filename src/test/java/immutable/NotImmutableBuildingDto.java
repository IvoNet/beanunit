package immutable;

import java.math.BigDecimal;

import builder.AddressDto;

/**
 * Holds information about a building. Immutable
 */
public final class NotImmutableBuildingDto {

    private AddressDto address;
    private final BigDecimal insuredAmount;
    private final String industrialClassificationChambersOfCommerceDescription;
    private final Boolean owner;
    private final Boolean rented;

    public NotImmutableBuildingDto(final AddressDto address, final BigDecimal insuredAmount,
                                   final String industrialClassificationChambersOfCommerceDescription,
                                   final Boolean owner, final Boolean rented) {
        this.address = address;
        this.insuredAmount = insuredAmount;
        this.industrialClassificationChambersOfCommerceDescription = industrialClassificationChambersOfCommerceDescription;
        this.owner = owner;
        this.rented = rented;
    }

    /**
     * @return {@link builder.AddressDto}
     */
    public AddressDto getAddress() {
        return this.address;
    }

    /**
     * @return {@link java.math.BigDecimal}
     */
    public BigDecimal getInsuredAmount() {
        return this.insuredAmount;
    }

    //Makes it not immutable
    public void setAddress(final AddressDto address) {
        this.address = address;
    }

    /**
     * @return the description of the industrial classification at the chamber of commerce
     */
    public String getIndustrialClassificationChambersOfCommerceDescription() {
        return this.industrialClassificationChambersOfCommerceDescription;
    }

    /**
     * @return {@link java.math.BigDecimal} whether or not the {@link javax.management.relation.Relation} is the owner
     */
    public Boolean getOwner() {
        return this.owner;
    }

    /**
     * @return {@link java.math.BigDecimal} whether or not the building is rented
     */
    public Boolean getRented() {
        return this.rented;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder();
        sb.append("NotImmutableBuildingDto");
        sb.append("{address=").append(address);
        sb.append(", insuredAmount=").append(insuredAmount);
        sb.append(", industrialClassificationChambersOfCommerceDescription='")
                .append(industrialClassificationChambersOfCommerceDescription).append('\'');
        sb.append(", owner=").append(owner);
        sb.append(", rented=").append(rented);
        sb.append('}');
        return sb.toString();
    }
}
