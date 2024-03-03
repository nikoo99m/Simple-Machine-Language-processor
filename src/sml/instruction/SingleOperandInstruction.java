package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;

import java.util.Objects;

public abstract class SingleOperandInstruction extends Instruction {
    protected final InstructionDestination result;

    public SingleOperandInstruction(String label, String opcode, InstructionDestination result) {
        super(label, opcode);
        this.result = result;
    }

    @Override
    public int getSize() {
        return 1 + result.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof SingleOperandInstruction other) {
            return this.result.equals(other.result)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), result);
    }
}
