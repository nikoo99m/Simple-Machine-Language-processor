package sml.instruction;


import sml.Instruction;
import sml.InstructionDestination;
import sml.InstructionSource;
import sml.Machine;

public class SubInstruction extends Instruction {
    private final InstructionDestination destination;
    private final InstructionSource source;
    public static final String OP_CODE = "sub";

    public SubInstruction(String label, InstructionDestination destination, InstructionSource source) {
        super(label, OP_CODE);
        this.destination = destination;
        this.source = source;
    }

    @Override
    public int execute(Machine m) {
        int x = destination.getValue();
        int y = source.getValue();
        destination.setValue(x - y);
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + destination.getSize() + source.getSize();
    }

    @Override
    public String toString() {
        return getLabelString() + getOpcode() + " " + destination + ", " + source;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SubInstruction that = (SubInstruction) o;
        return destination.equals(that.destination) && source.equals(that.source);
    }

    @Override
    public int hashCode() {
        int result = destination.hashCode();
        result = 31 * result + source.hashCode();
        return result;
    }
}
