package sml;

import java.util.Optional;
import java.util.regex.Pattern;

public class OperandMemory implements InstructionSource, InstructionDestination {
    private final int address;
    private final Memory memory;


    private static final Pattern pattern = Pattern.compile("^[\\d+]$");

    public static Optional<OperandMemory> parseOperandMemory(String s, Memory memory) {
        if (pattern.matcher(s).find())
            return Optional.of(new OperandMemory(Integer.parseInt(s.substring(1, s.length() - 1)), memory));

        return Optional.empty();
    }

    public OperandMemory(int address, Memory memory) {
        this.address = address;
        this.memory = memory;
    }

    @Override
    public int getValue() {
        return memory.get(address);
    }

    @Override
    public void setValue(int value) {
        memory.set(address, value);
    }

    @Override
    public int getSize() {
        return 1;
    }

    @Override
    public String toString() {
        return "[" + address + "]";
    }

    //TODO: implement methods .equals and .hashCode
}
