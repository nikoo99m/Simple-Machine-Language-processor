package sml;

// TODO: write a JavaDoc for the class and methods

import sml.instruction.JleInstruction;

import java.util.Objects;

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
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Flags other) {
            return (this.sign == other.sign)
                    && (this.zero == other.zero);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(zero, sign);
    }
}
