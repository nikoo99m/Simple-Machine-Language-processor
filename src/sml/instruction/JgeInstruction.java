package sml.instruction;

import sml.Flags;
import sml.Machine;

public class JgeInstruction extends JumpInstruction {
    public static final String OP_CODE = "jge";

    public JgeInstruction(String label, String goToLabel) {
        super(label, OP_CODE, goToLabel);
    }

    @Override
    public int execute(Machine m) {
        Flags flag = m.getFlags();
        if (!flag.getSF() || flag.getZF()){
         return m.getOffset(GOTOLABEL);
    }
        return getSize();
    }
    @Override
    public boolean equals(Object o) {
        return super.equals(o) && o instanceof JgeInstruction;
    }

}
