package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;

import java.util.Objects;

public abstract class DualOperandInstruction extends Instruction {
    protected final InstructionDestination result;
    protected final InstructionSource source;

    public DualOperandInstruction(String label, String opcode, InstructionDestination result, InstructionSource source) {
        super(label, opcode);
        this.result = result;
        this.source = source;
    }

    @Override
    public int getSize() {
        return 1 + result.getSize() + source.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + result + ", " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof DualOperandInstruction other) {
            return this.source.equals(other.source)
                    && this.result.equals(other.result)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), result, source);
    }
}
