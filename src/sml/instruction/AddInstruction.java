package sml.instruction;

import sml.IMachine;
import sml.InstructionDestination;
import sml.InstructionSource;


public class AddInstruction extends DualOperandInstruction {

    public static final String OP_CODE = "add";

    public AddInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(IMachine m) {
        int x = source.getValue();
        int y = result.getValue();
        result.setValue(x + y);
        return getSize();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof AddInstruction;
    }
}
