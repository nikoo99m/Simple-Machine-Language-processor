package sml.instruction;

import sml.IMachine;
import sml.InstructionDestination;
import sml.Registers;

public class MulInstruction extends SingleOperandInstruction {
    public static final String OP_CODE = "mul";

    public MulInstruction(String label, InstructionDestination result) {
        super(label, OP_CODE, result);
    }

    @Override
    public int execute(IMachine m) {
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
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof MulInstruction;
    }

}