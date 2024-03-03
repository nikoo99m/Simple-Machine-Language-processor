package sml;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: write a JavaDoc for the class // DONE

/**
 * Represents the memory of machine.
 * it contains behaviours to access and modify the contents of memory at particular addresses.
 *
 *  <p>
 *  An instance contains
 *  contents
 *  usedCells
 * @author nikoo99m
 */

public class Memory {
    private final int[] contents;
    private final BitSet usedCells = new BitSet();
    /**
     * Constructs a new Memory object with the specified size.
     *
     * @param size the size of memory.
     */
    public Memory(int size) {
        this.contents = new int[size];
    }
    /**
     * fetches the value that is stored at a particular memory address.
     *
     * @param address the memory address to access.
     * @return the value stored at the specified address.
     */
    public int get(int address) {
        usedCells.set(address);
        return contents[address];
    }
    /**
     * Sets the value at the specified memory address.
     *
     * @param address the memory address to set.
     * @param value   the value to store at the specified address.
     */
    public void set(int address, int value) {
        usedCells.set(address);
        contents[address] = value;
    }
    /**
     * Represents the string representation of the memory.
     *
     * @return a string containing a summary of memory usage.
     */
    @Override
    public String toString() {
        return usedCells.stream()
                .mapToObj(i -> "[" + i + "] = " + contents[i])
                .collect(Collectors.joining("\n"));
    }

    //TODO: implement methods .equals and .hashCode // DONE
    /**
     * Checks whether two objects of memory are equal or not.
     * by checking the equality of memory values and addresses.
     *
     * @param o the reference object with which to compare.
     * @return true if this object is the same as the o argument; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof Memory other) {
            return IntStream.range(0, this.contents.length)
                    .allMatch(i -> this.contents[i] == other.contents[i])
                    && this.usedCells.equals(other.usedCells);
        }
        return false;
    }
    /**
     * Generates hash code value for Memory object.
     *
     * @return a hash code value for this object.
     */
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(contents),usedCells);
    }
}
