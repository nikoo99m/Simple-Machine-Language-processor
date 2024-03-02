package sml;

import java.util.Arrays;
import java.util.BitSet;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: write a JavaDoc for the class

/**
 *
 * @author ...
 */

public class Memory {
    private final int[] contents;
    private final BitSet usedCells = new BitSet();

    public Memory(int size) {
        this.contents = new int[size];
    }

    public int get(int address) {
        usedCells.set(address);
        return contents[address];
    }

    public void set(int address, int value) {
        usedCells.set(address);
        contents[address] = value;
    }

    @Override
    public String toString() {
        return usedCells.stream()
                .mapToObj(i -> "[" + i + "] = " + contents[i])
                .collect(Collectors.joining("\n"));
    }

    //TODO: implement methods .equals and .hashCode
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
    @Override
    public int hashCode() {
        return Objects.hash(Arrays.hashCode(contents),usedCells);
    }
}
