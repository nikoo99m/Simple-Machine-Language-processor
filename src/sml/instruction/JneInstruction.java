package sml.instruction;

import sml.Flags;
import sml.Instruction;
import sml.Machine;

public class JneInstruction extends Instruction {
    private final Flags FLAG;
    public static final String OP_CODE = "jne";

    public JneInstruction(String label, String opcode, Flags flag) {
        super(label, opcode);
        FLAG = flag;
    }

    @Override
    public int getSize() {
        return 2;
    }

    @Override
    public int execute(Machine m) {
        if (!FLAG.getZF())
            return m.getOffset(label);
        return getSize();
    }

    @Override
    public String toString() {
        return getOpcode();
    }

    @Override
    public boolean equals(Object o) {
        return false;
    }

    @Override
    public int hashCode() {
        return 0;
    }
}
