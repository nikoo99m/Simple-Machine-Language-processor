package sml.instruction;

import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class MovInstruction extends DualOperandInstruction {
    public static final String OP_CODE = "mov";

    public MovInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        int value = source.getValue();
        result.setValue(value);
        return getSize();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof MovInstruction;
    }
}
