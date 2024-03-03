package sml;

// TODO: write a JavaDoc for the class and methods // DONE

import sml.instruction.JleInstruction;

import java.util.Objects;

/**
 * Represents flags (zero and sign) in machine which are used for comparison operations.
 *
 * @author nikoo99m
 */

public class Flags implements IFlags {
    private boolean zero, sign;

    /**
     * Gets the value of the zero flag.
     *
     * @return true if the zero flag is set, false otherwise.
     */
    @Override
    public boolean getZF() {
        return zero;
    }

    /**
     * Sets the value of the zero flag.
     *
     * @param zero true to set the sign flag, false to clear it.
     */
    public void setZF(boolean zero) {
        this.zero = zero;
    }

    /**
     * Gets the value of the sign flag.
     *
     * @return true if the sign flag is set, false otherwise.
     */
    @Override
    public boolean getSF() {
        return sign;
    }

    /**
     * Sets the value of the sign flag.
     *
     * @param sign true to set the sign flag, false to clear it.
     */
    public void setSF(boolean sign) {
        this.sign = sign;
    }

    //TODO: implement methods .toString, .equals and .hashCode // DONE

    /**
     * Checks whether this object is equal to another object.
     *
     * @param o the object to compare to.
     * @return true if the objects are equal, false otherwise.
     */
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

    /**
     * Generates a hash code value for this object.
     *
     * @return a hash code value based on the contents of this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(zero, sign);
    }

    /**
     * Returns a string representation of the Flags object.
     *
     * @return a string containing the values of the zero and sign flags
     */
    @Override
    public String toString() {
        return "Flags{" +
                "zero=" + zero +
                ", sign=" + sign +
                '}';
    }
}
