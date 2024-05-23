package sml;

import java.util.*;
import java.util.stream.Collectors;

// TODO: write a JavaDoc for the class // DONE

/**
 * Represents a map of labels and their associated addresses.
 *
 * @author nikoo99m
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
        //       What is the best way of handling duplicate labels? // DONE
        // answer: The best way to handle duplicate attempts its to raise an appropriate exception such that the user is informed of the improper operation.
        if (labels.containsKey(label)) {
            throw new IllegalStateException("Attempt to add a duplicate label in labels: " + label);
        }
        labels.put(label, address);
    }

    /**
     * Returns the address associated with the label.
     *
     * @param label the label
     * @return the address the label refers to
     */
    public Integer getAddress(String label) {
        //
        // answer: If the `get()` method of a `HashMap<X, Integer>` returns `null` because
        //         no value exists for the given key, casting this `null` to `Integer`
        //         is still valid however, if any operations are then applied to the null value this
        //         can result in a `NullPointerException`. For example the caller of Labels.getAddress() i.e.,
        //         Machine.getOffset() attempts a subtraction which can cause the aforementioned exception:
        //         return labels.getAddress(label) - programCounter.
        //
        // answer: Using the containsKey check, the existence of the key can be verified prior to accessing it.
        //

        if (label == null)
            throw new NullPointerException("Label cannot be null");

        if (!labels.containsKey(label))
            throw new NoSuchElementException("Label '" + label + "' not found");

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

        return labels.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .map(e -> e.getKey() + " = " + e.getValue())
                .collect(Collectors.joining(", ", "[", "]")) ;
    }



    /**
     * Indicates whether some other object is "equal to" this one.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Labels other) {
            return (this.labels.equals(other.labels));

        }
        return false;
    }

    /**
     * Returns a hash code value for the object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(labels);
    }

    /**
     * Removes the labels
     */
    public void reset() {
        labels.clear();
    }
}
