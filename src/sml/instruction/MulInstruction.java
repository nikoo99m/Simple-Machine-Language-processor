package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.Machine;
import sml.Registers;

public class MulInstruction extends Instruction {
    private final InstructionDestination destination;
    public static final String OP_CODE = "mul";

    public MulInstruction(String label, InstructionDestination destination) {
        super(label, OP_CODE);
        this.destination = destination;
    }

    @Override
    public int execute(Machine m) {
        int y = destination.getValue();

        Registers registers = m.getRegisters();
        int x = registers.get(Registers.RegisterNameImpl.AX);
        registers.set(Registers.RegisterNameImpl.AX, x * y);
        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + destination.getSize();
    }

    @Override
    public String toString() {
        return getLabelString()  + getOpcode() + " " + destination ;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MulInstruction that = (MulInstruction) o;
        return destination.equals(that.destination);
    }

    @Override
    public int hashCode() {
        int result = destination.hashCode();
        result = 31 * result;
        return result;
    }
}