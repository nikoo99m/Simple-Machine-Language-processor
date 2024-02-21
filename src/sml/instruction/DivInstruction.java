package sml.instruction;

import sml.*;

public class DivInstruction extends Instruction {
    private final InstructionDestination destination;
    public static final String OP_CODE = "div";

    public DivInstruction(String label, InstructionDestination destination) {
        super(label, OP_CODE);
        this.destination = destination;
    }

    @Override
    public int getSize() {
        return 1 + destination.getSize();
    }

    @Override
    public int execute(Machine m) {
        int y = destination.getValue();
        Registers registers = m.getRegisters();
        int lower = registers.get(Registers.RegisterNameImpl.AX);
        int upper = registers.get(Registers.RegisterNameImpl.DX);
        long combined = ((long) upper << 32) | (lower & 0xFFFFFFFFL);
        registers.set(Registers.RegisterNameImpl.AX, (int) (combined / y));
        registers.set(Registers.RegisterNameImpl.DX, (int) (combined % y));

        return getSize();
    }

    @Override
    public String toString() {
            return getLabelString()  + getOpcode() + " " + destination ;
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
