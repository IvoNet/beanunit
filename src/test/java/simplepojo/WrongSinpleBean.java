package simplepojo;

/**
 * @author Ivo Woltring
 */
public class WrongSinpleBean extends SimpleBean {

    String wrong;

    public String getWrong() {
        return wrong + "wrong";
    }

    public void setWrong(final String wrong) {
        this.wrong = wrong;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) { return true; }
        if (o == null || getClass() != o.getClass()) { return false; }
        if (!super.equals(o)) { return false; }

        final WrongSinpleBean that = (WrongSinpleBean) o;

        if (wrong != null ? !wrong.equals(that.getWrong()) : that.wrong != null) { return false; }

        return true;
    }

    @Override
    public int hashCode() {
        int result = super.hashCode();
        result = 31 * result + (wrong != null ? wrong.hashCode() : 0);
        return result;
    }
}
