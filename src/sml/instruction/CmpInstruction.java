package sml.instruction;

import sml.*;

public class CmpInstruction extends DualOperandInstruction {

    public static final String OP_CODE = "cmp";

    public CmpInstruction(String label, InstructionDestination result, InstructionSource source) {
        super(label, OP_CODE, result, source);
    }

    @Override
    public int execute(Machine m) {
        Flags flag = m.getFlags();
        int i = source.getValue();
        int j = result.getValue();

        if (i == j)
            flag.setZF(true);
        else
            flag.setZF(false);

        flag.setSF(j < i);

        return getSize();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof CmpInstruction;
    }
}

