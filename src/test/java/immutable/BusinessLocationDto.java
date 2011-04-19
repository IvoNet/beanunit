package immutable;

/**
 * Immutable Data transfer object for business location.
 */
public final class BusinessLocationDto {
    private final AddressDto address;
    private final boolean inventory;
    private final boolean stock;
    private final boolean stagnation;

    /**
     * @param address    {@link AddressDto}
     * @param inventory  boolean
     * @param stagnation boolean
     * @param stock      boolean
     */
    public BusinessLocationDto(final AddressDto address, final boolean inventory, final boolean stagnation,
                               final boolean stock) {
        this.address = address;
        this.inventory = inventory;
        this.stagnation = stagnation;
        this.stock = stock;
    }

    public AddressDto getAddress() {
        return this.address;
    }

    /**
     * @return true if it is inventory else false
     */
    public boolean isInventory() {
        return this.inventory;
    }

    /**
     * @return true of stock else false
     */
    public boolean isStock() {
        return this.stock;
    }

    /**
     * @return tue if stagnation else false
     */
    public boolean isStagnation() {
        return this.stagnation;
    }
}
