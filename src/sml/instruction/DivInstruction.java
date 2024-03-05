package sml.instruction;

import sml.*;

public class DivInstruction extends SingleOperandInstruction {

    public static final String OP_CODE = "div";

    public DivInstruction(String label, InstructionDestination result) {
        super(label, OP_CODE, result);

    }

    @Override
    public int execute(IMachine m) throws ArithmeticException {
        int y = result.getValue();
        Registers registers = m.getRegisters();
        int lower = registers.get(Registers.RegisterNameImpl.AX);
        int upper = registers.get(Registers.RegisterNameImpl.DX);

        if (y == 0) {
            throw new ArithmeticException("Division by zero error");
        }
        long combined = ((long) upper << 32) | (lower & 0xFFFFFFFFL);
        double divisionResult = (double) combined / y;

        registers.set(Registers.RegisterNameImpl.AX, (int) (divisionResult));
        registers.set(Registers.RegisterNameImpl.DX, (int) (combined % y));

        return getSize();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof DivInstruction;
    }
}
