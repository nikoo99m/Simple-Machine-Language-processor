package sml.instruction;


import sml.IMachine;
import sml.InstructionDestination;
import sml.InstructionSource;

public class SubInstruction extends DualOperandInstruction {
    public static final String OP_CODE = "sub";

    public SubInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(IMachine m) {
        int x = result.getValue();
        int y = source.getValue();
        result.setValue(x - y);
        return getSize();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof SubInstruction;
    }
}
