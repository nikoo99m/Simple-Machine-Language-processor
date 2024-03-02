package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

import java.util.Objects;


public class AddInstruction extends Instruction {

        private final InstructionDestination result;
        private final InstructionSource source;
        public static final String OP_CODE = "add";

    public AddInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE);
        this.result = result;
        this.source = source;
    }

    @Override
    public int getSize() {
        return 1 + result.getSize() + source.getSize();
    }

    @Override
    public int execute(Machine m) {
        int x = source.getValue();
        int y = result.getValue();
        result.setValue(x + y);
        return getSize();
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
        if (o instanceof AddInstruction other) {
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
