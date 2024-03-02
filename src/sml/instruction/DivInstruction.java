package sml.instruction;

import sml.*;

import java.util.Objects;

public class DivInstruction extends Instruction {
    private final InstructionDestination result;
    public static final String OP_CODE = "div";

    public DivInstruction(String label, InstructionDestination result) {
        super(label, OP_CODE);
        this.result = result;
    }

    @Override
    public int getSize() {
        return 1 + result.getSize();
    }

    @Override
    public int execute(Machine m) {
        int y = result.getValue();
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
            return getLabelString()  + getOpcode() + " " + result;
        }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof DivInstruction other) {
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
