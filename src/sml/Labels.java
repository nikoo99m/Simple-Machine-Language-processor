package sml;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */
public final class Labels {
    private final Map<String, Integer> labels = new HashMap<>();

    /**
     * Adds a label with the associated address to the map.
     *
     * @param label the label
     * @param address the address the label refers to
     */
    public void addLabel(String label, int address) {
        Objects.requireNonNull(label);
        // TODO: Add a check that ensures that label duplicates are not simply ignored.
        //       What is the best way of handling duplicate labels?
        labels.put(label, address);
    }

    /**
     * Returns the address associated with the label.
     *
     * @param label the label
     * @return the address the label refers to
     */
    public int getAddress(String label) {
        // TODO: A NullPointerException can be thrown in the code line below
        //       even when the label is not null. Why can it happen?
        //       (Write an explanation.)
        //       What is the best way of dealing with non-existing labels?
        //       Add code to deal with them.
        return labels.get(label);
    }

    /**
     * representation of this instance,
     * in the form "[label -> address, label -> address, ..., label -> address]"
     *
     * @return the string representation of the labels map
     */
    @Override
    public String toString() {
        // TODO: Implement the method using the Stream API (see also class Registers).
        return "";
    }

    // TODO: Implement methods .equals and .hashCode (needed in class Machine).

    /**
     * Removes the labels
     */
    public void reset() {
        labels.clear();
    }
}
