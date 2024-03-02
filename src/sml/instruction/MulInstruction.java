package sml.instruction;

import sml.Instruction;
import sml.InstructionDestination;
import sml.Machine;
import sml.Registers;

public class MulInstruction extends Instruction {
    private final InstructionDestination result;
    public static final String OP_CODE = "mul";

    public MulInstruction(String label, InstructionDestination result) {
        super(label, OP_CODE);
        this.result = result;
    }

    @Override
    public int execute(Machine m) {
        int y = result.getValue();
        Registers registers = m.getRegisters();
        int ax = registers.get(Registers.RegisterNameImpl.AX);

        long mul = (long) ax * y;

        int lower32 = (int) mul;
        int upper32 = (int) (mul >>> 31);

        lower32 = lower32 & 0x7FFFFFFF;

        registers.set(Registers.RegisterNameImpl.AX, lower32);
       registers.set(Registers.RegisterNameImpl.DX, upper32);

        return getSize();
    }

    @Override
    public int getSize() {
        return 1 + result.getSize();
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
        if (o instanceof MulInstruction other) {
            return this.result.equals(other.result)
                    && super.equals(other);
        }
        return false;
    }

    @Override
    public int hashCode() {
        int result = this.result.hashCode();
        result = 31 * result;
        return result;
    }
}