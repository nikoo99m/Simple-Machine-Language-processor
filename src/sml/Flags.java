package sml;

// TODO: write a JavaDoc for the class and methods

/**
 *
 * @author ...
 */

public class Flags {
    private boolean zero, sign;

    public boolean getZF() {
        return zero;
    }

    public void setZF(boolean zero) {
        this.zero = zero;
    }

    public boolean getSF() {
        return sign;
    }

    public void setSF(boolean sign) {
        this.sign = sign;
    }

    //TODO: implement methods .toString, .equals and .hashCode
}
