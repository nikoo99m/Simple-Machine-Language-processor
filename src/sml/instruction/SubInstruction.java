package sml.instruction;


import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class SubInstruction extends Instruction {
    private final InstructionDestination result;
    private final InstructionSource source;
    public static final String OP_CODE = "sub";

    public SubInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int x = result.getValue();
        int y = source.getValue();
        result.setValue(x - y);
        return getSize();
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
        if (o instanceof SubInstruction other) {
            return this.source.equals(other.source)
                    && this.result.equals(other.result)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = this.result.hashCode();
        result = 31 * result + source.hashCode();
        return result;
    }
}
