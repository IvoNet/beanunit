package simplepojo;

/**
 * @author Ivo Woltring
 */
public class Address {
    private String street;
    private int number;
    private ZipCode zip;

    public String getStreet() {
        return street;
    }

    public void setStreet(final String street) {
        this.street = street;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(final int number) {
        this.number = number;
    }

    public ZipCode getZip() {
        return zip;
    }

    public void setZip(final ZipCode zip) {
        this.zip = zip;
    }

    //I do not override the equals method (should be asserted)

    @Override
    public int hashCode() {
        int result = street != null ? street.hashCode() : 0;
        result = 31 * result + number;
        result = 31 * result + (zip != null ? zip.hashCode() : 0);
        return result;
    }
}
