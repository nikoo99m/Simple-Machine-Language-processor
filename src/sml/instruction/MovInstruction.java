package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class MovInstruction extends Instruction {
    private final InstructionDestination result;
    private final InstructionSource source;

    public static final String OP_CODE = "mov";

    public MovInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        result.setValue(value);
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + source.getSize() + result.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + ", " + source;
    }
}
